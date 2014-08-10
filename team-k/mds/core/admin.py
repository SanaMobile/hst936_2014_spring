"""Sana mDS Django admin interface

:Authors: Sana dev team
:Version: 2.0
"""

from django.contrib import admin
from django.forms.models import modelformset_factory
from .models import * 

class UuidHackInline(admin.StackedInline):

    def __init__(self,*args):
        super(admin.StackedInline,self).__init__(self,*args)
        self.queryset = Observation.objects.filter(encounter=request)
    #def formfield_for_foreignkey(self, db_field, request, **kwargs):
    #    if db_field.name == "uuid":
    #        #e = Enocunter.objects.get(pk=kwargs['object_id'])
    #        kwargs["id__exact"] = db_field
    #    return super(admin.StackedInline, self).formfield_for_foreignkey(db_field, request, **kwargs)

class DeviceAdmin(admin.ModelAdmin):
    readonly_fields = ['uuid']  
    list_display = ['name', 'uuid']
    list_filter = ['name',]

class ProcedureAdmin(admin.ModelAdmin):
    readonly_fields = ['uuid']
    list_display = ['title', 'author', 'uuid']

class RestAdmin(admin.TabularInline):
    app_label="REST Services"
    inlines = []

class RelationshipAdmin(admin.TabularInline):
    model = Relationship
    fk_name = 'to_concept'
    list_display_links = []
    
class ConceptAdmin(admin.ModelAdmin):
    inlines = [ 
        RelationshipAdmin,
        ]
    readonly_fields = ['uuid']  
    list_display = ['name', 'uuid']
    list_filter = ['name',]

class ObservationAdmin(admin.ModelAdmin):
    exclude = ('_complex_progress',)
    readonly_fields = ['_complex_size','uuid','value']
    list_display = ['question', 'concept','value', 'subject','device','modified', 'encounter']
    list_filter = ['node','concept', 'modified', 'encounter']

class ObservationInline(UuidHackInline):
    model = Observation
    #formset = modelformset_factory(Observation)
    #exclude = ('_complex_progress',)
    #readonly_fields = ['_complex_size','uuid','value']

class EncounterAdmin(admin.ModelAdmin):
    readonly_fields = ['uuid','device','procedure','subject','observer',]
    exclude = ['concept',]
    #inlines = [ ObservationInline,]
    list_display = ['subject', 'procedure', 'modified','uuid',"observer",]


class EncounterInline(admin.StackedInline):
    model = Encounter

class ObserverAdmin(admin.ModelAdmin):
    readonly_fields = ['uuid',]
    list_display = ['user', 'uuid']
    inlines = [ EncounterInline, ]


class SubjectAdmin(admin.ModelAdmin):
    readonly_fields = ['uuid',]
    list_display = ['system_id','given_name', 'family_name', 'uuid', "image"]

class SubjectInline(admin.StackedInline):
    model = Subject

class SurgicalSubjectAdmin(admin.ModelAdmin):
    readonly_fields = ['uuid',]
    list_display = ['system_id','given_name', 'family_name', 'uuid', "image"]

class LocationAdmin(admin.ModelAdmin):
    readonly_fields = ['uuid',]
    model = Location
    list_display = ('code','name',)
    #list_filter = ('name',)



    
admin.site.register(Concept, ConceptAdmin)
admin.site.register(Relationship)
admin.site.register(RelationshipCategory)
admin.site.register(Device, DeviceAdmin)
admin.site.register(Encounter, EncounterAdmin)
admin.site.register(Observation,ObservationAdmin)
admin.site.register(Location,LocationAdmin)
admin.site.register(Notification)
admin.site.register(Observer,ObserverAdmin)
admin.site.register(Procedure,ProcedureAdmin)
admin.site.register(Subject,SubjectAdmin)
admin.site.register(SurgicalSubject,SurgicalSubjectAdmin)
admin.site.register(Event)
admin.site.register(Surgeon)
admin.site.register(SurgicalAdvocate)

#admin.site.register(ClientEventLog, ClientEventLogAdmin)
