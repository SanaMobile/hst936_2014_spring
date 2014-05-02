'use strict';

angular.module('sanaAngApp')
  .controller('MainCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
    $scope.name = "VIT-19";
    $scope.cName = "";
    $scope.cConcept = "";
    $scope.cKeywords = "";
    $scope.cText = "";
    $scope.cHelperText = "";
    $scope.comp = {
        cName: '',
        cConcept:'',
        cPlaceholder:'',
        cValidation:'',
        cKeywords:'',cText:'', cHelperText:'',qTypeSel:'',
        cOption:[],
        cGroup:''
    };
    $scope.qComponentGroups=["Group 1","Group 2","Group 3","Group 4","Group 5","Group 6"];
    $scope.qConcepts= ['SURGERY SITE','ORGAN','SYMPTOMS','WOUND DRAINAGE','PAIN LEVEL','INITIAL DIAGNOSIS','RECOMMENDATION','FOLLOW UP PERIOD','FOLLOW UP PERIOD','COMMENTS','GPS','SURGERY SITE IMAGE'],

            $scope.qTypes = [
    // { name: 'typeA', templateUrl: 'views/viewA.html' },
    { name: 'Radio Buttons', templateUrl: 'views/radioButtons.html' },
    { name: 'Check Boxes', templateUrl: 'views/checkBoxes.html' },
    { name: 'Free Text', templateUrl: 'views/freeText.html' },
    { name: 'Number', templateUrl: 'views/number.html' },
    { name: 'Computation', templateUrl: 'views/computation.html' },
    { name: 'Geolocation', templateUrl: 'views/geolocation.html' },
    { name: 'Image', templateUrl: 'views/image.html' },
    { name: 'Video', templateUrl: 'views/video.html' },
    { name: 'Audio', templateUrl: 'views/audio.html' },
    { name: 'DatePicker', templateUrl: 'views/datePicker.html' },
    { name: 'Display', templateUrl: 'views/display.html' }


  ];

  // 		   <option>Radio Buttons</option>
  //           <option>Check Boxes</option>
  //           <option>Free Text</option>
  //           <option>Numerical Input</option>
  //           <option>Computation Based</option>
  //           <option>Geolocation</option>
  //           <option>Image</option>
  //           <option>Video</option>
  //           <option>Audio</option>
  //           <option>Display Only Text/Video/Audio</option>

  })
  .controller('AboutCtrl', function ($scope) { });
