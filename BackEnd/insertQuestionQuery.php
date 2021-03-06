<?php

    function shuffle_assoc(&$array) {
    
        $keys = array_keys($array);
    
        shuffle($keys);
    
        foreach($keys as $key) {
            $new[$key] = $array[$key];
        }
    
        $array = $new;
        return true;
    }
                
    if($_SERVER["REQUEST_METHOD"] == "POST"){
        
        $questionType = $_POST["question_type"];
    
        $con = mysql_connect("sql.njit.edu", "caj9", "qEpO163u6") 
               or die(json_encode(array(
                    "questionCreated" => "Failed",
                    "Error" =>  "MySQL_Connection_Err")));
                                
        // Selects the database that you want to use
        $selectdb = mysql_select_db("caj9", $con);

        switch($questionType){

            case "MultipleChoice":
                $questionType = "MC";
                $question = $_POST["question"];
                $correct = $_POST["correct"];
                $points = $_POST["points"];

                if(empty($points))
                    $points = 5;

                $correctReason = $_POST["correct_reason"];
                
                $wrongAnswer1 = $_POST["wrongAnswer1"];
                $wrongReason1 = $_POST["wrongReason1"];
          
                $wrongAnswer2 = $_POST["wrongAnswer2"];
                $wrongReason2 = $_POST["wrongReason2"];
           
                $wrongAnswer3 = $_POST["wrongAnswer3"];
                $wrongReason3 = $_POST["wrongReason3"];

                $array = array($correct => $correctReason,
                                $wrongAnswer1 => $wrongReason1,
                                $wrongAnswer2 => $wrongReason2,
                                $wrongAnswer3 => $wrongReason3);

                shuffle_assoc($array);

                $sql_query = "INSERT INTO QuestionBank (Question, QuestionType, Answer, Points) " .
                             "VALUES ('" . $question . "', '" . $questionType . "', '" . $correct . "', '" . $points . "')";

                $result = mysql_query($sql_query);

                if(!$result){
                        die(json_encode(array(
                                "questionCreated" => "Failed",
                                "Error" => "Invalid First Request")));
                }

                $lastQuestionID = mysql_insert_id();

                $index = 1;

                foreach($array as $key => $val){

                    $sql_query = "INSERT INTO Choice (ChoiceID, QuestionID, Choice, ChoiceComment) " .
                        "VALUES ('" . $index . "', '" . $lastQuestionID . "', '" . $key . "', '" . $val . "')";
    
                    $result = mysql_query($sql_query);

                    if(!$result){
                            die(json_encode(array(
                                    "questionCreated" => "Failed",
                                    "Error" => "Invalid Request")));
                    }
                    
                    $index++;

                }

                break;

            case "TrueFalse":
                   
                $questionType = "TF";
                $question = $_POST["question"];
                $correct = $_POST["correct"];
                $points = $_POST["points"];

                $correctReason = $_POST["correct_reason"];
                
                $wrongAnswer1 = $_POST["wrongAnswer1"];
                $wrongReason1 = $_POST["wrongReason1"];
                
                $array = array($correct => $correctReason,
                        $wrongAnswer1 => $wrongReason1);

                krsort($array);
                
                if(empty($points))
                    $points = 2;

                $sql_query = "INSERT INTO QuestionBank (Question, QuestionType, Answer, Points) " .
                             "VALUES ('" . $question . "', '" . $questionType . "', '" . $correct . "', '" . $points . "')";

                $result = mysql_query($sql_query);

                if(!$result){
                        die(json_encode(array(
                                "questionCreated" => "Failed",
                                "Error" => "Invalid First Request")));
                }

                $lastQuestionID = mysql_insert_id();

                $index = 1;

                foreach($array as $key => $val){

                    $sql_query = "INSERT INTO Choice (ChoiceID, QuestionID, Choice, ChoiceComment) " .
                        "VALUES ('" . $index . "', '" . $lastQuestionID . "', '" . $key . "', '" . $val . "')";
    
                    $result = mysql_query($sql_query);

                    if(!$result){
                            die(json_encode(array(
                                    "questionCreated" => "Failed",
                                    "Error" => "Invalid Request")));
                    }
                    
                    $index++;

                }

                break;
            case "ShortAnswer":
                    
                $questionType = "FB";               
                
                $question = $_POST["question"];
                $correct = $_POST["correct"];
                $points = $_POST["points"];

                if(empty($points))
                    $points = 10;
               
                $sql_query = "INSERT INTO QuestionBank (Question, QuestionType, Answer, Points) " .
                             "VALUES ('" . $question . "', '" . $questionType . "', '" . $correct . "', '" . $points . "')";

                $result = mysql_query($sql_query);

                if(!$result){
                        die(json_encode(array(
                                "questionCreated" => "Failed",
                                "Error" => "Invalid First Request")));
                }

                break;

        case "Programming":
                $questionType = "PM";

                $question = $_POST["question"];
                
                $input1 = $_POST["input1"];
                $input2 = $_POST["input2"];
                $input3 = $_POST["input3"];
                
                $output1 = $_POST["output1"];
                $output2 = $_POST["output2"];
                $output3 = $_POST["output3"];

                if(empty($input1) || empty($output1)){
                    die(json_encode(array(
                            "questionCreated" => "Failed",
                            "Error" => "Input 1 can't be empty")));
                }
                
                $points = $_POST["points"];
                
                $temp = "";

                $temp = $temp . "Input: " . $input1 . " Output: " . $output1 . ";";

                $correct = "Some value";

                if(empty($points))
                        $points = 15;

                if(!empty($input2) && !empty($output2))
                    $temp = $temp . "Input: " . $input2 . " Output: " . $output2 . ";";
                
                if(!empty($input3) && !empty($output3))
                        $temp = $temp . "Input: " . $input3 . " Output: " . $output3 . ";";

                $correct = $temp;
                
                $sql_query = "INSERT INTO QuestionBank (Question, QuestionType, Answer, Points) " .
                             "VALUES ('" . $question . "', '" . $questionType . "', '" . $correct . "', '" . $points . "')";
                
                $result = mysql_query($sql_query);
                
                if(!$result){
                        die(json_encode(array(
                                "questionCreated" => "Failed",
                                "Error" => "Invalid Question Entry")));
                }
                
                $lastQuestionID = mysql_insert_id();

                $sql_query = "INSERT INTO programQuestions (QuestionID, Input, Output) VALUES ('" . $lastQuestionID . "', '" . $input1 . "', '" . $output1 . "')";


                $result = mysql_query($sql_query);
                if(!$result){
                        die(json_encode(array(
                                "questionCreated" => "Failed",
                                "Error" => "Invalid First Request")));
                }

                if(!empty($input2) && !empty($output2)){
                    $sql_query = "INSERT INTO programQuestions (QuestionID, Input, Output) VALUES ('" . $lastQuestionID . "', '" . $input2 . "', '" . $output2 . "')";
                    
                    $result = mysql_query($sql_query);
                    if(!$result){
                            die(json_encode(array(
                                    "questionCreated" => "Failed",
                                    "Error" => "Invalid First Request")));
                    }
        
                    if(!empty($input3) && !empty($output3)){
                        $sql_query = "INSERT INTO programQuestions (QuestionID, Input, Output) VALUES ('" . $lastQuestionID . "', '" . $input3 . "', '" . $output3 . "')";
                        $result = mysql_query($sql_query);
                        if(!$result){
                                die(json_encode(array(
                                        "questionCreated" => "Failed",
                                        "Error" => "Invalid First Request")));
                        }
                    }

                }

                break;
            default:
                break;
        }

        echo json_encode(array("questionCreated" => "Success"));

        mysql_close($con);

    } else {
            die(json_encode(array(
                    "questionCreated" => "Failed",
                    "Error" => "Invalid POST request")));
    }
?>
