<?php

//load and connect to MySQL database stuff
require("config.inc.php");


if (!empty($_POST)) {
    
	$query = " 
            INSERT INTO adult (curp,name,phone,email,zip,colony,gender,cancer,diabetes,hypertension,created_by,created_on,clinic_name,dust,medicine,metals,food,animals,immunization,weight,height,waist_circumference,temperature,other,blood_pressure) VALUES (:curp , :name, :phone, :email, :zip, :colony, :gender, :cancer, :diabetes, :hypertension,:created_by,:created_on,  :cname , :dust,:med,:metal,:food,:animal,:imm,:weight,:height,:waist,:temp,:other,:bp)
			";
			
	$query_params = array(
        ':curp' => $_POST['Curp'],
		':name' => $_POST['Name'],
		':phone' => $_POST['Phone'],
		':email' => $_POST['Email'],
		':zip' => $_POST['Zip'],
		':colony' => $_POST['Colony'],
		':gender' => $_POST['Gender'],
		':cancer' => $_POST['Cancer'],
		':diabetes' => $_POST['Diabetes'],
        ':hypertension' => $_POST['Hypertension'],
		':created_by' => $_POST['Created_by'],
		':created_on' => $_POST['Created_on'],
		':cname' => $_POST['Cname'],
		':dust' => $_POST['Dust'],
		':med' => $_POST['Med'],
		':metal' => $_POST['Metal'],
			':animal' => $_POST['Animal'],
		':food' => $_POST['Food'],
		':imm' => $_POST['Imm'],
		':other' => $_POST['Other'],
	':height' => $_POST['Height'],
		':weight' => $_POST['Weight'],
		':waist' => $_POST['Waist'],
			':bp' => $_POST['Bp'],
		':temp' => $_POST['Temp'],
	
		
		
		
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
    
    //This will be the variable to determine whether or //not the user's information is correct.
    //we initialize it as false.
    $validated_info = false;
    
    //fetching all the rows from the query
   
    
    // If the user logged in successfully, then we send them to the private members-only page 
    // Otherwise, we display a login failed message and show the login form again 
    if ($result) {
        $response["success"] = 1;
        $response["message"] = "EMR created successful!";
        echo (json_encode($response));
    } else {
        $response["success"] = 0;
        $response["message"] = "Something went wrong ! Try again";
        echo (json_encode($response));
    }
} else {
?>
		<h1>Login</h1> 
		<form action="child.php" method="post"> 
		    CURP: 
		    <input type="text" name="Curp" placeholder="name" /> 
		      Name: 
		    <input type="text" name="Name" placeholder="name" />
			 Phone: 
		    <input type="text" name="Phone" placeholder="name" />
			 email: 
		    <input type="text" name="Email" placeholder="name" />
			 relation: 
		    <input type="text" name="Relation" placeholder="name" />
			 zip: 
		    <input type="text" name="Zip" placeholder="name" />
			 colony: 
		    <input type="text" name="Colony" placeholder="name" />
			 gender: 
		    <input type="text" name="Gender" placeholder="name" />
			 cancer: 
		    <input type="text" name="Cancer" placeholder="name" />
			 diabetes: 
		    <input type="text" name="Diabetes" placeholder="name" />
			hypertension: 
		    <input type="text" name="Hypertension" placeholder="name" />
		 created by: 
		    <input type="text" name="Created_by" placeholder="name" />
		   created on: 
		    <input type="text" name="Created_on" placeholder="name" /> 
		    <input type="submit" value="Submit" /> 
		</form> 
	
	<?php
}

 

