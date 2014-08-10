'''

@author: Sana Dev Team
Created on May 24, 2011
'''
from __future__ import with_statement
import sys, traceback

from django.conf import settings
from django.core import urlresolvers

from piston.utils import decorator
from sana import api
from sana.api.fields import REQUEST, DISPATCHABLE

CRUD = {'GET':'read', 'POST':'create','PUT':'update', 'DELETE':'delete'}

class DispatchConf(object):
    ''' configures and manages the dispatchables <--> dispatcher mappings '''    
    def __init__(self, dispatchables={}):
        self.dispatchables = dispatchables
        self.handlers = {}
        for dispatchable, dispatcher in dispatchables.items():
            self.handlers[dispatchable] = '{0}.handlers'.format(dispatcher)
        self.ctx = None
        self.dispatcher = None
    
    def reload(self, dispatcher):
        self.dispatcher = dispatcher
        mod = __import__('{0}'.format(dispatcher), fromlist=['contexts'])
        self.ctx = getattr(mod, 'paths')
    
    def get_context(self, dispatcher, dispatchable, method='GET', format='all'):
        if not self.dispatcher or self.dispatcher != dispatcher:
            self.reload(dispatcher)
        p = self.ctx.get(dispatchable,{})
        m = p.get(method, {})
        return m.get(format,None)
    
    def get_dispatcher(self, dispatchable):
        return self.dispatchables.get(dispatchable, None)
dispatchconf = DispatchConf(dispatchables=settings.DISPATCHABLES)

def dispatch(operation='POST'):
    ''' Adds form attr to a request and is intended to handle all CRUD requests.
     
        Note: Only 'POST' requests will be validated via django's 
        Form.is_valid(). All other requests will treat the request data as the 
        initial parameter for the Form.__init__ method in essence parsing any 
        query strings.
    '''
    @decorator
    def wrap(f, handler, request, *a, **kwa):
        # gets the dispatchable form we will validate
        klass = handler.__class__
        if hasattr(klass, 'v_form'):
            v_form = getattr(klass, 'v_form')
        else:
            return api.ERROR(u'No valid dispatchable form')
        
        form = v_form(data=getattr(request, REQUEST.CONTENT))
        if operation == 'POST':
            if not form.is_valid():
                errs = dict((key, [unicode(v) for v in values]) for key,values in form.errors.items())
                return api.FAIL(errs)
        # set attributes
        setattr(request, 'dispatch_form', form)
        setattr(request, DISPATCHABLE.DATA, form.dispatch_data)
        return f(handler, request, *a, **kwa)
    return wrap

def dispatcher(klass):
    ''' Decorator indicating a class method will dispatch a dispatchable object.
        
        klass => A class that extends piston.handler.BaseHandler
        
        Looks first the 'dispatchable'. If not set, an attempt will be made to 
        look up the dispatchable based on the klass 'model' attribute
    '''
    def wrap(klass):
        ''' Verifies that a dispatchable attribute is set and sets the callback
            to use for dispatching requests upstreams. 
            
            The callback may be a NoneType if not set in settings.py
        '''
        if not hasattr(klass, 'dispatchable'):
            if hasattr(klass, 'model'):
                setattr(klass,'dispatchable', klass.model.__name__.lower())
            else:
                setattr(klass,'dispatchable', None)
                
        # get the crud handler callback which will dipatch upstream
        callback = mdispatch_handler(klass.dispatchable)
        setattr(klass, '_mdispatcher', callback)
    wrap(klass)
    return klass

def dispatch_reverse(namespace, dispatchable, method='read', 
                     dconf='dispatch_urls', format=None, suffix=None):
    ''' Looks up a middleware handler CRUD method
    
        namespace => the namespace of the handler
        dispatchable => the type of dispatchable that will be sent
        method => the CRUD method name
        dconf => a module name containing the name/url mappings formatted as
                 per the standard django urls.py
        format => use if multiple formats are supported; i.e json, xml
        suffix => an additional flag; implementation dependent
    '''
    if method not in CRUD.values():
        raise Exception
    urlconf = '.'.join((namespace,dconf))
    parts = [dispatchable,method,] 
    name = '-'.join(parts)
    if format:
        name+= '-' + format    
    if suffix:
        name+= '-' + suffix    
    resolver = urlresolvers.get_resolver(urlconf)
    try:
        return resolver.reverse(name)
    except Exception as e:
        tb = sys.exc_info()[2]
        for item in traceback.format_tb(tb):
            print 'dispatch_reverse:::' , item
    return ''

def mdispatch_handler(dispatchable):
    ''' Gets the middleware handler which will send the dispatchables upstream
        or None if not available.
    '''
    try:
        module = dispatchconf.get_dispatcher(dispatchable)
        uconf = '{0}.{1}'.format(module, 'urls')
        match = urlresolvers.reverse(dispatchable, urlconf=uconf)
        resource, _, _ = urlresolvers.get_resolver(uconf).resolve(match)
        return resource.handler
    except Exception as e:
        return None
