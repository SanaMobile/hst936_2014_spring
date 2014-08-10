"""Model representation of a binary object stored as a file 
"""
from __future__ import absolute_import
import os
from django.db import models
from sana.api.util import fextension
from sana.mrs._models.encounter import *

__all__ = ["BinaryResource"]

class BinaryResource(models.Model):
    """ A Binary object stored as part of an encounter.  """
    class Meta:
        unique_together = (('uid', 'client_uid',),)
        app_label = 'mrs'
    
    def __unicode__(self):
        return "BinaryResource %s %s %s %s %d/%d" % (self.encounter_uid,
                                                  self.uid,
                                                  self.obs_id,
                                                  self.created,
                                                  self.upload_progress,
                                                  self.total_size)

    ''' Client assigned identifier '''
    uid = models.CharField(max_length=512)
    
    # the instance of an Encounter which this binary resource is associated with
    encounter = models.ForeignKey(Encounter)

    ''' The client which collected the binary '''
    client_uid = models.CharField(max_length=255)

    ''' Client assigned encounter id '''
    encounter_uid = models.CharField(max_length=255)

    ''' Client assigned id of the observation in which this collected '''
    obs_id = models.CharField(max_length=255)

    ''' Client declared mime type of the binary '''
    content_type = models.CharField(max_length=255)
    
    ''' File the data gets stored in on this server '''
    data = models.FileField(upload_to='binary/%Y/%m/%d', )

    ''' The current number of bytes stored for the resource '''
    upload_progress = models.IntegerField(default=0)

    ''' The client reported binary size in bytes '''
    total_size = models.IntegerField(default=0)

    ''' True if the binary resource was uploaded to an upstream server '''
    uploaded = models.BooleanField(default=False)

    created = models.DateTimeField(auto_now_add=True)
    modified = models.DateTimeField(auto_now=True)

    def create_stub(self, content_type=None):
        """ Creates an empty file stub in the application media directory"""
        self.total_size = 0
        # TODO (winkler): use a better file name
        extension = fextension[content_type]
        filename = "%s.%s" % (self.filename(), extension)
        self.data = self.data.field.generate_filename(self, filename)
        self.save()
        path, _ = os.path.split(self.data.path)
        if not os.path.exists(path):
            os.makedirs(path)
            open(self.data.path, "w").close()
        return path
    
    def receive_completed(self):
        ''' True if the total size is equal to the upload_progress '''
        return self.total_size > 0 and self.total_size == self.upload_progress

    def filename(self):
        return "%s_%s" % (self.encounter.guid, self.obs_id)

    def packet_key(self):
        return '{0}-{1}-{2}-{3}'.format(*(self.client_uid, 
                                          self.encounter_uid, 
                                          self.obs_id, 
                                          self.uid))