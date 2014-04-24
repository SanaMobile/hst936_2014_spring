'''

@author: Sana Dev Team
Created on May 18, 2011
'''
from django.conf.urls.defaults import patterns
import handlers

urlpatterns = patterns(
    '',
    (r'^login/(?P<host>)$',
        handlers.login_resource,
        {},
        'sana-api-middleware-http'),
        
    (r'^(?P<host>)$',
        handlers.http_resource,
        {},
        'sana-api-middleware-http'),
    
    (r'^/(?P<emitter_format>.+)$', 
        handlers.http_resource,
        {},
        'sana-api-middleware-http')
)