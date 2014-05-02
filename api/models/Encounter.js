/**
 * Encounter
 *
 * @module      :: Model
 * @description :: A short summary of how this model works and what it represents.
 * @docs		:: http://sailsjs.org/#!documentation/models
 */

module.exports = {
    attributes: {
        patientId: {
            type: 'INTEGER',
            required: true
        },
        workerId: {
            type: 'INTEGER',
            required: true
        },
        doctorId: {
            type: 'INTEGER',
            required: true
        },
        visitId: {
            type: 'INTEGER',
            required: true
        },
        date: {
            type: 'DATETIME',
            required: true
        },
        status: {
            type: 'STRING',
            defaultsTo: 'Pending'
        }
    }
};