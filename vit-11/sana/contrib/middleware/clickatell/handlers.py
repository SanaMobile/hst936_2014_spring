'''

@author: Sana Dev Team
Created on May 12, 2011
'''
from __future__ import with_statement
import logging
import urllib
try:
    import json
except ImportError:
    import simplejson as json
    
from django.conf import settings
from django.core.mail import send_mail

from piston.handler import BaseHandler
from piston.utils import rc
from piston import resource

from sana.api.middleware.sms.util import format_sms
from sana.api.util import fail, succeed, get_logger, validate

class NotificationHandler(BaseHandler):
    ''' Handles notification requests. The field names which will be recognized 
    while handling the request:
        
        Allowed methods: GET, POST
    '''
    allowed_methods = ('GET','POST',)

    def create(self, request):
        # TODO(winkler) document
        request.full_clean()
        form = request.data
        try:
            messages = format_sms(form['message'])

            #http://api.clickatell.com/http/sendmsg?user=clark&password=4moc@mobile&api_id=3135262&to=16178770502&text=this+is+a+test

            for message in messages:
                params = urllib.urlencode({
                    'user': settings.CLICKATELL_USER,
                    'password': settings.CLICKATELL_PASSWORD,
                    'api_id': settings.CLICKATELL_API,
                    'to': form['recipient_addr'],
                    'text': message
                    })
                response = urllib.urlopen(settings.CLICKATELL_URI % params).read()
            result = succeed('message sent to: %s' % form['recipient_addr'])
        except Exception as e:
            logging.error("Clickatell send fail: %s: %s" % (form['recipient_addr'], e))
            return fail('message send fail: %s' % form['recipient_addr'])
        return result
    
    def read(self, request, notification_id=None):
        ''' Requests notifications cached and sent from this server '''
        pass
notification_resource = resource.Resource(NotificationHandler)
    