''' Handlers here echo requests back to the sender. 

@author: Sana Dev Team
Created on May 12, 2011
'''
from __future__ import with_statement

from piston.handler import BaseHandler
from piston import resource

from sana.api import SUCCESS, ERROR
from sana.api.dispatch.dispatchlib import dispatch
from sana.api.middleware.echo.forms import *

__all__ = ('ClientHandler', 'EncounterHandler', 'NotificationHandler', 
           'PatientHandler', 'ProcedureHandler', 'StatusHandler', 'client_resource', 
           'encounter_resource', 'notification_resource', 'patient_resource',
           'procedure_resource', 'status_resource')

class EchoHandler(BaseHandler):
    ''' Echo requests '''
    allowed_methods = ('GET','POST', 'PUT', 'DELETE')
    
    def _echo(self, request, *args, **kwargs):
        ''' echos POST data '''
        #Don't really need the try catch here, included as template
        try:
            return SUCCESS(request.dispatch_data)
        except Exception as e:
            return ERROR(e.message)
    
    @dispatch('POST')
    def create(self, request, *args, **kwargs):
        ''' echos POST data '''
        return self._echo(request, *args, **kwargs)
    
    @dispatch('DELETE')
    def delete(self, request, *args, **kwargs):
        ''' echos DELETE request queries '''
        return self._echo(request, *args, **kwargs)
    
    @dispatch('GET')
    def read(self, request, *args, **kwargs):
        ''' echos GET request queries '''
        return self._echo(request, *args, **kwargs)
    
    @dispatch('PUT')
    def update(self, request, *args, **kwargs):
        ''' echos PUT request queries '''
        return self._echo(request, *args, **kwargs)
echo_resource = resource.Resource(EchoHandler)

class BinaryHandler(EchoHandler):
    ''' Binary echo handler provided for completeness '''
    v_form=BinaryForm
binary_resource = resource.Resource(BinaryHandler)

class ClientHandler(EchoHandler):
    ''' Client echo handler provided for completeness '''
    v_form=ClientForm
client_resource = resource.Resource(ClientHandler)

class EncounterHandler(EchoHandler):
    ''' Encounter echo handler provided for completeness '''
    v_form = EncounterForm
encounter_resource = resource.Resource(EncounterHandler)

class NotificationHandler(EchoHandler):
    ''' Notification echo handler provided for completeness '''
    v_form=NotificationForm
notification_resource = resource.Resource(NotificationHandler)
    
class SubjectHandler(EchoHandler):
    ''' Patient echo handler provided for completeness '''
    v_form=SubjectForm
subject_resource = resource.Resource(SubjectHandler)

class ProcedureHandler(EchoHandler):
    ''' Procedure echo handler provided for completeness '''
    v_form=ProcedureForm
procedure_resource = resource.Resource(ProcedureHandler)

class StatusHandler(EchoHandler):
    ''' Status echo handler provided for completeness '''
    v_form=StatusForm
status_resource = resource.Resource(StatusHandler)


