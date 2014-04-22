
var root="";
var ua = navigator.userAgent.toLowerCase();

function setRoot(newRoot){
  root = newRoot;
}

function postAjax(url, data, success, error){
    if( error == null)
        error = function(data){
                    if(window.console){ console.log("Fail creating task: ");}
                        alert("Fail posting: " + $('this'));
                    };
    if(success == null)
        success = function(data){
                    if(window.console){ console.log("Success");}
                        alert("Success! ");
                    };
        $.ajax({
            url: url,
            type: 'post',
            data: data,
            headers: {
                "sessionid": $.cookie("sessionid"),
                "csrftoken": $.cookie("csrftoken")
            },
            dataType: 'json',
            success: success,
            error: error,
        });
}

function submitSubject(){
    var system_id = $('#id_system_id').val();
    var given_name = $('#id_given_name').val();
    var family_name = $('#id_family_name').val();
    var use_age  = $('#id_use_age').val();
    var age  = $('#id__age').val();
    var dob  = $('#id_dob').val();
    var gender = $('#id_gender').val();
    var image = $('#id_image').val();
    var location = $('#id_location').val();
    //var image = $('#id_image').files[0];

    if(window.console){
        console.log("system_id "+system_id);
        console.log("location " + location);
        console.log("image " + image);
    }
    var subject_uuid = "";
    $.post(root + '/mds/core/surgicalsubject/',{ 
                    system_id: system_id,
                    given_name: given_name,
                    family_name: family_name,
                    dob: dob,
                    gender: gender,
                    location: location,
                    image: image
                },function(data){ 
                    if(window.console){
                        console.log("SUCCESS ");
                    }
                    var msg = data['message'];
                    $('#task_patient_id').text(msg['system_id']).show();
                    $('#id_patient_uuid').val(msg['uuid']);
                    subject_uuid = msg['uuid'];
                    alert("Success Creating Patient:\n " + msg['system_id']);
                    //submitEncounter(subject_uuid);
                    //submitEncounterTasks(subject_uuid);
               }).fail(function(){
                    if(window.console){ console.log("Fail creating patient: ");}
                    alert("Failure creating patient! ");
                });
    return subject_uuid;
}


function submitEncounter(){
        if(window.console){ console.log("submitEncounter()");}
        var subject = $('#id_patient_uuid').val();
        var observer = $('#id_observer_uuid').val();
        var concept = $('#id_encounter_concept_uuid').val();
        var procedure = $('#id_encounter_procedure_uuid').val();
        var device = $('#id_encounter_device_uuid').val();
        if(window.console){ console.log("posting: ( \n" + subject+", "
                + observer+", \n"+procedure+", "+concept+", \n"+ device +"\n )"); }
        var encounterCreated = false;
        var encounter_uuid = '';
        $.post(root + '/mds/core/encounter/',{ 
                    subject: subject,
                    procedure: procedure,
                    observer: observer,
                    device: device,
                    concept: concept
                },function(data){ 
                    var msg = data['message'];
                    if(window.console){ console.log("SUCCESS creating encounter" + msg['uuid']); }
                    
                    encounterCreated = true;
                    encounter_uuid = msg['uuid'];
                    alert("Success Entering Initial Encounter Data:\n " + msg['uuid']);
                    $('#id_encounter_uuid').val(msg['uuid']);
                    postObservations(encounter_uuid);
               }).fail(function(){
                    if(window.console){ console.log("Fail creating intake Encounter. ");}
                    alert("Failure creating encounter.");
                });
}

function postObservations(encounter_uuid){   
        //var encounter_uuid = $('#id_encounter_uuid').val();
        // Initial diagnosis
        var value = $('#id_diagnosis').val();
        if (value == "Other"){    
            value = $('#id_diagnosis_other').val();
        }
        postObs(encounter_uuid, '1', '104889a3-b6fa-4cdc-b232-d3b73e924cd1', value);
        // Operation(s)
        var value = $('#id_operation').val();
        var operations_other = $('#id_operations_other').val() || [];
        if(operations_other != null){
             value.push(operations_other);
        }
        postObs(encounter_uuid, '2', '9b01ef00-9fac-4f3c-87e5-66b152a3159b', value.join(","));
        // Operation date
        value= $('#id_date_of_operation').val();
        postObs(encounter_uuid, '3', '9082b5f8-74c6-4f54-a92b-04fa98e780d6', value);
        
        // Dicharge date
        value = $('#id_date_of_discharge').val();
        postObs(encounter_uuid, '4', '069ba5bb-a183-4d14-b485-9f93bca812c3', value);
        
        // Follow up date
        value = $('#id_date_of_regular_follow_up').val();
        postObs(encounter_uuid, '5', 'd24cf683-4b51-46d8-a4c4-154c38e1dd38', value);
}

function postObs(encounter,node,concept,value){
        if(window.console){ console.log("posting obs: (" 
                + encounter+", "+node+", "+concept+", "+ value +" )"); }
        $.post(root + '/mds/core/observation/',{ 
                    encounter: encounter,
                    node: node,
                    concept: concept,
                    value_text: value
                },function(data){ 
                    var msg = data['message'];
                    if(window.console){ console.log("SUCCESS creating obs" + node);}
                    $('#id_encounter_uuid').val(msg['uuid']);
                    //alert("Success Entering Initial Obs Data:\n " + node);
               }).fail(function(){
                    if(window.console){ console.log("Fail creating obs: " + node);}
                    alert("Fail creating obs" + $('this'));
                });
}
 
function postTask(assigned_to,status, due_on, subject, procedure){
        if(window.console){ console.log("posting task: ( " + assigned_to+", "+due_on
                                                    +", "+subject+", "+ procedure+" )"); }
        $.post(root + '/mds/tasks/encounter/',{ 
                    assigned_to: assigned_to,
                    status: status,
                    procedure: procedure,
                    subject: subject,
                    due_on: due_on
                },function(data){ 
                    var msg = data['message'];
                    $('#id_encounter_uuid').val(msg['uuid']);
                    alert("Success creating task: +\nDue on " + msg['due_on']);
               }).fail(function(){
                    if(window.console){ console.log("Fail creating task: ");}
                    alert("Failure creating task: +\nDue on " + msg['due_on']);
                    //alert("Fail posting task: " + $('this'));
                });
}

   /* 
     *  Ajax version wich supports cookies
     */
    function postTask2(assigned_to,status, due_on, subject, procedure){
        if(window.console){ console.log("posting task: ( " + assigned_to+", "+due_on
                                                    +", "+subject+", "+ procedure+" )"); }
        $.ajax({
            url: '/test/mds/tasks/encounter/',
            type: 'post',
            data: {
                    assigned_to: assigned_to,
                    status: status,
                    procedure: procedure,
                    subject: subject,
                    due_on: due_on
            },
            headers: {
                "sessionid": $.cookie("sessionid"),
                "csrftoken": $.cookie("csrftoken")
            },
            dataType: 'json',
            success: function(data){ 
                    var msg = data['message'];
                    alert("Success Entering Initial Data:\n " + msg['uuid']);
            },

            success: function (data) {
                if(window.console){ console.log("Fail creating task: ");}
                alert("Fail posting task: " + $('this'));
            },
        });
    }


function submitEncounterTasks(){
        var subject = $('#id_patient_uuid').val()
        var status = 1;
        var assigned_to = $('#id_assigned_to_sa').val();
        var system_id = $('#id_system_id').val();
        
        // Initial procedure - both
        var procedure = $('#id_procedure').val();

        // initial follow up
        var due_on = $('#id_date_of_first_sa_follow_up').val();
        postTask(assigned_to,status, due_on, subject, procedure);

        // final follow up
        due_on = $('#id_date_of_final_sa_follow_up').val();
        postTask(assigned_to,status, due_on, subject, procedure);
 }



