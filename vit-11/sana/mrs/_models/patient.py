from django.db import models

__all__ = ["Patient"]

class Patient(models.Model):
    """ A person seen during an encounter """
    class Meta:
        app_label = 'mrs'
        #unique_together =(('uid', 'client_uid'),)
    ''' Client assigned identifier '''
    uid = models.CharField(max_length=255)
    ''' Identifier of reporting device '''
    client_uid = models.CharField(max_length=512)
    name_first = models.CharField(max_length=512)
    name_last = models.CharField(max_length=512)
    birth_day = models.CharField(max_length=2)
    birth_month = models.CharField(max_length=2)
    birth_year = models.CharField(max_length=4)
    gender = models.CharField(max_length=256)
    created = models.DateTimeField(auto_now_add=True)
    modified = models.DateTimeField(auto_now=True)

