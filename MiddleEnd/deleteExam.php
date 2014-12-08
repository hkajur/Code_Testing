<?php
    
    if($_SERVER["REQUEST_METHOD"] == "POST"){

        $userID = $_POST["userID"];
        $examID = $_POST["examID"];

        $postfields = array("userID" => $userID, 
                            "examID" => $examID);

        $ch = curl_init();

        $URL = "http://afsaccess1.njit.edu/~caj9/Code_Testing/BackEnd/deleteExamQuery.php";

        curl_setopt($ch, CURLOPT_URL, $URL);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_POST, count($postfields));
        curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($postfields));

        $page = curl_exec($ch);

        if(curl_errno($ch)){
            die(json_encode(array("Error" => curl_error($ch))));
        }

        echo $page;
    
        curl_close($ch);    
    } else {
        die(json_encode(array("Error" => "Invalid post")));
    }
?>
