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
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<link rel="stylesheet" href="//ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/themes/flick/jquery-ui.css" />
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/jquery-ui.min.js"></script>
<script>
$(function() {
    var spinner = $( "#spinner" ).spinner();
    $( ".submits" ).button();
});
</script>

</head>
<body id="createQ">
<!-- Start of Page -->
<div id="page">

<!-- Start of Header -->
<div id="header">

        <!-- Start of Logo -->
        <div id="logos">
                <a href="instructor.php"><p>Code Testing</p></a>
        </div><!-- End of Logo -->

        <div id="nav">
                <ul id="navitems">
                        <li><a href="createQ.php" id="createNav">Create Questions</a></li>
                        <li><a href="createExam.php">Create Exam</a></li>
			<li><a href="release.php">Release Grades</a></li>
                </ul>

        </div>
                <div id="logout">
                <a href="logout.php"><input type="submit" value="Log out" /></a>
        </div>

        <!-- Start of Welcome Div -->
        <div id="welcome">

        <?php
                if ($_SESSION["usertype"] == "UCID") {
                        echo "Welcome " . $_SESSION["user"];
                        echo ", you are now logged in with your UCID";
                } else if ($_SESSION["usertype"] == "USERNAME") {
                        echo "Welcome " . $_SESSION["user"];
                        echo " (logged in)";
                }
        ?>

        </div> <!-- End of Welcome Div -->

</div> <!-- End of Header -->


<!-- Start of Main Content -->
<div id="main">
<div id="content">

<div id="iPanel">
        <h1>Create Question</h1><br>
	<p>Multiple Choice</p><br>

	<form action="MCSubmit.php" method="post">
	
	<div class="Qinput">
	Question: <input type="text" name="question" value="" required><br>
	Correct Answer: <input type="text" name="correct" value="" required><br>
	Correct Reason: <input type="text" name="correct_reason" value="" required><br>
	Wrong Answer 1: <input type="text" name="wrongAnswer1" value="" required><br>
	Wrong Reason 1: <input type="text" name="wrongReason1" value="" required><br>
	Wrong Answer 2: <input type="text" name="wrongAnswer2" value="" required><br>
	Wrong Reason 2: <input type="text" name="wrongReason2" value="" required><br>
	Wrong Answer 3: <input type="text" name="wrongAnswer3" value="" required><br>
	Wrong Reason 3: <input type="text" name="wrongReason3" value="" required><br><br>
	</div>
	
	<p>
	<label for="spinner">Points:</label>
	<input id="spinner" name="points">
	</p><br>
	
	<input class="submits" type="submit" value="Submit"><br>

	<?php
	//<form action="created.php" method="post">
	//	<input type="text" name="newQ" value=""><br><br>

	?>

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
