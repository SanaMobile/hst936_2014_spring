from django.contrib import admin

admin.autodiscover()

from django.conf.urls.defaults import patterns, url, include
from piston.resource import Resource
from piston.authentication import HttpBasicAuthentication

from .handlers import *

basic_auth = HttpBasicAuthentication(realm="Tasks")
etask_handler = Resource(EncounterTaskHandler,authentication=basic_auth)
otask_handler = Resource(ObservationTaskHandler,authentication=basic_auth)

urlpatterns = patterns(    
    'tasks',
    url(r'^encounter/$', etask_handler, name='encounter-task-list'),
    url(r'^encounter/(?P<uuid>[^/]+)/$', etask_handler, name='encounter-task'),
    url(r'^observation/$', otask_handler, name='observation-task-list'),
    url(r'^observation/(?P<uuid>[^/]+)/$', otask_handler, name='observation-task'),

    )