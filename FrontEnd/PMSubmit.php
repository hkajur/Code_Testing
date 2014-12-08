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
		$input4 = $_POST["input4"];
                $output4 = $_POST["output4"];
		$points = $_POST["points"];

		$ch = curl_init();

		$URL = "http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/insertProgrammingQuestion.php";
		     $postfields = array(
                        'question_type' => $question_type,
                        'question' => $question,
                        'input1' => $input1,
                        'output1' => $output1,
			'input2' => $input2,
                        'output2' => $output2,
			'input3' => $input3,
                        'output3' => $output3,
			'input4' => $input4,
                        'output4' => $output4,
			'points' => $points);

			$enc_JSON = json_encode($postfields);

	curl_setopt($ch, CURLOPT_URL, $URL);
	curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "POST");
	curl_setopt($ch, CURLOPT_HTTPHEADER, array('Accept: application/json', 'Content-Length: ' . strlen($enc_JSON)));
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_POST, 1);
        curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, $enc_JSON);

        $page = curl_exec($ch);
	
	//echo $page;

	echo "<script>
		alert('Question Created!');
		window.location.href='createQ.php';
		</script>";

        if(curl_errno($ch)){
                die(json_encode(array(
                        "questionCreated" => "Failed",
                        "Error" => curl_error($ch))));
        }
	
	$json = json_decode($page, true);
	//echo $json;
	if($json["questionCreated"] == "Success"){
		//echo "hello";
		//echo "<script>
                //alert('Question Created!');
                //window.location.href='createQ.php';
                //</script>";	
		//header("Location: createQ.php");
	}
	
	} else {
		die("Error");
	}
?>
