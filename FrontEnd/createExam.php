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
		$( "#tabs" ).tabs({
		});
		$( ".submits" ).button();
	});
</script>

</head>
<body id="createE">
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
			<li><a href="createExam.php" id="createExamNav">Create Exam</a></li>
			<li><a href="release.php" id="releaseNav">Release Grades</a></li>
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
        <h1>Create Exam</h1><br>

	<form id="examForm" action="teacherCreateExam.php" method="post">
	<h3>Exam Name</h3>
	<input id="examName" type="text" name="examName" value="" required><br><br>
	
	<?php
        $ch = curl_init();
	$userID = "5";
        $URL = "http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/allQuestions.php";
	$postfields = array("userID" => "5");
        curl_setopt($ch, CURLOPT_URL, $URL);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_POST, count($postfields));
        curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($postfields));

        $page = curl_exec($ch);

        if(curl_errno($ch)){
            die(json_encode(array("Error" => curl_error($ch))));
        }
	$json = json_decode($page, true);
	
	?>

	<div id="tabs">
		<ul>
			<li><a href="#tabs-1">Multiple Choice</a><li>
			<li><a href="#tabs-2">Fill in the Blank</a><li>
			<li><a href="#tabs-3">True or False</a><li>
			<li><a href="#tabs-4">Programming</a><li>
		</ul>

		<div id="tabs-1">
			<?php
			foreach($json["questions"] as $key => $value){
				$questionID = $value["questionID"];
				$question = $value["question"];
				$questionType = $value["questionType"];
				if ($questionType == "MC") {
					echo "<input type=\"checkbox\" name=\"questionArray[]\" value=\"$questionID\"> $question";
					echo "<br>";
				}
			} ?>
		</div>

		<div id="tabs-2">
                	<?php
                        foreach($json["questions"] as $key => $value){
                                $questionID = $value["questionID"];
                                $question = $value["question"];
                                $questionType = $value["questionType"];
                                if ($questionType == "FB") {
                                        echo "<input type=\"checkbox\" name=\"questionArray[]\" value=\"$questionID\" > $question";
                                        echo "<br>";
                                } 
                        } ?>
                </div>

		<div id="tabs-3">
                	<?php
                        foreach($json["questions"] as $key => $value){
                                $questionID = $value["questionID"];
                                $question = $value["question"];
                                $questionType = $value["questionType"];
                                if ($questionType == "TF") {
                                        echo "<input type=\"checkbox\" name=\"questionArray[]\" value=\"$questionID\" > $question";
                                        echo "<br>";
                                } 
                        } ?>
                </div>

		<div id="tabs-4">
                        <?php
                        foreach($json["questions"] as $key => $value){
                                $questionID = $value["questionID"];
                                $question = $value["question"];
                                $questionType = $value["questionType"];
                                if ($questionType == "PM") {
                                        echo "<input type=\"checkbox\" name=\"questionArray[]\" value=\"$questionID\" > $question";
                                        echo "<br>";
                                } 
                        } ?>
                </div>
	</div>	
	<br>

	<input class="submits" type="submit" value="Create Exam"><br><br>

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
