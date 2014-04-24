from django.conf.urls.defaults import patterns, url, include

# Uncomment the next two lines to enable the admin:
from django.contrib import admin
admin.autodiscover()

urlpatterns = patterns(
    '',

    (r'^log/', include('requestlog.urls')),
    (r'^admin/', include(admin.site.urls)),

    # Pass anything that doesn't match on to the mrs app
    url(r'^',
        include('moca.mrs.urls')),

)

from django.conf import settings
if settings.DEBUG:
    urlpatterns += patterns(
        '',
        (r'^static/(?P<path>.*)$',
         'django.views.static.serve',
         {'document_root': settings.MEDIA_ROOT}),
    )
