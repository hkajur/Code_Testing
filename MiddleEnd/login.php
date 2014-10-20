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

        $URL = "http://web.njit.edu/~caj9/Code_Testing/BackEnd/login.php";

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

        if(curl_errno($ch)){
            echo 'Curl error: ' . curl_error($ch);
        }

        return $page; 
    }

    if($_SERVER["REQUEST_METHOD"] == "POST"){

        $username = rec_input($_POST["username"]);
        $password = rec_input($_POST["password"]);

        $array = array();

        $array['NJIT_Login'] = "Failed";

        $res = backend_login($username, $password);
        
        if(strpos($res, 'Failed') !== false){
          $array['Backend_Login'] = "Failed";
        } else if(strpos($res, 'Success') !== false){
          $array['Backend_Login'] = "Success";
        } else {
          $array['Backend_Login'] = "Error";
        } 

        $obj = json_decode($res);

        $array["userType"] = $obj->{'userType'};

        echo json_encode($array);

    } else {
        die("Didn't post to the page");
    }
?>
