<?php
session_start();
$email=$_SESSION['email'];
echo "Hi, ".$email;

?>
<html>
<body>
<br>
<a href="view_queries.php">View Queries</a>
<br>
<a href="logout.php">Sign out</a>
</body>
</html>