<?php
	if($_SERVER["REQUEST_METHOD"] == "POST"){

		$question_type = "MultipleChoice";
		$question = $_POST["question"];
		$correct = $_POST["correct"];
		$correct_reason = $_POST["correct_reason"];
		$wrongAnswer1 = $_POST["wrongAnswer1"];
		$wrongAnswer2 = $_POST["wrongAnswer2"];
		$wrongAnswer3 = $_POST["wrongAnswer3"];
		$wrongReason1 = $_POST["wrongReason1"];
		$wrongReason2 = $_POST["wrongReason2"];
		$wrongReason3 = $_POST["wrongReason3"];


		$ch = curl_init();

		$URL = "http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/insertQuestion.php";
		     $postfields = array(
                        'question_type' => $question_type,
                        'question' => $question,
                        'correct' => $correct,
                        'correct_reason' => $correct_reason,
                        'wrongAnswer1' => $wrongAnswer1,
                        'wrongReason1' => $wrongReason1,
                        'wrongAnswer2' => $wrongAnswer2,
                        'wrongReason2' => $wrongReason2,
                        'wrongAnswer3' => $wrongAnswer3,
                        'wrongReason3' => $wrongReason3);

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
