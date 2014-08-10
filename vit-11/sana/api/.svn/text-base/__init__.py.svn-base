'''Sana mDS(mobile Dispatch Server)

The mDS API is designed takes a RESTful approach to data packetization using a 
CRUD implementation based on the Django-piston API.

The API declares the following objects:
    Binary - a  binary blob
    Client - something that talks to the server
    Encounter - an instance of data collection
    Notification - a message sent from/to a client
    Subject - an object about which data is collected
    Procedure - a description of a process for collecting data
    Status - a request for information

Requests to the server should be directed to the external facing urls defined in
urls.py. These are simplified to just the API objects. Request formats are 
defined by the object forms in forms.py

Responses Messages from the server are formatted with a 'status' code and 
'dispatch' body.

API Response:
    response       => {'status': int_term, 'dispatch': <object> } 
    object         => <kv_pair> [,<kv_pair>]  
    kv_pair        => <key> : <value>
    key            => str_term
    value          => term | { <object> }

@author: Sana Dev Team
Created on May 12, 2011
'''
import cjson, httplib, json, urllib2
from xml.dom import minidom

from piston.utils import rc, decorator

from django.conf import settings
from django.core import exceptions, validators
from django.core.exceptions import ValidationError
from django.utils.translation import ugettext_lazy as _
from django.http import HttpResponse
from django.forms import Field, CharField, Form, IntegerField, MultiValueField
from django.forms.models import modelform_factory, ModelForm
from django.utils.encoding import StrAndUnicode
from django.forms.widgets import HiddenInput

from sana.api.util import debug_api_request
from sana.api.dispatch.forms import DispatchableForm
from sana.api.forms import JSONField, APIResponse
from sana.api.fields import REQUEST, RESPONSE, STATUS

API_VERSION = {_('API'):settings.API_VERSION } 
API_CONFIG_ERROR = _('Incorrect dispatch configuration')

class APIConfigException():
    pass

_crud_methods = ( 'POST', 'DELETE','GET', 'PUT')
_crud_handlers = ('create', 'delete', 'read', 'update')

# HTTP request method names to CRUD names
CRUD = dict(zip(_crud_methods,_crud_handlers))

# CRUD NAMES to HTTP request methods
R_CRUD = dict(zip(_crud_handlers, _crud_methods))

#TODO Can we clean this up and refactor?
def validate(operation='POST'):
    ''' Adds the following attributes to all CRUD requests
        
        Request.FORM         => the raw dispatchable content
        Request.CONTENT      => the dispatchable object
        
        Adds the following to the form
        Request.FORMAT       => the output format
        
        The Request.$VALUE are field names taken from api.fields module.
        
        This implementation requires all requests to have valid form data.
    '''
    @decorator
    def wrap(f, handler, request, *a, **kwa):
        # gets the form we will validate
        klass = handler.__class__
        if hasattr(klass, 'v_form'):
            v_form = getattr(klass, 'v_form')
        else:
            return ERROR(u'Invalid dispatchable')
        # Create the dispatchable form and validate
        if operation == 'POST':
            data = getattr(request, operation)
        else:
            data = handler.flatten_dict(getattr(request, operation))
        
        form = v_form(data=data)
        if not form.is_valid():
            errs = dict((key, [unicode(v) for v in values]) for key,values in form.errors.items())
            return FAIL(errs)
        setattr(request, REQUEST.FORM, form)
        
        # Format attributes - assume client wants same format returned as sent
        setattr(form, REQUEST.FORMAT, request.REQUEST.get(REQUEST.FORMAT, 'json'))
        
        # Attaches content to the request. This is what the middleware will need
        # to operate on
        setattr(request, REQUEST.CONTENT, form.dispatch_data[REQUEST.CONTENT])
        return f(handler, request, *a, **kwa)
    return wrap


class APIException(Exception):
    ''' Exception for mds API '''
    def __init__(self, message):
        self.message = message
        
#lambda utilities for response generation
FAIL = lambda x: APIResponse(data={RESPONSE.STATUS: STATUS.FAILURE, 
                                   REQUEST.CONTENT: {RESPONSE.ERRORS: x}})
SUCCESS = lambda x: APIResponse(data={RESPONSE.STATUS: STATUS.SUCCESS, 
                                      REQUEST.CONTENT: x})
PROCESSING = lambda x: APIResponse(data={RESPONSE.STATUS: STATUS.DISPATCHED, 
                                         REQUEST.CONTENT: x})
ACTION = lambda x: APIResponse(data={RESPONSE.STATUS: STATUS.ACTION, 
                                     REQUEST.CONTENT: x})
INVALID = lambda x: APIResponse(data={RESPONSE.STATUS: STATUS.INVALID, 
                                      REQUEST.CONTENT: x})
ERROR = lambda x: APIResponse(data={RESPONSE.STATUS: STATUS.DISPATCH_ERROR, 
                                    REQUEST.CONTENT: x})

    