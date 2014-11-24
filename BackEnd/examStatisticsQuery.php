<?php

    if($_SERVER["REQUEST_METHOD"] == "POST"){
       
        $userID = $_POST["userID"];
        $examID = $_POST["examID"];

        $con = mysql_connect("sql.njit.edu", "caj9", "qEpO163u6") 
                or die(json_encode(array(
                    "Backend_Login" =>  "Failed",
                    "Error" => "Mysql connection error")));
                        
        // Selects the database that you want to use
        $selectdb = mysql_select_db("caj9", $con);
        
        $query = "SELECT QuestionID FROM Exam WHERE ExamID = " . $examID;

        $result = mysql_query($query);

        if(!$result){
            die(json_encode(array("Error" => "Result invalid due to query failure")));
        }

        if(mysql_num_rows($result) == 0){
            die(json_encode(array("Error" => "No questions in the exam")));
        }

        while($row = mysql_fetch_assoc($result)){
            
            $query2 = "SELECT COUNT(*) AS NumStudentsCorrectly"
                        . " FROM QuestionBank Q, StudentExamAnswers S "
                        . " WHERE S.QuestionID = Q.QuestionID AND Q.Answer = S.Answer " 
                        . " AND S.ExamID = " . $examID . " AND Q.QuestionID = " . $row["QuestionID"] ;
            
            $res = mysql_query($query2);

            if(!$res){
                die(json_encode(array("Error" => "Result invalid due to query failure")));
            }

            $row2 = mysql_fetch_assoc($res);

            echo "On Question " . $row["QuestionID"] . " Answer correct: " . $row2["NumStudentsCorrectly"] . "\n";
        }

        echo json_encode(array("ExamStatistics" => "Success"));

    } else {
        die(json_encode(array("Error" => "Invalid POST")));
    }
?>
