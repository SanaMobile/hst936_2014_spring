''' 
Http relay handlers.

@author: Sana Dev Team
Created on May 12, 2011
'''
from __future__ import with_statement

import cookielib, urllib, urllib2, cjson

from django.conf import settings

from piston.handler import BaseHandler
from piston import resource

from sana.contrib import MultipartPostHandler
from sana.api import status, APIResponse, API_VERSION, SUCCESS, FAIL

__all__ = ('HTTPOpener','HTTPHandler', 'http_resource')


_openers = {}

class HTTPOpener(object):
    ''' Thin wrapper around urllib2 '''
    def __init__(self,  host='127.0.0.1', port=80, path=None):
        parts = ['http:/', '{0}:{1}'.format(host,port)]
        if path:
            parts.append(path)
        self.root = '/'.join(parts)
        if not self.root.endswith('/'):
            self.root += '/'
        self.cookies = cookielib.CookieJar()
        if settings.DEBUG:
            print self.root
        self.opener = None
    
    def _build_openers(self, auth=None):
        # add the auth handler
        handlers = []
        # make sure we have post opener
        if auth:
            password_mgr = urllib2.HTTPPasswordMgrWithDefaultRealm()
            password_mgr.add_password(None, auth['uri'], auth['username'], auth['password'])
            handler = urllib2.HTTPBasicAuthHandler(password_mgr)
            handlers.append(handler)
            self.opener = urllib2.build_opener(*handlers)
            self.opener.open(auth['uri'])
            urllib2.install_opener(self.opener)
        if not self.opener:
            handlers.append(urllib2.HTTPCookieProcessor(self.cookies))
            handlers.append(MultipartPostHandler.MultipartPostHandler)
            self.opener = urllib2.build_opener(*handlers)
            urllib2.install_opener(self.opener)
            
    def _initialize(self, uri, auth=None):
        handlers = []
        self._build_auth_handlers(uri,auth, handlers)
        self._build_handlers(handlers)
        self._install_openers(handlers)
        
    def _build_handlers(self, handlers):
        # make sure we have post opener
        #handlers.append(urllib2.HTTPCookieProcessor(self.cookies))
        handlers.append(MultipartPostHandler.MultipartPostHandler)
        return handlers
            
    def _build_auth_handlers(self, uri, auth, handlers):
        # add the auth handler
        if not auth:
            return handlers
        password_mgr = urllib2.HTTPPasswordMgrWithDefaultRealm()
        password_mgr.add_password(None, uri, auth['username'], auth['password'])
        handler = urllib2.HTTPBasicAuthHandler(password_mgr)
        handlers.append(handler)
        
    def _install_openers(self, handlers):
        opener = urllib2.build_opener(*handlers)
        urllib2.install_opener(opener)     
    
    def login(self, path=None, auth=None):
        #Attempts login to server
        if len(self.cookies) > 0:
            return SUCCESS(API_VERSION)
        uri =  self.root + path if path else self.root
        password_mgr = urllib2.HTTPPasswordMgrWithDefaultRealm()
        password_mgr.add_password(None, uri, auth['username'], auth['password'])
        handler = urllib2.HTTPBasicAuthHandler(password_mgr)
        self.opener = urllib2.build_opener(handler)
        r = self.opener.open(uri,urllib.urlencode(auth))
        urllib2.install_opener(self.opener)
        
        #r = self.open(path, data=auth)
        return APIResponse(status.SUCCESS, r.read())
       
    def post(self, path='', data=None, auth=None):
        return self.open(path, data, auth)
        
    def get(self, path='', data=None, auth=None):
        ''' wrapper around open which appends data to url '''
        qpath = '{0}?{1}'.format(path, urllib.urlencode(data)) if data else path
        return self.open(qpath, auth=auth)
    
    def open(self, path='', data=None, auth=None):
        ''' Opens a url '''
        url = self.root + path if path else self.root
        if auth:
            self._initialize(url, auth)
        else:
            self._build_openers()
        try:
            if data:
                response = urllib2.urlopen(url,urllib.urlencode(data))
            else:
                response = urllib2.urlopen(url)
            return SUCCESS(response.read())
        except Exception as e:
            return FAIL(e.message)

def _open(host, port, path, data=None):
    if len(_openers) == 0 or not host in _openers :
        _openers[host] = HTTPOpener(host=host,port=port)
    return _openers[host].open(path=path, data=data)

def _login(host, port, path, data=None):
    if len(_openers) == 0 or not host in _openers :
        _openers[host] = HTTPOpener(host=host,port=port)
    return _openers[host].login(path=path, auth=data)

last = None
class HTTPHandler(BaseHandler):
        
    def create(self, request, **kwargs):
        return _open(request, **kwargs)
    
    def delete(self, request, **kwargs):
        return _open(request, **kwargs)
    
    def read(self, request, **kwargs):
        data = self.flatten_dict(getattr(request, 'GET'))
        host, port, path = _strip_host(data)
        _data = dict(list((str(k),str(v)) for k,v in data.items()))
        if len(_data) == 0:
            _data = None
        last = host if host else None
        q = urllib.urlencode(self.flatten_dict(getattr(request, 'GET')))
        #return HttpResponseRedirect('/api/echo/client/?' + q )
        #return HttpResponseRedirect('http://' + host )
        return _open(last,port,path +'?' +q)
    
    def update(self, request, **kwargs):
        return _open(request, **kwargs)
http_resource = resource.Resource(HTTPHandler)

# path --> user --> opener
_logins = {}

class LoginHandler(BaseHandler):
    
    def read(self, request, **kwargs):
        _d = self.flatten_dict(getattr(request, 'GET'))
        host, port, path = _strip_host(_d)
        data = dict(list((str(k),str(v)) for k,v in _d.items()))
        if len(data) == 0:
            data = None
        return _login(host, port, path, data=data)
login_resource = resource.Resource(LoginHandler)

def _strip_host(data, excludes=('emitter_format', 'format')):
    host = data.pop('host') if 'host' in data else  None
    port = data.pop('port') if 'port' in data else  None
    path = data.pop('path') if 'path' in data else  None
    for x in excludes:
        if x in data:
            data.pop(x)
    return host, port, path
