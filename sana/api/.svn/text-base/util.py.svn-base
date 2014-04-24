'''

@author: Sana Dev Team
Created on May 14, 2011
'''
from __future__ import with_statement
import logging, sys, traceback
try:
    import json
except ImportError:
    import simplejson as json

import httplib
from django.conf import settings

from piston.decorator import decorator

__all__ = ('fail', 'succeed', 'get_logger', 'validate', 'file_extension',
           'debug_api_response', 'debug_api_request', 'debug_error')

def api_response(response, success=None, failure=None):
    if response.code == httplib.OK:
        data = success if success else response.read()
        result = succeed(data) 
    else:
        data = failure if failure else response.read()
        result = fail(data)
    return result

def fail(data):
    ''' Fail response as a python dict with data '''
    response = {'status': 'FAILURE',
                'data': data}
    logging.debug("fail() %s" % json.dumps(response))
    return response

def succeed(data):
    ''' Success response as a python dict with data '''
    response = {'status': 'SUCCESS',
                'data': data}
    logging.debug("succeed() %s" % json.dumps(response))
    return response

def get_logger(request):
    # TODO(winkler) document
    if hasattr(request, 'LOG_ID'):
        log_name = 'Request' + '.' + str(getattr(request, 'LOG_ID'))
        logger = logging.getLogger(log_name)
    else:
        logger = logging.getLogger()
    return logger

# content type to extension dict
_extensions = {'application/octet-stream':'bin',
               'application/vnd.sensor.ecg':'csv',
               'application/x-shockwave-flash':'flv',
               'audio/3gpp': '3gp',
               'audio/mpeg': 'mp3',
               'audio/vnd.wav': 'wav',
               'image/gif':'gif',
               'image/jpeg':'jpg',
               'image/png':'png',
               'text/csv':'csv',
               'text/plain':'txt',
               'text/xml':'xml',
               'video/3gpp':'3gp',
               'video/mpeg': 'mpg'
               }
# Fall back mimetype if we don't get a match
_default_type = 'application/octet-stream'


def fname(obj):
    guid = obj.guid
    if hasattr(obj, 'content_type'):
        ext = fextension(content_type=obj.content_type)
    else:
        ext = 'txt'
    return '{0}.{1}'.format(guid,ext)

def fextension(content_type=None):
    """ Returns the file extension for the content type """ 
    return _extensions.get(content_type, _extensions[_default_type])

# separator line
_sep = lambda x: str(x)*80

def debug_api_form(form, debugs):
    ''' Appends mds.api form data to debug list '''
    debugs.append('form.is_valid(): %s' % form.is_valid())
    # should have data on post calls initial on others
    if form.data:
        debugs.append('form.data: %s' % form.data)
    elif form.initial:
        debugs.append('form.initial: %s' % form.initial)
    if hasattr(form,  'dispatch_data'):
        debugs.append('form.dispatch_data: %s %s' % (type(form.dispatch_data), form.dispatch_data))
    # add errors if present
    if form.errors:
        debugs.append('form.errors %s' % form.errors)

def debug_api_request(obj,method,request):
    ''' Prints some debug information to std out for mds.api requests '''
    if settings.DEBUG:
        debugs = [_sep('*')]
        klass = getattr(obj, '__class__', None)
        _hdr = ['debug_request',]
        if klass:
            _hdr.append(klass.__module__)
            _hdr.append(klass.__name__)
        _hdr.append(method)
        _h = '.'.join(_hdr)
        debugs.append(_h.upper())
        debugs.append(_sep('*'))
        if hasattr(request, 'form'):
            debug_api_form(request.form, debugs)
        else:
            debugs.append('No form')
        debugs.append(_h)
        debugs.append(_sep('*'))
        print '\n', '\n'.join(debugs)
        
def debug_api_response(obj, method, response):
    ''' Prints some debug information to std out for mds.api responses '''
    if settings.DEBUG:
        pkg = obj.__class__.__module__
        name = obj.__class__.__name__
        _hdr = ['debug_response',]
        _hdr.append(pkg)
        _hdr.append(name)
        _hdr.append(method)
        _h = '.'.join(_hdr)
        debugs = [_sep('*')]
        debugs.append(_h.upper())
        debugs.append(_sep('*'))
        _m = unicode('response.render: %s' % response.render())
        debugs.append(_m)
        debugs.append(_h)
        debugs.append(_sep('*'))
        print '\n', '\n'.join(debugs)
        
def debug_error(klass, f, e):
    if settings.DEBUG:
        t, v, tb = sys.exc_info()
        print u'\n--- BEGIN EXCEPTION ---'
        print u'%s.%s.type: %s' % (klass, f.__name__, t)
        print u'%s.%s.value: %s'% (klass, f.__name__, v)
        for item in traceback.format_tb(tb):
            print item
        print u'--- END EXCEPTION ---\n'
    
            