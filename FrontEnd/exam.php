<?php session_start(); ?>
<?php 
header("Cache-control: no-store, no-cache, must-revalidate, max-age=0");
header("Expires: Sat, 26 Jul 1997 05:00:00 GMT");
if(!isset($_SESSION["user"]) || empty($_SESSION["user"]))
        header("Location: index.php");

$id = $_GET['id'];

			$field = 'userID='.'5'.'&examID='.$id;
	                $ch = curl_init();

	                $url = "http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/studentExams.php";

	                curl_setopt_array($ch, array(
	                        CURLOPT_RETURNTRANSFER => 1,
	                        CURLOPT_URL => $url,
	                        CURLOPT_USERAGENT => 'Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.9.2.13) Gecko/20101206 Ubuntu/10.10 (maverick) Firefox/3.6.13',
	                        CURLOPT_FOLLOWLOCATION => true,
	                        CURLOPT_POST => 1,
	                        CURLOPT_POSTFIELDS => $field
	                ));
	                //CURLOPT_HEADER => 1, //for server heading information purposes

	                $studentExams = curl_exec($ch);
	                $studentResult = json_decode($studentExams, true);

	                if(curl_errno($ch)){
	                        echo "Request Error" . curl_error($ch);
	                        exit;
	                }

	                curl_close($ch);

			$_SESSION["examID"] = $id;

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
    $( ".submits" ).button();
});
</script>

</head>
<body id="takeE">
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
			<li><a href="takeEx.php" id="takeNav">Take Exam</a></li>
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
	<h1>Taking exam</h1><br>
	
	<form action="submitExam.php" method="post">
		
		<?php
		foreach($studentResult[questions] as $p) {
			echo $p[question]."<br>";
			if ($p[question_type] == "MC") {
				/*echo "<input type=\"radio\" name=\"answer$p[questionID]\" value=\"$p[questionID]&$p[choices][0][choice]\"> " . ' ' . $p[choices][0][choice] . "<br>";
				echo "<input type=\"radio\" name=\"answer$p[questionID]\" value=\"$p[questionID]&$p[choices][1][choice]\"> " . ' ' . $p[choices][1][choice] . "<br>";
				echo "<input type=\"radio\" name=\"answer$p[questionID]\" value=\"$p[questionID]&$p[choices][2][choice]\"> " . ' ' . $p[choices][2][choice] .
				"<br>"; 
				echo "<input type=\"radio\" name=\"answer$p[questionID]\" value=\"$p[questionID]&$p[choices][3][choice]\"> " . ' ' .
				$p[choices][3][choice] . "<br>";*/
				echo "<input type='radio' name='answer$p[questionID]' value='1'>".' '.$p[choices][0][choice]."<br>";
				echo "<input type='radio' name='answer$p[questionID]' value='2'>".' '.$p[choices][1][choice]."<br>";
				echo "<input type='radio' name='answer$p[questionID]' value='3'>".' '.$p[choices][2][choice]."<br>";
				echo "<input type='radio' name='answer$p[questionID]' value='4'>".' '.$p[choices][3][choice]."<br><br>";
			} else if ($p[question_type] == "TF") {
				echo "<input type='radio' name='answer$p[questionID]' value='1'>".' '.$p[choices][0][choice]."<br>";
				echo "<input type='radio' name='answer$p[questionID]' value='2'>".' '.$p[choices][1][choice]."<br><br>";
			} else if ($p[question_type] == "FB") {
				echo "<input type='text' name='answer$p[questionID]' value=''><br><br>";
			} else if ($p[question_type] == "PM") {
			  	echo "<textarea name='answer$p[questionID]' rows='15' cols='80'></textarea><br><br>";
			}
		}
		?>
	<input class="submits" type="submit" value="Submit Exam" /><br><br>
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
