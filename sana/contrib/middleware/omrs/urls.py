'''

@author: Sana Dev Team
Created on May 12, 2011
'''
from django.conf.urls.defaults import patterns
import handlers

urlpatterns = patterns(
    '',
    (r'^client/$',
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
        
    (r'^subject/$',
        handlers.subject_resource,
        {},
        'subject'), 
    
    (r'^status/$',
        handlers.status_resource,
        {},
        'status'),
    )

