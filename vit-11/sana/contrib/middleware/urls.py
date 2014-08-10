from django.conf.urls.defaults import patterns, include
from django.contrib import admin
admin.autodiscover()
from django.conf import settings

if settings.DEBUG:
    urlpatterns = patterns(
        '',
            
        (r'^null/', include('sana.contrib.middleware.null.urls')),
        (r'^omrs/', include('sana.contrib.middleware.omrs.urls')),
        (r'^clickatell/', include('sana.contrib.middleware.clickatell.urls')),
        (r'^kannel/', include('sana.contrib.middleware.kannel.urls')),
        (r'^pushxmpp/', include('sana.contrib.middleware.pushxmpp.urls')),
)
    
