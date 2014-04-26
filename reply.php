<?php
session_start();
$reply=$_POST['reply'];
$query_id=$_POST['query_id'];
$email=$_SESSION['email'];
$con=mysqli_connect("sql2.freesqldatabase.com","sql237741","nB8%qG4*","sql237741");
$sql="insert into reply_query (doc_id, query_id, reply) values ('$email', '$query_id','$reply')";
$result=mysqli_query($con,$sql);
if($result)
{
	header("Location:nutrition.php");
	$sql2="select status from post_query where query_id='$query_id'";
	$result2=mysqli_query($con,$sql2);
	if(mysqli_num_rows($result2)>=1)
	{
		$sql3="update post_query set status='answered' where query_id='$query_id'";
		$result3=mysqli_query($con,$sql3);
	}
}
else
	echo "error";
?>