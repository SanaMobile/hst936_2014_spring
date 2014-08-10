from django.conf.urls.defaults import patterns, include

# Uncomment the next two lines to enable the admin:
import handlers
from django.contrib import admin
admin.autodiscover()
urlpatterns = patterns(
    '',
    
    (r'^binary/$',
        handlers.binary_resource,
        {},
        'binary'),
        
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
    
    (r'^procedure/$',
        handlers.procedure_resource,
        {},
        'procedure'),
    
    (r'^status/$',
        handlers.status_resource,
        {},
        'status'),
    )
from django.conf import settings
if settings.DEBUG:
    urlpatterns += patterns(
        '',
        (r'^echo/', include('sana.api.middleware.echo.urls', )),
        (r'^relay/', include('sana.api.middleware.http.urls',)),
)
