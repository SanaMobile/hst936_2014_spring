''' Cacheable objects for the mrs. These will be stored and retrieved by the 
dropbox. Defined objects:

    BinaryResource => any binary object which can be stored in a file
    Client => Represents the downstream, typically mobile clients
    Encounter => An instance of a Procedure where data is collected
    Log => Django logging objects
    Notification => messages sent to Clients
    Patient => A person for whom data was collected in an Encounter
    Procedure => A set of instructions for collecting data.

This file wraps the imports for all of the models used by the mrs dropbox to
provide a more OOP approach for handling the models.

@author: Sana Dev Team
Created on May 5, 2011
'''
from _models.binary import *
from _models.client import *
from _models.encounter import *
from _models.log import *
from _models.notification import *
from _models.patient import *
from _models.procedure import *
from _models.queue_element import *