<?php
session_start();
?>
<html>
	<head>
		<style type="text/css">
			.text_info{
				border:1px solid #de9b0a;
				background:white;
				text-color:black;
				font-weight:500;
				font-size: large;
				text-align: center;
				margin:0;
				
			}
			#nav {
				background: #E5BF85;
				float: none;
				padding: 0;	
				border: 1px solid #e5bf85;
				border-bottom: none;
				display:block;
				margin-left:35%;
				
			}

			#nav li a, #nav li {
				float: left;
				border-radius:5px;
								}

			#nav li {
				list-style: none;
				position: relative;
					}

			#nav li a {
				padding: 1em 2em;
				text-decoration: none;
				color: white; 
				background: #e99473;
				border: 1px solid #000000;
	 				}

			#nav li a:hover {
				background: #b5571f;
		 					}

			body{
				background-color: #E5BF85;
			}
		</style>
	</head>
	<body>
		
		<?php echo "Hi, " ;  echo $_SESSION['name']."(".$_SESSION['email'].")";
	
		?><br>
		<p><a href="client_profile.php">Profile</a><br>
		<a href="query.php">Queries</a><br>
		
		<a href="questionnaire.php">Suffering from disease</a><br>
		<a href="logout.php">Sign out</a><br></p>
	</body>
</html>