'''Provides fields for api forms

@author: Sana Dev Team
Created on Jun 10, 2011
'''
from django.forms.widgets import Input, MultiWidget, TextInput, PasswordInput, mark_safe, force_unicode, flatatt 

class JSONInput(Input):
    input_type = 'hidden'
    is_hidden = True

class BinaryWidget(MultiWidget):
    ''' Provides multi binary upload meta data with a dispatch '''
    
    def __init__(self, attrs=None):
        widgets = (TextInput(), TextInput(), TextInput(), TextInput() )
        super(BinaryWidget, self).__init__(widgets, attrs=attrs)
        
    def decompress(self, value):
        '''
        Returns the 'uid', 'client, 'size', 'content_type'
        '''
        if value:
            return [value.uid, value.client, value.answer]
        return [None, None, None ]

class ObservationWidget(MultiWidget):
    ''' Provides multi binary upload meta data with a dispatch '''
    
    def __init__(self, attrs=None):
        widget = (TextInput(), TextInput(), TextInput() )
        super(ObservationWidget, self).__init__(widget, attrs=attrs)
        
    def decompress(self, value):
        '''
        Returns the 'uid', 'client', 'dispatch', and 'binaries' attributes as a list
        '''
        if value:
            return [value.concept, value.question, value.answer]
        return [None, None, None ]

class EncounterWidget(MultiWidget):
    ''' Provides multi binary upload meta data with a dispatch '''
    
    def __init__(self, attrs=None):
        widget = (TextInput(), TextInput(), TextInput() )
        super(EncounterWidget, self).__init__(widget, attrs=attrs)
        
    def decompress(self, value):
        '''
        Returns the 'title', 'author', 'observations' 
        '''
        if value:
            return [value.title, value.author, value.observations]
        return [None, None, None ]

class DispatchWidget(MultiWidget):
    ''' Represents the data sent with an api request from a client '''
    def __init__(self, attrs=None):
        widgets = (TextInput(attrs=attrs), 
                   TextInput(attrs=attrs), 
                   TextInput(attrs=attrs))
        super(DispatchWidget, self).__init__(widgets, attrs)
        
    def decompress(self, value):
        '''
        Returns the 'uid', 'client', and 'dispatch' attributes as a list
        '''
        if value:
            return [value.uid, value.client, value.dispatch]
        return [None, None, None, None]
    
    def render(self, name, value, attrs=None):
        if value is None:
            value = []
        final_attrs = self.build_attrs(attrs, type=self.input_type, name=name)
        if value != '':
            # Only add the 'value' attribute if a value is non-empty.
            final_attrs['value'] = force_unicode(self._format_value(value))
        return mark_safe(u'<input%s />' % flatatt(final_attrs))

class LoginWidget(MultiWidget):
    ''' Username and password widget '''
    
    def __init__(self, attrs=None):
        widgets = (TextInput(attrs=attrs), PasswordInput(attrs=attrs))
        super(DispatchWidget, self).__init__(widgets, attrs)
    def decompress(self, value):
        """
        Returns a list of decompressed values for the given compressed value.
        The given value can be assumed to be valid, but not necessarily
        non-empty.
        """
        if value:
            return [value.username, value.password]
        return [None, None]
    
    def render(self, name, value, attrs=None):
        if value is None:
            value = []
        final_attrs = self.build_attrs(attrs, type=self.input_type, name=name)
        if value != '':
            # Only add the 'value' attribute if a value is non-empty.
            final_attrs['value'] = force_unicode(self._format_value(value))
        return mark_safe(u'<input%s />' % flatatt(final_attrs))
    