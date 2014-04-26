<?php
session_start();
$email=$_POST['email'];
$password=$_POST['password'];
$con=mysqli_connect("sql2.freesqldatabase.com","sql237741","nB8%qG4*","sql237741");
if($con)
{
	$sql1="select * from client where email='$email' and password='$password'";
	$sql2="select * from admin where email='$email' and password='$password'";
	$result1=mysqli_query($con,$sql1);
	if(mysqli_num_rows($result1)==1)
	{
		while($row=mysqli_fetch_array($result1)){
		$_SESSION['name']=$row['name'];
		$_SESSION['email']=$row['email'];
		$_SESSION['dob']=$row['dob'];
		$_SESSION['address']=$row['address'];
		$_SESSION['city']=$row['city'];
		$_SESSION['country']=$row['country'];
		$_SESSION['phone']=$row['phone'];
		}
		$_SESSION['email'];
		header("Location:client_home.php");
		//echo "user client";
		//$row1=mysqli_fetch_array($result1);
		//echo $row1['email'];
	}
	else
	{
		
		$result2=mysqli_query($con,$sql2);
		if(mysqli_num_rows($result2)==1)
		{
			while($row=mysqli_fetch_array($result2)){
			$_SESSION['name']=$row['name'];
			$_SESSION['email']=$row['email'];}
			header("Location:nutrition.php");
			//echo "user not client";
			//$row2=mysqli_fetch_array($result2);
			//echo $row2['email'];
		}
		else
			echo "SORRY! that account does not exist";
	}
}
else 
	echo "error";
?>