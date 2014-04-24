''' The paths for upstream dispatches 

@author: Sana Dev Team
Created on May 9, 2011
'''
from django.conf.urls.defaults import patterns

dispatch_patterns = patterns(
    # Sana module permissions check
    (r'^moduleServlet/moca/permissionsServlet/',
        None,
        {},
        'client-read'),
    # initial login
    (r'^loginServlet/',
        None,
        {},
        'status-create'),
    # encounters
    (r'^modules/moca/encounterViewer.form',
        None,
        {},
        'encounter-create-item'),
    (r'^modules/moca/queue.form',
        None,
        {},
        'encounter-get'),
    (r'^moduleServlet/moca/uploadServlet',
        None,
        {},
        'encounter-create'),
    (r'^admin/encounters/encounter.form',
        None,
        {},
        'encounter-update'),
        
    # patient
    (r'^admin/patients/newPatient.form',
        None,
        {},
        'patient-create'),
    (r'^moduleServlet/restmodule/json/patient/',
        None,
        {},
        'patient-read-item-id-json'),
    
    (r'^moduleServlet/restmodule/api/patient/',
        None,
        {},
        'patient-read-item-id-xml'),
    (r'^moduleServlet/restmodule/json/findPatient/',
        None,
        {},
        'patient-read-item-name-json'),
    (r'^moduleServlet/restmodule/api/findPatient/',
        None,
        {},
        'patient-read-item-name-xml'),
    )

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