from django.db import models

from sana.mrs._models.client import Client

__all__ = ["TIME_FORMAT", "Log", "RequestLog", "ClientEventLog"]

TIME_FORMAT = "%m/%d/%Y %H:%M:%S"

class Log(models.Model):
    """Generic Logging Facility."""

    class Meta:
        app_label = 'mrs'

    object_type = models.CharField(max_length=256)
    object_id = models.CharField(max_length=256)
    buffer = models.TextField()
    created = models.DateTimeField(auto_now_add=True)
    modified = models.DateTimeField(auto_now=True)

class RequestLog(models.Model):
    """
    Logging facility for requests.
    """

    class Meta:
        app_label = 'mrs'

    def __unicode__(self):
        return "%s : %s : Duration: %0.4fs" % (self.uri,
                                 self.timestamp.strftime(TIME_FORMAT),
                                 self.duration)

    # max keylength of index is 767
    uri = models.CharField(max_length=767)
    message = models.TextField()
    timestamp = models.DateTimeField(auto_now_add=True)
    duration = models.FloatField()

class ClientEventLog(models.Model):

    client = models.ForeignKey(Client)
    event_type = models.CharField(max_length=512)
    event_value = models.TextField()
    event_time = models.DateTimeField()

    encounter_reference = models.TextField()
    patient_reference = models.TextField()
    user_reference = models.TextField()

    created = models.DateTimeField(auto_now_add=True)
    modified = models.DateTimeField(auto_now=True)

    class Meta:
        app_label = 'mrs'
        unique_together = (('event_type', 'event_time'),)

