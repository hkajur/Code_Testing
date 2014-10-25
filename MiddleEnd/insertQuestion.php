<?php

    function rec_input($data){
        $data = trim($data);
        $data = stripslashes($data);
        $data = htmlspecialchars($data);
        return $data;
    }

    if($_SERVER["REQUEST_METHOD"] == "POST"){
        
        $question_type = rec_input($_POST["question_type"]);

        switch($question_type){

            case "MultipleChoice":
                    
                $question = rec_input($_POST["question"]);
                $correct = rec_input($_POST["correct"]);
                $correctReason = rec_input($_POST["correct_reason"]);
                
                $wrongAnswer1 = rec_input($_POST["wrongAnswer1"]);
                $wrongReason1 = rec_input($_POST["wrongReason1"]);
          
                $wrongAnswer2 = rec_input($_POST["wrongAnswer2"]);
                $wrongReason2 = rec_input($_POST["wrongReason2"]);
           
                $wrongAnswer3 = rec_input($_POST["wrongAnswer3"]);
                $wrongReason3 = rec_input($_POST["wrongReason3"]);

                $postfields = array(
                        'question_type' => $question_type,
                        'question' => $question,
                        'correct' => $correct,
                        'correct_reason' => $correctReason,
                        'wrongAnswer1' => $wrongAnswer1,
                        'wrongReason1' => $wrongReason1,
                        'wrongAnswer2' => $wrongAnswer2,
                        'wrongReason2' => $wrongReason2,
                        'wrongAnswer3' => $wrongAnswer3,
                        'wrongReason3' => $wrongReason3);

                break;

            case "TrueFalse":
                    
                $question = rec_input($_POST["question"]);
                $correct = rec_input($_POST["correct"]);
                $correctReason = rec_input($_POST["correct_reason"]);
                
                $wrongAnswer1 = rec_input($_POST["wrongAnswer1"]);
                $wrongReason1 = rec_input($_POST["wrongReason1"]);

                $postfields = array(
                        'question_type' => $question_type,
                        'question' => $question,
                        'correct' => $correct,
                        'correct_reason' => $correctReason,
                        'wrongAnswer1' => $wrongAnswer1,
                        'wrongReason1' => $wrongReason1);
                break;
            case "ShortAnswer":
                
                $question = rec_input($_POST["question"]);
                $correct = rec_input($_POST["correct"]);
                
                $postfields = array(
                        'question_type' => $question_type,
                        'question' => $question,
                        'correct' => $correct);

                break;

            default:
                break;

        } 

        $ch = curl_init();

        $URL = "http://afsaccess1.njit.edu/~vk255/Code_Testing/BackEnd/insertQuestionQuery.php";

        curl_setopt($ch, CURLOPT_URL, $URL);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_POST, count($postfields));
        curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($postfields));

        $page = curl_exec($ch);

        if(curl_errno($ch)){
                die(json_encode(array(
                        "questionCreated" : "Failed",
                        "Error" => curl_error($ch))));
        }

        echo $page;
    
    } else {
            die(json_encode(array(
                    "questionCreated" : "Failed",
                    "Error" => "Invalid post")));
    }
?>
