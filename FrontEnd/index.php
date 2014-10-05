<?php session_start(); 
	if(isset($_SESSION["user"]) && !empty($_SESSION["user"]))
		header("Location: login_success.php");
	echo $_SESIION["user"];
	if(!isset($_SESSION["user"]))
		$_SESSION["usertype"] = "";
?>
<!DOCTYPE HTML>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<title>Code Testing</title>
<link href='http://fonts.googleapis.com/css?family=Source+Sans+Pro' rel='stylesheet' type='text/css'>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
<!-- Start of Page -->
<div id="page">

<!-- Start of Header -->
<div id="header">
    
    <!-- Start of Banner -->
    <div id="banner">
        
        <!-- Start of Logo -->
        <div id="logo">
        <a href="./index.php"><h1>Code Testing</h1></a>
        </div><!-- End of Logo -->
    
    </div> <!-- End of Banner -->
   
</div> <!-- End of Header -->

<!-- Start of Main Content -->
<div id="main">
<div id="content">

     <!-- Start of Login Div -->
    <div id="login">
        
        <!-- Start of Login Form -->
        <form name="loginForm" action="login.php" method="post">
            <h2> Login </h2>
            <!-- Start of Username Div -->
            <div  id="username">
		        <input type="text" name="username" placeholder="UCID or Username" value="" required/>
            </div> <!-- End of Username Div -->
            
            <!-- Start of Password Div -->
            <div  id="password">
                <input type="password" name="passwd" placeholder="Password" value="" required/>
            </div> <!-- End of Password Div -->
            
            <!-- Start of Submit Div -->
            <div id="submit">
                <input type="submit" value="Sign in"/>
            </div> <!-- End of Submit Div -->

	    <div id="error">
	    	<?php
			//echo $_SESSION["usertype"];
			if ($_SESSION["usertype"] == "INVALID") {
				echo "Incorrect username or password<br>";
				session_unset();
			}
		?>  
	    </div>
        </form> <!-- End of Login Form -->
    
    </div> <!-- End of Login Div -->
</div> 
</div> <!-- End of Main Content -->

<div id="push">
</div>

</div> <!-- End of Page -->

<!-- Start of Footer -->
<div id="footer">
    
    <!-- Start of Copyright -->
    <div id="copyright">
        <p>Copyright &copy 2014</p>
    </div> <!-- End of Copyright -->
    
    <!-- Start of Authors -->
    <div id="authors">
        <p>Developed by: Harish, Andres, Dmitri, Christopher</p>
    </div> <!-- End of Authors -->

</div> <!-- End of Footer -->
</body>
</html>
