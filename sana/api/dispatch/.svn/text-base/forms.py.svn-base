'''

@author: Sana Dev Team
Created on May 26, 2011
'''
from django import forms

class DispatchableForm:
    ''' Provides the form data necessary for dispatching '''
    _dexcludes = ['format', 'emitter_format', 'created', 'modified']
    
    username = forms.CharField(required=False)
    password = forms.CharField(required=False)
    
    @property
    def dexcludes(self):
        ''' List of parameters not needed by subsequent dispatchers.
            @see DispatchableForm._dexcludes for default params which
            will be removed 
        '''
        excludes = getattr(self.__class__, 'dispatch_excludes', [])
        return DispatchableForm._dexcludes + excludes
        
    def _get_args(self):
        ''' Returns all of the forms field data regardless validation 
            Defaults to form 'data' if available else returns any form 
            'initial' values
        '''
        if hasattr(self,'cleaned_data'):
            return getattr(self,'cleaned_data')
        elif self.data:
            return self.data
        else:
            return getattr(self,'initial',{})
    
    # raw unprocessed parameters
    args = property(fget=_get_args)
    
    def _process_attr(self, attr, val):
        return getattr(self, '_dispatch_' + attr, lambda x: (attr, x))(val)
    
    def _preprocess_dispatch_data(self, args):
        ''' Strips out any parameters that need to be excluded from upstream 
            services. @see DispatchableForm._dexcludes for default params which
            will be removed
        '''
        _data = []
        for arg,data in args.items():
            if not arg in self.dexcludes:
                _data.append(self._process_attr(arg, data))
        return dict(_data) if _data else {}
    
    def _postprocess_dispatch_data(self, data):
        ''' Override for modifying dispatch data '''
        return data
    
    def _get_dispatch_data(self):
        ''' getter for dispatch_data '''
        data = self._preprocess_dispatch_data(self.args)
        return self._postprocess_dispatch_data(data)
    
    # What should get passed to upstream services
    dispatch_data = property(fget=_get_dispatch_data, 
                             doc='Processed data suitable for dispatch') 
     
    def _mutate_query(self, data):
        ''' Override for additional processing '''
        return data
    
    def as_query(self, allow_empty=False, pprocessor=None):
        ''' Returns the dispatch data as a query string '''
        if not allow_empty:
            query = {}
            for k,v in self.dispatch_data:
                if v:
                    query[k] = v
        else:
            query = self.dispatch_data
        result = pprocessor(query) if pprocessor else query
        return result
    