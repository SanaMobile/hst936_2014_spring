/**
 * HomeController
 *
 * @module      :: Controller
 * @description	:: A set of functions called `actions`.
 *
 *                 Actions contain code telling Sails how to respond to a certain type of request.
 *                 (i.e. do stuff, then send some JSON, show an HTML page, or redirect to another URL)
 *
 *                 You can configure the blueprint URLs which trigger these actions (`config/controllers.js`)
 *                 and/or override them with custom routes (`config/routes.js`)
 *
 *                 NOTE: The code you write here supports both HTTP and Socket.io automatically.
 *
 * @docs        :: http://sailsjs.org/#!documentation/controllers
 */

module.exports = {
    
  
  /**
   * Action blueprints:
   *    `/home/login`
   */
   login: function (req, res) {
      if(req.method == 'POST' && req.param('email') != null && req.param('password') != null) {
          Doctor.find()
              .where({
                  email: req.param('email')
              })
              .done(function(err, doctor){
                  if(err || doctor == null) {
                      return res.view({
                          loginError: "User not found"
                      });
                  }

                  //CHECK PASSWORD
//                  if(doctor.password != req.param('password')) {
//                      return res.view({
//                          loginError: "Incorrect Password"
//                      });
//                  }

                  req.session.user = doctor;
                  return res.redirect('/home/dashboard');
              });
      } else {
          return res.view({
              loginError: null
          });
      }
  },

  /**
   * Action blueprints:
   *    `/home/logout`
   */
   logout: function (req, res) {
      if(req.session.user) req.session.user = null;

      return res.redirect('/home/login');
  },

  created: function(req, res) {
      Doctor.create({
          name: 'Team23',
          email: 'admin@sana.com',
          password: 'team23'
      }).done(function(err, doctor){
          if(err) return res.json(err);

          return res.json(doctor);
      })
  },

  /**
   * Action blueprints:
   *    `/home/dashboard`
   */
   dashboard: function (req, res) {
      if(!req.session.user) return res.redirect('/home/login');

      //return res.json(getWorkers());

      if(req.method == 'POST' && req.param('type') != null){
          if(req.param('type') == 'addPatient' && req.param('firstName') != null && req.param('lastName') != null
              && req.param('dob') != null && req.param('gender') != null) {
              var firstName = req.param('firstName');
              var lastName = req.param('lastName');
              var gender = req.param('gender');
              var dob = new Date(req.param('dob'));

              Patient.create({
                  firstName: firstName,
                  lastName: lastName,
                  gender: gender,
                  dob: dob
              }).done(function(err, patient){
                  if(err) return dashboardLoad(null, 'Failed to Add New Patient');

                  return dashboardLoad(null, 'New Patient Added');
              });
          } else if(req.param('type') == 'addWorker' && req.param('firstName') != null && req.param('lastName') != null
              && req.param('dob') != null && req.param('gender') != null) {
              var firstName = req.param('firstName');
              var lastName = req.param('lastName');
              var gender = req.param('gender');
              var dob = new Date(req.param('dob'));

              Worker.create({
                  firstName: firstName,
                  lastName: lastName,
                  gender: gender,
                  dob: dob
              }).done(function(err, worker){
                  if(err) return dashboardLoad('Failed to Add Worker', null)

                  return dashboardLoad('New Worker Added', null);
              });
          }
      } else {
          return dashboardLoad(null, null);
      }


      function dashboardLoad(workerMessage, patientMessage) {
          Encounter.find().done(function(err, encounters){
              if(err || !encounters) return res.json({status: 500, error: 'Internal Error. Contact Technical Support.'});

              Patient.find().done(function(err, patients){
                  if(err) return res.json({status: 500, error: 'Internal Error. Contact Technical Support.'});

                  Worker.find().done(function(err, workers){
                      if(err) return res.json({status: 500, error: 'Internal Error. Contact Technical Support.'});

                      return res.view({
                          doctor: req.session.user,
                          encounters: JSON.stringify(encounters),
                          workerMessage: workerMessage,
                          patientMessage: patientMessage,
                          patients: JSON.stringify(patients),
                          workers: JSON.stringify(workers)
                      });
                  });
              });
          });
      }
  },


  /**
   * Overrides for the settings in `config/controllers.js`
   * (specific to HomeController)
   */
  _config: {}

  
};
