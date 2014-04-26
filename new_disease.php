<?php
session_start();
$disease=$_POST['disease'];
$symptom=$_POST['symptom'];
$prescription=$_POST['prescription'];
$sql="insert into d_s_p values('$disease','symptom','$prescription')";
$con=mysqli_connect("sql2.freesqldatabase.com","sql237741","nB8%qG4*","sql237741");
mysqli_query($con,$sql);
echo "<a href='nutritionist.php'><button value='Home'></a>";
?>