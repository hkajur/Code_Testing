<?php
	session_start();
	if($_SERVER["REQUEST_METHOD"] == "POST"){
            
		//$num = count($_POST);
		//echo $num;
		
		$type = $_POST["type"];
		//echo $type;
		
		if ($type == 'MC'){
			header ('Location: MCcreate.php');
		} else if ($type == 'TF'){
			header ('Location: TFcreate.php');
		} else if ($type == 'FB'){
			header ('Location: FBcreate.php');
		} else if ($type == 'PM'){
                        header ('Location: PMcreate.php');
                }





		//$arr = array('userID' => $userID, 'examID' => $examID, 'questionAnswer' => array('questionID' => 1, 'userAnswer'=> 3)); 	
		//$json = json_encode($arr);

		//echo $_POST;
		//echo $ans;
		
		//$username = test_input($_POST["username"]);
		//$password = test_input($_POST["passwd"]);

		//$field = "username=" . urlencode($username) . "&password=" . urlencode($password);
		//CURLOPT_HEADER => 1, //for server heading information purposes

		/*
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
		/*
		$_SESSION["exams"] = $result;
		

		$_SESSION["user"] = $username;
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
		*/

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
