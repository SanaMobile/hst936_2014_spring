'''

@author: Sana Dev Team
Created on May 12, 2011
'''
from django.conf.urls.defaults import patterns
import handlers

urlpatterns = patterns(
    '', 
    
    (r'^notification/$',
        handlers.notification_resource,
        {},
        'notification'),
    
    )
