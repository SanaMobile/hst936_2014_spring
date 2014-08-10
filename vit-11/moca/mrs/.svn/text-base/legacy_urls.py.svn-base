from django.conf.urls.defaults import patterns, url

from moca.mrs import api_handlers

urlpatterns = patterns(
    '',

    url(r'^notifications/submit/$',
        'moca.mrs.legacy_api.notification_submit',
        name="moca-api-notification-submit"),

    url(r'^notifications/submit/email/$',
        'moca.mrs.legacy_api.email_notification_submit',
        name="moca-api-email-notification-submit"),

     url(r'^json/patient/list/$',
         'moca.mrs.legacy_api.patient_list',
         name="moca-json-patient-list"),

     url(r'^json/patient/(?P<id>[0-9-]+)/$',
         'moca.mrs.legacy_api.patient_get',
         name="moca-json-patient-get"),

    url(r'^json/validate/credentials/$',
        'moca.mrs.legacy_api.validate_credentials',
        name = "moca-json-validate-credentials"),

    url(r'^json/procedure/submit/$',
        'moca.mrs.legacy_api.procedure_submit',
        name="moca-json-procedure-submit"),

    url(r'^json/binary/submit/$',
        'moca.mrs.legacy_api.binary_submit',
        name="moca-json-binary-submit"),

    url(r'^json/binarychunk/submit/$',
        'moca.mrs.legacy_api.binarychunk_submit',
        name="moca-json-binarychunk-submit"),

    url(r'^json/textchunk/submit/$',
        'moca.mrs.legacy_api.binarychunk_hack_submit',
        name="moca-json-binarychunk-hack-submit"),

    )
