<?php

    if($_SERVER["REQUEST_METHOD"] == "POST"){
            
        $userID = $_POST["userID"];
        $examID = $_POST["examID"];
        $grade = $_POST["grade"];

        $con = mysql_connect("sql.njit.edu", "caj9", "qEpO163u6") 
                or die(json_encode(array(
                    "Backend_Login" =>  "Failed",
                    "Error" => "Mysql connection error")));
                        
        // Selects the database that you want to use
        $selectdb = mysql_select_db("caj9", $con);

        $insertQuery = "INSERT INTO Grades (UserID, ExamID, Grade) " .
                " VALUES('" . $userID . "', '" . $examID . "', '" . $grade . "')";

        $result = mysql_query($insertQuery);

        if(!$result){
                die(json_encode(array(
                        "insertGrade" => "Failed",
                        "Error" => "Invalid result")));
        }

        echo json_encode(
                array("insertGrade" => "Success"));

    } else {
            die(json_encode(
                array("Error" => "Invalid POST Request")));
    }
?>
