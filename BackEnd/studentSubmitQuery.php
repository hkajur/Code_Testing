<?php

    if($_SERVER["REQUEST_METHOD"] == "POST"){ 

        $json = file_get_contents('php://input');
        $obj = json_decode($json, true);

        /*
         * Make a connection to MySql Database
         * If the connection fails, then 
         * return a JSON indication that there
         * is a MySQL connection error
         */

        $con = mysql_connect("sql.njit.edu", "caj9", "qEpO163u6") 
                or die(json_encode(array(
                    "examSubmit" =>  "Failed",
                    "Error" => "Mysql connection error")));
                        
        // Selects the database that you want to use
        $selectdb = mysql_select_db("caj9", $con);

        $userID = $obj["userID"];
        $examID = $obj["examID"];
        
        foreach($obj["questionAnswer"] as $key => $val){
                
            $insertExam = "INSERT INTO StudentExamAnswers (ExamID, QuestionID, UserID, Answer) " .
                        "VALUES ('" . $examID . "', '" . 
                        $val["questionID"] . "', '" . 
                        $userID . "', '" . 
                        $val["userAnswer"] . "')";

            $result = mysql_query($insertExam); 
            
            if(!$result){
                    die(json_encode(array(
                            "examSubmit" => "Failed",
                            "Error" => "Invalid request")));
            }
        }

        // Encode the JSON and return it
        echo json_encode(array("examSubmit" => "Success"));

        mysql_close($con); 
    } else {
        die(json_encode(array(
            "examSubmit" => "Failed",
            "Error" => "Invalid post request")));
    }
?>
