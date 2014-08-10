from django.db import models

from mds.api.utils import make_uuid
from mds.core.models import Encounter, Instruction, Subject, Observer, Procedure, Concept

def _now_plus(days):
    now = datetime.date.today()
    year = now.year if now.year < 12 else now.year + 1
    month = now.month +1 if now.month else 1
    day = now.day 
    return datetime.datetime(year,month,day)

class Status(models.Model):
    class Meta:
        verbose_name = "Task Status"
        verbose_name_plural = "Allowed status"
    current = models.CharField(max_length=128)
    #name = models.CharField(max_length=128)
    """A label for the task."""

    def __unicode__(self):
        return self.current


class Task(models.Model):
    assigned_to = models.ForeignKey(Observer)
    status = models.ForeignKey(Status)
    
    due_on = models.DateTimeField()
    """ updated on modification """
    
    created = models.DateTimeField(auto_now_add=True)
    """ When the object was created """
    
    modified = models.DateTimeField(auto_now=True)
    """ updated on modification """
    
class Subject_Visit(Task):
    subject = models.ForeignKey(Subject)
    procedure = models.ForeignKey(Procedure)

class EncounterTask(Task):
    """An encounter task that must be completed."""
    subject = models.ForeignKey(Subject, to_field='uuid')
    """Who the task will be executed on."""
    
    procedure = models.ForeignKey(Procedure, to_field='uuid')
    """What will be executed."""
    
    concept = models.ForeignKey(Concept, to_field='uuid')
    

class ObservationTask(Task):
    """A single instruction that must be executed for an encounter such as for
       follow up data collection related to the original encounter.
    """
    encounter = models.ForeignKey(Encounter, to_field='uuid')
    """Who or what the instruction will be executed on"""

class Notification(models.Model):
    pass    

