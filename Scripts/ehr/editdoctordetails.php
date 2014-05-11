<?php

//load and connect to MySQL database stuff
require("config.inc.php");

if (!empty($_POST)) {

    $query = " 
            UPDATE  children SET 
			pysical_exploration1= :pe1,pysical_exploration2= :pe2,pysical_exploration3= :pe3,pysical_exploration4= :pe4,pysical_exploration5= :pe5,pysical_exploration6= :pe6,improvement= :imp,recovery= :rec,aggravate=     :agg,diagnosis1= :d1,diagnosis2= :d2,diagnosis3= :d3,diagnosis4= :d4,diagnosis5= :d5,diagnosis6= :d6,d_notes= :dn,treatement1= :t1,dosis1= :td1,via1= :tv1,periodicity1= :tp1,treatement2= :t2,dosis2= :td2,via2= :tv2,periodicity2= :tp2,treatement3= :t3,treatement4= :t4,dosis3= :td3,via3= :tv3,periodicity3= :tp3,labrequest1= :lr1,labrequest2= :lr2,labrequest3= :lr3,labrequest4= :lr4,labrequest5= :lr5
			WHERE curp = :curp
			";
       
    $query_params = array(
        ':curp' => $_POST['Curp'],
		':pe1' => $_POST['Physical1'],
		':pe2' => $_POST['Physical2'],
		':pe3' => $_POST['Physical3'],
		':pe4' => $_POST['Physical4'],
		':pe5' => $_POST['Physical5'],
		':pe6' => $_POST['Physical6'],
		':imp' => $_POST['Imp'],
		':rec' => $_POST['Rec'],
		':agg' => $_POST['Agg'],
		':d1' => $_POST['Diagnosis1'],
		
		':d2' => $_POST['Diagnosis2'],
		
		':d3' => $_POST['Diagnosis3'],
		':d4' => $_POST['Diagnosis4'],
		
		':d5' => $_POST['Diagnosis5'],
		
		':d6' => $_POST['Diagnosis6'],
		':dn' => $_POST['Dnotes'],
		':t1' => $_POST['Treatment1'],
		':td1' => $_POST['Td1'],
		':tv1' => $_POST['Tv1'],
		':tp1' => $_POST['Tp1'],
		':t2' => $_POST['Treatment2'],
		':td2' => $_POST['Td2'],
		':tv2' => $_POST['Tv2'],
		':tp2' => $_POST['Tp2'],
		':t3' => $_POST['Treatment3'],
		':t4' => $_POST['Treatment4'],
		':td3' => $_POST['Td3'],
		':tv3' => $_POST['Tv3'],
		':tp3' => $_POST['Tp3'],
		':lr1' => $_POST['Lr1'],
		':lr2' => $_POST['Lr2'],
		':lr3' => $_POST['Lr3'],
		':lr4' => $_POST['Lr4'],
		':lr5' => $_POST['Lr5'],
		
		
		
		
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
		<form action="editdoctordetails.php" method="post"> 
		    CURP: 
		    <input type="text" name="Curp" placeholder="name" /> 
		      Name: 
		    <input type="text" name="Physical1" placeholder="name" />
			 Phone: 
		    <input type="text" name="Physical2" placeholder="name" />
			 email: 
		    <input type="text" name="Physical3" placeholder="name" />
			 relation: 
		    <input type="text" name="Pd1" placeholder="name" />
			 zip: 
		    <input type="text" name="Pd2" placeholder="name" />
			 colony: 
		    <input type="text" name="Pd3" placeholder="name" />
			 gender: 
		    <input type="text" name="Diagnosis1" placeholder="name" />
			 cancer: 
		    <input type="text" name="Diagnosis2" placeholder="name" />
			 diabetes: 
		    <input type="text" name="Diagnosis3" placeholder="name" />
			hypertension: 
		    <input type="text" name=" Dd1" placeholder="name" />
			  <input type="text" name="Dd2" placeholder="name" />
			  <input type="text" name="Dd3" placeholder="name" />
			  <input type="text" name="Dnotes" placeholder="name" />  
			    <input type="text" name="Treatment1" placeholder="name" />
				  <input type="text" name="Treatment2" placeholder="name" 	/>		    <input type="text" name="Treatment3" placeholder="name"/>
				 <input type="text" name="Td1" placeholder="name" />
			    <input type="text" name="Td2" placeholder="name" />
				 <input type="text" name="Td3" placeholder="name" />
			    <input type="text" name="Tv1" placeholder="name" /> 				<input type="text" name="Tv2" placeholder="name" />
				  <input type="text" name="Tv3" placeholder="name" />					
				  <input type="text" name="Tp1" placeholder="name" />
				 <input type="text" name="Tp2" placeholder="name" />
					 <input type="text" name="Tp3" placeholder="name" />
					  <input type="text" name="Tdetails1" placeholder="name" />
					  <input type="text" name="Tdetails2" placeholder="name" />
					   <input type="text" name="Tdetails3" placeholder="name" />
					 <input type="text" name="Lr1" placeholder="name" />
					 <input type="text" name="Lr2" placeholder="name" />
					 <input type="text" name="Lr3" placeholder="name" />
					 <input type="text" name="Lr4" placeholder="name" />
					 <input type="text" name="Lr5" placeholder="name" />
					 <input type="text" name="Imp" placeholder="name" />
					 <input type="text" name="Rec" placeholder="name" />
					  <input type="text" name="Agg" placeholder="name" />
										 
						
		 
		    <input type="submit" value="Submit" /> 
		</form> 
	
	<?php
}

 

