<?php

    /*
     * Function rec_input
     * Validates the input that I am getting
     * Checks if there are bad html code
     */

    function rec_input($data){
        $data = trim($data);
        $data = stripslashes($data);
        $data = htmlspecialchars($data);
        return $data;
    }

    if($_SERVER["REQUEST_METHOD"] == "POST"){

        $userID = rec_input($_POST["userID"]);
        $examID = rec_input($_POST["examID"]);

        if(strlen($userID) == 0 
        || strlen($examID) == 0){

                die(json_encode(array(
                        'Error'    => "Post request is empty",
                )));

        }

        $postfields = array(
                      'userID' => urlencode($userID),
                      'examID' => urlencode($examID),
                    );

        $URL = "http://afsaccess1.njit.edu/~caj9/Code_Testing/BackEnd/studentExamQuery.php";

        $ch = curl_init();

        curl_setopt($ch, CURLOPT_URL, $URL);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_POST, count($postfields));
        curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($postfields));

        $page = curl_exec($ch); // make request

        if(curl_errno($ch))
            die(json_encode(array('Error:' => curl_error($ch))));
        
        $result = json_decode($page, true);

        echo json_encode($result);

    } else {
        die(json_encode(array(
            'Error' => "Not posted"
        )));
    }
?>
