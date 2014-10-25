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

    function backend_login($username, $password){
            
        $ch = curl_init();

        //$URL = "http://web.njit.edu/~caj9/Code_Testing/BackEnd/login.php";
        $URL = "http://afsaccess1.njit.edu/~vk255/Code_Testing/BackEnd/login.php";

        $postfields = array(
                      'username' => urlencode($username),
                      'password' => urlencode($password)
        );

        curl_setopt($ch, CURLOPT_URL, $URL);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_POST, count($postfields));
        curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($postfields));

        $page = curl_exec($ch); // make request

        if(curl_errno($ch))
            echo 'Curl error: ' . curl_error($ch);

        return $page; 
    }

    if($_SERVER["REQUEST_METHOD"] == "POST"){

        $username = rec_input($_POST["username"]);
        $password = rec_input($_POST["password"]);

        if(strlen($username) == 0 
        || strlen($password) == 0){

                die(json_encode(array(
                        'NJIT_Login'    => "Failed",
                        'Backend_Login' => "Failed",
                        'userType'      => "null"
                )));

        }

        $array = array();

        $result = backend_login($username, $password);
        $result = json_decode($result, true);

        $result["NJIT_Login"] = "Failed"; 
        
        echo json_encode($result);

    } else {
        die(json_encode(array(
            'Error' => "Not posted"
        )));
    }
?>
