/**
 * Created by karthik on 2/5/14.
 */
$('#addOption').click(function($scope){
    var a=angular.element(document.getElementById('optionsGroup')).scope();
    var newOp={index: a.comp.cOption.length,
    value:''};
    a.comp.cOption.push(newOp);
});