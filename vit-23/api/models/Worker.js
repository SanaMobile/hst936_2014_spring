/**
 * Worker
 *
 * @module      :: Model
 * @description :: A short summary of how this model works and what it represents.
 * @docs		:: http://sailsjs.org/#!documentation/models
 */

module.exports = {
    attributes: {
        firstName: {
            type: 'STRING',
            required: true
        },
        lastName: {
            type: 'STRING',
            required: true
        },
        gender: {
            type: 'STRING',
            required: true
        },
        dob: {
            type: 'DATE',
            required: true
        },
        phone: {
            type: 'STRING',
            numeric: true
        },
        address: {
            type: 'STRING'
        },
        status: {
            type: 'STRING',
            defaultsTo: 'Active'
        },
        email: {
            type: 'email',
            unique: true
        }
    },

    getFullName: function(){
        return this.firstName + ' ' + this.lastName;
    }
};