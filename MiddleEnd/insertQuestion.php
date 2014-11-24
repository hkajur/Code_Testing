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
                $points = rec_input($_POST["points"]);
                
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
                        'points' => $points,
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
                $points = rec_input($_POST["points"]);

                $wrongAnswer1 = rec_input($_POST["wrongAnswer1"]);
                $wrongReason1 = rec_input($_POST["wrongReason1"]);

                $postfields = array(
                        'question_type' => $question_type,
                        'question' => $question,
                        'correct' => $correct,
                        'points' => $points,
                        'correct_reason' => $correctReason,
                        'wrongAnswer1' => $wrongAnswer1,
                        'wrongReason1' => $wrongReason1);
                break;

            case "ShortAnswer":
                
                $question = rec_input($_POST["question"]);
                $correct = rec_input($_POST["correct"]);
                $points = rec_input($_POST["points"]);
                
                $postfields = array(
                        'question_type' => $question_type,
                        'question' => $question,
                        'points' => $points,
                        'correct' => $correct);

                break;

            case "Programming":
                echo "Inside progaramming question";
                $question = rec_input($_POST["question"]);
                $input1 = rec_input($_POST["input1"]);
                $input2 = rec_input($_POST["input2"]);
                $input3 = rec_input($_POST["input3"]);
                $output1 = rec_input($_POST["output1"]);
                $output2 = rec_input($_POST["output2"]);
                $output3 = rec_input($_POST["output3"]);
                $points = rec_input($_POST["points"]);
                
                $postfields = array(
                        'question_type' => $question_type,
                        'question' => $question,
                        'points' => $points,
                        'input1' => $input1,
                        'input2' => $input2,
                        'input3' => $input3,
                        'output1' => $output1,
                        'output2' => $output2,
                        'output3' => $output3);
            
                break;
            default:
                break;

        } 

        $ch = curl_init();

        $URL = "http://afsaccess1.njit.edu/~caj9/Code_Testing/BackEnd/insertQuestionQuery.php";

        curl_setopt($ch, CURLOPT_URL, $URL);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_POST, count($postfields));
        curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($postfields));

        $page = curl_exec($ch);

        if(curl_errno($ch)){
                die(json_encode(array(
                        "questionCreated" => "Failed",
                        "Error" => curl_error($ch))));
        }

        echo $page;
    
    } else {
            die(json_encode(array(
                    "questionCreated" => "Failed",
                    "Error" => "Invalid post")));
    }
?>
