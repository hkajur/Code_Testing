<?php
    if($_SERVER["REQUEST_METHOD"] == "POST"){
    
        $examID = $_POST["examID"];

        $con = mysql_connect("sql.njit.edu", "caj9", "qEpO163u6") 
                or die(json_encode(array(
                    "Backend_Login" =>  "Failed",
                    "Error" => "Mysql connection error")));
                        
        // Selects the database that you want to use
        $selectdb = mysql_select_db("caj9", $con);

        $sql_query = "SELECT QuestionBank.QuestionID FROM QuestionBank, Exam WHERE QuestionBank.QuestionID = Exam.QuestionID AND QuestionBank.QuestionType = \"PM\" AND ExamID = " . $examID;

        $result = mysql_query($sql_query);

        if(!$result){
            die(json_encode(array("Error" => "Invalid request")));
        }

        $array = array(
            "examID" => $examID,
            "questionIDs" => array());

        $index = 0;

        while($row = mysql_fetch_assoc($result)){
            $array["questionIDs"][$index] = $row["QuestionID"];
            $index++;
        }

        echo json_encode($array);

    } else {
        die(json_encode(array("Error" => "Invalid Post")));
    }
?>
