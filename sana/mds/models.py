'''
Models representing the core dispatchables of the API:

    Binary       A blob of data that can be dispatched 
    Client       Something that sends and receives dispatches to this server
    Encounter    An instance of a procedure where data is collected about a 
                 subject
    Notification A message sent to this server to be dispatched
    Subject      Something or someone about whom data is collected
    Procedure    A series of one or more instructions for collecting data
    Status       A request for additional information
    
Naming conventions: 
    client      The originating client when used within the context of another
                dispatchable
    uuid        A universally unique identifier-unique in any context
    uid         An identifier which is unique only within its context of origin

Uniqueness: The dispatch server maintains the uniqueness of dispatchables based 
on the assumption that client will provide a unique identifier and that client's
will guarantee the uniqueness of all resources within there own context. Hence, 
a combination of client id and client assigned identifier for the dispatchable 
must be unique. @see additional documentation within each dispatchable.

@author: Sana Dev Team
Created on May 31, 2011
'''

__all__ = ('Binary', 'Client', 'Encounter', 'Notification', 'Procedure', 
           'Status', 'Subject')
    
from django.db import models
from sana.api.fields import STATUS_CODES
            
class APIModel:
    '''
    Provides the fields common to all dispatchable models
    '''
    created = models.DateTimeField(auto_now_add=True)
    modified = models.DateTimeField(auto_now=True)
    status = models.CharField(choices=STATUS_CODES)
    
    def rtt(self):
        ''' The time from when recieved until last modified '''
        if self.status:
            return self.modified - self.created
    
    def __unicode__(self):
        return self.uuid
    
class Client(models.Model, APIModel):
    ''' A dispatchable representing something that sends and receives dispatches
     to this server '''
    class Meta:
        app_label = 'mds'
        verbose_name = 'client'
    uid = models.CharField(unique=True, max_length=128)
        
    ''' The client username and password to authenticate with this server '''
    
    # TODO: do we want to actually store here?
    username = models.CharField(max_length=128)
    password = models.CharField(max_length=128)
    
    def __unicode__(self):
        return self.uuid

    @property
    def uuid(self):
        return self.uid

class Dispatchable(APIModel):
    
    @property
    def uuid(self):
        return self.client.uuid +'-'+ self.uid

class Procedure(models.Model, Dispatchable):
    ''' A dispatchable representing series of one or more instructions for 
    collecting data '''
    class Meta:
        app_label = 'mds'
        verbose_name = 'procedure'
    client = models.ForeignKey(Client)
    uid = models.CharField(max_length=16)

class Subject(models.Model, Dispatchable):
    ''' A dispatchable representing something or someone about whom data is 
    collected '''
    class Meta:
        unique_together = ('client', 'uid')
        app_label = 'mds'
        verbose_name = 'subject'
        
    client = models.ForeignKey(Client)
    uid = models.CharField(max_length=16)
    
class Encounter(models.Model, Dispatchable):
    ''' A dispatchable representing an instance of a procedure where data is 
    collected about a subject'''
    
    class Meta:
        unique_together = ('client', 'uid')
        app_label = 'mds'
        verbose_name = 'encounter'
        
    client = models.ForeignKey(Client)
    uid = models.CharField(max_length=16)
    

class Binary(models.Model, Dispatchable):
    ''' A packetized a blob of data that can be dispatched that will utlimately
        be disptached when received in its entirety 
    '''
    class Meta:
        unique_together = ('client', 'uid')
        app_label = 'mds'
        verbose_name = 'binary'
    
    client = models.ForeignKey(Client)
    uid = models.CharField(max_length=16)
    encounter = models.ForeignKey(Encounter)
    content_type = models.CharField(max_length=128)
    data = models.FileField(upload_to='binary/%Y/%m/%d', blank=True)


class Notification(models.Model, Dispatchable):
    ''' A dispatchable representing a message sent to this server to be 
    dispatched or retrieved '''
    
    class Meta:
        unique_together = ('client', 'uid')
        app_label = 'mds'
        verbose_name = 'notification'
        
    client = models.ForeignKey(Client)
    uid = models.CharField(max_length=16)

class Status(models.Model, Dispatchable):
    ''' A dispatchable representing a request for additional information '''
    class Meta:
        unique_together = ('client', 'uid')
        app_label = 'mds'
        verbose_name = 'status'
        
    client = models.ForeignKey(Client)
    uid = models.CharField(max_length=16)


