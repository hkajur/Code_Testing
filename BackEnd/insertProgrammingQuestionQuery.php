<?php

    function encodeCommaSep($array){
        $str = "";
        $len = count($array);

        for($i = 0; $i < $len; $i++){
            if($i+1 != $len)
                $str = $str . $array[$i] . ",";
            else 
                $str = $str . $array[$i];
        }
        
        return $str;
    }

    if($_SERVER["REQUEST_METHOD"] == "POST"){

        $obj = file_get_contents("php://input");

        $json_obj = json_decode($obj, true);

        $questionType = $json_obj["question_type"];
    
        $con = mysql_connect("sql.njit.edu", "caj9", "qEpO163u6") 
               or die(json_encode(array(
                    "questionCreated" => "Failed",
                    "Error" =>  "MySQL_Connection_Err")));
                                
        // Selects the database that you want to use
        $selectdb = mysql_select_db("caj9", $con);

        $questionType = "PM";

        $question = $json_obj["question"];

        $input1 = encodeCommaSep($json_obj["input1"]);
        $input2 = encodeCommaSep($json_obj["input2"]);
        $input3 = encodeCommaSep($json_obj["input3"]);
        $input4 = encodeCommaSep($json_obj["input4"]);
        
        $output1 = $json_obj["output1"];
        $output2 = $json_obj["output2"];
        $output3 = $json_obj["output3"];
        $output4 = $json_obj["output4"];

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
        
        if(!empty($input4) && !empty($output4))
                $temp = $temp . "Input: " . $input4 . " Output: " . $output4 . ";";

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

                
                if(!empty($input4) && !empty($output4)){
                    $sql_query = "INSERT INTO programQuestions (QuestionID, Input, Output) VALUES ('" . $lastQuestionID . "', '" . $input4 . "', '" . $output4 . "')";
                    $result = mysql_query($sql_query);
                    if(!$result){
                            die(json_encode(array(
                                    "questionCreated" => "Failed",
                                    "Error" => "Invalid First Request")));
                    }
                }
            
            }

        }
        echo json_encode(array("questionCreated" => "Success"));
        mysql_close($con);

    } else {
            die(json_encode(array(
                    "questionCreated" => "Failed",
                    "Error" => "Invalid POST request")));
    }
?>
