<?php


if (isset($_REQUEST['tag']) && $_REQUEST['tag'] != '') 
{
    // get tag
    $tag = $_REQUEST['tag'];
    $token = $_REQUEST['token'];
    
 
    // check for tag type
    if ($tag == 'login' && $token == "0xACA021") 
    {
        // Request type is check Login
        $email = cleanData($_REQUEST['ucid']);
        $password = cleanData($_REQUEST['password']);
        
		$field = 'username='.$email.'&password='.$password;
		$ch = curl_init();
		
		$url = "http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/login.php";

		curl_setopt_array($ch, array(
			CURLOPT_RETURNTRANSFER => 1,
			CURLOPT_URL => $url,
			CURLOPT_FOLLOWLOCATION => true,
			CURLOPT_POST => 1,
			CURLOPT_POSTFIELDS => $field
		));

		$resp = curl_exec($ch);

		if(curl_errno($ch))
        {
			echo curl_error($ch);
			exit;
		}		
		curl_close($ch);		
		echo $resp;
        
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

function cleanData($data) {
		$data = trim($data);
		$data = stripslashes($data);
		$data = htmlspecialchars($data);
		return $data;
	}

?>


