<?php
	if($_SERVER["REQUEST_METHOD"] == "POST"){

		$question_type = "ShortAnswer";
		$question = $_POST["question"];
		$correct = $_POST["correct"];
		$correct_reason = $_POST["correct_reason"];


		$ch = curl_init();

		$URL = "http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/insertQuestion.php";
		     $postfields = array(
                        'question_type' => $question_type,
                        'question' => $question,
                        'correct' => $correct,
                        'correct_reason' => $correct_reason);

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
