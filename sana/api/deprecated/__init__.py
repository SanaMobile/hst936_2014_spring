''' Where old code goes for backward compatibility

@author: Sana Dev Team
Created on May 12, 2011
'''
BINARY_TYPES = ['PICTURE', 'SOUND', 'VIDEO', 'BINARYFILE']

BINARY_TYPES_EXTENSIONS = {'PICTURE': 'jpg',
                           'SOUND': '3gp',
                           'VIDEO': '3gp',
                           'BINARYFILE': 'mpg'}

CONTENT_TYPES = {'PICTURE': 'image/jpeg',
                 'SOUND': 'audio/3gpp',
                 'VIDEO': 'video/3gpp',
                 'BINARYFILE': 'video/mpeg'}
                    #'BINARYFILE': 'application/octet-stream'}

CONVERTED_BINARY_TYPES_EXTENSIONS = {'SOUND': 'mp3',
                                    'VIDEO': 'flv'}

CONVERTED_CONTENT_TYPES = {'SOUND': 'audio/mpeg',
               'VIDEO': 'video/x-flv'}

DEFAULT_PATIENT_ID = "500"