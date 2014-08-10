import urllib.parse
import urllib.request
import settings
import sys, json


# Load the data that PHP sent us
try:
   
    
    
    
    data = json.loads(sys.argv[1]);
   	
except:
    print ("Parameter error");
    sys.exit(1)

# Generate some data to send to PHP
result = {'status': 'Yes!'}

# Send it to stdout (to PHP)







a=data;
     # for input from JSON:-
     # a=json.loads(qwerty.json)

    
# dictionary to store referral message with the question number as keys
ref={'10':'If_boils_on_skin_visit_local_clinic',
         '11.1.1':'If_umbilical_cord_not_separated_within_15_days,_visit_local_clinic',
         '11.1.2':'If_umbilical_cord_not_separated,_visit_local_clinic',
         '11.2':'If_discharge_appears_from_umbilical_cord,_visit_local_clinic',
         '11.3':'If_umbilical_cord_is_reddish_in_color,_visit_local_clinic',
         '11.4':'If_umbilical_cord_is_smelly,_visit_local_clinic',
         '12':'If_jaundice_affects_stomach_or_below_stomach,_visit_local_clinic',
         '12.1':'If_affected_by_jaundice_for_more_than_14_days,_visit_local_clinic',
         '12.2':'If_weight_is_less_than_2.5kg,_visit_local_clinic',
         '12.3':'If_suffering_from_cephalohematoma,_visit_local_clinic',
         '12.4':'If_suffering_from_dehydration,_visit_local_clinic',
         '12.5':'If_color_of_urine_is_yellow,_visit_local_clinic',
         '12.6':'In_case_of_pale_stools,_visit_local_clinic',
         '12.7':'If_suffering_from_fits,_visit_local_clinic',
         '12.8':'In_case_of_unconsciousness,_patient_be_taken_to_local_clinic',
         '13':'If_stopped_breastfeeding,_visit_local_clinic',
         '14':'If_exclusive_breastfeeding_is_practised,_visit_local_clinic',
         '15.1':'If_suffering_from_cough_for_more_than_21_days,_visit_local_clinic',
         '16.1':'If_suffering_from_fever_for_more_than_7_days,_visit_local_clinic',
         '17.1':'If_suffering_from_diarrhea_for_more_than_14_days,_visit_local_clinic',
         '18':'If_blood_in_stool,_visit_local_clinic',
         '19':'If_suffering_from_fits,_visit_local_clinic',
         '20':'In_case_of_constant_vomiting,_visit_local_clinic',
         '21':'If_child_is_not_eating/drinking_properly,_visit_local_clinic',
         '22':'In_case_of_chest_indrawing,_visit_local_clinic',
         '23.1':'If_breathing_rate_is_above_50_breaths_per_minute,_visit_local_clinic',
         '24':'If_suffering_from_excessive_drowsiness,_visit_local_clinic',
         '25':'In_case_of_swelling_of_feet,_visit_local_clinic',
         '26':'If_feet_gets_hollow_when_pressed,_visit_local_clinic'
        }


#dictionary to store one or more referral messages to be send
referral={}




for i in ['10','11.2','11.3','11.4','13','14','18','19','20','21','22','24','25','26','12.1','12.2','12.3','12.4','12.5','12.6','12.7','12.8']:
    try:
        if a[i]['answer'] == 'yes':
            flag = 1
            referral[i]=ref[i]
    except:
        t=1

"""
the try except block is used to deal with KeyError when pages are missing from procedure
"""	
try:

    if a['11.1']['answer'] == 'no':
        flag = 1
        if a['11']['answer'] <= 2 :
            referral['11.1.1']=ref['11.1.1']


        if a['11']['answer'] >= 3 :
            referral['11.1.2']=ref['11.1.2']
except:
    t=1


try:
    temp = a['12']['answer']
    if temp in {'stomach','thigh','shin','feet and palms'} :
        flag = 1
        referral['12']=ref['12']
except:
    t=1

try:
    if a['15']['answer']=='yes':
        flag=1
        if a['15.1']['answer'] > 21 :
            referral['15.1']=ref['15.1']
except:	
    t=1

try:
    if a['16']['answer']=='yes':
        flag=1
        if a['16.1']['answer'] > 7 :
            referral['16.1']=ref['16.1']
except:	
    t=1

try:
    if a['17']['answer']=='yes':
        flag=1
        if a['17.1']['answer'] > 14 :
            referral['17.1']=ref['17.1']
except:
    t=1


try:
    if a['23']['answer']=='yes':
        flag=1
        if a['23.1']['answer'] > 50 :
            referral['23.1']=ref['23.1']
except:
    t=1




    # printing the sms string(referral message)

    if flag==1:
        sms_string ="".join(referral.values())
    else:
        sms_string = '####'












response=sms_string




if not(response=="####"): 
    a="https://api.clickatell.com/http/sendmsg?user=sunnyrajkotiya&password=PASSWORD&api_id=3476725&to=917667839974&text=Test"
    b='https://api.clickatell.com/http/sendmsg'
    try:	

       
        #params = {'user': 'sunnyrajkotiya','password': 'HGgDMMgUYeMRfS','api_id': 'RSVP01','to': '918124612664','text':'Test'	}
       
        username=settings.username
       
        password=settings.password
       
        apiid=settings.apiid
        
        tom=settings.to
        
        #to=settings.to
        
        
        url_values="user="+username+"&password="+password+"&api_id="+apiid+"&to="+tom+"&text="+response
       
        

        
        fullurl= b+'?'+url_values
       
        print(fullurl)
        datavar=urllib.request.urlopen(fullurl)	
        
        response=datavar.read()
        

    except:
        print ("\n Sending Error");

    