

<html>
<head>
<title>Sana</title>
<script src="jquery.js"></script>
<link href="theme.css" rel="stylesheet"  />
<script src="jquerymobile.js"></script>

</head>


<body>
<div data-role="page">
			<div data-role="header" data-theme="b"><a href="user_page.php"  data-rel="external" class="ui-btn ui-icon-arrow-l ui-btn-icon-notext ui-corner-all">No text</a>
			<h2>Health Guide</h2>
			</div>
<div data-role="content">


<?php

include('includes/connection.php');
include('includes/function.php');

$q=$_POST["doc"];

$sql="SELECT * FROM doctor WHERE doctor_city = '".$q."'   AND doctor_status = 'Free' ";

$result = mysql_query($sql);
if(!$result){
	echo "No Doctors Listed";
	exit();
}

echo "<table border='1'>
<tr>
<th>Doctor Name</th>
<th>City</th>
<th>Hospital</th>
<th>Status</th>
</tr>";

while($row = mysql_fetch_array($result))
  {
  echo "<tr>";
  echo "<td>" . $row['doctor_name'] . "</td>";
  echo "<td>" . $row['doctor_city'] . "</td>";
  echo "<td>" . $row['doctor_hosp'] . "</td>";
  echo "<td>" . $row['doctor_status'] . "</td>";
  echo "</tr>";
  }
echo "</table>";
echo "<br><br><br><br><br>";
?> 

</div>
</div>
</body>
</html>