<?php


if (isset($_REQUEST['tag']) && $_REQUEST['tag'] != '') 
{
    // get tag
    $tag = $_REQUEST['tag'];
    $token = $_REQUEST['token'];
    $field="";
    $url = "http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/login.php";
 
    // check for tag type
    if ($tag == 'login' && $token == "0xACA021") 
    {
        // Request type is check Login
        $email = cleanData($_REQUEST['ucid']);
        $password = cleanData($_REQUEST['password']);
        
		$field = 'username='.$email.'&password='.$password;
    } 
    else if($tag == 'MultipleChoiceQuestionInsert')
    {
        $field = 'question_type='.$_REQUEST['question_type'].
                '&question='.$_REQUEST['question'].
                '&correct='.$_REQUEST['correct'].
                '&correct_reason='.$_REQUEST['correct_reason'].
                '&wrongAnswer1='.$_REQUEST['wrongAnswer1'].
                '&wrongReason1'.$_REQUEST['wrongReason1'].
                '&wrongAnswer2'.$_REQUEST['wrongAnswer2'].
                '&wrongReason2'.$_REQUEST['wrongReason2'].
                '&wrongAnswer3'.$_REQUEST['wrongAnswer3'].
                '&wrongReason3'.$_REQUEST['wrongReason3']
    }
    else if($tag == 'TrueFalseChoiceQuestionInsert')
    {
        $field = 'question_type='.$_REQUEST['question_type'].
                '&question='.$_REQUEST['question'].
                '&correct='.$_REQUEST['correct'].
                '&correct_reason='.$_REQUEST['correct_reason'].
                '&wrongAnswer1='.$_REQUEST['wrongAnswer1'].
                '&wrongReason1'.$_REQUEST['wrongReason1'].
    }
    else if($tag == 'ShortAnswerQuestionInsert')
    {
        $field = 'question_type='.$_REQUEST['question_type'].
                '&question='.$_REQUEST['question'].
                '&correct='.$_REQUEST['correct'].
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

		$ch = curl_init();
		

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

function cleanData($data) {
		$data = trim($data);
		$data = stripslashes($data);
		$data = htmlspecialchars($data);
		return $data;
	}

?>


