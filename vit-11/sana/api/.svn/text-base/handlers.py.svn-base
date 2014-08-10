''' 
Handle incoming requests to mds. 

This module relies heavily on the piston framework with the addition of the 
dispatcher and validate decorators from the Sana mds API.

The dispatcher decorator sets the _mdispatcher attribute which provides the 
callback to the middelware layer

The validate decorator is a slight modification to the piston validate in that 
it always sets a form attribute for the request and only calls the form 
is_valid() on a post. This was done so that the form data could be maintained 
and formatted for output as it passes through the middleware

@author: Sana Dev Team
Created on May 18, 2011
'''
from __future__ import with_statement
import sys
from django.http import HttpResponse, HttpRequest
from django.db.utils import IntegrityError
from piston.handler import BaseHandler
from piston import resource

from sana.api import API_CONFIG_ERROR
from sana.api.util import debug_api_request, debug_api_response, debug_error
from sana.api.dispatch.dispatchlib import dispatcher
from sana.api.packets import packetlib
from sana.api import validate, FAIL, ERROR, PROCESSING
from sana.api.fields import REQUEST, HEADER, STATUS, PACKET
from sana.api.forms import *
from sana.mds.models import *

class BaseDispatchHandler(BaseHandler):
    ''' Extension of piston BaseHandler. Extending handler classes should  be
        marked with @dispatch decorator and have the following fields declared:
        
            v_form => a model form
            allowed_methods => Piston CRUD API
            model 
            [dispatchable] => overrides 
        The @dispatch decorator will overwrite the _dispatch_ methods in this 
        class based on the class resolved by dispatchconf 
    '''
    # default allow all
    allowed_methods = ('GET','POST','PUT','DELETE')
    
    @property
    def mdispatcher(self):
        return getattr(self.__class__, '_mdispatcher')
    
    def _depacketize(self, object, form):
        ''' Pulls the packet information from the header and adds it to the 
            object as it is reassembled. 
            
            Returns true if the object is complete and ready for dispatch
        '''
        pdata = form.cleaned_data[REQUEST.HEADER]
        # Packetization code
        if REQUEST.SEQUENCE in form.cleaned_data:
            packet = form.cleaned_data[REQUEST.CONTENT]
            offset = pdata[PACKET.OFFSET]
            is_complete = packetlib.accept(object, offset, packet)
        else:
            is_complete = True
        return is_complete
    
    def _get_or_create(self, request):
        ''' Creates the mds level object. Non client objects must have a valid
            client uuid sent in the header 
        '''
        # gets the related objects
        data = {}
        header = request.form.cleaned_data[REQUEST.HEADER]
        for field in self.model._meta.fields:
            if 'related' in field.__dict__:
                model = field.related.parent_model
                object = model.objects.get(uid=header[field.name])
                data[field.name] = object
                
        # if pkt_data is present we know we have a packetized object and can reuse
        # get as well as create
        if REQUEST.SEQUENCE in request.form.cleaned_data:
            object,_ = self.model.objects.get_or_create(**data)
        else:
            object = self.model.objects.create(**data)
        return object
    
    # CRUD methods from piston API
    @validate('POST')
    def create(self, request, *pargs, **kwargs):
        ''' Wrapper around mdispatch handler create method '''
        debug_api_request(self,'create', request)
        object = None
        try:
            #TODO replace with proper create method
            object = self._get_or_create(request)
            is_complete = self._depacketize(object, request.form)
            if not is_complete:
                response = PROCESSING(u'Recieved Packet. Send Next')
                
            # This actually executes the create upstream
            else:
                response = self.mdispatcher.create(request, *pargs, **kwargs)
        # object already exists
        except IntegrityError as e:
            debug_error(self.__class__.__name__, self.create, e)
            response = ERROR(sys.exc_info()[1])
        # catch all
        except Exception as e:
            debug_error(self.__class__.__name__, self.create, e)
            response = ERROR(sys.exc_info()[1])
            
        #TODO mark response code and save for rtt
        if object:
            object.status = response.resp_status
            object.save()
        debug_api_response(self,'create', response)
        return response.render()
    
    @validate('GET')    
    def read(self, request, *pargs, **kwargs):
        ''' Wrapper around mdispatch handler read method '''
        debug_api_request(self,'read', request)
        object = None
        try:
            object = self._get_or_create(request)
            if self.mdispatcher:
                response = self.mdispatcher.read(request, *pargs, **kwargs)
            else:
                response =  ERROR(API_CONFIG_ERROR)
        except Exception as e:
            debug_error(self.__class__.__name__, self.read, e)
            response = ERROR(sys.exc_info()[1])
        debug_api_response(self,'read', response)
        #TODO mark response code and save for rtt
        if object:
            object.status = response.resp_status
            object.save()
        return response.render()
    
    @validate('DELETE')  
    def delete(self,request, *pargs, **kwargs):
        ''' Wrapper around mdispatch handler delete method '''
        debug_api_request(self,'delete', request)
        object = None
        try:
            object = self._create(request)
            if self.mdispatcher:
                response = self.mdispatcher.delete(request, *pargs, **kwargs)
            else:
                response =  ERROR(API_CONFIG_ERROR)
        except Exception as e:
            debug_error(self.__class__.__name__, self.delete, e)
            response = ERROR(sys.exc_info()[1])
        debug_api_response(self,'delete', response)
        #TODO mark response code and save for rtt
        if object:
            object.status = response.resp_status
            object.save()
        return response.render()
    
    @validate('PUT')  
    def update(self, request, *pargs, **kwargs):
        ''' Wrapper around mdispatch handler update method '''
        debug_api_request(self,'update', request)
        object = None
        try:
            object = self._create(request)
            if self.mdispatcher:
                response = self.mdispatcher.update(request, *pargs, **kwargs)
            else:
                response =  ERROR(API_CONFIG_ERROR)
        except Exception as e:
            debug_error(self.__class__.__name__, self.update, e)
            response = ERROR(sys.exc_info()[1])
        debug_api_response(self,'update', response)
        #TODO mark response code and save for rtt
        if object:
            object.status = response.resp_status
            object.save()
        return response.render(format = request.form.format)
    
@dispatcher
class BinaryHandler(BaseDispatchHandler):
    ''' Handles binary requests'''
    allowed_methods = ('GET', 'POST')
    v_form = APIRequestForm
    model = Binary
    dispatchable = 'binary'
    
    def _create(self,data):
        binary = Binary.objects.get_or_create(**data)
        # Save extra data
        total_size = data.get('total_size', None)
        uploaded = data.get('uploaded', False)                                    
        if total_size:
            pass
        if uploaded:
            pass
        binary.save()
        #TODO THis should happen in a queue and signal back to upload if 
        # complete
        eof = packetlib.accept(binary, data.offset, data.bytes)
        # eof should be true once complete
        if eof:
            encounter = Encounter.objects.get(uid='l')
            # mark encounter complete and upload
            dispatch = HttpRequest(encounter.dispatch)
            response = encounter_resource.handler.create(dispatch)
            if response.resp_status == STATUS.SUCCESS:
                encounter.uploaded = True
                encounter.save()
            return response
binary_resource = resource.Resource(BinaryHandler)
    
@dispatcher
class ClientHandler(BaseDispatchHandler):
    ''' Handles client requests'''
    allowed_methods = ('GET','POST','PUT','DELETE')
    v_form = APIClientForm
    model = Client
client_resource = resource.Resource(ClientHandler)

@dispatcher
class EncounterHandler(BaseDispatchHandler):
    """ Handles encounter requests. """
    allowed_methods = ('GET', 'POST', 'PUT', 'DELETE')
    v_form = APIRequestForm
    model = Encounter
encounter_resource = resource.Resource(EncounterHandler)

@dispatcher
class NotificationHandler(BaseDispatchHandler):
    ''' Handles notification requests '''
    allowed_methods = ('GET', 'POST')
    v_form = APIRequestForm
    model = Notification
notification_resource = resource.Resource(NotificationHandler)
    
@dispatcher
class SubjectHandler(BaseDispatchHandler):
    ''' Handles patient creation and lookup requests. '''
    allowed_methods = ('GET', 'POST', 'PUT', 'DELETE')
    v_form = APIRequestForm
    model = Subject
subject_resource = resource.Resource(SubjectHandler)


@dispatcher 
class ProcedureHandler(BaseDispatchHandler):
    ''' Handles procedure requests. '''
    allowed_methods = ('GET','POST',)
    dispatch_methods = ( 'GET','POST',)
    v_form = APIRequestForm
    model = Procedure
procedure_resource = resource.Resource(ProcedureHandler)

@dispatcher
class StatusHandler(BaseDispatchHandler):
    """ Handles status requests. """
    allowed_methods = ('GET','POST')
    dispatch_methods = ( 'GET','POST',)
    #v_form = StatusForm
    v_form = APIRequestForm
    model = Status
status_resource = resource.Resource(StatusHandler)
