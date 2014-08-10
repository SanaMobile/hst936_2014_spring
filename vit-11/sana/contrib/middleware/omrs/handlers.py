'''

@author: Sana Dev Team
Created on Apr 13, 2011
'''
from __future__ import with_statement

import re, cjson, urllib
from xml.dom import minidom
try:
    import json
except ImportError:
    import simplejson as json

from django.conf import settings

from piston.handler import BaseHandler
from piston import resource

from sana.api import APIResponse, FAIL, SUCCESS
from sana.api.dispatch.dispatchlib import dispatch, dispatch_reverse
from sana.api.util import debug_api_request, debug_api_response

from sana.api.middleware.http.handlers import HTTPOpener
from sana.contrib.middleware.omrs.forms import ClientForm, EncounterForm, SubjectForm, StatusForm
from sana.api.fields import Status, Dispatchable

__all__ = ('ClientHandler', 'EncounterHandler', 'NotificationHandler', 
           'PatientHandler', 'ProcedureHandler')

DEFAULT_PT_ID = '500'
_namespace = 'sana.contrib.middleware.omrs'
_login_re = re.compile('Currently logged in as')

class OpenMRSOpener(HTTPOpener):
    
    def has_rest_auth(self,request, path, *pargs, **kwargs):
        ''' Handles the rest authorization. Should only be called within a CRUD 
            method with request having a "form" attribute '''
        u = request.dispatch_form.args.get('username', None)
        pw = request.dispatch_form.args.get('password', None)
        auth = { "username": u, "password" : pw } if u and pw else None
        result = False
        
        # Have to be logged in and then post username and password to REST url
        client_form = ClientForm(initial=auth)
        client_path = dispatch_reverse(_namespace, Dispatchable.CLIENT, method='create')
        login = _opener.open(path=client_path,data=client_form.dispatch_data)

        #TODO From older code.  Why do we need this???
        status_path = dispatch_reverse(_namespace, 'status', method='create')
        rest = _opener.open(path=status_path,data=auth)
        
        if login.status == Status.SUCCESS:
            r = _opener.open(path, auth=auth)
            result = True if r.status == Status.SUCCESS else False
        return result

_opener = OpenMRSOpener(host=settings.OPENMRS_SERVER_HOST, 
                     port=settings.OPENMRS_SERVER_PORT,
                     path=settings.OPENMRS_PATH,)

class ClientHandler(BaseHandler):
    ''' Checks permissions against Sana module '''
    allowed_methods = ('POST',)
    v_form = ClientForm
    
    @dispatch('POST') 
    def create(self, request, *pargs, **kwargs):
        path = dispatch_reverse(_namespace, Dispatchable.CLIENT, method='create')
        # execute upstream request
        result = _opener.open(path=path,
                              data=request.dispatch_form.dispatch_data)
        
        if re.search(_login_re, result.data['dispatch']):
            return SUCCESS( u'logged in to OpenMRS')
        else:
            return FAIL(u'could not log in to OpenMRS')
            
client_resource = resource.Resource(ClientHandler)

class EncounterHandler(BaseHandler):
    """ Handles encounter requests. """
    allowed_methods = ('GET', 'POST')
    v_form = EncounterForm
    
    @dispatch('POST')
    def create(self, request, **kwargs):
        ''' Posts an encounter to the Sana queue in OpenMRS '''
        debug_api_request(self,'create', request)
        # Do an auth check first
        u = request.dispatch_form.data.get('username', None)
        p = request.dispatch_form.data.get('password', None)
        auth = { 'username': u, 'password':p} if u and p else None
        
        #TODO do a permissions servlet check??? --> would go to status create?
        path = dispatch_reverse(_namespace, Dispatchable.CLIENT)
        auth_form = ClientForm(data=auth)
        login = _opener.open(path, data=auth_form.dispatch_data)
        if login.status != Status.SUCCESS:
            return login
        
        # Execute post
        path = dispatch_reverse(_namespace, Dispatchable.ENCOUNTER, method='create')
        response = _opener.open(path=path,data=request.dispatch_data)
        return APIResponse(response.status, response.data) 
        
    def read(self, request, **kwargs):
        #TODO
        return APIResponse(Status.DISPATCH_ERROR, 'unimplemented')
encounter_resource = resource.Resource(EncounterHandler)

class NotificationHandler(BaseHandler):
    ''' Handles notification requests. '''
    allowed_methods = ('GET','POST',)
    
    def create(self, request, *pargs, **kwargs):
        # TODO(winkler) Implement and document
        debug_api_request(self,'read', request)
        pass

    def read(self, request, notification_id=None):
        pass
notification_resource = resource.Resource(NotificationHandler)

_gender_abbrev = {'Male':'M', 'male':'M', 'M':'M', 'm':'M',
                   'Female':'F', 'female':'F', 'F':'F', 'f':'F' }
class SubjectHandler(BaseHandler):
    """ Handles patient creation and lookup requests. """
    allowed_methods = ('GET','POST')
    v_form = SubjectForm
    
    @dispatch('POST') 
    def create(self, request, *pargs, **kwargs):
        ''' Post patient data '''
        debug_api_request(self,'create', request)
        data = request.dispatch_data
        u = data.get('username',None)
        p = data.get('password',None)
        auth = { 'username': u, 'password':p} if u and p else None
        
        # do a permissions servlet check??? --> should go to client create
        path = dispatch_reverse(_namespace, Dispatchable.CLIENT, method='create')
        login = _opener.open(path, data=auth)
        if login.status != Status.SUCCESS:
            return login
        
        # Execute patient post
        path = dispatch_reverse(_namespace, Dispatchable.SUBJECT, method='create')
        response = _opener.open(path=path,data=request.form.dispatch_data)
        debug_api_response(self,'create', response)
        return response
    
    @dispatch('GET')    
    def read(self, request, *pargs, **kwargs):
        format = request.form.format
        query = request.dispatch_data
        # OpenMRS REST module specific
        if not query['identifier']:
            path = dispatch_reverse(_namespace, 'subject', suffix='name', 
                                    format=format)
            path += '{0}+{1}'.format(query.get('name.givenName',''),
                                     query.get('name.familyName',''))
        else:
            path = dispatch_reverse(_namespace, Dispatchable.SUBJECT, format=format)
            path += query['identifier']
            
        # auth for openmrs REST module
        if not _opener.has_rest_auth(request, path, *pargs, **kwargs):
            return FAIL(u'Authorization Failure')
        
        # have to make sure we decode/encode returned data correctly
        #TODO is there a better way to handle this?
        response = _opener.get(path=path).data['dispatch']
        if format == 'json':
            return SUCCESS(cjson.decode(response,True))
        elif format == 'xml':
            return SUCCESS(parse_patient_xml(response))
subject_resource = resource.Resource(SubjectHandler)

def parse_patient_xml(s):
    ''' Remaps to api compatible Subject dict'''
    doc = minidom.parseString(s)
    doc = doc.childNodes[0]
    if (len(doc.childNodes)==0):
        return ""
    node = doc.childNodes[0]
    gender = node.getAttribute("gender")
    birthdate = node.getAttribute("birthdate")
    namenode = node.getElementsByTagName("name")[0]
    firstname = namenode.getElementsByTagName("givenName")[0].firstChild.data.strip()
    lastname = namenode.getElementsByTagName("familyName")[0].firstChild.data.strip()
    idnode = node.getElementsByTagName("identifierList")[0]
    subject = {"name": {"patient_first": firstname, "patient_last": lastname },
               "gender" : node.getAttribute("gender"), 
               "uid" : idnode.getElementsByTagName("identifier")[0].firstChild.data.strip(),
               "birthdate": birthdate }
    return subject

class StatusHandler(BaseHandler):
    """ Handles status requests. """
    allowed_methods = ('POST','GET')
    v_form = StatusForm
    
    @dispatch('POST') 
    def create(self, request, *pargs, **kwargs):
        debug_api_request(self,'create', request)
        path = dispatch_reverse(_namespace, Dispatchable.STATUS, method='create')
        return _opener.open(path=path,data=request.form.dispatch_data)
    
    @dispatch('GET')
    def read(self, request, *pargs, **kwargs):
        debug_api_request(self,'read', request)
        path = dispatch_reverse(_namespace, Dispatchable.STATUS)
        return _opener.get(path=path,data=request.form.dispatch_data)
status_resource = resource.Resource(StatusHandler)
