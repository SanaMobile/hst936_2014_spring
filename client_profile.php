<?php session_start();
?>
<html>
	<head>
		<style type="text/css">
			#form{
				margin-top:5%;
				width:50%;
				margin-left:25%; 
			}
			
			.div1{
			 float: left;
			 text-align:right;
			 width:35%;
			}
			legend{
				background:brown;
				font-weight:bold;
				font-size:1.2em;
				color:white;
			}
			textarea{
				resize:vertical;
				
				min-width:20px;
			}
			.div2{
			 margin-left:2%;
			 width:63%;
			}
			.div22{
			
			}
			div div{
				display: inline-block;
			}
			body{
				background-color: #E5BF85;
			}
		</style>
	</head>
	<body onload="disableForm()">
		<form id="form" >
		<fieldset>
		<legend>Personal Details</legend>
		<div>
			<div class="div1">Name:</div>
			<div class="div2"><input value="<?php echo ($_SESSION['name'])?>" class="div22" type="text" id="name" required="yes"></div>
		</div>
		<div>
			<div class="div1">Email:</div>
			<div class="div2"><input value="<?php echo ($_SESSION['email'])?>" type="email" name="email" required="yes"></div>
		</div>
		<div>
			<div class="div1">Date of Birth:</div>
			<div class="div2"><input value="<?php echo ($_SESSION['dob'])?>" type="date" name="dob" required="yes"></div>
		</div>
		<div>
			<div class="div1">Address:</div>
			<div class="div2"><textarea name="address" required="yes"><?php echo ($_SESSION['address'])?></textarea></div>
		</div>
		<div>
			<div class="div1">City:</div>
			<div class="div2"><input value="<?php echo ($_SESSION['city'])?>" class="div22" type="text" id="name" required="yes"></div>
		</div>
		<div>
			<div class="div1">Country:</div>
			<div class="div2"><input value="<?php echo ($_SESSION['country'])?>" class="div22" type="text" id="name" required="yes"></div>
		</div>
		<div>
			<div class="div1">Phone:</div>
			<div class="div2"><input value="<?php echo ($_SESSION['phone'])?>" type="number" name="phone" required="yes"></div>
		</div>
		
		<div style="margin-left:37%;margin-top:5%;">
		<p id="button"></p>
		</fieldset>
		</form>
	</body>
	<script type="text/javascript">
	function disableForm() {
	theform=document.forms[0];
    	if (document.all || document.getElementById) {
    		for (i = 1; i < theform.length; i++) {
    		var formElement = theform.elements[i];
    			if (true) {
    				formElement.disabled = true;
    			}
    		}
    	}
		id = document.getElementById("button");
		id.innerHTML='<input type="button" value="Edit" onclick="edit()"><a href="client_home.php"><input type="button" value="Back"></a>';
    }
	function edit()
	{
		theform=document.forms[0];
    	if (document.all || document.getElementById) {
    		for (i = 1; i < theform.length-1; i++) {
    		var formElement = theform.elements[i];
    			if (true) {
    				formElement.disabled = false;
    			}
    		}
    	}
		id = document.getElementById("button");
		id.innerHTML='<input type="submit" value="Submit">';
	}
		</script>
</html>