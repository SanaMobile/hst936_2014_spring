''' 
Namespaces with the fields for each which will be used for the API objects. 
Defined here so that any modifications can be reflected throughout the code 
base.

@author: Sana Dev Team
Created on Jul 11, 2011
'''
class Header:
    ''' Parameters in a request 'header' Field '''
    # Used for deternining UUID
    UID = 'uid'
    CLIENT = 'client'
    
class Packet:
    # Packetization fields
    OFFSET = 'offset'
    OF ='of'
    
    
class Request:
    ''' Fields used by the API in request processing '''
    HEADER = 'header'
    SEQUENCE = 'pkt_seq'
    CONTENT = 'content'
    
    # following are appended to requests as they are passed though handlers
    DISPATCHABLE = 'dispatchable'
    FORM = 'form'
    FORMAT = 'format'

class Response:
    ''' Fields in responses returned to clients '''
    STATUS = 'status'
    CONTENT = 'dispatch'
    ERRORS = 'errors'
    
class Dispatchable:
    ''' The object names which will be accepted '''
    BINARY = 'binary'
    CLIENT = 'client'
    ENCOUNTER = 'encounter'
    NOTIFICATION = 'notification'
    PROCEDURE = 'procedure'
    SUBJECT = 'subject'
    STATUS = 'status'
    DATA = 'dispatch_data'
    
class Status:
    ''' Status messages returned to clients in responses '''
    SUCCESS = u'SUCCESS'
    FAILURE = u'FAILURE'
    RECEIVED = u'RECEIVED'
    PROCESSING = u'PROCESSING'
    ACTION = u'ACTION'
    DISPATCHED = u'DISPATCHED'
    DISPATCH_ERROR = u'DISPATCH_ERROR'
    INVALID = u'INVALID_CODE'
    
    @staticmethod
    def is_valid(value):
        return hasattr(Status, value)
    
STATUS_CODES = (Status.SUCCESS, Status.FAILURE, Status.RECEIVED, 
                Status.PROCESSING, Status.ACTION, Status.DISPATCHED,
                Status.DISPATCH_ERROR, Status.INVALID)

# Here to prevent name collisons
DISPATCHABLE = Dispatchable
PACKET = Packet
HEADER = Header
REQUEST = Request
RESPONSE = Response
STATUS = Status

