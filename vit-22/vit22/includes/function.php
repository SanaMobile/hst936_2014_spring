<?php
//File to store functions
function checker($result)
{
if (!$result){
	die("Database query failed : " . mysql_error());
	}
}

?>
