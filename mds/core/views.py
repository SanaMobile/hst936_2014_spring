'''
Created on Aug 3, 2012

@author: Sana Development
'''
import cjson
import logging

from django.http import HttpResponse
from django.conf import settings
from django.contrib.auth import authenticate
from django.contrib.auth.decorators import login_required
from django.core.paginator import Paginator, InvalidPage, EmptyPage
from django.forms.models import modelformset_factory
from .forms import *

from django.shortcuts import render_to_response,redirect
from django.template import RequestContext 
from django.views.generic import list_detail


from mds.api import version
from mds.api.responses import JSONResponse
from .models import *

from django.db.models import Count
from mds.tasks.models import *
from collections import Counter
from datetime import datetime
from django import forms
from gmapi import maps
from gmapi.forms.widgets import GoogleMap
from copy import deepcopy

#__all__ = ['home', 'index','intake']

def home(request):
    """Top level url
    
    Displays ::
        {"path": HttpRequest.path, 
         "host": HttpRequest.get_host(), 
         "version": sana.api.version, 
         "service": "REST"}
    """
    username = request.REQUEST.get('username', 'empty')
    password = request.REQUEST.get('password','empty')
    user = authenticate(username=username, password=password)
    if user is not None:
        return HttpResponse(cjson.encode( {
               'status':'SUCCESS',
               'code':200,
               'message': version()}))
    else:
        message = unicode('UNAUTHORIZED:Invalid credentials!')
        logging.warn(message)
        logging.debug(u'User' + username)
        return HttpResponse(cjson.encode({
                'status':'FAIL',
                'code':401, 
                'message': message}))
    

def _list(request,*args,**kwargs):
    query = dict(request.GET.items())
    start = int(query.get('start', 1))
    limit = int(query.get('limit', 20))
    objects = Event.objects.all().filter().order_by('-created')
    paginator = Paginator(objects, limit, allow_empty_first_page=True)
    objs = []
    for p in paginator.page(start).object_list:#.all():
        #p.full_clean()
        #obj = p.to_python()
        #m = obj.pop('message')
        m = p.messages
        try:
            #obj['message'] = cjson.decode(m,True)
            p.messages = cjson.decode(m,True)
        except:
            pass
            #obj['message'] = m
        objs.append(p)
    data = {'objects': objs,
            'limit': limit,
            'start': start,
            "rate": int(query.get('refresh', 5)),
            'range': range(1, paginator.num_pages + 1),
            "version": settings.API_VERSION }
    return data

def log_index(request,*args,**kwargs):
    data = _list(request)
    return render_to_response('logging/index.html', RequestContext(request,data))

def log_list(request):
    data = _list(request)
    return render_to_response('logging/list.html', RequestContext(request,data))

def log_report(request):
    post = dict(request.POST.items())
    selected = []
    for k,v in post.items():
        if v:
            selected.append(k)
    objects = Event.objects.all().filter(uuid__in=selected)
    return JSONResponse(objects)

def log_detail(request, uuid):
    log = Event.objects.get(uuid=uuid)
    data = []
    messages = cjson.decode(log.messages)
    for m in messages:
        try:
            m['message'] = cjson.decode(m['message'])
        except:
            pass
        
        data.append(m)
#           m['message']  = cjson.decode(m['message'])
#            data.append(m)
#        except:
            
            
    message = { 'message': data, 'uuid': uuid, }
    return HttpResponse(cjson.encode(message))

def log(request,*args,**kwargs):
    query = dict(request.GET.items())
    page = int(query.get('page', 1))
    page_size = int(query.get('page_size', 20))
    
    data = {'object_list': {},
            'page_range': range(0, 1),
            'page_size': page_size,
            'page': page,
            "rate": int(query.get('refresh', 5)) }
    return render_to_response('logging/index.html', RequestContext(request,data))

#@login_required(login_url='/test/mds/login/')
def encounter(request,**kwargs):
    #if not request.user.is_authenticated():
    #    return render_to_response('core/login.html')
    tmpl = 'core/mobile/encounter.html'
    uuid = kwargs.get('uuid',None) if kwargs else None
    if kwargs:
        tmpl = 'core/mobile/encounter.html'
        encounters = Encounter.objects.filter(**kwargs)
        num = encounters.count()
        data = []
        
        for encounter in encounters:
            obj = { 'procedure' : encounter.procedure,
                 'subject' : encounter.subject,
                 'device' : encounter.device,
                 'observer' :encounter.observer }
            obsqs = Observation.objects.filter(encounter=encounter.uuid)
            observations = []
            for obs in obsqs:
                obsdata = { "node": obs.node,
                        "concept": obs.concept.name}
                if obs.concept.is_complex:
                    obsdata["url"] = obs.value_complex.url
                    obsdata["thumb"] = u''
                    obsdata["value"] =  obs.value_complex.url
                else:
                    obsdata["url"] = ""
                    obsdata["value"] =  unicode(obs.value)
                observations.append(obsdata)
            obj['observations'] = observations
            data.append(obj)
        
        #data = encounters
    else:
        tmpl = 'core/mobile/encounter_list.html'
        data = Encounter.objects.all()
        num = data.count()
    return render_to_response(tmpl, 
                              context_instance=RequestContext(request,{ "objs": data , 'count': num }))

def subject(request,**kwargs):
    #if not request.user.is_authenticated():
    #    return render_to_response('core/login.html')
    uuid = kwargs.get('uuid',None) if kwargs else None
    if uuid:
        tmpl = 'core/mobile/subject.html'
        subjects = Subject.objects.filter(uuid=uuid)
    else:
        tmpl = 'core/mobile/subject_list.html'
        subjects = Subject.objects.all()
    return render_to_response(tmpl, 
                               context_instance=RequestContext(request,{ "objs": subjects }))

def subject_create(request,**kwargs):
    return render_to_response('core/mobile/registration.html', 
                               context_instance=RequestContext(request,{}))
                               
@login_required(login_url='/mds/login')
def intake(request,**kwargs):
    method = request.META['REQUEST_METHOD']
    if method == 'POST':
        return intake_post(request,kwargs=kwargs)
    else:
        return intake_get(request,kwargskwargs=kwargs)

def intake_post(request,**kwargs):    
    user = request.user
    observer = Observer.objects.get(user=request.user)
    flavor = request.GET.get('flavor',None)
    tmpl = 'core/intake.html'
    if flavor:
        if flavor == 'mobile':
            tmpl = 'core/mobile/intake.html'
        else:
            tmpl = 'core/intake.html'
        
    return render_to_response(tmpl,
                                context_instance=RequestContext(request, 
                                                                {'subject_form':  SurgicalSubjectForm(),
                                                                 'encounter_form': SurgicalIntakeForm(),
                                                                 'sa_form' : SurgicalAdvocateFollowUpForm(),
                                                                 'observer': observer}))

def intake_get(request,**kwargs):
    # Get the user
    user = request.user
    observer = Observer.objects.get(user=request.user)
    flavor = request.GET.get('flavor',None)
    tmpl = 'core/intake.html'
    if flavor:
        if flavor == 'mobile':
            tmpl = 'core/mobile/intake.html'
        else:
            tmpl = 'core/intake.html'
        
    return render_to_response(tmpl,
                                context_instance=RequestContext(request, 
                                                                {'subject_form':  SurgicalSubjectForm(),
                                                                 'encounter_form': SurgicalIntakeForm(),
                                                                 'sa_form' : SurgicalAdvocateFollowUpForm(),
                                                                 'observer': observer}))
def index_page(request,**kwargs):
    flavor = request.GET.get('flavor',None)
    tmpl = 'core/index.html'
    if flavor:
        if flavor == 'mobile':
            tmpl = 'core/mobile/index.html'
        else:
            tmpl = 'core/index.html'
        
    return render_to_response(tmpl,
                                context_instance=RequestContext(request, {'flavor': flavor}))

if __name__ == 'main':
    data = {"data": [
               {'line_number': 44, 
                'level_number': 10, 
                'delta': '0.000s', 
                'timestamp': 1344815434.9979069, 
                'level_name': 'DEBUG', 
                'function_name': 'execute', 
                'message': u'(0.002) SELECT \"core_concept\".\"id\", \"core_concept\".\"uuid\", \"core_concept\".\"created\", \"core_concept\".\"modified\", \"core_concept\".\"name\", \"core_concept\".\"display_name\", \"core_concept\".\"description\", \"core_concept\".\"conceptclass\", \"core_concept\".\"datatype\", \"core_concept\".\"mimetype\", \"core_concept\".\"constraint\" FROM \"core_concept\"; args=()', 
                'module': 'util', 
                'filename': 'util.py'}], 
            "id": "2bbeb878-33f1-4590-9237-e41b151fa553"}
    
#necessary for the map in dana()
class MapForm(forms.Form):
    map = forms.Field(widget=GoogleMap(attrs={'width':500, 'height':400}))
        
def dana(request, **kwargs):
    tmpl = 'core/dana.html'
    
    #Contains counts of #encounters/x, x is location, concept, etc.
    numencs = {}
    
    #Contains counts of #subjects/x, x is location, concept, etc.  All x except location are associated with subject via the encounters
    numsubjs = {}
    
    #Contains counts of #x and #x that have actually happened in the trial so far
    nums = {'enum':Encounter.objects.all().count()}
    
    numencs['e_in_s'] = Subject.objects.annotate(revcount = Count('encounter')).order_by('-revcount')
    nums['snum'] = numencs['e_in_s'].count()
    nums['snum_in_e'] = numencs['e_in_s'].exclude(revcount=0).count()
    numencs['e_in_p'] = Procedure.objects.annotate(revcount = Count('encounter')).order_by('-revcount')
    numencs['e_in_cc'] = Concept.objects.annotate(revcount = Count('encounter')).order_by('-revcount')
    numencs['e_in_o'] = Observer.objects.annotate(revcount = Count('encounter')).order_by('-revcount')
    nums['onum_in_e'] = numencs['e_in_o'].exclude(revcount=0).count()
    nums['onum'] = numencs['e_in_o'].count()
    numsubjs['s_in_l'] = Location.objects.annotate(revcount = Count('subject')).order_by('-revcount')
    numsubjs['o_s_in_e'] = Observer.objects.annotate(scount = Count('encounter__subject__uuid', distinct = True)).order_by('-scount')
    numsubjs['p_s_in_e'] = Procedure.objects.annotate(scount = Count('encounter__subject__uuid', distinct = True)).order_by('-scount')
    numsubjs['cc_s_in_e'] = Concept.objects.annotate(scount = Count('encounter__subject__uuid', distinct = True)).order_by('-scount')
    numencs['e_in_l'] = Location.objects.annotate(revcount = Count('subject__encounter')).order_by('-revcount')
    
    #All EncounterTasks that have an associated concept
    datesas = EncounterTask.objects.exclude(concept = '').prefetch_related()

    #structures that will contain sorted counts of late, ontime, early, and missing encounters
    basedict = {'conc': [], 'proc': [], 'loc': [], 'subj': [], 'obs': [], 'days': [], 'count': 0}
    timely = {'late': deepcopy(basedict), 'ontime': deepcopy(basedict), 'early': deepcopy(basedict), 'missing': deepcopy(basedict)}
    
    #function that returns whether an encounter was late, ontime, or early, and by how much
    def checklate(a,b):
        ch = (a-b).days
        if ch > 0:
            return (ch, 'late')
        elif ch < -1:
            return (abs(ch), 'early')
        else:
            return (abs(ch), 'ontime')
    
    #Adds values to appropriate list
    def sorttimes(entry, delay, late, timely):
        timely[late]['conc'].append(entry.concept)
        timely[late]['proc'].append(entry.procedure)
        timely[late]['loc'].append(entry.subject.location)
        timely[late]['subj'].append(entry.subject)
        timely[late]['obs'].append(entry.observer)
        timely[late]['days'].append(delay)
        timely[late]['count'] += 1
            
    upcomingcount = 0
    
    #for each EncounterTask, finds and processes corresponding encounters
    for x in datesas:
        f = Encounter.objects.filter(subject = x.subject).filter(concept = x.concept)
        #checks if nonextant encounters were already due.  If so, they are missing.  If not, they are upcoming
        if f.count() == 0:
            delay, late = checklate(datetime.today().date(), x.due_on.date())
            if late == 'late':
                timely['missing']['conc'].append(x.concept)
                timely['missing']['proc'].append(x.procedure)
                timely['missing']['loc'].append(x.subject.location)
                timely['missing']['subj'].append(x.subject)
                timely['missing']['obs'].append(x.assigned_to)
                timely['missing']['days'].append(delay)
                timely['missing']['count'] += 1
            else:
                upcomingcount += 1
        else:
            #processes existing encounters
            for entry in f:
                delay, late = checklate(entry.modified.date(), x.due_on.date())
                sorttimes(entry, delay, late, timely)
                         
    #actually perform sorted counts on the entries that have been assigned to each category
    danacounts = {}
    for x in timely:
        danacounts[x] = {}
        for y in timely[x]:
            if y != 'count':
                danacounts[x][y] = Counter(timely[x][y]).most_common()
    
    #function that takes in a uuid and returns a hex color of the form XXXXXX
    def chwtocolor(uuid):
        return uuid.replace('-','').upper()[2:8] 
    
    #Sets the basic map options
    gmap = maps.Map(opts = {
        'center': maps.LatLng(19.3, -72.7),
        'mapTypeId': maps.MapTypeId.ROADMAP,
        'zoom': 9,
        'mapTypeControlOptions': {
             'style': maps.MapTypeControlStyle.DROPDOWN_MENU
        },
    })
    
    chwscolors = {}
    for x in Observer.objects.all():
        chwscolors[x.uuid] = {'color': chwtocolor(x.uuid), 'chw': x}
    markers = []
    pinImages = []
    infos = []
    content = []
    mycounter = -1
    obswithgps = Observation.objects.filter(node = '1a')
    
    #Places a map pin with correct observer color for every location observation
    for obj in obswithgps:
        mycounter += 1
        a,b,c = map(float, obj.value_text.strip('()').replace(',','').split())
        pinImages.append(maps.MarkerImage("http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|" + chwscolors[obj.encounter.observer.uuid]['color'],
                                          maps.Size(21, 34),
                                          maps.Point(0,0),
                                          maps.Point(10, 34)))
        markers.append(maps.Marker(opts = {
                                           'map': gmap,
                                           'position': maps.LatLng(a, b),
                                           'icon': pinImages[mycounter],
        }))
        
        #Adds popup textboxes with info to describe the pinned encounter
        maps.event.addListener(markers[mycounter], 'mouseover', 'myobj.markerOver')
        maps.event.addListener(markers[mycounter], 'mouseout', 'myobj.markerOut')
        content.append('<p>' + str(obj.encounter.observer) + ' visited ' + str(obj.encounter.subject) + '</br>for ' + str(obj.encounter.concept) + ' on ' + str(obj.encounter.modified.date()) + '</p>')
        infos.append(maps.InfoWindow({
                                      'content': content[mycounter],
                                      'disableAutoPan': True
        }))
        infos[mycounter].open(gmap, markers[mycounter])

    return render_to_response(tmpl, 
                              context_instance=RequestContext(request, {'chwscolors': chwscolors.values(),
                                                                        'nums': nums,
                                                                        'numencs': numencs,
                                                                        'numsubjs': numsubjs,
                                                                        'form': MapForm(initial={'map': gmap}),
                                                                        'upcomingcount': upcomingcount,
                                                                        'danacounts': danacounts,
                                                                        'timely': timely}))