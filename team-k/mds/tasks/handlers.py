import logging
import cjson

from django.contrib.auth import authenticate

from piston.handler import BaseHandler

from .forms import *
from .models import *
from mds.api.decorators import logged
from mds.api.handlers import DispatchingHandler
from mds.api.responses import succeed, error



class StatusHandler(BaseHandler):
    model = Status
    
@logged
class EncounterTaskHandler(DispatchingHandler):
    allowed_methods = ('GET', 'POST')
    model = EncounterTask
    form = EncounterTaskForm
    fields = ("uuid",
              ("assigned_to",("uuid",)),
              ("subject",("uuid")),
              "status",
              "due_on",
              "procedure")
"""
class EncounterTaskHandler2(BaseHandler):
    model = EncounterTask
    fields = ("uuid",
              ("assigned_to",("uuid",)),
              "subject",
              "status",
              "due_on",
              "procedure")
              
    def read(self,request, uuid=None):
        observer_uuid = request.GET.get("observer",None)
        if observer_uuid:
            objects = []
            try:
                observer = Observer.objects.get(uuid=observer_uuid)
                objects = EncounterTask.objects.filter(assigned_to=observer)
            except:
                pass
            return succeed(objects)
        else:
            return succeed(BaseHandler.read(self,request))
    
    def create(self,request):
	obj = BaseHandler.create(self,request)
	# do soomething with the SMS here
	return obj
    
    def update(self,request):
	obj = BaseHandler.update(self,request)
	# DO something
	return obj
"""
class ObservationTaskHandler(BaseHandler):
    model = ObservationTask

    def read(self,request, uuid=None):
        observer_uuid = request.GET.get("observer",None)
        if observer_uuid:
            objects = []
            try:
                observer = Observer.objects.get(uuid=observer_uuid)
                objects = ObservationTask.objects.filter(assigned_to=observer)
            except:
                pass
            return succeed(objects)
        else:
            return succeed(BaseHandler.read(self,request))