<?php include "phpfunctions.php"?>

<html>
<body>
<!-- we ha ve to c-->
    <form name="enc" action="<?php echo $_SERVER['PHP_SELF'] ?>" method="POST">
       Fits:<input type="radio" name="q1" value="yes"/>Yes
            <input type="radio" name="q1" value="no" checked/>No<br>
       Constant vomiting:<input type="radio" name="q2" value="yes"/>Yes
                         <input type="radio" name="q2" value="no" checked/>No<br> 
    Child isn't eating/drinking anything:<input type="radio" name="q3" value="yes"/>Yes
        <input type="radio" name="q3" value="no" checked/>No<br>
        Child indrawing:<input type="radio" name="q4" value="yes"/>Yes
                        <input type="radio" name="q4" value="no" checked/>No<br>
        <input type="submit"   value="Submit"/>
    </form>
    
    
    
    
    
    
    
    
    
    
   
    
   <?php
    
    $json_form=modify_formdata_to_customizejson();



system('C:\/Python34\/python.exe  C:\/wamp\/www\/mmitra\/vit\/notification\/pytho\/smsprocessor.py '.$json_form,$res);

echo "<hr>";


?>    
    
</body>
 </html>


