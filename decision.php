<?php
$id=$_POST['disease'];
$con=mysqli_connect("sql2.freesqldatabase.com","sql237741","nB8%qG4*","sql237741");
$sql1="select distinct disease from d_s_p where disease_id='$id'";
$result1=mysqli_query($con,$sql1);
$row1=mysqli_fetch_array($result1);
echo "You are suffering from <b>".$row1['disease']."</b>";
$sql2="select medicine from d_s_p where disease_id='$id'";
$result2=mysqli_query($con,$sql2);
echo "<br>-->Please take following prescription <ul>";
while($row2=mysqli_fetch_array($result2))
{
	echo "<li>".$row2['medicine']."</li>";
}
echo "</ul>";
?>