<?php

$url = "";
$field="";

if (isset($_REQUEST['tag']) && $_REQUEST['tag'] != '') 
{
    // get tag
    $tag = $_REQUEST['tag'];
    $token = $_REQUEST['token'];

 
    // Confirm Token    
    if($token == '0xACA021'){
        
        //Choose tag
        switch($tag){
            //-----------------------GENERIC
            case 'login':
                $email = cleanData($_REQUEST['ucid']);
                $password = cleanData($_REQUEST['password']);        
                $field = 'username='.$email.'&password='.$password;
                $url = "http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/login.php";
                callingCurl($url,$field);
                break;

            //-----------------------INSTRUCTOR POSTS
            case 'DeleteExam':
                break;
            
            case 'ReleaseExam':
                $url = 'http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/insertExam.php';
                $field = 'userID='.cleanData($_REQUEST['userID']).
                        '&examID='.cleanData($_REQUEST['examID']);
                callingCurl($url,$field);
                break;
            
            case 'ExamStats':
                break;
                        
            case 'TrueFalseChoiceQuestionInsert':
                $url = 'http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/insertQuestion.php';
                $field = 'question_type='.cleanData($_REQUEST['question_type']).
                    '&question='.cleanData($_REQUEST['question']).
                    '&correct='.cleanData($_REQUEST['correct']).
                    '&correct_reason='.cleanData($_REQUEST['correct_reason']).
                    '&wrongAnswer1='.cleanData($_REQUEST['wrongAnswer1']).
                    '&wrongReason1'.cleanData($_REQUEST['wrongReason1']);
                callingCurl($url,$field);
                break;        
            
            case 'MultipleChoiceQuestionInsert':
                $url = 'http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/insertQuestion.php';
                $field = 'question_type='.cleanData($_REQUEST['question_type']).
                        '&question='.cleanData($_REQUEST['question']).
                        '&correct='.cleanData($_REQUEST['correct']).
                        '&correct_reason='.cleanData($_REQUEST['correct_reason']).
                        '&wrongAnswer1='.cleanData($_REQUEST['wrongAnswer1']).
                        '&wrongReason1'.cleanData($_REQUEST['wrongReason1']).
                        '&wrongAnswer2'.cleanData($_REQUEST['wrongcleanData(Answer2']).
                        '&wrongReason2'.cleanData($_REQUEST['wrongReason2']).
                        '&wrongAnswer3'.cleanData($_REQUEST['wrongAnswer3']).
                        '&wrongReason3'.cleanData($_REQUEST['wrongReason3']);
                callingCurl($url,$field);
                break;
            
            case 'ShortAnswerQuestionInsert':
                $url = 'http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/insertQuestion.php';
                $field = 'question_type='.cleanData($_REQUEST['question_type']).
                    '&question='.cleanData($_REQUEST['question']);
                callingCurl($url,$field);        
                break;

            case 'ProgramQuestionInsert':
                $url = 'http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/insertQuestion.php';
                $field = 'question_type='.cleanData($_REQUEST['question_type']).
                    '&question='.cleanData($_REQUEST['question']);
                callingCurl($url,$field);        
                break;
            
            case 'getExamQuestions':
                $url = 'http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/allQuestions.php';
                $field = 'userID='.cleanData($_REQUEST['userID']);
                callingCurl($url,$field);
                break;

            //CAREFUL -- ARRAY INPUT HERE
            case 'createExam':
                $url = 'http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/insertExam.php';
            
                $ids = cleanData($_REQUEST['questionId']);                
                $name = cleanData($_REQUEST['examName']);
                $ids = substr($ids,1,-1);
                $idArray = explode(',',$ids);
                
            
                $json_array = array("examName" => $name,
                                    "questionIDs" => $idArray);
                $json = json_encode($json_array);
                
                callingCurlJSON($url,$json);
                break;
            
            //-----------------------STUDENT POSTS
            case 'conductExams':
                $url = 'http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/studentExams.php';
                $field = 'userID='.cleanData($_REQUEST['userID']).
                        '&examID='.cleanData($_REQUEST['examID']);
                callingCurl($url,$field);
                break;

            //CAREFUL -- ARRAY INPUT HERE
            case 'submitExam':
                $url = 'http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/studentSubmit.php';
                $field = 'userID='.cleanData($_REQUEST['userID']).
                        '&examID='.cleanData($_REQUEST['examID']).
                        '&questionAnswer='.cleanData($_REQUEST['questionAnswer']);
                callingCurl($url,$field);
                break;

            case 'gradedExams':
                $url = 'http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/studentGradedExams.php';
                $field = 'userID='.cleanData($_REQUEST['userID']);
                callingCurl($url,$field);
                break;

            case 'gradedFeedback':
                $url = 'http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/studentCheckGradedExam.php';
                $field = 'userID='.cleanData($_REQUEST['userID']).
                        '&examID='.cleanData($_REQUEST['examID']);
                callingCurl($url,$field);
                break;

            default:
                echo "Invalid Tag";
        }        
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


function callingCurl($url,$field){
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
}


function callingCurlJSON($url,$field){

    $ch = curl_init($url);
        curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "POST");
        curl_setopt($ch, CURLOPT_POSTFIELDS, $field);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, array(
            'Content-Type: application/json',
            'Content-Length: ' . strlen($field))
                   );
    $result = curl_exec($ch);
    if(curl_errno($ch))
    {
        echo curl_error($ch);
        exit;
    }		
    curl_close($ch);		
    echo $result;
}

function cleanData($data) {
		$data = trim($data);
		$data = stripslashes($data);
		$data = htmlspecialchars($data);
		return $data;
	}

?>


