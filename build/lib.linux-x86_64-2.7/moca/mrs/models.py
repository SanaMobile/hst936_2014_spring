try:
    import json
except ImportError:
    import simplejson as json

from django.db import models

GUID_LENGTH = 512

class Client(models.Model):
    """ Some arbirary way to refer to a client."""
    name = models.CharField(max_length=512)
    last_seen = models.DateTimeField(auto_now=True)

    def touch(self):
        self.save()

    def __unicode__(self):
        return self.name

class Patient(models.Model):
    name = models.CharField(max_length=512)

    # the remote record identifier for the Patient, i.e. OpenMRS ID
    remote_identifier = models.CharField(max_length=1024)

    created = models.DateTimeField(auto_now_add=True)
    modified = models.DateTimeField(auto_now=True)

class Procedure(models.Model):
    title = models.CharField(max_length=255)
    xml = models.FileField(upload_to='procedure')

    created = models.DateTimeField(auto_now_add=True)
    modified = models.DateTimeField(auto_now=True)

class BinaryResource(models.Model):

    def __unicode__(self):
        return "BinaryResource %s %s %d/%d" % (self.procedure.guid,
                                               self.created,
                                               self.upload_progress,
                                               self.total_size)

    # the instance of a procedure which this binary resource is associated with
    procedure = models.ForeignKey('SavedProcedure')

    # the element id to which this binary resource applies
    element_id = models.CharField(max_length=255)

    guid = models.CharField(max_length=GUID_LENGTH)

    content_type = models.CharField(max_length=255)
    data = models.FileField(upload_to='binary/%Y/%m/%d', )

    # the current number of bytes stored for the resource
    upload_progress = models.IntegerField(default=0)
    # the binary size in bytes
    total_size = models.IntegerField(default=0)

    # Whether the binary resource was uploaded to a remote queueing
    # server.
    uploaded = models.BooleanField(default=False)

    created = models.DateTimeField(auto_now_add=True)
    modified = models.DateTimeField(auto_now=True)

    def receive_completed(self):
        """ Indicates whether the client has uploaded this entire
        binary resource to the MDS."""
        return self.total_size > 0 and self.total_size == self.upload_progress

    def filename(self):
        return "%s_%s" % (self.procedure.guid, self.element_id)

    class Meta:
        unique_together = (('procedure', 'element_id', 'guid'),)

class SavedProcedure(models.Model):

    def __unicode__(self):
        return self.guid

    guid = models.CharField(max_length=GUID_LENGTH)

    # GUID of the procedure this is an instance of
    procedure_guid = models.CharField(max_length=GUID_LENGTH)

    # ID of the reporting phone
    client = models.ForeignKey('Client')

    # Text responses of the saved procedure
    responses = models.TextField()

    # OpenMRS login credentials for this user
    upload_username = models.CharField(max_length=512)
    upload_password = models.CharField(max_length=512)

    # Whether the saved procedure was uploaded to a remote queueing
    # server.
    uploaded = models.BooleanField(default=False)

    created = models.DateTimeField(auto_now_add=True)
    modified = models.DateTimeField(auto_now=True)

class Notification(models.Model):
    # some identifier that tells us which client it is (phone #?)
    client = models.CharField(max_length=512)
    patient_id = models.CharField(max_length=512)
    procedure_id = models.CharField(max_length=512)

    message = models.TextField()
    delivered = models.BooleanField()

    def to_json(self):
        return json.dumps({
            'phoneId': self.client,
            'message': self.message,
            'procedureId': self.procedure_id,
            'patientId': self.patient_id
            })

class QueueElement(models.Model):
    procedure = models.ForeignKey('Procedure')
    saved_procedure = models.ForeignKey('SavedProcedure')

    finished = models.BooleanField()

    created = models.DateTimeField(auto_now_add=True)
    modified = models.DateTimeField(auto_now=True)

class RequestLog(models.Model):
    """
    Logging facility for requests.
    """

    def __unicode__(self):
        return "%s : %s : Duration: %0.4fs" % (self.uri,
                                 self.timestamp.strftime("%m/%d/%Y %H:%M:%S"),
                                 self.duration)

    # max keylength of index is 767
    uri = models.CharField(max_length=767)
    message = models.TextField()
    timestamp = models.DateTimeField(auto_now_add=True)
    duration = models.FloatField()

