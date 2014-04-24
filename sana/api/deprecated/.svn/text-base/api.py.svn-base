from __future__ import with_statement
import os
import cjson
import datetime
import logging

#from sana.mrs.util import enable_logging

from collections import defaultdict

from django.conf import settings
from django.db.models import ObjectDoesNotExist

from sana.mrs.emr import *
from sana.mrs import models
#from sana.mrs.models import * #Procedure#, Encounter, Client, Patient, BinaryResource, ClientEventLog
from moca.mrs import openmrs
from sana.api.util import fextension

# api.py -- interface-agnostic API methods

BINARY_TYPES = ['PICTURE', 'SOUND', 'VIDEO', 'BINARYFILE']

BINARY_TYPES_EXTENSIONS = {'PICTURE': 'jpg',
                           'SOUND': '3gp',
                           'VIDEO': '3gp',
                           'BINARYFILE': 'mpg'}

CONTENT_TYPES = {'PICTURE': 'image/jpeg',
                 'SOUND': 'audio/3gpp',
                 'VIDEO': 'video/3gpp',
                 'BINARYFILE': 'video/mpeg'}
                    #'BINARYFILE': 'application/octet-stream'}

CONVERTED_BINARY_TYPES_EXTENSIONS = {'SOUND': 'mp3',
                                    'VIDEO': 'flv'}

CONVERTED_CONTENT_TYPES = {'SOUND': 'audio/mpeg',
               'VIDEO': 'video/x-flv'}

DEFAULT_PATIENT_ID = "500"

# Client Methods
def get_client(client_guid, username, password):
    pass

def post_client(client_guid, username, password):
    pass

# Procedure Methods
# A Procedure is a blank assessment form containing no patient data.
def get_procedure_list():
    """Retrieve list of all available Procedures."""
    message = models.Procedure.objects.values('procedure_guid', 'title')
    result = True
    return result, message

def get_procedure(procedure_id=None):
    """Retrieve a particular Procedure or all Procedures."""
    # TODO -EW ? do we really want to store the entire procedure 
    if procedure_id == None:
        procedure = models.Procedure.objects.all()
        result = True
    else:
        try:
            db_procedure = models.Procedure.objects.get(pk=procedure_id)
            with open(db_procedure.xml.path, 'rb') as f:
                procedure = f.read()
            result = True
        except ObjectDoesNotExist as e:
            procedure = ""
            result = False
    return result, procedure

def put_procedure(username, password):
    """Add a new Procedure to the collection or update an existing Procedure."""

    # TODO (blakeney):  Enable RESTful upload of procedures
    pass

# Patient Methods
# A Patient is an individual person receiving health care through this system.

def get_patient_list(username, password):
    """Retrieve a list of all Patients in the EMR."""

    result = True
    #mrs = get_emr(username, password, settings.OPENMRS_SERVER_URL)
    #validate on create here
    emrs = openmrs.OpenMRS(username, password, settings.OPENMRS_SERVER_URL)
    message = emrs.get_all_patients()
    """
    if mrs.validate_credentials(username, password):
        message = mrs.get_all_patients()
    else:
        result = False
        message = "Could not connect to EMR."
    """
    return result, message

def get_patient(patient_guid, username, password):
    """Retrieve essential biographical information about a Patient."""
    result = True
    emrs = openmrs.OpenMRS(username, password, settings.OPENMRS_SERVER_URL)
    message = emrs.get_patient(patient_guid)
    #omrs = openmrs.OpenMRS(username, password, settings.OPENMRS_SERVER_URL)
    """
    if mrs.validate_credentials(username, password):
        message = mrs.get_patient(patient_id)
        print "api.get_patient: %s" % message
    else:
        result = False
        message = "Could not connect to EMR."
    """
    return result, message

def post_patient(pt_data, username, password):
    """Set or update essential biographical information about a Patient."""
    emrs = openmrs.OpenMRS(username, password, settings.OPENMRS_SERVER_URL)
    message = emrs.create_patient(pt_data)
    pass

def put_patient(username, password):
    """Set or update essential biographical information about a Patient."""
    pass

# Encounter Methods
# An Encounter is an assessment form completed at a particular time
# about a particular Patient.
def get_encounter_list(username, password):
    """Retrieve list of all completed assessments."""
    result = True
    # TODO pull from OpenMRS
    message = models.Encounter.objects.values('guid', 'procedure_guid', 'client')
    return result, message

def get_encounter(encounter_id, client=None):
    """Retrieve a particular Encounter."""
    # TODO:  Make MDS pull from OpenMRS if record missing
    # TODO (blakeney):  Require authentication
    # url = settings.OPENMRS_SERVER_URL
    result = True
    encounter = models.Encounter.objects.get(guid=encounter_id)
    return result, encounter

#TODO replace with
#def post_encounter(encounter_id, procedure_id, client_id, responses, upload=None, logger=None):

def post_encounter(encounter_id, procedure_id, client_id, username, password, responses, uploads={}, logger=None):
    """Accept a new Encounter. It will get stored and uploaded if ready"""
    
    # Note that encounter_id is generated by the client.  This produces
    # a (negligible) possibility of a collision for a well-behaved client.
    # This will fail with an error if a collision occurs.
    # Assume that each client will not have two encounters with same id 
    encounter, new = models.Encounter.objects.get_or_create(guid=encounter_id,
                                                            procedure_guid=procedure_id,
                                                            client_id=client_id)
    # responses can be updated
    encounter.responses = responses
    #TODO add uploads to encounter model
    #enounter.uploads = uploads
    encounter.save()
    # if new set any additional values and create any stubs
    if new:
        """
        # TODO Do we really need this? Probably not
        encounter.upload_username = username
        encounter.upload_password = password # Better to avoid
        encounter.save()
        """
        if not logger is None:
            logger.debug("api.post_encounter(): new encounter: %s" % encounter.guid)
        # Assuming here that we only want to create for new encounters
        # Replacing the previous API:
        #   upload = {{ 'label': '','filename':'', 'mediatype':'' }
        #             [,{'label': '','filename':'', 'mediatype':''}]}
        # Should get rid of a significant amount of response processing not 
        # relevant to sds. Will add slight amount of overhead on the client
        # for item in upload:
        # Xforms upload attr   Sana Android               BinaryResource
        # 'label'              Element id                 element_id 
        # 'filename'           an id in answer='id[,id]'  guid
        # 'mediatype'          'type' value               content_type
        for item in encounter.parse_response_binaries():               
            br, new_bin = models.BinaryResource.objects.get_or_create(
                                        procedure=encounter,
                                        element_id=item['label'] , 
                                        guid=item['filename'],
                                        content_type=item['mediatype'])
            # This should always be true for a new encounter
            if new_bin:
                br.create_stub()
            # Should always return false on a new encounter
            if not logger is None:
                logger.debug("api.post_encounter(): encounter: %s binary: %s" % 
                             (encounter.guid, br.guid))
                
    # check if it is ready to upload
    do_upload, message = encounter.upload()
    if not logger is None:
        logger.debug("api.do_upload=%s for encounter for id: %s" % 
                     (do_upload, encounter.guid))
    if do_upload:
        emrs = openmrs.OpenMRS(username, password, settings.OPENMRS_SERVER_URL)
        result, message = emrs.upload_encounter(encounter, username, password)
        # upload goes well update encounter and binary upload status
        if result:
            encounter.set_uploaded()
    else:
        # TODO better way than reset result here 
        result = True
    return result, message

# Binary Methods
# A Binary is a file or part of a file associated with a particular Encounter
# and containing additional information about a patient.  It may be an image,
# sound recording, video, or other supported media.
def get_content_type(mimetype=None, element_type=None):
    """ legacy support which returns a mime type for a ProcedureElement type """
    if mimetype is None:
        mediatype = CONTENT_TYPES.get(element_type,'application/octet-stream')
    else:
        mediatype = mimetype
    return mediatype

def get_binary_extension(content_type, element_type=None):
    """ Returns an extension for either a mime type as well as support for
        legacy ProcedureElement type by calling 
        get_binary_extension(None, element_type=val)
    """
    type = get_content_type(mimetype=content_type, element_type=element_type)
    extension = fextension(type)
    return extension
        
def get_binary(encounter, element_id, binary_guid):
    """ Gets a BinaryResource from the db """
    binary = models.BinaryResource.objects.get(element_id=element_id, encounter=encounter)
    return binary  
        
def get_or_create_binary(encounter, element_id, content_type, binary_guid, file_size):
    binary, is_new = models.BinaryResource.objects.get_or_create(
                                                        element_id=element_id,
                                                        encounter=encounter,
                                                        guid=binary_guid)
    binary.content_type = content_type
    binary.total_size = int(file_size)
    binary.save()
    return binary, is_new

def post_binary(binary, byte_start, byte_data):
    """Accept a binary file or packet.
        binary = binary to get the file to write to from
        byte_start = offset from beginning of file to begin writing
        byte_data = an iterable that contains data 
    """
    result = True
    message = 'EOF'
    # raise an exception if start is past end of file or return true with an
    # EOF marker
    if byte_start > binary.total_size:
        # TODO use a better exception
        raise Exception
    elif byte_start == binary.total_size:
        return result, message  
    
    # Open file
    with open(binary.data.path, "r+b") as dest:
        bytes_written = 0
        byte_start = int(byte_start)
        dest.seek(0, os.SEEK_END)
        # seek to byte start marker
        if dest.tell() != byte_start:
            dest.seek(byte_start, os.SEEK_SET)
        # Write bytes and throw if we go past end 
        for chunk in byte_data:
            logging.info("Writing chunk of size %d bytes." % len(chunk))
            dest.write(chunk)
            bytes_written += len(chunk)
            if dest.tell() > binary.total_size:
                # TODO use a better exception
                raise Exception
        dest.flush()
        if dest.tell() > binary.upload_progress:
            binary.upload_progress = dest.tell()
            binary.save()
        # EOF check
        if binary.upload_progress < binary.total_size:
            result = False
            message = ("Uploaded binary: %s, (%s/%s) pointer at %s" % 
                       (binary.pk, binary.upload_progress, binary.total_size, 
                        dest.tell()))
    return result, message

# Get rid of the distinction between binaries and binary chunks/packets.
#def post_binary_packet():
#    # Accept a packet of binary data.  Check whether it is the last one.
#    pass

# Notification Methods
# A Notification is a message sent to a Provider in reference to a particular
# Encounter.
def get_notification(notification_id):
    result = True
    if notification_id is None:
        message = "Null notification id"
        result = False
    else:
        try:
            message = models.Notification.objects.get(pk=notification_id)
        except models.Notification.DoesNotExist:
            message = "Notification does not exist"
            result = False
    return result, message

def send_notification(notification_type, destination, encounter_id, subject, message):
    """Cache and send a Notification through SMS/e-mail/etc.."""
    pass

# Location methods
def get_location(location_id, username, password):
    pass

def get_location_list(username, password):
    result = True
    emrs = openmrs.OpenMRS(username, password, settings.OPENMRS_SERVER_URL)
    locations = emrs.get_all_locations()
    logging.debug("Got locations from EMR: %s" % locations)
    if len(locations) > 0:
        message = locations
    else:
        message = "No locations available"
        result = False
    return result, message
    
