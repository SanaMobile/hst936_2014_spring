<?php

//load and connect to MySQL database stuff
require("config.inc.php");

if (!empty($_POST)) {
    $query = " 
            UPDATE  adult SET labresult1= :lb1,labresult2= :lb2,labresult3= :lb3,labresult4= :lb4,labresult5= :lb5  WHERE curp = :curp
			";
        
    
    $query_params = array(
       
		':lb1' => $_POST['Lab1'],
		':lb2' => $_POST['Lab2'],
		':lb3' => $_POST['Lab3'],
		':lb4' => $_POST['Lab4'],
		':lb5' => $_POST['Lab5'],
		':curp' => $_POST['Curp']
		
		
		
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
		<form action="editlabrequesta.php" method="post"> 
		    CURP: 
		    <input type="text" name="Curp" placeholder="name" /> 
		      Name: 
		    <input type="text" name="Lab1" placeholder="name" />
			 Phone: 
		    <input type="text" name="Lab2" placeholder="name" />
			 email: 
		    <input type="text" name="Lab3" placeholder="name" />
			 relation: 
		    <input type="text" name="Lab4" placeholder="name" />
			 zip: 
		    <input type="text" name="Lab5" placeholder="name" />
			 
		 
		    <input type="submit" value="Submit" /> 
		</form> 
	
	<?php
}

 

