<?php 
require("constants.php");
	#1. Connect To Database
	$connection = mysql_connect(DB_SERVER,DB_USER,DB_PASS);
	if(!$connection){
	die("Database Connection Failed: " . mysql_error());
	}
	
	#2. Select A Database To Use
	$db_select = mysql_select_db (DB_NAME,$connection);
	if(!$db_select) {
		die("Database selection failed : " . mysql_error());
		}
?>