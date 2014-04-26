<?php
///this part will be seen by nutritionist and will be replied.

session_start();
$con=mysqli_connect("sql2.freesqldatabase.com","sql237741","nB8%qG4*","sql237741");
$sql1="select pat_id,query,query_id from post_query";
$result1=mysqli_query($con,$sql1);
if(mysqli_num_rows($result1)>=1)
{
	while($row1=mysqli_fetch_array($result1))
	{
		$query_id=$row1['query_id'];
		$_SESSION['query_id']=$row1['query_id'];
		$_SESSION['pat_id'] = $row1['pat_id'];
		$query_id=$_SESSION['query_id'];
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
		echo "<form action='reply.php' method='post'>";
		echo $_SESSION['email'];
		echo "<textarea name='reply' required='yes'></textarea>";
		echo "<input type='hidden' name='query_id' value='$query_id'>";
		echo "<input type='submit' value='reply'></form>";
	}
}
	
	
?>