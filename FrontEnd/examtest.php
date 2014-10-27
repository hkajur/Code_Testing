<?php session_start(); ?>
<?php 
header("Cache-control: no-store, no-cache, must-revalidate, max-age=0");
header("Expires: Sat, 26 Jul 1997 05:00:00 GMT");
if(!isset($_SESSION["user"]) || empty($_SESSION["user"]))
        header("Location: index.php");
?>
<!DOCTYPE HTML>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width">
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
   
     <!-- Start of Welcome Div -->
    <div id="welcome">
        
	<?php 
		if ($_SESSION["usertype"] == "UCID") {
			echo "Welcome " . $_SESSION["user"];
			echo ", you are now logged in with your UCID";
		} else if ($_SESSION["usertype"] == "USERNAME") {
			echo "Welcome " . $_SESSION["user"];
			echo ", you are now logged in with your Username";
		}
	?>
    

    </div> <!-- End of Welcome Div -->
    <div id="logout">
    <a href="logout.php"><input type="submit" value="Log out" />
    </a>
    </div>
</div> <!-- End of Header -->

<!-- Start of Main Content -->
<div id="main">
<div id="content">
<h3>Exam 1</h3><br>
<div id="question1">
	<p>1) How many bits in a Byte?</p>
	
	<form action="">
	A. <input type="radio" value="2bits"> 2 bits<br>
	B. <input type="radio" value="4bits"> 4 bits<br>
	C. <input type="radio" value="6bits"> 6 bits<br>
	D. <input type="radio" value="8bits"> 8 bits<br>
	</form>
</div>
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
