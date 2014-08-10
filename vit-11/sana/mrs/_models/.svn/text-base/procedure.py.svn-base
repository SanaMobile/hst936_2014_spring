from django.db import models

__all__ = ["Procedure"]

class Procedure(models.Model):
    """ Representation of a form to collect data """
    class Meta:
        app_label = 'mrs'
        
    def __unicode__(self):
        return "Procedure %s, %s, %s, %s" % (self.title,
                                             self.procedure_uid,
                                             self.created,
                                             self.modified)
        
    ''' A assigned identifier '''
    uid = models.CharField(max_length=255, unique=True)
    ''' A short title '''
    title = models.CharField(max_length=255)
    ''' Author of the procedure '''
    author = models.CharField(max_length=255, blank=True)
    ''' Text of the procedure '''
    text = models.TextField()
    # ? would this make more sense
    #procedure_file = models.FileField(upload_to='procedure',blank=True)
    created = models.DateTimeField(auto_now_add=True)
    modified = models.DateTimeField(auto_now=True)

