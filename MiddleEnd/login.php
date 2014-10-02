<?php

    function rec_input($data){
        $data = trim($data);
        $data = stripslashes($data);
        $data = htmlspecialchars($data);
        return $data;
    }

    function njit_login($username, $password){

        $ch = curl_init();
        
        /*
         * Headers for the request that is being sent
         */ 
        
        $postfields = 'pass=' . urlencode($password) . '&user=' . urlencode($username) . '&uuid=' . urlencode('0xACA021');
        
        $URL = "https://cp4.njit.edu/cp/home/login";
        
        curl_setopt($ch, CURLOPT_URL, $URL);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_USERAGENT, 'Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.9.2.13) Gecko/20101206 Ubuntu/10.10 (maverick) Firefox/3.6.13');
        curl_setopt($ch, CURLOPT_POST, 1);
        curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, $postfields);

        $page = curl_exec($ch); // make request

        if(curl_errno($ch)){
                curl_close($ch);
                return "";
        }
        
        if(strpos($page, 'Failed') !== false){
            return "NJIT Failed";
        } else {
            return "NJIT Success";
        }
        
        curl_close($ch);    
    }

    function backend_login($username, $password){
            
        $ch = curl_init();

        $URL = "http://web.njit.edu/~caj9/Code_Testing/BackEnd/login.php";

        $postfields = 'username=' . $username . '&password=' . $password;

        curl_setopt($ch, CURLOPT_URL, $URL);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_USERAGENT, 'Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.9.2.13) Gecko/20101206 Ubuntu/10.10 (maverick) Firefox/3.6.13');
        curl_setopt($ch, CURLOPT_POST, 1);
        curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, $postfields);

        $page = curl_exec($ch); // make request

        if(curl_errno($ch)){
            echo 'Curl error: ' . curl_error($ch);
        }

        return $page; 

        if(strpos($page, 'Failed') !== false){
            return "Backend Login Failed";
        } else if(strpos($page, 'Success') !== false){
            return "Backend Login Success";
        } else {
            return "Error";
        }
        
    }

    if($_SERVER["REQUEST_METHOD"] == "POST"){

        $username = rec_input($_POST["username"]);
        $password = rec_input($_POST["password"]);

        $result = njit_login($username, $password);

        $jsonObj = "{ ";

        if(strpos($result, 'Success') !== false){
                $jsonObj .= "\"NJIT_Login\": \"Success\"";
        } else if(strpos($result, 'Failed') !== false){
                $jsonObj .= "\"NJIT_Login\": \"Failed\"";
        } else {
                $jsonObj .= "\"NJIT_Login\": \"Error\"";
        }
      
        $jsonObj .= ", ";

        $res = backend_login($username, $password);

        if(strpos($res, 'Failed') !== false){
            $jsonObj .= "\"Backend_Login\": \"Failed\"";
        } else if(strpos($res, 'Success') !== false){
            $jsonObj .= "\"Backend_Login\": \"Success\"";
        } else {
            $jsonObj .= "\"Backend_Login\": \"Error\"";
        } 

        $jsonObj .= " }";

        echo $jsonObj;

    } else {
        die("Didn't post to the page");
    }
?>
