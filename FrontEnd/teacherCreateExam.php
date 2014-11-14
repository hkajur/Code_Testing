<?php

	if($_SERVER["REQUEST_METHOD"] == "POST"){

		$examName = $_POST["examName"];
		$array = $_POST["questionArray"];
		
		$N = count($array);
		$index = 0;	

		$questionIDs = array();
		$j = 0;
	
		while($index < $N){
			if(!empty($array[$index])){
				$questionIDs[$j] = $array[$index];
				$j++;
			}
			$index++;
		}
		
		$size = $j;
		$j = 0;

		$json_array = array("examName" => $examName,
				"questionIDs" => $questionIDs);

		$json = json_encode($json_array);

		$URL = "http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/insertExam.php";

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

        echo $page;
	
	$json = json_decode($page, true);

	if($json["examCreated"] == "Success"){
		header("Location: createExam.php");
	} else {

	}
	

	} else {

	}
?>
