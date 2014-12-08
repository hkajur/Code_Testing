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

        //$sql_query = "SELECT COUNT(*) AS NumRight FROM QuestionBank, StudentExamAnswers WHERE QuestionBank.Answer = StudentExamAnswers.Answer AND QuestionBank.QuestionID = StudentExamAnswers.QuestionID AND StudentExamAnswers.UserID = " . $userID . " AND StudentExamAnswers.ExamID = " . $examID;
        
        $sql_query = "SELECT SUM(QuestionBank.Points) AS NumRight FROM QuestionBank, StudentExamAnswers WHERE QuestionBank.Answer = StudentExamAnswers.Answer AND QuestionBank.QuestionID = StudentExamAnswers.QuestionID AND StudentExamAnswers.UserID = " . $userID . " AND StudentExamAnswers.ExamID = " . $examID . " AND QuestionBank.QuestionType != 'PM'";

        $result = mysql_query($sql_query);

        if(!$result){
            die(json_encode(
                array("Error" => "Result invalid")));
        }
    
        $array = array();

        if(mysql_num_rows($result) == 1){
            $row = mysql_fetch_assoc($result);
            $array["numCorrect"] = $row["NumRight"];
        } else {
            $array["numCorrect"] = 0;
        }
    
        //$sql_query = "SELECT Count(QuestionID) AS TotalQuestions FROM Exam WHERE ExamID = " . $examID;
        $sql_query = "SELECT SUM(QuestionBank.Points) AS TotalQuestions FROM Exam, QuestionBank WHERE QuestionBank.QuestionID = Exam.QuestionID AND ExamID = " . $examID;

        $result = mysql_query($sql_query);

        if(!$result){
            die(json_encode(
                array("Error" => "Result invalid")));
        }
    
        $row = mysql_fetch_assoc($result);
        
        $array["totalQuestions"] = $row["TotalQuestions"];

        $sql_query = "SELECT QuestionBank.Points, StudentExamAnswers.Answer AS StudentAnswer, QuestionBank.Answer AS ActualAnswer FROM QuestionBank, StudentExamAnswers WHERE QuestionBank.QuestionID = StudentExamAnswers.QuestionID AND StudentExamAnswers.UserID = $userID AND StudentExamAnswers.ExamID = $examID AND QuestionBank.QuestionType = 'PM'";

    
        $result = mysql_query($sql_query);

        if(!$result){
            die(json_encode(
                array("Error" => "Result invalid")));
        }

        while($row = mysql_fetch_assoc($result)){

            $studentArr = explode(";", $row["StudentAnswer"]);
            $actualArr = explode(";", $row["ActualAnswer"]);

            $len = count($studentArr);
    
            $i = 0;
            $nRight = 0;

            while($i < $len - 1){

                if($studentArr[$i] == $actualArr[$i]){
                    $nRight++;
                } 
                $i++;
            }

            $tot = $nRight / ($len - 1);

            $array["numCorrect"] = $array["numCorrect"] + ($tot * $row["Points"]);
              
        }

        echo json_encode($array);

    } else {
        die(json_encode(
            array("Error" => "POST invalid")));
    }
?>
