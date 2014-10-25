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
                "Error" =>  "MySQL_Connection_Err")));
                                
        // Selects the database that you want to use
        $selectdb = mysql_select_db("caj9", $con);

        switch($questionType){

            case "MultipleChoice":
                $questionType = "MC";
                $question = $_POST["question"];
                $correct = $_POST["correct"];
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

                $sql_query = "INSERT INTO QuestionBank (Question, QuestionType, Answer) " .
                             "VALUES ('" . $question . "', '" . $questionType . "', '" . $correct . "')";

                $result = mysql_query($sql_query);

                if(!$result){
                    die(json_encode(array("Error" => "Invalid First Request")));
                }

                $lastQuestionID = mysql_insert_id();

                $index = 1;

                foreach($array as $key => $val){

                    $sql_query = "INSERT INTO Choice (ChoiceID, QuestionID, Choice, ChoiceComment) " .
                        "VALUES ('" . $index . "', '" . $lastQuestionID . "', '" . $key . "', '" . $val . "')";
    
                    $result = mysql_query($sql_query);

                    if(!$result){
                        die(json_encode(array("Error" => "Invalid Request")));
                    }
                    
                    $index++;

                }

                break;

            case "TrueFalse":
                   
                $questionType = "TF";
                $question = $_POST["question"];
                $correct = $_POST["correct"];
                
                $correctReason = $_POST["correct_reason"];
                
                $wrongAnswer1 = $_POST["wrongAnswer1"];
                $wrongReason1 = $_POST["wrongReason1"];
                
                $array = array($correct => $correctReason,
                        $wrongAnswer1 => $wrongReason1);

                krsort($array);

                $sql_query = "INSERT INTO QuestionBank (Question, QuestionType, Answer) " .
                             "VALUES ('" . $question . "', '" . $questionType . "', '" . $correct . "')";

                $result = mysql_query($sql_query);

                if(!$result){
                    die(json_encode(array("Error" => "Invalid First Request")));
                }

                $lastQuestionID = mysql_insert_id();

                $index = 1;

                foreach($array as $key => $val){

                    $sql_query = "INSERT INTO Choice (ChoiceID, QuestionID, Choice, ChoiceComment) " .
                        "VALUES ('" . $index . "', '" . $lastQuestionID . "', '" . $key . "', '" . $val . "')";
    
                    $result = mysql_query($sql_query);

                    if(!$result){
                        die(json_encode(array("Error" => "Invalid Request")));
                    }
                    
                    $index++;

                }

                break;
            case "ShortAnswer":
                    
                $questionType = "FB";               
                
                $question = $_POST["question"];
                $correct = $_POST["correct"];

                $sql_query = "INSERT INTO QuestionBank (Question, QuestionType, Answer) " .
                             "VALUES ('" . $question . "', '" . $questionType . "', '" . $correct . "')";

                $result = mysql_query($sql_query);

                if(!$result){
                    die(json_encode(array("Error" => "Invalid First Request")));
                }

                break;
            default:
                break;
        }

        echo json_encode(array("questionCreated" => "Success"));

        mysql_close($con);

    } else {
        die(json_encode(array("Error" => "Invalid POST request")));
    }
?>
