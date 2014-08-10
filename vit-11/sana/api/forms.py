'''Defines the containers and fields which form the backbone of the dispatch
server.

@author: Sana Dev Team
Created on May 13, 2011
'''
import cjson, json
from django import forms# import Field, BooleanField, CharField, DateField, EmailField, Form, IntegerField
from django.core import exceptions, validators
from django.core.exceptions import ValidationError
from django.forms.widgets import HiddenInput

#from sana.mds.models import 
from sana.api.fields import Response, Status
from sana.api.dispatch.forms import DispatchableForm

__all__ = ('BinaryForm', 'ClientForm', 'EncounterForm','NotificationForm', 
           'SubjectForm', 'ProcedureForm', 'StatusForm', 'JSONField',
           'APIRequestForm', 'APIClientForm')

class JSONField(forms.CharField):
    ''' Provides the automatic decoding to a python dict '''
    
    def to_python(self,value):
        if not value:
            return {}
        return json.loads(value)
    
    def validate(self,value):
        # Use the parent's handling of required fields, etc.
        super(JSONField, self).validate(value)
        
        for v in self.validators:
            v(value)

class UUIDField(forms.Field):
    ''' A unique identifier '''
    def to_python(self, value):
        return value
    
    def validate(self, value):
        if value in validators.EMPTY_VALUES and self.required:
            raise ValidationError(self.error_messages['required'])

class BaseAPIForm(forms.Form):
    ''' The basic form for all transaction with mds '''
    
    '''Client assigned identifier '''
    header = forms.Field(widget=HiddenInput)
    
    ''' The dispatchable data  '''
    content = forms.Field()
    
    def _post_clean(self):
        ''' API forms are forms of forms so we occasionally get dicts presented
            as unicode objects 
        '''
        for k,v in self.cleaned_data.items():
            print 'BaseAPIForm._post_clean', k, type(v), v
            if not isinstance(v, dict):
                self.cleaned_data[k] = cjson.decode(v,True)
    
        
class APIClientForm(BaseAPIForm, DispatchableForm):
    
    '''Client identifier '''
    header = forms.Field()
    
    ''' The dispatchable data  '''
    content = forms.Field()

class APIRequestForm(BaseAPIForm, DispatchableForm):
    ''' The basic form for all transaction with mds '''
    pass

class APIResponse(forms.Form):
    ''' Definition of response content returned by handlers '''
    status = forms.CharField()
    dispatch = forms.Field()
    
    @property
    def resp_status(self):
        return self.data[Response.STATUS] or Status.INVALID
    
    def __init__(self, format=None, *args, **kwargs):
        super(APIResponse, self).__init__(*args,**kwargs)
        self._format = format
        
    def render(self, format=None):
        ''' Produces form data in the following order
                cleaned_data -> data -> initial
            This is intended to provide access to the forms data regardless of 
            validation.
        '''
        
        if format:
            self._format = format
        #TODO Need to do something with the formatting here
        if self._format == 'xml':
            if self.is_valid():
                return self.cleaned_data
            return self.data
        elif self._format == 'json':
            if self.is_valid():
                return self.cleaned_data
            elif self.data:
                return self.data
            else:
                return self.initial
        else:
            return self.data

class BinaryForm(forms.Form, DispatchableForm):
    ''' Binary packet form '''
    uid = forms.CharField()
    client  = forms.CharField()
    encounter = forms.CharField()
    observation  = forms.CharField()
    content_type  = forms.CharField()
    bytes = forms.CharField()
    
    uploaded = forms.BooleanField(required=False)
    total_size = forms.IntegerField(required=False)
    
class ClientForm(forms.Form, DispatchableForm):
    ''' Registers a client with the system '''
    uid = forms.CharField()
    
class EncounterForm(forms.Form, DispatchableForm):
    """ Form for encounter requests """
    uid = forms.CharField()
    client  = forms.CharField()
    date = forms.DateField()
    procedure = forms.CharField()
    subject = forms.CharField()
    observations = forms.Field()
    uploads = forms.Field(required=False)

class NotificationForm(forms.Form, DispatchableForm):
    ''' Form for notification requests.'''
    address = forms.CharField()
    message = forms.CharField()

class ObservationForm(forms.Form):
    ''' A dispatchable representing series of one or more instructions for 
    collecting data '''
    id = forms.CharField()
    type = forms.CharField()
    concept = forms.CharField()
    question = forms.CharField()
    answer = forms.Field()

class SubjectForm(forms.Form, DispatchableForm):
    ''' Form for patient information requests '''
    uid = forms.CharField()
    name = forms.Field()

class ProcedureForm(forms.Form, DispatchableForm):
    ''' Form for procedure requests '''
    title = forms.CharField()
    author = forms.CharField()
    data = forms.Field()
            
class StatusForm(forms.Form, DispatchableForm):
    ''' Checks a status message '''
    uid = forms.CharField()
    client  = forms.CharField()
    data = forms.Field(required=False)

    

