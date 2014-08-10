<?php

//load and connect to MySQL database stuff
require("config.inc.php");

if (!empty($_POST)) {
    $query = " 
            UPDATE  children SET temperature= :temp,weight= :weight,height= :height,waist_circumference= :waist,blood_pressure= :bp,pregnancy_status= :pregnancy,symptoms= :symptoms,notes= :notes,updated_on= :updatedon,updated_by= :updatedby, med1=:m1,med2= :m2, med3= :m3 WHERE curp = :curp
			";
        
    
    $query_params = array(
       
		':temp' => $_POST['Temp'],
		':weight' => $_POST['Weight'],
		':height' => $_POST['Height'],
		':waist' => $_POST['Waist'],
		':bp' => $_POST['Bp'],
		':pregnancy' => $_POST['Pregnancy'],
		':symptoms' => $_POST['Symptoms'],
		':notes' => $_POST['Notes'],
		':updatedon' => $_POST['Updatedon'],
		':updatedby' => $_POST['Updatedby'],
		 ':curp' => $_POST['Curp'],
		 ':m1' => $_POST['T1'],
		 ':m2' => $_POST['T2'],
		 ':m3' => $_POST['T3'],
		
		
    );
    
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        // For testing, you could use a echo  and message. 
        //echo ("Failed to run query: " . $ex->getMessage());
        
        //or just use this use this one to product JSON data:
        $response["success"] = 0;
        $response["message"] = "Database Error1. Please Try Again!";
        echo (json_encode($response));
        
    }
    
    //This will be the variable to determine whether or not the user's information is correct.
    //we initialize it as false.
    $validated_info = false;
    
    //fetching all the rows from the query
   
    
    // If the user logged in successfully, then we send them to the private members-only page 
    // Otherwise, we display a login failed message and show the login form again 
    if ($result) {
        $response["success"] = 1;
        $response["message"] = "EMR edited successful!";
        echo (json_encode($response));
    } else {
        $response["success"] = 0;
        $response["message"] = "Something went wrong ! Try again";
        echo (json_encode($response));
    }
} else {
?>
		<h1>Login</h1> 
		<form action="editdetails.php" method="post"> 
		    CURP: 
		    <input type="text" name="Curp" placeholder="name" /> 
		      Name: 
		    <input type="text" name="Temp" placeholder="name" />
			 Phone: 
		    <input type="text" name="Weight" placeholder="name" />
			 email: 
		    <input type="text" name="Height" placeholder="name" />
			 relation: 
		    <input type="text" name="Waist" placeholder="name" />
			 zip: 
		    <input type="text" name="Bp" placeholder="name" />
			 colony: 
		    <input type="text" name="Pregnancy" placeholder="name" />
			 gender: 
		    <input type="text" name="Symptoms" placeholder="name" />
			 cancer: 
		    <input type="text" name="Notes" placeholder="name" />
			 diabetes: 
		    <input type="text" name="Updatedon" placeholder="name" />
			hypertension: 
		    <input type="text" name="Updatedby" placeholder="name" />
		 
		    <input type="submit" value="Submit" /> 
		</form> 
	
	<?php
}

 

