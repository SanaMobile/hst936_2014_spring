from django.conf.urls.defaults import patterns, include

urlpatterns = patterns(
    '',

    (r'^$',
     'django.views.generic.simple.direct_to_template',
     {'template': 'index.html'},
     'mrs_home'),

    (r'^api/',
     include('moca.mrs.api_urls')),

    (r'^legacy_api/',
     include('moca.mrs.legacy_urls')),

)
