import urllib
import cookielib
import logging
import urllib2
import time
import sys
import traceback
from urlparse import urlparse
import telnetlib

try:
    import json
except ImportError:
    import simplejson as json

from django.conf import settings

from moca.mrs import MultipartPostHandler


class OpenMRS(object):

    """ OpenMRS EMR Backend implementation. """

    def __init__(self, username, password, url):
        self.username = username
        self.password = password
        self.url = url

        self.cookies = cookielib.CookieJar()

        try:
            self.opener = urllib2.build_opener(
                urllib2.HTTPCookieProcessor(self.cookies),
                MultipartPostHandler.MultipartPostHandler)
            
            logging.info("Enabling OpenMRS REST API Handler: url: %s username: %s password: %s" % (url, username, password))

            if len(self.cookies) == 0:
                self._login()

            self.auth_handler = urllib2.HTTPBasicAuthHandler()
            self.auth_handler.add_password("OpenMRS Rest API",
                                           self.url,
                                           self.username,
                                           self.password)
            self.rest_opener = urllib2.build_opener(self.auth_handler)
        except Exception, e:
            logging.error("Couldn't initialize openMRS interface, exception: %s" % e)
            raise e

    def validate_credentials(self, username, password):
        try:
            loginParams = urllib.urlencode(
                {"uname": self.username,
                 "pw": self.password,
                 "redirect": "/openmrs",
                 "refererURL": self.url+"index.htm"
                 })

            result = self.opener.open("%sloginServlet" % self.url, loginParams).geturl()

            if result==self.url + "index.htm":
                logging.info("Credentials validated.")
                return True
            else:
                logging.info("Credentials invalid.")
                return False
        except Exception, e:
            logging.error("Trying to validate credentials with openMRS, got exception: %s" % e)
            return False

    def validate_patient(self, patient_id):
        pass

    def create_patient(self, patient_id, first_name,
                       last_name, gender, birthdate):
        try:
            if len(self.cookies) == 0:
                self._login()

            # name.givenName
            # name.familyName
            # identifier
            # identifierType (use "2")
            # location (use "1")
            # gender (M or F)
            # birthdate ("03/03/2009" format)
            # action (use "save")

            parameters = {"name.givenName": first_name,
                          "name.familyName": last_name,
                          "identifier": patient_id,
                          "identifierType": 2,
                          "location": 1,
                          'gender': gender,
                          'birthdate': birthdate,}
            #parameters = urllib.urlencode(parameters)

            url = "%sadmin/patients/newPatient.form" % self.url

            logging.info("Creating new patient %s" % patient_id)
            self.opener.open(url, parameters)

        except Exception, e:
            logging.info("Exception trying to create patient: %s" % str(e))

    def get_patient(self, patient_id):
        target = ('%smoduleServlet/restmodule/api/patient/%s'
                  % (self.url, patient_id))
        logging.debug("OpenMRS.get_patient() calling %s" % target)
        request = self.opener.open(target)
        return request.read()

    def get_all_patients(self):
        request = self.opener.open('%smoduleServlet/restmodule/api/allPatients/'
                                   % self.url)
        return request.read()

    def get_all_roles(self):
        roles = []
        try: 
            request = self.opener.open(
                '%smoduleServlet/restmodule/json/roles/' % self.url)
            json_roles = request.read()
            logging.info("Got roles list from OpenMRS, about to parse: %s" 
                         % json_roles)
            roles = json.loads(json_roles)
        except Exception, e:
            logging.error("While trying to parse roles list from OpenMRS, "
                          "got exception: %s" % e)
        return roles

    def get_all_locations(self):
        locations = []
        try: 
            request = self.opener.open(
                '%smoduleServlet/restmodule/json/locations/' % self.url)
            json_locations = request.read()
            logging.info("Got locations list from OpenMRS, about to parse: %s" 
                         % json_locations)
            locations = json.loads(json_locations)
        except Exception, e:
            logging.error("While trying to parse locations list from OpenMRS, "
                          "got exception: %s" % e)
        return locations

    def _login(self):
        loginParams = urllib.urlencode(
            {"uname": self.username,
             "pw": self.password,
             "redirect": "/openmrs",
             "refererURL": self.url+"index.htm"
             })

        try:
            self.opener.open("%sloginServlet" % self.url, loginParams)
            logging.debug("Logged into OpenMRS")

            for cookie in self.cookies:
                logging.debug("Cookie: %s" % cookie)

            return True
        except Exception, e:
            logging.debug("Error logging into OpenMRS: %s" % e)
            print "Error logging into OpenMRS: %s" % e
            return False

    def upload_procedure(self, patient_id, phone_id,
                         procedure_title, saved_procedure_id,
                         responses, files):
        hasPermissions = False
        result = False
        mesage = ""
        try:
	    if len(self.cookies) == 0:
                self._login()

	    logging.debug("Validating permissions to manage moca queue")

            url = "%smoduleServlet/moca/permissionsServlet" % self.url
            response = self.opener.open(url).read()
            logging.debug("Got result %s" % response)

            try:
                response = json.loads(response)
                message = response['message']
                hasPermissions = response['status'] == 'OK'
            except Exception, e:
                logging.debug("Error decoding result: %s" % e)
                message = str(e)

            if not hasPermissions:
		return result, message

            logging.debug("Uploading procedure")

            # WARNING: Anything that goes into the POST must be ASCII only, not
            # Unicode. Otherwise MultipartPostHandler will barf because it
            # builds the HTTP POST by adding the file bytes to the request. If
            # it's unicode then Python will try to convert the file to Unicode
            # which will break half the time if the file happens to have Unicode
            # escape characters in it.

            description = {'phoneId': str(phone_id),
                           'procedureDate': time.strftime("%m/%d/%Y %H:%M"),
                           'patientId': str(patient_id),
                           'procedureTitle': str(procedure_title),
                           'caseIdentifier': str(saved_procedure_id),
                           'questions': responses}
            description = json.dumps(description)

            post = {'description': str(description)}
            logging.debug("Encoded parameters, checking files.")
            for elt in responses:
                etype = elt.get('type', None)
                eid = elt.get('id', None)

                if eid in files:
                    logging.info("Checking for files associated with %s" % eid)
                    for i,path in enumerate(files[eid]):
                        logging.info('medImageFile-%s-%d -> %s' % (eid, i, path))
                        # The str(eid) must be here for the reasons in the above
                        # warning.
                        post['medImageFile-%s-%d' % (str(eid), i)] = open(path, "rb")

            url = "%smoduleServlet/moca/uploadServlet" % self.url

            logging.debug("About to post to " + url)
            response = self.opener.open(url, post).read()
            logging.debug("Got result %s" % response)
            try:
                response = json.loads(response)
                message = response['message']
                result = response['status'] == 'OK'
            except Exception, e:
                logging.debug("Error decoding result: %s" % e)
                message = str(e)
            logging.debug("Done with upload")
        except Exception, e:
            exceptionType, exceptionValue, exceptionTraceback = sys.exc_info()
            tb = traceback.format_exc(exceptionTraceback)
            logging.error("Exception in uploading procedure: %s" % e)
            logging.error("Traceback: %s" % tb)
            message = str(e)
        return result, message
