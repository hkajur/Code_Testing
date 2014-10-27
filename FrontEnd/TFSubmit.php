<?php
	if($_SERVER["REQUEST_METHOD"] == "POST"){

		$question_type = "TrueFalse";
		$question = $_POST["question"];
		$correct = $_POST["correct"];
		$correct_reason = $_POST["correct_reason"];
		$wrongAnswer1 = $_POST["wrongAnswer1"];
		$wrongReason1 = $_POST["wrongReason1"];


		$ch = curl_init();

		$URL = "http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/insertQuestion.php";
		     $postfields = array(
                        'question_type' => $question_type,
                        'question' => $question,
                        'correct' => $correct,
                        'correct_reason' => $correct_reason,
                        'wrongAnswer1' => $wrongAnswer1,
                        'wrongReason1' => $wrongReason1);

	  curl_setopt($ch, CURLOPT_URL, $URL);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_POST, count($postfields));
        curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($postfields));

        $page = curl_exec($ch);

        if(curl_errno($ch)){
                die(json_encode(array(
                        "questionCreated" => "Failed",
                        "Error" => curl_error($ch))));
        }
	
	$json = json_decode($page, true);

	if($json["questionCreated"] == "Success"){
		header("Location: createQ.php");
	}
	
	} else {
		die("Error");
	}
?>
