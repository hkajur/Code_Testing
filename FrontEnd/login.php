<?php

    if($_SERVER["REQUEST_METHOD"] == "POST"){
            
        $username = $_POST["username"];
        $password = $_POST["passwd"];

        $url = "http://web.njit.edu/~vk255/Code_Testing/MiddleEnd/login.php";

        $field = "username=" . urlencode($username) . "&password=" . urlencode($password);

        $ch = curl_init();
    
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_USERAGENT, 'Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.9.2.13) Gecko/20101206 Ubuntu/10.10 (maverick) Firefox/3.6.13');
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_HEADER, 1);
        curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
        curl_setopt($ch, CURLOPT_POST, 1);
        curl_setopt($ch, CURLOPT_POSTFIELDS, $field);

        $page = curl_exec($ch);
    
        if(curl_errno($ch)){
            echo "Error";
            exit;
        }
        
        echo $page;
        //var_dump($page);
        curl_close($ch);

    } else {
        die("ERROR: Page must be posted");
    } 

?>
