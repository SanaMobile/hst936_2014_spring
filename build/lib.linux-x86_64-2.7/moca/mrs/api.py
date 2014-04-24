from __future__ import with_statement
import os
import logging
from collections import defaultdict

try:
    import json
except ImportError:
    import simplejson as json


from django.conf import settings

from moca.mrs.models import Procedure, SavedProcedure, Client, Patient, BinaryResource
from moca.mrs import openmrs


# api.py -- interface-agnostic API methods

BINARY_TYPES = ['PICTURE', 'SOUND', 'VIDEO', 'BINARYFILE']
BINARY_TYPES_EXTENSIONS = {'PICTURE': 'jpg',
                           'SOUND': 'mp3',
                           'VIDEO': 'mpg',
                           'BINARYFILE': 'mpg'}
CONTENT_TYPES = {'PICTURE': 'image/jpeg',
                 'SOUND': 'audio/wav',
                 'VIDEO': 'video/mpeg',
                 'BINARYFILE': 'video/mpeg'}
                 #'BINARYFILE': 'application/octet-stream'}
DEFAULT_PATIENT_ID = "500"

def register_saved_procedure(sp_guid, procedure_guid, responses,
                             client_id, username, password):
    logging.info("Registering saved procedure %s" % sp_guid)
    logging.debug("sp_guid:%s, procedure_guid:%s, responses:%s, client_id: %s"
                  % (sp_guid,procedure_guid,responses,client_id))

    client,_ = Client.objects.get_or_create(name=client_id)
    client.save()

    sp, created = SavedProcedure.objects.get_or_create(guid=sp_guid,
                                                       procedure_guid=procedure_guid,
                                                       client=client)

    if not created:
        logging.warning("SavedProcedure already exists! "
                        "We may be on an unstable connection.")

    sp.upload_username = username
    sp.upload_password = password

    sp.responses = responses
    sp.uploaded = False
    sp.save()

    try:
        responses_dict = json.loads(responses)

        for k,v in responses_dict.items():
            if v['type'] in BINARY_TYPES:
                items = v['answer'].split(',')

                for item in items:
                    if item == "":
                        continue

                    br,created = BinaryResource.objects.get_or_create(procedure=sp,
                                                                      element_id=k,
                                                                      guid=item)
                    br.total_size = 0
                    br.content_type = CONTENT_TYPES[v['type']]
                    br.save()
                    filename = "%s.%s" % (br.pk, BINARY_TYPES_EXTENSIONS[v['type']])
                    br.data = br.data.field.generate_filename(br, filename)
                    path, file_ = os.path.split(br.data.path)
                    if not os.path.exists(path):
                        os.makedirs(path)
                    open(br.data.path, "w").close()
                    logging.info("BinaryResource %s will have file %s"
                                 % (br.pk, br.data.path))
                    br.save()
    except Exception,e:
        logging.error("Error while trying to make stub BinaryResources: %s" % str(e))

    return maybe_upload_procedure(sp)

months_dict = {'January':'01', 'February':'02', 'March':'03', 'April':'04', 'May':'05', 'June':'06', 'July':'07', 'August':'08', 'September':'09', 'October':'10', 'November':'11', 'December':'12'}
def maybe_upload_procedure(saved_procedure):
    result = True
    message = ""
    logging.debug("Should I upload %s to the MRS?" % saved_procedure.guid)
    binaries = saved_procedure.binaryresource_set.all()

    ready = True
    for binary in binaries:
        if not binary.receive_completed():
            ready = False
    if not ready:
        message = ("%s has unreceived binary resources. Waiting."
                   % saved_procedure.guid)
        logging.debug(message)
        return result, message

    if not saved_procedure.uploaded:
        logging.debug("%s : uploading saved procedure text"
                      % saved_procedure.guid)
        # do the upload

    binaries_to_upload = [binary for binary in binaries if not binary.uploaded]
    files = defaultdict(list)
    for binary in binaries_to_upload:
        files[str(binary.element_id)].append(str(binary.data.path))

    if len(binaries_to_upload) != 1:
        logging.debug("Uploading a procedure with multiple or no binaries.")

    client_name = saved_procedure.client.name
    savedprocedure_guid = saved_procedure.guid

    responses = json.loads(saved_procedure.responses)

    cleaned_responses = []
    patient_id = ""
    patient_first = ""
    patient_last = ""
    patient_gender = ""
    patient_birthdate = ""
    patient_month = ""
    patient_day = ""
    patient_year = ""

    enrolledtemp = responses.get('patientEnrolled')
    enrolled = enrolledtemp.get('answer')

    procedure_title = ''
    procedure_title_element = responses.get('procedureTitle', None)
    if procedure_title_element:
        del responses['procedureTitle']
        procedure_title = procedure_title_element.get('answer', '')

    for eid,attr in responses.items():
        if (enrolled == "Yes"):
            if (eid == "patientId"):
                patient_id = attr.get('answer')
            elif (eid == "patientGender"):
                patient_gender = attr.get('answer')
            elif (eid == 'patientFirstName'):
                patient_first = attr.get('answer')
            elif (eid == 'patientLastName'):
                patient_last = attr.get('answer')
            elif (eid == 'patientBirthdateDay'):
                 patient_day = attr.get('answer')
            elif (eid == 'patientBirthdateMonth'):
                patient_month = attr.get('answer')
            elif (eid == 'patientBirthdateYear') and (patient_year==""):
                patient_year = attr.get('answer')
            else:
                attr['id'] = eid
                cleaned_responses.append(attr)
        else:
            if (eid == "patientIdNew"):
                patient_id = attr.get('answer')
            elif (eid == "patientGenderNew"):
                patient_gender = attr.get('answer')
            elif (eid == 'patientFirstNameNew'):
                patient_first = attr.get('answer')
            elif (eid == 'patientLastNameNew'):
                patient_last = attr.get('answer')
            elif (eid == 'patientBirthdateDayNew'):
                patient_day = attr.get('answer')
            elif (eid == 'patientBirthdateMonthNew'):
                patient_month = attr.get('answer')
            elif (eid == 'patientBirthdateYearNew'):
                patient_year = attr.get('answer')
            else:
                attr['id'] = eid
                cleaned_responses.append(attr)

    patient_birthdate = months_dict[patient_month] + "/" + patient_day + "/" + patient_year
    if patient_id is None:
        patient_id = DEFAULT_PATIENT_ID

    logging.debug("Uploading to OpenMRS: "
                  "%s %s %s %s %s (%s:%s)"
                  % (patient_id, client_name,
                     savedprocedure_guid, files, cleaned_responses,
                     saved_procedure.upload_username,
                     saved_procedure.upload_password))

    omrs = openmrs.OpenMRS(saved_procedure.upload_username,
                          saved_procedure.upload_password,
                          settings.OPENMRS_SERVER_URL)

    if patient_gender in ['Male', 'male', 'M', 'm']:
        patient_gender = 'M'
    elif patient_gender in ['Female', 'female', 'F', 'f']:
        patient_gender = 'F'

    omrs.create_patient(patient_id,
                        patient_first,
                        patient_last,
                        patient_gender,
                        patient_birthdate)

    result, message = omrs.upload_procedure(patient_id,
                                            client_name,
                                            procedure_title,
                                            savedprocedure_guid,
                                            cleaned_responses,
                                            files)

    if result:
        saved_procedure.uploaded = True
        saved_procedure.save()

        for binary in binaries_to_upload:
            binary.uploaded = True
            binary.save()

    return result, message

def register_binary(procedure_guid, element_id, data):
    try:
        sp = SavedProcedure.objects.get(guid=procedure_guid)
    except SavedProcedure.DoesNotExist:
        logging.error("Couldn't register binary %s for %s -- the saved "
                      "procedure does not exist."
                      % (element_id, procedure_guid))
        return

    binary, created = BinaryResource.objects.get_or_create(element_id=element_id,
                                                           procedure=sp)
    binary.data.save(data.name, data)
    binary.save()

    return maybe_upload_procedure(sp)

def register_binary_chunk(procedure_guid, element_id, element_type, binary_guid,
                          file_size, byte_start, byte_end, byte_data):

    """ byte_data should be an iterable that contains data """

    logging.info("Registering binary chunk for (%s,%s)"
                 % (procedure_guid, element_id))
    try:
        sp = SavedProcedure.objects.get(guid=procedure_guid)
    except SavedProcedure.DoesNotExist:
        message = ("Couldn't register binary chunk %s for %s -- the saved "
                      "procedure does not exist."
                      % (element_id, procedure_guid))
        logging.error(message)
        return False, message

    logging.info("Saved procedure %s fetched." % sp.pk)

    binary, created = BinaryResource.objects.get_or_create(element_id=element_id,
                                                           procedure=sp,
                                                           guid=binary_guid)

    logging.info("fetched binary %s, %s" % (binary.pk, created))
    if created:
        logging.error("Created new BinaryResource. NORMALLY SHOULDNT HAPPEN")
#         binary.content_type = element_type
#         binary.total_size = file_size
#         binary.upload_progress = 0
#         binary.save()
#         binary.data = binary.data.field.generate_filename(binary, binary.pk)
#         binary.save()

#         #filename = binary.data.field.generate_filename(binary, binary.pk)



#         #binary.data.save(binary.filename(), [])
#         #binary.data.save(binary.filename(), byte_data)
#         logging.info("Created new binary file %s with total size %d"
#                      % (binary.data.path, file_size))

    binary.content_type = element_type
    binary.total_size = int(file_size)
    bytes_written = 0
    byte_start = int(byte_start)
    byte_end = int(byte_end)

    try:
        with open(binary.data.path, "r+b") as dest:
            dest.seek(0, os.SEEK_END)
            logging.info("File pointer at %d (should be at %d)" % (dest.tell(), byte_start))
            if dest.tell() != byte_start:
                logging.error("Synchronization error! Client indicates offset "
                              "is %s while we have offset %s. Seeking to "
                              "appropriate location." % (dest.tell(), byte_start))
                dest.seek(byte_start, os.SEEK_SET)

            for chunk in byte_data:
                logging.info("Writing chunk of size %d bytes." % len(chunk))
                dest.write(chunk)
                bytes_written += len(chunk)
            dest.flush()
            current_position = dest.tell()
            logging.debug("Current file position is %s, current upload progress is %s"
                          % (current_position, binary.upload_progress))
            if current_position > binary.upload_progress:
                binary.upload_progress = current_position
    except Exception, e:
        message = "Error writing file: %s" % e
        logging.error(message)
        return False, message

    logging.info("Extended binary file %s with %s bytes"
                 % (binary.data.path, bytes_written))
    binary.save()

    return maybe_upload_procedure(sp)
