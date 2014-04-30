/**
 * EncounterController
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

    delete: function(req, res) {
        if(req.param('id') != null) {
            Encounter.findOne(1)
                .where({id: req.param('id')})
                .done(function(err, encounter){
                   if(err) return res.json(false);

                    encounter.destroy(function(err){
                        if(err) return res.json(false);

                        return res.json(true);
                    })
                });
        } else {
            return res.json(false);
        }
    },

  /**
   * Overrides for the settings in `config/controllers.js`
   * (specific to EncounterController)
   */
  _config: {}

  
};