<!DOCTYPE html>
<html>
<head>
<title>VIT-13 PROTCOL BUILDER</title>
<style>
.kim {
	background: #151515;
   position:center; 

	text-align:center;

}

.kim input {
	background: #222;
	background: -webkit-linear-gradient(#333, #222);	
	background: -moz-linear-gradient(#333, #222);	
	background: -o-linear-gradient(#333, #222);	
	background: -ms-linear-gradient(#333, #222);	
	background: linear-gradient(#333, #222);	
	border: 1px solid #444;
	border-radius: 5px 0 0 5px;
	box-shadow: 0 2px 0 #000;
	color: #888;
	display: block;
	}
</style>
</head>
<body bgcolor="silver">

<div style="margin-left:400px;margin-top:200px;">
<form  action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>" method="POST">
<p style='color:"white";' >Enter the search key for xml</p>
<br>
<input type="text" name="key">
<input type="submit" name="submit" value="search">
<br>
<br>
</form>
</div>

<?php
   $host        = "host=127.0.0.1";
   $port        = "port=5432";
   $dbname      = "dbname=mysanadb";
   $credentials = "user=kundan password=kundan165";
   $key=$_POST['key'];
   $db = pg_connect( "$host $port $dbname $credentials"  );
   if(!$db){
      echo "Error : Unable to open database\n";
   } else {
//      echo "Opened database successfully\n";
   }
$sql =<<<EOF
      SELECT id from sample where key='$key';
EOF;
print "<table id='t1' border='5' width='80%' align='center'style='color:white;'>";



print "<br><br><br> <br> <br><tr>

<th>LIST OF XML's</th>


</tr>";
   $ret = pg_query($db, $sql);
   if(!$ret){
      echo pg_last_error($db);
      exit;
   } 
   while($row = pg_fetch_row($ret)){
      $val =$row[0].".xml";
         print "<tr >";

print "<td>";
print "<a href=".$val." target=_blank >";
print $row[0].".xml";
//print $val;

print "</td>";
      
     
   }
print "</table>";
//   echo "Operation done successfully\n";
   pg_close($db);
?>
</body>



