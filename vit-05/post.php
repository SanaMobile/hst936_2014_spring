<?php
session_start();
$email=$_SESSION['email'];
$query=$_POST['query'];
echo $email."<br>";
echo $query;
$con=mysqli_connect("sql2.freesqldatabase.com","sql237741","nB8%qG4*","sql237741");
$sql="insert into post_query (pat_id,query,status) values ('$email','$query','pending')";
$result=mysqli_query($con,$sql);
echo "query posted";
?>