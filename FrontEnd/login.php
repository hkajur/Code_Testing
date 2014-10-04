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
		
		//for testing curl and json results
		//echo $resp;
		//echo $result['NJIT_Login'];
		//echo $result['Backend_Login'];
		
		//test for either a UCID or database username
		$_SESSION["user"] = $username;
                if($result['NJIT_Login'] == "Success") {
			$_SESSION["usertype"] = "UCID";
		} else if ($result['Backend_Login'] == "Success") {
			$_SESSION["usertype"] = "USERNAME";
		}

		curl_close($ch);
		header ('Location: login_success.php');

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
