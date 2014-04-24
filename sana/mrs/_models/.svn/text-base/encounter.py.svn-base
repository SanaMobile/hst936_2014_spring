"""Model representation of an encounter where data is collected
"""
from __future__ import absolute_import
import cjson
import logging
from django.conf import settings
from collections import defaultdict

from django.db import models

#from sana.mrs import openmrs
from sana.api.deprecated import BINARY_TYPES, CONTENT_TYPES
from sana.mrs._models.binary import *

__all__ = ["Encounter"]

_gender_dict = {'Male':'M', 'male':'M', 'M':'M', 'm':'M',
                   'Female':'F', 'female':'F', 'F':'F', 'f':'F' }
class Encounter(models.Model):
    """
        Represents an encounter where data has been collected
    """
    class Meta:
        # Maybe
        #unique_together = (('uid', 'client_uid'),)
        app_label = 'mrs'

    def __unicode__(self):
        return "Encounter %s %s" % (self.uid, self.created)
    
    ''' client assigned id '''
    uid = models.CharField(max_length=512)

    ''' UID of the procedure this is an instance of '''
    procedure_uid = models.CharField(max_length=512)

    ''' Client assigned UID of the patient this was collected for '''
    patient_uid = models.CharField(max_length=512)

    ''' UID of the reporting phone '''
    client_uid = models.CharField(max_length=512)

    ''' Text responses of the encounter '''
    observations = models.TextField()

    # Whether the encounter was uploaded to a remote queueing
    # server.
    uploaded = models.BooleanField(default=False)

    created = models.DateTimeField(auto_now_add=True)
    modified = models.DateTimeField(auto_now=True)


    def get_binary_set(self):
        """ Returns the set of binaries for this encounter """
        try:
            binary_set = self.binaryresource_set.all()
        except:
            binary_set = set([])
        return binary_set
           
    def get_binaries_to_upload(self):
        """ Returns a dictionary of {'id':'file path'} for the binaries that 
        are ready to upload """
        files = defaultdict(list)
        binaries_to_upload = [binary for binary in self.get_binary_set() if not binary.uploaded]
        for binary in binaries_to_upload:
            files[str(binary.element_uid)].append(str(binary.data.path))
        return files
    
    def set_binaries_uploaded(self):
        for binary in self.get_binaries_to_upload():
            binary.uploaded = True
            binary.save()
        
    def upload(self, username=None, password=None):
        """ Returns true if the Encounter is ready to upload. """
        binaries_ready = True
        for binary in self.get_binary_set():
            if not binary.recieve_completed:
                binaries_ready = False
        if self.uploaded:
            message = "Encounter %s already uploaded." % self.guid
            logging.info(message)
            result = False
        elif not binaries_ready:
            result = False
            message = "Encounter %s has unreceived binaries.  Waiting." % self.guid
            return result, message
        else:
            message = "Encounter %s is ready to upload." % self.guid
            logging.info(message)
            result = True
        return result, message
    
    def set_uploaded(self):
        self.uploaded = True
        self.save()
        for binary in self.get_binary_set():
            binary.uploaded = True
            binary.save()
        
    def format(self):
        """Convert to dictionary and remove unnecessary data for transmission."""
        dict = self.__dict__
        remove_list = ['id', '_state', 'upload_password']
        for key in remove_list:
            if dict.has_key(key):
                del dict[key]
        
        for key in dict.keys():
            dict[key] = str(dict[key])
        return dict

    def parse_response_binaries(self):
            """ returns a dict of binary objects parsed from the encounter formatted
                as { id : {'label' : label, 'mediatype': mediatype } }
            """
            binaries = {}
            responses_dict = cjson.decode(self.responses)
            for obs_id, obs_data in responses_dict.items():
                concept = obs_data['type']
                if concept in BINARY_TYPES:
                    eid = obs_id
                    items = obs_data['answer'].split(',')
                    #binaries_dict = dict(v['upload'])
                    #for label,mediatype in binaries_dict.items():
                    #TODO remove block below and replace with above when client is ready
                    for item in items:
                        if item == "":
                            continue
                        mediatype = obs_data.get('content_type', None)
                        if mediatype is None:
                            mediatype = CONTENT_TYPES.get(concept, 'application/octet-stream')
                        binaries[eid] = {'label' : obs_id, 'filename':item ,'mediatype': mediatype }
            return binaries

