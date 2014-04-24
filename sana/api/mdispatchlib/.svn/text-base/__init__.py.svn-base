'''

@author: Sana Dev Team
Created on Jun 27, 2011
'''
from __future__ import with_statement
import sys, traceback

from django.conf import settings
from django.core import urlresolvers

from piston.utils import rc, decorator
from sana.api.util import debug_api_request

CRUD = {'GET':'read', 'POST':'create','PUT':'update', 'DELETE':'delete'}

def mDispatcher(klass):
    '''
    Decorator indicating a class method will dispatch an mDispatchable object.
    '''
    def wrap(klass):
        model = getattr(klass, 'model', None)
        # Retrieves and sets the dispatchable type if not explictly declared
        # from model
        if model:
            obj = getattr(model._meta, 'module_name', None)
            setattr(klass,'dispatchable', obj)
        else:
            obj = getattr(klass, 'dispatchable', None)
        # rest of the method meaningless if no dispatchable
        if not obj:
            return klass
        
        # check for dispatcher namespace registered for the dispatchable
        module = _mDConf.get_dispatcher(obj) if obj else None
        if not module:
            return klass
        
        # use the django urlconf mechanism to get the handler whose methods
        # we will remap to
        uconf = '{0}.{1}'.format(module, 'urls')
        match = urlresolvers.reverse(obj, urlconf=uconf)
        resource, _, _ = urlresolvers.get_resolver(uconf).resolve(match)
        handler = resource.handler
        
        # remap the methods
        allowed_methods = getattr(klass, 'dispatch_methods', [])
        for method in allowed_methods:
            f = CRUD[method]
            new_f = '_dispatch_'+f
            crud = getattr(handler, f)
            setattr(klass, new_f, crud)
    wrap(klass)
    return klass

class mDispatchConf(object):
    ''' configures and manages the mDispatchable to mDispatcher mappings '''    
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
_mDConf = mDispatchConf(dispatchables=settings.DISPATCHABLES)

def mDispatch_reverse(namespace, dispatchable, method='read', 
                     dconf='dispatch_urls', format=None, suffix=None):
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
    except:
        tb = sys.exc_info()[2]
        for item in traceback.format_tb(tb):
            print 'dispatch_reverse:::' , item
    return u''