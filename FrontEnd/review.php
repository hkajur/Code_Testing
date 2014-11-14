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
    
        <!-- Start of Logo -->
        <div id="logos">
        	<a href="student.php"><p>Code Testing</p></a>
        </div><!-- End of Logo -->
	
	<div id="nav">
		<ul id="navitems">
			<li><a href="takeEx.php">Take Exam</a></li>
			<li><a href="graded.php">Graded Exam</a></li>
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

<div id="sPanel">
	<h1>Graded Exams</h1><br>
	
	<?php

	
		$examID = $_GET['id'];
		$userID = $_SESSION["userpid"];
		 $URL = "http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/studentCheckGradedExam.php"; 
		 $ch = curl_init($URL);

        curl_setopt($ch, CURLOPT_POST, 1);
        curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query(
                            array("userID" => $userID,
			    		"examID" => $examID)));
        curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

        $page = curl_exec($ch);

        if(curl_errno($ch)){
            die(json_encode(array("Error" => curl_error($ch))));
        }
	$result = json_decode($page, true);
	
		//echo $listExam[exams][0][examName];
		//$num = 1;

		foreach($result[exam] as $p) {
			

			echo "<p>Question: $p[question]</p>";
			echo "<p>Your Answer: $p[studentAnswer]</p>";
			echo "<p>Correct Answer: $p[correctAnswer]</p>";

			//echo "<div id=\"wrong\">";
			if($p["userCorrect"] == "True"){
				echo "<div id=\"right\"><p>You are correct</p></div>";
			} 
			else {
				echo "<div id=\"wrong\"><p>You are incorrect</p></div>";
			}
			//echo "</div>";

			if(!empty($p["comment"]) && !is_null($p["comment"])){
			echo "<p>Feedback: $p[comment]</p>";
			}

		}
	?>
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
