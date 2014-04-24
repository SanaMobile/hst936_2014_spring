"""Model representation of a client which connects to this server
"""
from django.db import models

__all__ = ["Client"]

class Client(models.Model):
    """ Some arbitrary way to refer to a client."""
    guid = models.CharField(max_length=255, unique=True)
    username = models.CharField(max_length=255, unique=True)
    password = models.CharField(max_length=255, unique=True)
    # when the client was registered
    created = models.DateTimeField(auto_now_add=True)
    # when the client was last seen
    last_seen = models.DateTimeField(auto_now=True)

    def __unicode__(self):
        return "Client: %s, %s" % (self.name, self.last_seen) 

    def touch(self):
        self.save()

