<?php

//load and connect to MySQL database stuff
require("config.inc.php");

if (!empty($_POST)) {
    $query = " 
            SELECT 
                Name, 
                Licence 
            FROM technician
            WHERE 
                Name = :name 
        ";
    
    $query_params = array(
        ':name' => $_POST['Name']
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
    $row = $stmt->fetch();
    if ($row) {
        //if we encrypted the password, we would unencrypt it here, but in our case we just
        //compare the two passwords
        if ($_POST['Licence'] === $row['Licence']) {
            $login_ok = true;
        }
    }
    
    // If the user logged in successfully, then we send them to the private members-only page 
    // Otherwise, we display a login failed message and show the login form again 
    if ($login_ok) {
        $response["success"] = 1;
        $response["message"] = "Login successful!";
        echo (json_encode($response));
    } else {
        $response["success"] = 0;
        $response["message"] = "Invalid Credentials!";
        echo (json_encode($response));
    }
} else {
?>
		<h1>Login</h1> 
		<form action="technicianlogin.php" method="post"> 
		    UserName: 
		    <input type="text" name="Name" placeholder="name" /> 
		     
		    Password:
		    <input type="password" name="Licence" placeholder="licence" value="" /> 
		    
		    <input type="submit" value="Login" /> 
		</form> 
	
	<?php
}

 

