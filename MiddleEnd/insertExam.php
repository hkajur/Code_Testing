<?php

    if($_SERVER["REQUEST_METHOD"] == "POST"){

        $json = file_get_contents('php://input');
        $obj = json_decode($json, true);

        $URL = "http://afsaccess1.njit.edu/~caj9/Code_Testing/BackEnd/insertExamQuery.php";

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

    } else {
        die(json_encode(array(
            "examCreated" => "Failed",
            "Error" => "Invalid POST request")));
    } 
?>
