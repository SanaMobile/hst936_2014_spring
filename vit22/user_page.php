
<html>
<head>
<title>Sana</title>
<script src="jquery.js"></script>
<link href="theme.css" rel="stylesheet"  />
<script src="jquerymobile.js"></script>

</head>

<?php

include('includes/connection.php');
include('includes/function.php');

$query = "SELECT * FROM customer where customer_id = '".$_COOKIE['user']."'";
$result=mysql_query($query);
$row=mysql_fetch_array($result);
echo "Welcome ".$row['customer_name']."<br>";

?>
<body>

<div data-role="page" id="query">
			<div data-role="header" data-theme="b"><a href="index1.php" data-rel="external"  class="ui-btn ui-icon-arrow-l ui-btn-icon-notext ui-corner-all">No text</a>
			<h2>Health Guide</h2>
			</div>
<div data-role="content">

<div class="ui-grid-b">
    <div class="ui-block-a"><div class="ui-bar ui-bar-a">
	<form method="POST" action="getpharma.php" data-ajax="false">

Choose Pharmacy Location :<select name="users">
<?php
$query = "SELECT DISTINCT pharma_city from pharmacy";
$result = mysql_query($query);
checker($result);
$x= 0;
while($row = mysql_fetch_array($result)){
echo "<option value='".$row[$x]."'>".$row[$x]."</option>";	
}
?></select>
<button type="submit" name="pharmasubmit" class="ui-btn ui-btn-inline ui-icon-check ui-btn-icon-left">Submit</button>
</form>
	</div></div>
    

	
<div class="ui-block-b"><div class="ui-bar ui-bar-a" >
		
<form method="POST" action="gethosp.php" data-ajax="false">

Choose Hospital Location :<select name="hosp">

<?php
$query = "SELECT DISTINCT hosp_city from hospital";
$result = mysql_query($query);
checker($result);
$x= 0;
while($row = mysql_fetch_array($result)){
echo "<option value='".$row[$x]."'>".$row[$x]."</option>";	
}
?></select>
<button type="submit" class="ui-btn ui-btn-inline ui-icon-check ui-btn-icon-left">Submit</button>
</form>
	
	
	</div></div>
    <div class="ui-block-c"><div class="ui-bar ui-bar-a">
	
	

<form method="POST" action="getdoc.php" data-ajax="false">

Doctor Info :<select name="doc">

<?php
$query = "SELECT DISTINCT doctor_city from doctor";
$result = mysql_query($query);
checker($result);
$x= 0;
while($row = mysql_fetch_array($result)){
echo "<option value='".$row[$x]."'>".$row[$x]."</option>";	
}
?></select>
<button type="submit" href="#" class="ui-btn ui-btn-inline ui-icon-check ui-btn-icon-left">Submit</button>
</form>
	
	</div></div>
</div><!-- /grid-b -->





</div>
</div>
</div>
</body>
</html>

</body>
</html> 