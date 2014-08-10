
modules.exports = {
    getWorker: function(workerId){
        $.ajax({
            url: '/worker/'+workerId
        }).done(function(data){
            var worker = JSON.parse(data);
            return worker.firsName + " " + worker.lastName;
        });
    }
}