''' Simple example of how to construct a middleware plugin for the dispatch API.

@author: Sana Dev Team
Created on May 12, 2011
'''

from piston.handler import BaseHandler
from piston import resource
from piston.utils import rc

from sana.api import status, APIResponse

__all__ = ('ClientHandler', 'EncounterHandler', 'NotificationHandler', 
           'SubjectHandler', 'ProcedureHandler', 'StatusHandler', 'client_resource', 
           'encounter_resource', 'notification_resource', 'subject_resource',
           'procedure_resource', 'status_resource')

class NullOpener(object):
    ''' This does nothing. Calls to open always return a 404
    '''
    def open(self, request, **kwargs):
        return APIResponse(status.FAILURE, 'Null check.')
opener = NullOpener()

class NullHandler(BaseHandler):
    ''' Does nothinh requests '''
    
    def create(self, request, **kwargs):
        return opener.open(request, **kwargs)
    
    def delete(self, request, **kwargs):
        return opener.open(request, **kwargs)
    
    def read(self, request, **kwargs):
        return opener.open(request, **kwargs)
    
    def update(self, request, **kwargs):
        return opener.open(request, **kwargs)

class BinaryHandler(NullHandler):
    pass
binary_resource = resource.Resource(BinaryHandler)
    
class ClientHandler(NullHandler):
    pass
client_resource = resource.Resource(ClientHandler)

class EncounterHandler(NullHandler):
    pass
encounter_resource = resource.Resource(EncounterHandler)

class NotificationHandler(NullHandler):
    pass
notification_resource = resource.Resource(NotificationHandler)
    
class SubjectHandler(NullHandler):
    pass
subject_resource = resource.Resource(SubjectHandler)

class ProcedureHandler(NullHandler):
    pass
procedure_resource = resource.Resource(ProcedureHandler)

class StatusHandler(NullHandler):
    pass
status_resource = resource.Resource(StatusHandler)


