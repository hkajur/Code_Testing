<?php
	if($_SERVER["REQUEST_METHOD"] == "POST"){

		$question_type = "Programming";
		$question = $_POST["question"];
		$input1 = $_POST["input1"];
		$output1 = $_POST["output1"];
		$input2 = $_POST["input2"];
                $output2 = $_POST["output2"];
		$input3 = $_POST["input3"];
                $output3 = $_POST["output3"];
		$points = $_POST["points"];

		$ch = curl_init();

		$URL = "http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/insertQuestion.php";
		     $postfields = array(
                        'question_type' => $question_type,
                        'question' => $question,
                        'input1' => $input1,
                        'output1' => $output1,
			'input2' => $input2,
                        'output2' => $output2,
			'input3' => $input3,
                        'output3' => $output3,
			'points' => $points);

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
		echo "<script>
                alert('Question Created!');
                window.location.href='createQ.php';
                </script>";	
		//header("Location: createQ.php");
	}
	
	} else {
		die("Error");
	}
?>
