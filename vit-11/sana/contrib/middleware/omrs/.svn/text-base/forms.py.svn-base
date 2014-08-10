'''

@author: Sana Dev Team
Created on May 14, 2011
'''
import cjson
try:
    import json
except ImportError:
    import simplejson 
from collections import defaultdict
from datetime import datetime
from django import forms
from django.conf import settings

from sana.api.dispatch.forms import DispatchableForm
from sana.api import forms as apiforms

class ClientForm(apiforms.ClientForm):
    dispatch_excludes = ['uid','data','client']
    def _dispatch_username(self, data):
        return 'uname',data 
    
    def _dispatch_password(self, data):
        return 'pw',data
    
    def _postprocess_dispatch_data(self, data):
        ''' Override for additional processing '''
        data['redirect'] = '/openmrs'
        data['refererURL'] =  settings.OPENMRS_SERVER_URL+'index.htm'
        return dict(data)

class EncounterForm(apiforms.EncounterForm, DispatchableForm):
    
    dispatch_excludes = ['password','username']
    _files = {'binary': lambda (obs,id): 'medImageFile-{0}-{1}'.format(obs,id)}
    
    def _postprocess_dispatch_data(self,data):
        print 'omrs.encounter._postprocess_dispatch_data', data
        result = {'description': cjson.encode(data)}
        # TODO better way to handle file uploads
        files = defaultdict(list)
        binaries = cjson.decode(self.data.get('uploads','{}'))
        for binary in binaries:
            fname = str('%s/test/binary/%s' % (settings.MEDIA_ROOT, binary['filename']))
            files[str(binary['id'])].append(fname)
        for elt in data['questions']:
            eid = elt.get('id',None)
            if eid in files:
                for i,path in enumerate(files[eid]):
                    result['medImageFile-%s-%d' % (str(eid), i)] = open(path, "rb")
        return result
    
    def _dispatch_uid(self, data):
        return 'caseIdentifier', data
        
    def _dispatch_client(self, data):
        return 'clientId', data
        
    def _dispatch_date(self, data):
        return 'procedureDate', datetime.strftime(data, "%m/%d/%Y %H:%M")
        
    def _dispatch_subject(self,data):
        return 'patientId', data
        
    def _dispatch_procedure(self,data):
        return 'procedureTitle', data
        
    def _dispatch_observations(self,data):
        responses = data
        cleaned_observations = []
        
        if 'patientEnrolled' in responses:
            enrolled = responses.get('patientEnrolled').get('answer', 'Yes')
            del responses['patientEnrolled']
        else:
            enrolled = 'Yes'
        
        if 'procedureTitle' in responses:
            del responses['procedureTitle']
        for eid,attr in responses.items():
            if not eid in getattr(self.__class__, '_legacy',{}):
                attr['id'] = eid
            cleaned_observations.append(attr)
        return 'questions', cleaned_observations
        
    
    def _mutate_query(self, data):
        ''' Override for additional processing '''
        if 'caseIdentifier' in data:
            return {'encounterId': data }
        

class LegacyEncounterForm(apiforms.EncounterForm):    
    # Utility method to replace encounter legacy_parse responses
    def emit(self):
        self.full_clean()
        data = self.cleaned_data
        date = datetime.today()
        
        responses = cjson.decode(data['responses'])
        cleaned_responses = []
        
        # openmrs pt bio for new patients
        cleaned_pt = {}
    
        enrolled = 'Yes'
        enrolledtemp = responses.get('patientEnrolled', None)
        if enrolledtemp:
            enrolled = enrolledtemp.get('answer', 'Yes')
            del responses['patientEnrolled']
        
        
        title = None
        if responses.get('procedureTitle', None):
            title = responses.get('procedureTitle', None).get('answer')
            del responses['procedureTitle']
        
        cleaned_pt['birth_year'] = ""
        for eid,attr in responses.items():
            if (eid == "patientId"):
                cleaned_pt['patient_id'] = attr.get('answer')
            elif (eid == "patientGender"):
                cleaned_pt['patient_gender'] = _gender_abbrev[attr.get('answer')]
            elif (eid == 'patientFirstName'):
                cleaned_pt['patient_first'] = attr.get('answer')
            elif (eid == 'patientLastName'):
                cleaned_pt['patient_last'] = attr.get('answer')
            elif (eid == 'patientBirthdateDay'):
                cleaned_pt['birth_day'] = attr.get('answer')
            elif (eid == 'patientBirthdateMonth'):
                cleaned_pt['birth_month'] = attr.get('answer')
            elif eid == 'patientBirthdateYear' and cleaned_pt['birth_year'] == "":
                cleaned_pt['birth_year'] = attr.get('answer')
            else:
                attr['id'] = eid
            cleaned_responses.append(attr)
        
        mrs_post = {'phoneId': str(data['client_id']),
                     'procedureDate': datetime.strftime(date, "%m/%d/%Y %H:%M"),
                     'patientId': str(data['patient_id']),
                     'procedureTitle': data['procedure_id'],
                     'caseIdentifier': data['encounter_id'],
                     'questions': cleaned_responses }
        mrs_clean = {'description': str(cjson.encode(mrs_post))}
        # get the file object defs
        # uploads => 'upload [,upload]}'
        # upload => '{ id: int(term), filename : str(term), mediatype: str(term) }'
        # Android client mapping
        # for item in uploads 
        #     elementId => item['id']
        #     binaryGuid => item['filename'] 
        #     content_type => item['mediatype']
        files = defaultdict(list)
        binaries = cjson.decode(data['uploads'])
        for binary in binaries:
            fname = str('%s/test/binary/%s' % (settings.MEDIA_ROOT, binary['filename']))
            files[str(binary['id'])].append(fname)
        for elt in mrs_post['questions']:
            eid = elt.get('id',None)
            if eid in files:
                for i,path in enumerate(files[eid]):
                    mrs_clean['medImageFile-%s-%d' % (str(eid), i)] = open(path, "rb")
        return mrs_clean
    
_gender_abbrev = {'Male':'M', 'male':'M', 'M':'M', 'm':'M',
                   'Female':'F', 'female':'F', 'F':'F', 'f':'F' }

class SubjectForm(apiforms.SubjectForm, DispatchableForm):
    dispatch_excludes = ['client', 'format']
    
    def _dispatch_data(self,data):
        attrs = ('patient_year', 'patient_month','patient_day',
                 'patient_gender', 'patient_first', 'patient_last')
        def_dict = dict((k, '') for k in attrs)
        try:
            py_data = cjson.decode(data,True)#, all_unicode=True) if data else {}
        except Exception as e:
            py_data = {}
        return ('data',py_data)
        
    def _preprocess_dispatch_data(self, data):
        _name = data.get('name', {})
        birthdate = datetime(data.get('patient_year',2001),
                             data.get('patient_month',1),
                             data.get('patient_day',1))
        gender = _gender_abbrev[data.get('gender','M')]
        cleaned = { "name.givenName": _name.get('patient_first',''),
                    "name.familyName": _name.get('patient_last',''),
                    "identifier": data.get('uid',''),
                    "identifierType": 2,
                    "location": 1,
                    "gender": gender,
                    "birthdate": datetime.strftime(birthdate,"%m/%d/%Y")}
        return cleaned
    
    def _mutate_query(self,data):
        id = data.get('identifier',None)
        name = (data.get('name.givenName',''), data.get('name.familyName',''))
        if id:
            encoded = id
        elif name[0] or name[1]:
            encoded = '{0}+{1}'.format(*name)
        else:
            encoded = ''
        return str(encoded)

class StatusForm(apiforms.StatusForm, DispatchableForm):
    ''' Provides data formatting for check against Sana permissionsServlet '''
    dispatch_excludes = ('uid','data','client', 'format')
    def _dispatch_username(self, data):
        return 'username',data 
    
    def _dispatch_password(self, data):
        return 'password',data
    
    def _preprocess_args(self, data):
        ''' Override for additional processing '''
        return dict(data)

class NameForm(forms.Form):
    givenName = forms.CharField(max_length=255)
    familyName = forms.CharField(max_length=255)
    
class IdentifierForm(forms.Form):
    identifier = forms.CharField(max_length=255)
    type = forms.ChoiceField(choices=(1,'Old Identification Number',))
    
class PatientForm(forms.formsets.Form):
    pass

    