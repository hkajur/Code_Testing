<?php

    function rec_input($data){
        $data = trim($data);
        $data = stripslashes($data);
        $data = htmlspecialchars($data);
        return $data;
    }

    if($_SERVER["REQUEST_METHOD"] == "POST"){

        $username = rec_input($_POST["username"]);
        $password = rec_input($_POST["passwd"]);
    
        $_COOKIE[session_name()] = session_id();

        $cookie_string="";

        foreach($_COOKIE as $key => $value){
            $cookie_stirng .= "$key=$value";
        }
        $URL = 'https://www.njit.edu/cp/login.php';
        $cookie_file = "cookiefile.txt"; 
        $ch = curl_init();
        /*
         * Headers for the request that is being sent
         */ 

        curl_setopt($ch, CURLOPT_URL, $URL);    
        curl_setopt($ch, CURLOPT_COOKIEJAR, $cookie_string);//realpath($cookie_file));
        curl_setopt($ch, CURLOPT_COOKIEFILE, $cookie_string);//realpath($cookie_file));
        curl_setopt($ch, CURLOPT_USERAGENT, 
        'Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.9.2.13) Gecko/20101206 Ubuntu/10.10 (maverick) Firefox/3.6.13');
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_COOKIESESSION, TRUE); 
        curl_setopt($ch, CURLOPT_HEADER, 1);        
        curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);

        /*
         * Executing the url and storing the output to $page variable
         */
        $page = curl_exec($ch);
        
        /*if (!file_exists($cookie_string) || ! is_writable($cookie_string))
        {
                echo 'Cookie file missing or not writable.';
                exit;
        }*/
        curl_close($ch);
        $postfields = 'pass=' . urlencode($password) . '&user=' . urlencode($username) . '&uuid=' . urlencode('0xACA021');
        //unset($ch); 

        //$ch = curl_init();
        $URL2 = "https://cp4.njit.edu/cp/home/login";
        
        curl_setopt($ch, CURLOPT_COOKIEFILE, $cookie_string);
        curl_setopt($ch, CURLOPT_URL, $URL2);
        curl_setopt($ch, CURLOPT_REFERER, $URL2);
        curl_setopt($ch, CURLOPT_POST, 1);
        curl_setopt($ch, CURLOPT_POSTFIELDS, $postfields);

        $page = curl_exec($ch); // make request
        
        if(curl_errno($ch)){
                echo "Error";
                exit;
        }
        
        var_dump($page); // should be logged in
        
        curl_close($ch);
    } else {
        die("Didn't post to the page");
    }
?>
