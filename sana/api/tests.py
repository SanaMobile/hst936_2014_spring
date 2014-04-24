'''

@author: Sana Dev Team
Created on May 20, 2011
'''
import unittest

from sana.api.middleware.http.handlers import HTTPOpener
try:    
    mds = HTTPOpener(host='127.0.0.1', port=8000)
except:
    print 'Fail obtaining mds opener..............'
    
class Test(unittest.TestCase):


    def setUp(self):
        pass


    def tearDown(self):
        pass


    def testName(self):
        pass


if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()