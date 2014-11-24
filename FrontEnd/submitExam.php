<?php
	session_start();
	if($_SERVER["REQUEST_METHOD"] == "POST"){
            
		//$num = count($_POST);
		//echo $num;
		$examID = $_SESSION["examID"];
		$userID = $_SESSION["userpid"];
		$answer = $_POST["answer"];

		$finalArray = array("examID" => $examID,
				"userID" => $userID,
				"questionAnswer" => array());
			
		$index = 0;
		foreach($_POST as $key => $val){
			$questionID = str_replace("answer", "", $key);
			$choiceID = $val;


        		$postfields = array("questionID" => $questionID,
        		                "choiceID" => $choiceID);

        		$ch = curl_init();

        		$URL = "http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/getChoiceQuestion.php";

        		curl_setopt($ch, CURLOPT_URL, $URL);
        		curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        		curl_setopt($ch, CURLOPT_POST, count($postfields));
        		curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
        		curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($postfields));

        		$page = curl_exec($ch);

        		if(curl_errno($ch)){
        		    die(json_encode(array("Error" => curl_error($ch))));
        		}

			if(empty($page))
				$userAnswer = $val;
			else 
				$userAnswer = $page;

			$assocArray = array("questionID" => $questionID,
					"userAnswer" => $userAnswer);

			$finalArray["questionAnswer"][$index] = $assocArray;
			$index++;
		}

		$json = json_encode($finalArray);
		
        $URL = "http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/studentSubmit.php";

        $ch = curl_init($URL);

        curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "POST");                                                                     
        curl_setopt($ch, CURLOPT_HTTPHEADER, array('Accept: application/json', 'Content-Length: ' . strlen($json)));
        curl_setopt($ch, CURLOPT_POST, 1);
        curl_setopt($ch, CURLOPT_POSTFIELDS, $json);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

        $page = curl_exec($ch);

        if(curl_errno($ch)){
            die(json_encode(array("Error" => curl_error($ch))));
        }
	
	$json = json_decode($page, true);

	if($json["examSubmit"] == "Success") {
		echo "<script>
                alert('Exam Submitted!');
                window.location.href='student.php';
                </script>";	
		//header("Location: student.php");
	} else {
		header("Location: exam.php");
	}

	//echo $page;

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
