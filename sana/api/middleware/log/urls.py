from django.conf.urls.defaults import *
from django.views.generic import list_detail
from sana.mrs.models import Log

list_args = {
    'queryset': Log.objects.all(),
    'template_name': 'log_list.html',
}

detail_args = {
    'queryset': Log.objects.all(),
    #'object_id': '1',
    'template_name': 'log_detail.html',
}

urlpatterns = patterns('',
    (r'^$', list_detail.object_list, list_args),
    (r'^(?P<object_id>\d+)$', list_detail.object_detail, detail_args)
)

