// Start sails
require('sails').lift(
    {
        appName: "Sana MDS"
    },
    function(err, sails) {
        // pass it command line arguments
        require('optimist').argv;
    }
);