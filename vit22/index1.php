
<html>
<head>
<title>Sana</title>
<script src="jquery.js"></script>
<link href="theme.css" rel="stylesheet"  />
<script src="jquerymobile.js"></script>


</head>
<?php
include('includes/connection.php');
include('includes/function.php');
session_start();
if(isset($_POST['name'])){
$query = " INSERT into customer(customer_email,customer_name,customer_age,customer_sex,customer_password)
		   VALUES ('".$_POST['email']."',	
		   		   '".$_POST['name']."',
				   ".$_POST['age'].",
				   '".$_POST['sex']."',
				   '".$_POST['password']."')";
$result = mysql_query($query);
checker($result);	
echo "Account Created";
}

if(isset($_POST['email_login'])){


$query=" SELECT * FROM customer WHERE customer_email = '".$_POST['email_login']."' AND customer_password = '".$_POST['password_login']."'";
$result=mysql_query($query);
checker($result);
if(!$row=mysql_fetch_array($result)){
$_SESSION['error'] = "Invalid Credentials";
}
else{	
setcookie('user',$row['customer_id'],time()+13600);
header("Location: user_page.php");
}
	
	
}

?>
<body>
<div data-role="page" id="signin">

	<div data-role="header" data-theme="b">
		<h2>Health Guide</h2>
		<div data-role="navbar">
    <ul>
        <li><a href="#" class="ui-btn-active">Sign In</a></li>
        <li><a href="#signup"  >Sign Up</a></li>
    </ul>
	</div><!-- /navbar -->
	</div>
	
	<div data-role="content">
			
	<center>		
<form method="POST" action="" data-ajax="false" style="width:50%">


<p>Email : <input type="email" name="email_login" required></p>
<p>Password : <input type="password" name="password_login" required></p>   
<p><input type="submit" value="Login In"></p>
<?php
if(isset($_SESSION['error'])){
	echo $_SESSION['error'];
	unset($_SESSION['error']);
}
?>

</form>
	</center>
	
	</div>
</div>
<div data-role="page" id="signup">

	<div data-role="header" data-theme="b">
		<h2>Health Guide</h2>
		<div data-role="navbar">
    <ul>
        <li><a href="#signin" >Sign In</a></li>
        <li><a href="#" class="ui-btn-active">Sign Up</a></li>
    </ul>
	</div><!-- /navbar -->
	</div>
	
	
	<div data-role="content">
	
	<center>
		<form method="post" action="<?php  echo $_SERVER['PHP_SELF'];?>" style="width:50%">
					
					
					<p>Email : <input type="email" name="email" required></p>
					<p>Name : <input type="text" name="name" required></p>
					<p>Sex : <input type="radio" name="sex" value="Male" checked>Male
							 <input type="radio" name="sex" value="Female">Female</p>
					<p>Age : <input type="number" name='age' required min="1"></p>
					<p>Password : <input type="password" name="password" required></p>   
					<p><input type="submit" value="Register!"></p>
	</center>
</form>



</div>
	
	
	</div>
	
	
</body>
</html>