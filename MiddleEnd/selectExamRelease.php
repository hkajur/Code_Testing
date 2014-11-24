<?php

    if($_SERVER["REQUEST_METHOD"] == "POST"){
    
        $userID = $_POST["userID"];
    
        $URL = "http://afsaccess1.njit.edu/~caj9/Code_Testing/BackEnd/selectExamsRelease.php"; 
                
        $ch = curl_init();

        $postfields = array("userID" => $userID);

        curl_setopt($ch, CURLOPT_URL, $URL);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_POST, count($postfields));
        curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($postfields));
        
        $page = curl_exec($ch);

        if(curl_errno($ch)){
            die(json_encode(array("Error" => curl_error($ch))));
        }
    
        curl_close($ch);

        echo $page;
        
    } else {
        die(json_encode(
            array("Error" => "Invalid POST Request")));
    }
?>
