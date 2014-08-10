import logging
import time
from logging import DEBUG, INFO, WARNING, ERROR, CRITICAL
from sana.settings import LOG_LEVEL

from sana.mrs._models.log import Log

def get_log_level(level=LOG_LEVEL):
    # TODO(winkler) document
    if level == 'OFF':
        # TODO(winkler) fix this
        #return f(*args, **kwargs)
        return None
    elif level in ['DEBUG', 'INFO', 'WARNING', 'ERROR', 'CRITICAL']:
        level = eval(level)
    else:
        level = 20
        print("Invalid LOG_LEVEL in settings file.")

    return level

def log_http_request(f):
    """Decorator to enable logging on an HTTP request."""
    level = get_log_level()
    def new_f(*args, **kwargs):
        request = args[1] # Second argument should be request.
        object_type = 'Request'
        object_id = time.time()
        log_name = object_type + '.' + str(object_id)
        setattr(request, 'LOG_ID', object_id)

        logger = logging.getLogger(log_name)
        logger.setLevel(level)
        handler = LogModelHandler(object_type, object_id)
        logger.addHandler(handler)

        return f(*args, **kwargs)

    new_f.func_name = f.func_name
    return new_f

def log_class_method(f):
    """Decorator to enable logging on a class method of a Django model."""
    level = get_log_level()
    def new_f(*args, **kwargs):
        object = args[0] # First argument should be 'self'.
        object_type = object.__class__.__name__
        object_id = object.pk
        log_name = object_type + '.' + str(object_id)

        logger = logging.getLogger(log_name)
        logger.setLevel(level)
        handler = LogModelHandler(object_type, object_id)
        logger.addHandler(handler)

        #log = models.Log(object_type = object_type, object_id = object_id)
    #log.save()

        return f(*args, **kwargs)

    new_f.func_name = f.func_name
    return new_f
    

class LogModelHandler(logging.Handler):
    """A logging handler that writes to instances of a Django model."""
    def __init__(self, object_type, object_id):
        self.log, new = Log.objects.get_or_create(object_type = object_type,
                                                  object_id = object_id)
        self.log.save()
        logging.Handler.__init__(self)

    def emit(self, record):
        self.log.buffer = self.log.buffer + str(record)
        self.log.save()

