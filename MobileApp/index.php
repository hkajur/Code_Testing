<?php
/**
 * File to handle all API requests
 * Accepts GET and POST
 * 
 * Each request will be identified by TAG
 * Response will be JSON data
 
  /**
 * check for POST request 
 */
if (isset($_POST['tag']) && $_POST['tag'] != '') 
{
    // get tag
    $tag = $_POST['tag'];
  
    // response Array
    $response = array("tag" => $tag, "success" => 0, "error" => 0);
 
    // check for tag type
    if ($tag == 'login') 
    {
        // Request type is check Login
        $email = $_POST['ucid'];
        $password = $_POST['password'];
        $token = $_POST['token'];
 
        //CURL all of this information to Harish (Middle Tier)
        
    } 
    else 
    {
        echo "Invalid Request";
    }
} 
else 
{
    echo "Access Denied";
}

//========================================================================================================================

function njit_login($user, $pass, $token){
	// user=UCID&pass=pass&uuid=0xACA021
    $ch = curl_init();
	curl_setopt($ch, CURLOPT_URL, "https://cp4.njit.edu/cp/home/login");
	curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query(array(
	 	"user" => $user,
	 	"pass" => $pass,
	 	"uuid" => $token
	)));
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
	$result = curl_exec($ch);
	curl_close($ch);
 
	// Logout to kill any sessions
	$ch = curl_init();
	curl_setopt($ch, CURLOPT_URL, "http://cp4.njit.edu/up/Logout?uP_tparam=frm&frm=");
	curl_exec($ch);
	curl_close($ch);
 
	// Return validation bool
	return strpos($result, "loginok.html") !== false;
}

?>