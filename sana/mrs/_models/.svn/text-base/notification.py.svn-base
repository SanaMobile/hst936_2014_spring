from django.db import models
import cjson

__all__ = ["Notification"]
    
class Notification(models.Model):
    """ A notification message; e.g. sms, email, etc. """

    class Meta:
        #unique_together = (('uid', 'client_uid',),)
        app_label = 'mrs'
    ''' Client assigned identifier '''
    uid = models.CharField(max_length=512)
    
    ''' Who or what is sending the message '''
    client_uid = models.CharField(max_length=512)
    
    ''' An optional direct return address '''
    return_addr = models.CharField(max_length=512, blank=True)
    
    ''' Where the message will be going. Interpretation depends on type '''
    recipient_addr = models.CharField(max_length=512)
    
    ''' An identifier for Who or what the message is about; e.g. a patient ''' 
    patient_uid = models.CharField(max_length=512, blank=True)
    
    ''' UID of the encounter the message pertains to '''
    encounter_uid = models.CharField(max_length=512, blank=True)
    
    ''' Brief summary or subject '''
    summary = models.CharField(max_length=128, blank=True)
    
    ''' Text body '''
    message = models.TextField()
    
    ''' Who or what composed the message body '''
    author = models.CharField(max_length=512)
    
    ''' True upon successful delivery '''
    delivered = models.BooleanField()

'''
    def to_json(self):
        """ Generates the JSON representation of the object """
        return cjson.encode({
            'phoneId': self.client,
            'message': self.message,
            'procedureId': self.procedure_id,
            'patientId': self.patient_id
            })
        
    def to_xml(self):
        """ Generates the xml representation of the object """
        #TODO (winkler): implement this?
        pass
'''
