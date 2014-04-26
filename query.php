<?php
session_start();
$email=$_SESSION['email'];
$_SESSION['email']=$email;
$con=mysqli_connect("sql2.freesqldatabase.com","sql237741","nB8%qG4*","sql237741");
$sql1="select pat_id,query,query_id from post_query where pat_id='$email'";
$result1=mysqli_query($con,$sql1);
if(mysqli_num_rows($result1)>=1)
{
	while($row1=mysqli_fetch_array($result1))
	{
		$query_id=$row1['query_id'];
		$_SESSION['query_id']=$row1['query_id'];
		$_SESSION['pat_id'] = $row1['pat_id'];
		$_SESSION['query'] = $row1['query'];
		echo "<br>".$_SESSION['pat_id'];
		echo "<textarea>".$_SESSION['query']."</textarea><br>";
		$sql2="select doc_id,reply from reply_query where query_id='$query_id'";
		$result2=mysqli_query($con,$sql2);
		if(mysqli_num_rows($result2)>=1)
		{
			while($row2=mysqli_fetch_array($result2))
			{
				$_SESSION['doc_id']=$row2['doc_id'];
				$_SESSION['reply']=$row2['reply'];
				echo "<br>".$_SESSION['doc_id'];
				echo "<textarea>".$_SESSION['reply']."</textarea><br>";
			}
		}
		//else "no replies";
	}
}
	echo "<form action='post.php' method='post'>";
	echo "<br>".$_SESSION['pat_id'];
	echo "<textarea name='query' required='yes'></textarea><br>";
	echo "<input type='submit' value='Submit'></form>"
	
?>