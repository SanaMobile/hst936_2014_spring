<?php

//load and connect to MySQL database stuff
require("config.inc.php");

if (!empty($_POST)) {
    $query = " 
            UPDATE  children SET date1 = :d1 , date2 = :d2, time1= :t1, time2= :t2 WHERE curp = :curp
			";
        
    
    $query_params = array(
       
		
		 ':curp' => $_POST['Curp'],
		 ':d1' => $_POST['s'],
		 ':d2' => $_POST['s1'],
		 ':t1' => $_POST['t'],
		 ':t2' => $_POST['t1'],
		
		
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

 

