<?php
    
    if($_SERVER["REQUEST_METHOD"] == "POST"){

        $userID = $_POST["userID"];

        $URL = "http://afsaccess1.njit.edu/~vk255/Code_Testing/BackEnd/studentGradedExamsQuery.php";

        $ch = curl_init($URL);

        curl_setopt($ch, CURLOPT_POST, 1);
        curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query(
                            array("userID" => $userID)));
        curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

        $page = curl_exec($ch);

        if(curl_errno($ch)){
            die(json_encode(array("Error" => curl_error($ch))));
        }
    
        echo $page;

    } else {
            die(json_encode(array(
                "Error" => "Invalid post request")));
    }
?>
