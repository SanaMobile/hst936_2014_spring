from __future__ import with_statement
import logging

try:
    import json
except ImportError:
    import simplejson as json

from django import forms
from django.core.mail import send_mail
from django.conf import settings

from piston.handler import BaseHandler
from piston import resource
from piston.utils import rc
from piston.decorator import decorator

from moca.mrs.util import enable_logging

from moca.mrs import api, models, openmrs, patient, sms


def fail(data):
    response = {'status': 'FAILURE',
                'data': data}
    logging.debug("fail() %s" % json.dumps(response))
    return response

def succeed(data):
    response = {'status': 'SUCCESS',
                'data': data}
    logging.debug("succeed() %s" % json.dumps(response))
    return response

def validate(v_form, operations=None):
    if operations is None:
        operations = ['POST']
    @decorator
    def wrap(f, self, request, *a, **kwa):
        op_dicts = [getattr(request, operation) for operation in operations]
        form = v_form(*op_dicts)

        if form.is_valid():
            return f(self, request, *a, **kwa)
        else:
            logging.debug("Request failed due to form validation error. Printing %s" % operations)
            for operation, op_dict in zip(operations, op_dicts):
                logging.debug("Printing %s" % operation)
                for k,v in op_dict.items():
                    logging.debug("%s: %s" % (k,v))
            return fail("Missing required values.")
    return wrap


class ClientHandler(BaseHandler):
    model = models.Client
    fields = ('name')
client_resource = resource.Resource(ClientHandler)

class StatusHandler(BaseHandler):
    allowed_methods = ('GET',)

    def read(self, request):
        username = request.REQUEST.get("username", None)
        password = request.REQUEST.get("password", None)
        logging.info("Status handler checking whether username: "
                     "%s and password: %s are valid." % (username, password))

        omrs = openmrs.OpenMRS(username,password,
                          settings.OPENMRS_SERVER_URL)
        if omrs.validate_credentials(username, password):
            status = json.dumps({'api_version': "1.0",})
            return succeed(status)

        return fail("username and password combination incorrect")
status_resource = enable_logging(resource.Resource(StatusHandler))

class NotificationForm(forms.Form):
    # TODO(XXX) Use a ModelForm? Type and destinations are the only thing that
    # differ.
    type = forms.ChoiceField(choices=((u'SMS', u'SMS'),
                                      (u'EMAIL', u'EMAIL')))
    patient_id = forms.CharField()
    case_identifier = forms.CharField()
    destination = forms.CharField()
    email_subject = forms.CharField(required=False)
    message = forms.CharField()

class NotificationHandler(BaseHandler):
    allowed_methods = ('GET','POST',)
    fields = ('client', 'patient_id', 'procedure_id', 'message', 'delivered',)
    model = models.Notification

    def read(self, request, notification_id):
        try:
            return models.Notification.objects.get(pk=notification_id)
        except models.Notification.DoesNotExist:
            return rc.NOT_FOUND

    @validate(NotificationForm)
    def create(self, request):
        # Form validated so we don't have to check any arguments.
        notification_type  = request.POST.get('type')
        patient_id = request.POST.get('patient_id')
        case_identifier = request.POST.get('case_identifier')
        message = request.POST.get('message')
        destination = request.POST.get('destinations')

        n = models.Notification(patient_id=patient_id,
                                client=destination,
                                procedure_id=case_identifier,
                                message=message,
                                delivered=False)


        if notification_type == 'SMS':
            sms.send_sms_notification(n)
        elif notification_type == 'EMAIL':
            subject = request.POST.get('email_subject')
            try:
                send_mail(subject, n.message, 'moca-specialist@mit.edu',
                          destination, fail_silently=False)
                n.delivered = True
            except Exception, e:
                logging.error("Could not send email: %s." % e)

        n.save()
        return n
notification_resource = enable_logging(resource.Resource(NotificationHandler))

class PatientHandler(BaseHandler):
    allowed_methods = ('GET',)
    model = models.Patient

    def read(self, request, patient_id=None):
        # Get patient by ID.
        username = request.REQUEST.get("username", None)
        password = request.REQUEST.get("password", None)
        logging.info("Setting up EMR connection. Username: %s Password: %s" % (username, password))
        omrs = openmrs.OpenMRS(username,password,
                               settings.OPENMRS_SERVER_URL)
        try:
            if patient_id is None:
                logging.info("Looking up all patients.")
                patients_xml = omrs.get_all_patients()
                logging.info("Got response from EMR: %s" % patients_xml)
                data = patient.parse_patient_list_xml(patients_xml)
            else:
                logging.info("Looking up patient id %s." % patient_id)
                patient_xml = omrs.get_patient(patient_id)
                logging.debug("Patient data returned by OpenMRS: %s" % patient_xml)
                data = patient.parse_patient_xml(patient_xml)
            response = succeed(data)
        except Exception, e:
            logging.error("Got error %s" % e)
            response = fail("couldn't get patient")
        return response
patient_resource = enable_logging(resource.Resource(PatientHandler))

class RoleHandler(BaseHandler):
    allowed_methods = ('GET',)

    def read(self, request):
        username = request.REQUEST.get("username", None)
        password = request.REQUEST.get("password", None)
        omrs = openmrs.OpenMRS(username,password,
                               settings.OPENMRS_SERVER_URL)
        roles = omrs.get_all_roles()
        logging.debug("Got roles from EMR: %s" % roles)
        if len(roles) > 0:
            response = succeed(roles)
        else:
            response = fail("could not get roles")
        return response
roles_resource = enable_logging(resource.Resource(RoleHandler))

class LocationHandler(BaseHandler):
    allowed_methods = ('GET',)

    def read(self, request):
        username = request.REQUEST.get("username", None)
        password = request.REQUEST.get("password", None)
        omrs = openmrs.OpenMRS(username,password,
                               settings.OPENMRS_SERVER_URL)
        locations = omrs.get_all_locations()
        logging.debug("Got locations from EMR: %s" % locations)
        if len(locations) > 0:
            response = succeed(locations)
        else:
            response = fail(locations)
        return response
locations_resource = enable_logging(resource.Resource(LocationHandler))

class ProcedureHandler(BaseHandler):
    allowed_methods = ('GET',)
    model = models.Procedure
    fields = ('title',)

    def read(self, request, procedure_id=None):
        if procedure_id is None:
            return models.Procedure.objects.all()
        else:
            try:
                procedure = models.Procedure.objects.get(pk=procedure_id)
                with open(procedure.xml.path, 'rb') as f:
                    procedure_data = f.read()
                return succeed(procedure_data)
            except models.Procedure.DoesNotExist, e:
                return rc.NOT_FOUND
procedure_resource = enable_logging(resource.Resource(ProcedureHandler))

class CaseSubmitForm(forms.Form):
    username = forms.CharField(max_length=256)
    password = forms.CharField(max_length=256)
    savedproc_guid = forms.CharField(max_length=512)
    procedure_guid = forms.CharField(max_length=512)
    responses = forms.CharField()
    phone = forms.CharField(max_length=255)

class CaseHandler(BaseHandler):
    allowed_methods = ('GET', 'POST')

    def read(self, request, case_id):
        pass

    @validate(CaseSubmitForm)
    def create(self, request):
        savedproc_guid = request.POST.get('savedproc_guid', None)
        procedure_guid = request.POST.get('procedure_guid', None)
        responses = request.POST.get('responses', None)
        phone = request.POST.get('phone', None)
        username = request.POST.get('username', None)
        password = request.POST.get('password', None)

        result, message = api.register_saved_procedure(savedproc_guid,
                                                       procedure_guid,
                                                       responses,
                                                       phone,
                                                       username,
                                                       password)
        if result:
            response = succeed(message)
            logging.info("Saved case text successfully.")
        else:
            response = fail(message)
            logging.info("Failed to save case text: %s" % message)
        return response
case_resource = enable_logging(resource.Resource(CaseHandler))

class BaseBinarySubmitForm(forms.Form):
    procedure_guid = forms.CharField(max_length=512)
    element_id = forms.CharField()
    element_type = forms.CharField()
    binary_guid = forms.CharField()
    file_size = forms.IntegerField()
    byte_start = forms.IntegerField()
    byte_end = forms.IntegerField()
    done = forms.BooleanField(initial=False, required=False)

class BinarySubmitForm(BaseBinarySubmitForm):
    byte_data = forms.FileField()

class BinaryHandler(BaseHandler):
    allowed_methods = ('POST')

    @validate(BinarySubmitForm, ['POST', 'FILES'])
    def create(self, request):
        case_guid = request.POST.get('procedure_guid', None)
        element_id = request.POST.get('element_id', None)
        element_type = request.POST.get('element_type', None)
        binary_guid = request.POST.get('binary_guid', None)
        file_size = request.POST.get('file_size', None)
        byte_start = request.POST.get('byte_start', None)
        byte_end = request.POST.get('byte_end', None)
        byte_data = request.FILES.get('byte_data', None)

        result, message = api.register_binary_chunk(case_guid,
                                                    element_id,
                                                    element_type,
                                                    binary_guid,
                                                    file_size,
                                                    byte_start,
                                                    byte_end,
                                                    byte_data.chunks())
        if result:
            response = succeed(message)
            logging.info("Successfully saved binary chunk: %s" % message)
        else:
            response = fail("Failed to save the binary chunk: %s" % message)
            logging.error("Failed to save binary chunk: %s" % message)
        return response
binary_resource = enable_logging(resource.Resource(BinaryHandler))

class TextSubmitForm(BaseBinarySubmitForm):
    byte_data = forms.CharField()

class BinaryTextHandler(BaseHandler):
    allowed_methods = ('POST',)

    @validate(TextSubmitForm)
    def create(self, request):
        case_guid = request.POST.get('procedure_guid', None)
        element_id = request.POST.get('element_id', None)
        element_type = request.POST.get('element_type', None)
        binary_guid = request.POST.get('binary_guid', None)
        file_size = request.POST.get('file_size', None)
        byte_start = request.POST.get('byte_start', None)
        byte_end = request.POST.get('byte_end', None)
        byte_data = request.POST.get('byte_data', None)

        # This hack submits byte_data as base64 encoded, so decode it.
        byte_data = byte_data.decode('base64')

        result, message = api.register_binary_chunk(case_guid,
                                                    element_id,
                                                    element_type,
                                                    binary_guid,
                                                    file_size,
                                                    byte_start,
                                                    byte_end,
                                                    [byte_data,])
        if result:
            response = succeed(message)
            logging.info("Successfully saved text-binary chunk: %s" % message)
        else:
            response = fail("Failed to save the text-binary chunk: %s" % message)
            logging.error("Failed to save text-binary chunk: %s" % message)
        return response
text_resource = enable_logging(resource.Resource(BinaryTextHandler))
