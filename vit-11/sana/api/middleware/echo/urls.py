'''

@author: Sana Dev Team
Created on May 18, 2011
'''
from django.conf.urls.defaults import patterns
import handlers

urlpatterns = patterns(
    '',
        
    (r'^binary/$',
        handlers.binary_resource, 
        {},
        'binary'),
        
    (r'^client/',
        handlers.client_resource, 
        {},
        'client'),
        
    (r'^encounter/$',
        handlers.encounter_resource,
        {},
        'encounter'), 

    (r'^notification/$',
        handlers.notification_resource,
        {},
        'notification'),
    
    (r'^procedure/$',
        handlers.procedure_resource,
        {},
        'procedure'),    
    
    (r'^status/$',
        handlers.status_resource,
        {},
        'status'),
        
    (r'^subject/$',
        handlers.subject_resource,
        {},
        'subject'),
    )