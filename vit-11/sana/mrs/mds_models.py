'''

@author: Sana Dev Team
Created on May 17, 2011
'''
from django.db import models

__all__ = ('Client', 'Encounter', 'Notification', 'Procedure', 'Subject' )


class mDSModel(models.Model):
    ''' A client assigned identifier '''
    guid = models.CharField(max_length=255, unique=True)
    created = models.DateTimeField(auto_now_add=True)
    modified = models.DateTimeField(auto_now=True)


class Client(mDSModel):
    """ Some arbitrary way to refer to a downstream client."""
    class Meta:
        app_label = 'mds'
        
    uname = models.CharField(max_length=255)
    pw = models.CharField(max_length=255)

    def __unicode__(self):
        return "{ \'Client\': %s }" % ([self.guid, self.created, self.modified]) 

    def touch(self):
        self.save()

class Encounter(mDSModel):
    ''' Represents an encounter where data has been collected '''
    class Meta:
        app_label = 'mds'

    def __unicode__(self):
        return "{ \'Encounter\': %s }" % ([self.guid, self.created, self.modified]) 

    ''' The client which collected the binary '''
    client = models.ForeignKey(Client)

    ''' Text responses of the encounter '''
    observations = models.TextField()

    ''' True if  the encounter was uploaded to a remote queueing server '''
    uploaded = models.BooleanField(default=False)

class Binary(mDSModel):
    """ A Binary object stored as part of an encounter.  """
    class Meta:
        app_label = 'mds'
    
    def __unicode__(self):
        return "{ \'Binary\': %s }" % ([self.guid, self.created, self.modified]) 

    ''' The client which collected the binary '''
    client = models.ForeignKey(Client)
    
    ''' An Encounter in which this binary resource was collected '''
    encounter = models.ForeignKey(Encounter)

    ''' Client assigned id of the observation in which this collected '''
    obs_id = models.CharField(max_length=255)
    
    ''' File the data gets stored in on this server '''
    data = models.FileField(upload_to='binary/%Y/%m/%d', )

    ''' The client reported binary size in bytes '''
    total_size = models.IntegerField(default=0)
    

class Notification(mDSModel):
    """ A notification message; e.g. sms, email, etc. """

    class Meta:
        app_label = 'mds'
    
    def __unicode__(self):
        return "{ \'Binary\': %s }" % ([self.guid, self.created, self.modified]) 
    
    ''' Who or what is sending the message '''
    client = models.ForeignKey(Client)
    
    ''' An optional direct return address '''
    return_addr = models.CharField(max_length=512)
    
    ''' Where the message will be going. Interpretation depends on type '''
    recipient_addr = models.CharField(max_length=512)
    
    ''' Brief summary or subject '''
    summary = models.CharField(max_length=128)
    
    ''' Text body '''
    data = models.TextField()
    
    ''' True upon succssful delivery '''
    delivered = models.BooleanField()
    
class Procedure(mDSModel):
    ''' A set of instructions. May be a Representation of a form to collect data '''
    class Meta:
        app_label = 'mds'
    
    def __unicode__(self):
        return "{ \'Procedure\': %s }" % ([self.guid, self.created, self.modified]) 
        
    client = models.ForeignKey(Client)
    data = models.TextField(blank=True)
    procedure_file = models.FileField(upload_to='procedure',blank=True)

class Subject(mDSModel):
    """ Something about which data is collected during an encounter """
    class Meta:
        app_label = 'mds'
    
    def __unicode__(self):
        return "{ \'Procedure\': %s }" % ([self.guid, self.created, self.modified]) 
    
    client = models.ForeignKey(Client)
    data = models.TextField()
    