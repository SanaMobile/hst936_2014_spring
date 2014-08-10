import logging

from django.contrib import admin

from moca.mrs.util import enable_logging
#from requestlog import util

from moca.mrs.models import Patient, Procedure, BinaryResource, SavedProcedure, Notification, QueueElement, Client
from moca.mrs import api

class ProcedureAdmin(admin.ModelAdmin):
    list_display = ('title', 'created')

class NotificationAdmin(admin.ModelAdmin):
    list_display = ('client', 'patient_id', 'procedure_id', 'message', 'delivered')

class SavedProcedureAdmin(admin.ModelAdmin):
    list_display = ('guid', 'procedure_guid', 'client', 'uploaded', 'created',)

    actions = ['upload_to_emr']

    #@util.enable_logging_with_request_at(1)
    @enable_logging
    def upload_to_emr(self, request, queryset):
        messages = []
        logging.info("User manually requested upload of cases: %s" % ', '.join([case.guid for case in queryset]))
        for case in queryset:
            logging.info("Attempting to upload case %s." % case.guid)
            messages.append(api.maybe_upload_procedure(case))
        total = len(queryset)
        message = ''
        if all([result for result,message in messages]):
            if total > 1:
                message = "%d cases uploaded successfully to the MRS: " % total
            else:
                message = "1 case uploaded successfully: "
        else:
            if total > 1:
                message = "Failed to upload some cases to the MRS: "
            else:
                message = "Failed to upload case to the MRS: "
        message += ', '.join([m for result, m in messages])
        self.message_user(request, message)
    upload_to_emr.short_description = "Upload selected procedures to Medical Records System"


class BinaryResourceAdmin(admin.ModelAdmin):
    list_display = ('guid', 'procedure', 'element_id', 'content_type', 'upload_progress', 'total_size', 'uploaded', 'created')

class ClientAdmin(admin.ModelAdmin):
    list_display = ('name', 'last_seen',)

admin.site.register(Client, ClientAdmin)
admin.site.register(Procedure, ProcedureAdmin)
admin.site.register(Patient)
admin.site.register(BinaryResource, BinaryResourceAdmin)
admin.site.register(SavedProcedure, SavedProcedureAdmin)
admin.site.register(Notification, NotificationAdmin)
admin.site.register(QueueElement)
