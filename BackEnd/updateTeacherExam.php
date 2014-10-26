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

        $query = "UPDATE TeacherExam SET ExamReleased = 'True' " .
                 "WHERE UserID = " . $userID . " " .
                 "AND ExamID = " . $examID;

        $result = mysql_query($query);

        if(!$result){
            die(json_encode(array("Error" => "Result invalid")));
        }

        echo json_encode(array("Update" => "Success"));

    } else {
        die(json_encode(array("Error" => "Invalid POST")));
    }
?>
