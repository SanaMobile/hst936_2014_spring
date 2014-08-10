<?php
//session_start();
echo "<h2>Get your deficiency diagnosed</h2>";
$con=mysqli_connect("sql2.freesqldatabase.com","sql237741","nB8%qG4*","sql237741");
$sql1="select distinct disease_id from d_s_p";
$result1=mysqli_query($con,$sql1);
$i=1;
echo "<form action='decision.php' method='post'>";
while($row1=mysqli_fetch_array($result1))
{
	$id=$row1['disease_id'];
	echo "<br><input type='radio' name='disease' value='$i'>If you have following symptoms check this radio button";
	$sql2="select symptom from d_s_p where disease_id='$id'";
	$result2=mysqli_query($con,$sql2);
	echo "<ul>";
	while($row2=mysqli_fetch_array($result2))
	{
		echo "<li>".$row2['symptom']."</li>";
	}
	$i++;
	echo "</ul>";
}
echo "<input type='submit' value='Submit'></form>";
?>