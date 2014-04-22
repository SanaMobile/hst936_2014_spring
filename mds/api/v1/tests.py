from mds.api.v1 import v2compatlib
from mds.mrs.models import SavedProcedure
from mds.core.models import Subject

__all__ = ['run']

def run():
    subject = Subject.objects.get(uuid="26da580a-fc75-4f1a-92b2-be54e2865ceb")
    sp = SavedProcedure.objects.get(guid="5bcf6e9d-73a3-48db-9359-e1f110d3019f")
    encounter, observations =  v2compatlib.sp_to_encounter(sp,subject)
    print encounter
    for obs in observations:
        print obs
