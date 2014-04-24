''' The paths for upstream dispatches 

@author: Sana Dev Team
Created on Jun 14, 2011
'''
from django.conf.urls.defaults import patterns
import handlers

urlpatterns = patterns(
    '',
    # Sana module permissions check
    (r'^moduleServlet/moca/permissionsServlet/',
        'status-read'),
    (r'^moduleServlet/moca/permissionsServlet/',
        'status-create'),
        
    # initial login
    (r'^loginServlet',
        'client-create'),
    (r'^loginServlet',
        'client-read'),
        
    # encounters
    (r'^modules/moca/encounterViewer.form',
        'encounter-read-item'),
    (r'^modules/moca/queue.form',
        'encounter-read'),
    (r'^moduleServlet/moca/uploadServlet',
        'encounter-create'),
    (r'^admin/encounters/encounter.form',
        'encounter-update'),
        
    # patient
    (r'^admin/patients/newPatient.form',
        'subject-create'),
    (r'^moduleServlet/restmodule/json/patient/',
        'subject-read'),
    (r'^moduleServlet/restmodule/json/patient/',
        'subject-read-json'),
    (r'^moduleServlet/restmodule/api/patient/',
        'subject-read-xml'),
    
    (r'^moduleServlet/restmodule/api/patient/',
        'subject-read-name'),
    (r'^moduleServlet/restmodule/json/findPatient/',
        'subject-read-json-name'),
    (r'^moduleServlet/restmodule/api/patient/',
        'subject-read-xml-name'),
    )

# This will be removed soon
paths = {
    'client' : {
        'GET' : { 'all' : 'moduleServlet/moca/permissionsServlet'},
        'POST': {},
        'PUT':{},
        'DELETE':{}
        },
    'encounter' : {
        'GET':{'id': {'json':'modules/moca/encounterViewer.form'},
               'list' :{'all':'modules/moca/queue.form'}},
        'POST': {'all' : 'moduleServlet/moca/uploadServlet', },
        'PUT' :{'all': 'admin/encounters/encounter.form'},#encounterId
        'DELETE':{}
        }, 
    'patient':{
        'GET':{'name': {'json':'moduleServlet/restmodule/json/findPatient/',
                        'xml':'moduleServlet/restmodule/api/findPatient/',},
                'id': {'json':'moduleServlet/restmodule/json/patient/',
                       'xml':'moduleServlet/restmodule/api/patient/',}},
        'POST': {'all' : 'admin/patients/newPatient.form',},
        'PUT':{},
        'DELETE':{}
        },
    'status' : {
        'GET': {'omrs': 'loginServlet/',} ,
        'POST': {},
        'PUT':{},
        'DELETE':{}
        },
    } 