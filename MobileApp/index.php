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
                $url = 'http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/releaseExam.php';
                $field = 'userID='.cleanData($_REQUEST['userID']).
                        '&examID='.cleanData($_REQUEST['examID']);
            
                callingCurl($url,$field);
                break;
            
            case 'ExamStats':
                break;
            
            case 'TrueFalseChoiceQuestionInsert':
                $url = 'http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/insertQuestion.php';                
                $responses = cleanData($_REQUEST['responses']);
                $ids = substr($ids,1,-1);
                $idArray = explode(',',$ids);
                $field = 'question_type='.cleanData($_REQUEST['question_type']).
                    '&question='.cleanData($_REQUEST['question']).
                    '&points='.cleanData($_REQUEST['points']).
                    '&correct='.$idArray[0].
                    '&correct_reason='.$idArray[1].
                    '&wrongAnswer1='.$idArray[2].
                    '&wrongReason1'.$idArray[3];
                callingCurl($url,$field);
                break;        
            
            case 'MultipleChoiceQuestionInsert':
                $url = 'http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/insertQuestion.php';
                $responses = cleanData($_REQUEST['responses']);
                $ids = substr($ids,1,-1);
                $idArray = explode(',',$ids);            
                $field = 'question_type='.cleanData($_REQUEST['question_type']).
                        '&question='.cleanData($_REQUEST['question']).
                        '&points='.cleanData($_REQUEST['points']).
                        '&correct='.$idArray[0].
                        '&correct_reason='.$idArray[1].
                        '&wrongAnswer1='.$idArray[2].
                        '&wrongReason1'.$idArray[3].
                        '&wrongAnswer2'.$idArray[4].
                        '&wrongReason2'.$idArray[5].
                        '&wrongAnswer3'.$idArray[6].
                        '&wrongReason3'.$idArray[7];
                callingCurl($url,$field);
                break;
            
            case 'ShortAnswerQuestionInsert':
                $url = 'http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/insertQuestion.php';
                $responses = cleanData($_REQUEST['responses']);
                $ids = substr($ids,1,-1);
                $idArray = explode(',',$ids);
                $field = 'question_type='.cleanData($_REQUEST['question_type']).
                    '&question='.cleanData($_REQUEST['question']).
                    '&points='.cleanData($_REQUEST['points']).
                    '&correct='.cleanData($_REQUEST['response']);
                callingCurl($url,$field);        
                break;

            case 'ProgramQuestionInsert':
                $url = 'http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/insertQuestion.php';
                $responses = cleanData($_REQUEST['responses']);
                $ids = substr($ids,1,-1);
                $idArray = explode(',',$ids);
                $field = 'question_type='.cleanData($_REQUEST['question_type']).
                    '&question='.cleanData($_REQUEST['question']).
                    '&points='.cleanData($_REQUEST['points']).
                    '&input1='.$idArray[0].
                    '&output1='.$idArray[1].
                    '&input2='.$idArray[2].
                    '&output2='.$idArray[3].
                    '&input3='.$idArray[4].
                    '&output3='.$idArray[5];

                echo $field;
                callingCurl($url,$field);        
                break;
            
            case 'getExamQuestions':
                $url = 'http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/allQuestions.php';
                $field = 'userID='.cleanData($_REQUEST['userID']);
                callingCurl($url,$field);
                break;

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
            
            //FIX THIS
            
                $answers = cleanData($_REQUEST['questionAnswer']);
                
                                     
            
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


