<?php

    function deleteRowsFromTable($tableName, $examID){
        
        $query = "DELETE FROM " . $tableName . " WHERE ExamID = " . $examID;

        $result = mysql_query($query);

        if(!$result){
            die(json_encode(array("Error" => "Result invalid due to query failure")));
        }

    }

    if($_SERVER["REQUEST_METHOD"] == "POST"){
       
        $userID = $_POST["userID"];
        $examID = $_POST["examID"];

        $con = mysql_connect("sql.njit.edu", "caj9", "qEpO163u6") 
                or die(json_encode(array(
                    "Backend_Login" =>  "Failed",
                    "Error" => "Mysql connection error")));
                        
        // Selects the database that you want to use
        $selectdb = mysql_select_db("caj9", $con);

        deleteRowsFromTable("TeacherExam", $examID);
        deleteRowsFromTable("Grades", $examID);
        deleteRowsFromTable("StudentExamAnswers", $examID);
        deleteRowsFromTable("ProgramStudentExamAnswers", $examID);
        deleteRowsFromTable("Exam", $examID);

        echo json_encode(array("Delete" => "Success"));

    } else {
        die(json_encode(array("Error" => "Invalid POST")));
    }
?>
