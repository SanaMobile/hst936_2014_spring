<html>

<?php
 $name=$_POST['name'];
 $email=$_POST['email'];
 $password=$_POST['password'];
 $dob=$_POST['dob'];
 $sex=$_POST['sex'];
 $address=$_POST['address'];
 $city=$_POST['city'];
 $country=$_POST['country'];
 $phone=$_POST['phone'];

 $con=mysqli_connect("sql2.freesqldatabase.com","sql237741","nB8%qG4*","sql237741");

// Check connection
if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }
  
  $sql="INSERT INTO client values('$email','$password','$age','$sex','$city','$country','$name','$phone','$address','$dob')" ;

  if (!mysqli_query($con,$sql))
  {
  die('Error: ' . mysqli_error($con));
  }
  echo "Successfully Registered";
  ?>
  </html>