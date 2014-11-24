<?php
    if($_SERVER["REQUEST_METHOD"] == "POST"){

        $examID = $_POST["examID"];
        $questionID = $_POST["questionID"];
        $userID = $_POST["userID"];
        $userAnswer = $_POST["userAnswer"];

        $con = mysql_connect("sql.njit.edu", "caj9", "qEpO163u6") 
                or die(json_encode(array(
                    "InsertPQuestion" =>  "Failed",
                    "Error" => "Mysql connection error")));
                        
        // Selects the database that you want to use
        $selectdb = mysql_select_db("caj9", $con);

        $sql_query = "INSERT INTO ProgramStudentExamAnswers (ExamID, QuestionID, UserID, Answer) VALUES ('" . $examID . "', '" . $questionID . "', '" . $userID . "', '" . $userAnswer . "')";


        $result = mysql_query($sql_query);

        if(!$result){
            die(json_encode(array("Error" => "Invalid result")));
        }

        echo json_encode(array("InsertPQuestion" => "Success"));

    } else {
        die(array("Error" => "Not posted"));
    }
?>
