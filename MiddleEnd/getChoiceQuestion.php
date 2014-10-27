<?php
    
    if($_SERVER["REQUEST_METHOD"] == "POST" || $_SERVER["REQUEST_METHOD"] == "GET"){

        $questionID = $_POST["questionID"];
        $choiceID = $_POST["choiceID"];

        $postfields = array("questionID" => $questionID,
                        "choiceID" => $choiceID);

        $ch = curl_init();

        $URL = "http://afsaccess1.njit.edu/~caj9/Code_Testing/BackEnd/getChoiceQuestion.php";

        curl_setopt($ch, CURLOPT_URL, $URL);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_POST, count($postfields));
        curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($postfields));

        $page = curl_exec($ch);

        if(curl_errno($ch)){
            die(json_encode(array("Error" => curl_error($ch))));
        }

        if($page == "Nothing")
            die("");
        echo $page;
    
    } else {
        die(json_encode(array("Error" => "Invalid post")));
    }
?>
