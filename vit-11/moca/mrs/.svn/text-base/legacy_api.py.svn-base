from __future__ import absolute_import

try:
    import json
except ImportError, e:
    import simplejson as json

import logging
import urllib2
import telnetlib
import urllib

from django.conf import settings
from django.template import loader
from django.http import HttpResponse
from django import forms
from django.core.mail import send_mail
from django.shortcuts import render_to_response

from util import enable_logging

from moca.mrs import openmrs, patient, sms
from moca.mrs.api import register_saved_procedure, register_binary, register_binary_chunk
from moca.mrs.models import Notification

def render_json_template(*args, **kwargs):
    """Renders a JSON template, and then calls render_json_response()."""
    data = loader.render_to_string(*args, **kwargs)
    return render_json_response(data)

def render_json_response(data):
    """Sends an HttpResponse with the X-JSON header and the right mimetype."""
    resp = HttpResponse(data, mimetype=("application/json; charset=" +
                                        settings.DEFAULT_CHARSET))
    resp['X-JSON'] = data
    return resp

def json_fail(message):
    response = {
        'status': 'FAILURE',
        'data': message,
        }
    return json.dumps(response)

def json_succeed(data):
    response = {
        'status': 'SUCCESS',
        'data': data,
        }
    return json.dumps(response)

def validate_credentials(request):
    try:
        username = request.REQUEST.get("username", None)
        password = request.REQUEST.get("password", None)
        logging.info("username: " + username)
        logging.info("pasword: " + password)

        response = ''
        omrs = openmrs.OpenMRS(username,password,
                          settings.OPENMRS_SERVER_URL)
        if omrs.validate_credentials(username, password):
            response = json_succeed("username and password validated!")
        else:
            response = json_fail("username and password combination incorrect!")
        return render_json_response(response)
    except Exception, e:
        logging.error( "Exception in validate_credentials: " + str(e))

class ProcedureSubmitForm(forms.Form):
    username = forms.CharField(required=True, max_length=256)
    password = forms.CharField(required=True, max_length=256)
    savedproc_guid = forms.CharField(required=True, max_length=512)
    procedure_guid = forms.CharField(required=True, max_length=512)
    responses = forms.CharField(required=True)
    phone = forms.CharField(max_length=255)

@enable_logging
def procedure_submit(request):
    try:
        logging.info("Received saved procedure submission.")
        for key,value in request.REQUEST.items():
            print key,value

#    if request.method != 'POST':
#        return HttpResponse('get')

        form = ProcedureSubmitForm(request.REQUEST)

        response = ''
        if form.is_valid():
            result, message = register_saved_procedure(form.cleaned_data['savedproc_guid'],
                                                       form.cleaned_data['procedure_guid'],
                                                       form.cleaned_data['responses'],
                                                       form.cleaned_data['phone'],
                                                       form.cleaned_data['username'],
                                                       form.cleaned_data['password'])

            if result:
                response = json_succeed("Successfully saved the procedure: %s" % message)
                logging.info("Saved procedure successfully registerd.")
            else:
                response = json_fail(message)
                logging.error("Failed to register procedure: %s" % message)
        else:
            logging.error("Saved procedure submission was invalid, dumping REQUEST.")
            for k,v in request.REQUEST.items():
                logging.error("SavedProcedure argument %s:%s" % (k,v))
            response = json_fail("Could not parse submission : missing parts or invalid data?")

    except Exception, e:
        error = "Exception : %s" % e
        logging.error(error)
        response = json_fail(error)
    return render_json_response(response)

class BinaryChunkSubmitForm(forms.Form):
    procedure_guid = forms.CharField(required=True, max_length=512)
    element_id = forms.CharField(required=True)
    element_type = forms.CharField(required=True)
    binary_guid = forms.CharField(required=True)
    file_size = forms.IntegerField(required=True)
    byte_start = forms.IntegerField(required=True)
    byte_end = forms.IntegerField(required=True)

    #byte_data = forms.CharField(required=True)
    byte_data = forms.FileField(required=True)
    done = forms.BooleanField(initial=False, required=False)

@enable_logging
def binarychunk_submit(request):

    """Processes an individual chunk of binary data uploaded."""

    response = ''
    form = BinaryChunkSubmitForm(request.POST, request.FILES)

    if form.is_valid():
        logging.info("Received valid binarychunk form")

        procedure_guid = form.cleaned_data['procedure_guid']
        element_id = form.cleaned_data['element_id']
        element_type = form.cleaned_data['element_type']
        binary_guid = form.cleaned_data['binary_guid']
        file_size = form.cleaned_data['file_size']
        byte_start = form.cleaned_data['byte_start']
        byte_end = form.cleaned_data['byte_end']
        byte_data = form.cleaned_data['byte_data']

        try:
            result, message = register_binary_chunk(procedure_guid,
                                                    element_id,
                                                    element_type,
                                                    binary_guid,
                                                    file_size,
                                                    byte_start,
                                                    byte_end,
                                                    byte_data.chunks())
            if result:
                response = json_succeed("Successfully saved the binary chunk: %s" % message)
            else:
                response = json_fail("Failed to save the binary chunk: %s" % message)
        except Exception, e:
            logging.error("registering binary chunk failed: %s" % e)
            response = json_fail("Registering binary chunk failed: %s" % e)
        logging.info("Finished processing binarychunk form")
    else:
        logging.error("Received invalid binarychunk form")
        for k,v in request.REQUEST.items():
            if k == 'byte_data':
                logging.debug("%s:(binary length %d)" % (k,len(v)))
            else:
                logging.debug("%s:%s" % (k,v))
        response = json_fail("Could not parse submission. Missing parts?")
    return render_json_response(response)

class BinaryChunkHackSubmitForm(forms.Form):
    procedure_guid = forms.CharField(required=True, max_length=512)
    element_id = forms.CharField(required=True)
    element_type = forms.CharField(required=True)
    binary_guid = forms.CharField(required=True)
    file_size = forms.IntegerField(required=True)
    byte_start = forms.IntegerField(required=True)
    byte_end = forms.IntegerField(required=True)

    byte_data = forms.CharField(required=True)
    #byte_data = forms.FileField(required=True)
    done = forms.BooleanField(initial=False, required=False)


@enable_logging
def binarychunk_hack_submit(request):

    """Processes an individual chunk of binary data uploaded."""

    response = ''
    form = BinaryChunkHackSubmitForm(request.POST, request.FILES)

    if form.is_valid():
        logging.info("Received valid binarychunk-hack form")

        procedure_guid = form.cleaned_data['procedure_guid']
        element_id = form.cleaned_data['element_id']
        element_type = form.cleaned_data['element_type']
        binary_guid = form.cleaned_data['binary_guid']
        file_size = form.cleaned_data['file_size']
        byte_start = form.cleaned_data['byte_start']
        byte_end = form.cleaned_data['byte_end']
        byte_data = form.cleaned_data['byte_data']

        # This hack submits byte_data as base64 encoded, so decode it.
        byte_data = byte_data.decode('base64')

        try:
            result, message = register_binary_chunk(procedure_guid,
                                                    element_id,
                                                    element_type,
                                                    binary_guid,
                                                    file_size,
                                                    byte_start,
                                                    byte_end,
                                                    [byte_data,])
            if result:
                response = json_succeed("Successfully saved the binary chunk: %s" % message)
            else:
                response = json_fail("Failed to save the binary chunk: %s" % message)
        except Exception, e:
            logging.error("registering binary chunk failed: %s" % e)
            response = json_fail("Registering binary chunk failed: %s" % e)
        logging.info("Finished processing binarychunk form")
    else:
        logging.error("Received invalid binarychunk form")
        for k,v in request.REQUEST.items():
            if k == 'byte_data':
                logging.debug("%s:(binary length %d)" % (k,len(v)))
            else:
                logging.debug("%s:%s" % (k,v))
        response = json_fail("Could not parse submission. Missing parts?")

    logging.info("Sending response %s" % response)
    return render_json_response(response)


class BinarySubmitForm(forms.Form):
    procedure_guid = forms.CharField(required=True, max_length=512)
    element_id = forms.CharField(required=True)
    #data = forms.FileField(required=True)

@enable_logging
def binary_submit(request):
    response = ''

    form = BinarySubmitForm(request.REQUEST)
    data = request.FILES.get('data',None)

    if form.is_valid() and data:
        logging.info("Received a valid Binary submission form")
        element_id = form.cleaned_data['element_id']
        procedure_guid = form.cleaned_data['procedure_guid']

        register_binary(procedure_guid, element_id, data)

        response = json_succeed("Successfully saved the binary")
        logging.info("Done processing Binary submission form")
    else:
        logging.info("Received an invalid Binary submission form")
        response = json_fail("Could not parse submission. Missing parts?")

    return render_json_response(response)


class OpenMRSQueryForm(forms.Form):
    username = forms.CharField(required=True, max_length=256)
    password = forms.CharField(required=True, max_length=256)

@enable_logging
def patient_list(request):
    logging.info("entering patient list proc")
    username = request.REQUEST.get("username", None)
    password = request.REQUEST.get("password", None)
    omrs = openmrs.OpenMRS(username,password,
                           settings.OPENMRS_SERVER_URL)
    try:
        patients_xml = omrs.get_all_patients()
        data = patient.parse_patient_list_xml(patients_xml)
        logging.info("we finished getting the patient list")
        response = json_succeed(data)
    except Exception, e:
        logging.error("Got exception while fetching patient list: %s" % e)
        response = json_fail("Problem while getting patient list: %s" % e)
    return render_json_response(response)

@enable_logging
def patient_get(request, id):
    logging.info("entering patient get proc")
    username = request.REQUEST.get("username", None)
    password = request.REQUEST.get("password", None)
    omrs = openmrs.OpenMRS(username,password,
                           settings.OPENMRS_SERVER_URL)
    logging.info("About to getPatient")
    try:
        patient_xml = omrs.get_patient(id)
        data = patient.parse_patient_xml(patient_xml)
        response = json_succeed(data)
    except Exception, e:
        logging.error("Got error %s" % e)
        response = json_fail("couldn't get patient")
    logging.info("finished patient_get")
    return render_json_response(response)

@enable_logging
def notification_submit(request):
    phoneId = request.REQUEST.get("phoneIdentifier", None)
    text = request.REQUEST.get("notificationText", None)
    caseIdentifier = request.REQUEST.get("caseIdentifier", None)
    patientIdentifier = request.REQUEST.get("patientIdentifier", None)
    delivered = False

    logging.info("Notification submit received")

    for key,value in request.REQUEST.items():
        logging.info("Notification submit %s:%s" % (key,value))

    response = json_fail('Failed to register notification.')
    if phoneId and text and caseIdentifier and patientIdentifier:
        n = Notification(client=phoneId,
                         patient_id=patientIdentifier,
                         message=text,
                         procedure_id=caseIdentifier,
                         delivered=delivered)
        n.save()
        response = json_succeed('Successfully registered notification.')
        try:
            sms.send_sms_notification(n)
        except Exception, e:
            logging.error("Got error while trying to send notification: %s" % e)

    return render_json_response(response)

def email_notification_submit(request):
    addresses = request.REQUEST.get("emailAddresses",None)
    caseIdentifier = request.REQUEST.get("caseIdentifier", None)
    patientId = request.REQUEST.get("patientIdentifier", None)
    subject = request.REQUEST.get("subject", "")
    message = request.REQUEST.get("notificationText", "")

    logging.info("Email notification submit received")

    for key,value in request.REQUEST.items():
        logging.info("Notification submit %s:%s" % (key,value))

    response = json_fail('Failed to register email notification.')

    try:
        emailAddresses = json.loads(addresses)
    except Exception, e:
        response = json_fail('Got error when trying to parse email addresses.')

    if addresses and caseIdentifier and patientId:
	try:
            send_mail(subject, message, 'moca-specialist@mit.edu',
                      emailAddresses, fail_silently=False)
            response = json_succeed('Successfully registered email notification')
	except Exception, e:
            logging.error('Email could not be sent: %s' % e)
    return render_json_response(response)
