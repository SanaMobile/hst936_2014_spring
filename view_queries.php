<html>
<body>
<?php
session_start();
$email=$_SESSION['email'];
$con=mysqli_connect("sql2.freesqldatabase.com","sql237741","nB8%qG4*","sql237741");
$sql="select pat_id,status,query_id from post_query where status='pending'";
$result=mysqli_query($con,$sql);
if(mysqli_num_rows($result)>=1)
{
echo "<table border='1'>
		<tr>
		<th>Patient_id</th>
		<th>status</th>
		<th></th>
		</tr>";
while($row=mysqli_fetch_array($result))
{
	$pat_id=$row['pat_id'];
	$status=$row['status'];
	$query_id=$row['query_id'];
	echo "<tr><td>$pat_id</td><td>$status</td><td><a href='query_view.php'><input type='button' value='view' id='$query_id'></a></td></tr>";
}echo "</table>";

}else
echo "Congratulations! NO new Queries<br>";
echo "For old queries, Please click Button ";
echo "<a href='query_view.php'><input type='button' value='View'></a>";
?>

</body>
</html>