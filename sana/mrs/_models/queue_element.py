from django.db import models
from sana.mrs._models.encounter import *
from sana.mrs._models.procedure import *

__all__ = ["QueueElement"]

class QueueElement(models.Model):
    #TODO (winkler) document
    class Meta:
        app_label = 'mrs'

    procedure = models.ForeignKey(Procedure)
    encounter = models.ForeignKey(Encounter)

    finished = models.BooleanField()

    created = models.DateTimeField(auto_now_add=True)
    modified = models.DateTimeField(auto_now=True)

