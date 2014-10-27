<?php

	$userID = 11;
	$examID = $_GET['id'];
	echo $examID;
  	$URL = "http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/releaseExam.php";
                
        $ch = curl_init();

        $postfields = array("examID" => $examID,
			   "userID" => $userID);

        curl_setopt($ch, CURLOPT_URL, $URL);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_POST, count($postfields));
        curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($postfields));

	echo "UserID: " . $userID;
	$page = curl_exec($ch);

        if(curl_errno($ch)){
            die(json_encode(array("Error" => curl_error($ch))));
        }
  	
	echo $page;
        curl_close($ch);
?>
