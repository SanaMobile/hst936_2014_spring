

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

$q=$_POST["hosp"];

$sql="SELECT * FROM hospital WHERE hosp_city = '".$q."'";

$result = mysql_query($sql);
if(!$result){
	echo "No Hospitals Listed";
	exit();
}

echo "<table border='1'>
<tr>
<th>Hospital Name</th>
<th>City</th>
<th>Address</th>
<th>Number</th>
</tr>";

while($row = mysql_fetch_array($result))
  {
  echo "<tr>";
  echo "<td>" . $row['hosp_name'] . "</td>";
  echo "<td>" . $row['hosp_city'] . "</td>";
  echo "<td>" . $row['hosp_address'] . "</td>";
  echo "<td>" . $row['hosp_number'] . "</td>";
  echo "</tr>";
  }
echo "</table>";
echo "<br><br><br><br><br>";
?> 
</div>
</div>
</body>
</html>