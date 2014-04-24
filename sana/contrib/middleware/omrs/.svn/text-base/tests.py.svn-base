'''
Created on May 11, 2011

@author: nojnk
'''
import unittest

from django.conf import settings

from sana.api.middleware.http.handlers import HTTPOpener

try:
    opener = HTTPOpener(host=settings.OPENMRS_SERVER_HOST, 
                        port=settings.OPENMRS_SERVER_PORT, 
                        path=settings.OPENMRS_PATH)
except:
    print 'Failed to get OpenMRS opener'

client_data = {}
encounter_data = {}
notification_data = {}
patient_data = {}
procedure_data = {}
status_data = {}

class Test(unittest.TestCase):
    
    def testClientHandler(self):
        pass
    
    def testNotificationHandler(self):
        pass

    def testPatientHandler(self):
        pass
    
    def testProcedureHandler(self):
        pass

    def testStatusHandler(self):
        pass
    

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()