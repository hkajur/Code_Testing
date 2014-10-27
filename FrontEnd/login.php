<?php
	session_start();
	if($_SERVER["REQUEST_METHOD"] == "POST"){
            
		$username = test_input($_POST["username"]);
		$password = test_input($_POST["passwd"]);

		//$field = "username=" . urlencode($username) . "&password=" . urlencode($password);
		$field = 'username='.$username.'&password='.$password;
		$ch = curl_init();
		
		$url = "http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/login.php";

		curl_setopt_array($ch, array(
			CURLOPT_RETURNTRANSFER => 1,
			CURLOPT_URL => $url,
			CURLOPT_USERAGENT => 'Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.9.2.13) Gecko/20101206 Ubuntu/10.10 (maverick) Firefox/3.6.13',
			CURLOPT_FOLLOWLOCATION => true,
			CURLOPT_POST => 1,
			CURLOPT_POSTFIELDS => $field
		));
		//CURLOPT_HEADER => 1, //for server heading information purposes

		$resp = curl_exec($ch);
		$result = json_decode($resp, true);

		if(curl_errno($ch)){
			echo "Request Error" . curl_error($ch);
			exit;
		}
		
		curl_close($ch);

		//for testing curl and json results
		//echo $resp;
		//echo $result['NJIT_Login'];
		//echo $result['Backend_Login'];
		//echo $result['userID'];
		//echo $result['userType'];
		//echo $result['username'];
		//echo $result['exams'];
		/*
		foreach($result[exams] as $p) {
			echo 'examID: ' . $p[examID] . '
			name: ' . $p[examName] . '
			taken: ' . $p[examTaken];
		}*/
		//$_SESSION["user"] = $username;
                //if($result['NJIT_Login'] == "Success") {
		//	$_SESSION["usertype"] = "UCID";
		//	header ('Location: login_success.php');
		

		//test for database username
		$_SESSION["exams"] = $result;
		
		$_SESSION["user"] = $username;
		$_SESSION["userpid"] = $result["userID"];
		
		if ($result['Backend_Login'] == "Success") {
			$_SESSION["usertype"] = "USERNAME";
			if ($_SESSION["user"] == "student5") {
				header ('Location: student.php');
			} else if ($_SESSION["user"] == "professor1") {
				header ('Location: instructor.php');
			}
        	} else {
            		$_SESSION["user"] = "";
			$_SESSION["usertype"] = "INVALID";
			header ('Location: index.php');
		}
		

	} else {
		die("ERROR: Page must be posted");
	}

	function test_input($data) {
		$data = trim($data);
		$data = stripslashes($data);
		$data = htmlspecialchars($data);
		return $data;
	}
?>
