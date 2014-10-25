<?php

    if($_SERVER["REQUEST_METHOD"] == "POST"){

        $json = file_get_contents('php://input'); 
        $obj = json_decode($json, true);

        $examName = $obj["examName"];
    
        $con = mysql_connect("sql.njit.edu", "caj9", "qEpO163u6") 
            or die(json_encode(array(
                "Backend_Login" =>  "MySQL_Connection_Err")));
                                
        // Selects the database that you want to use
        $selectdb = mysql_select_db("caj9", $con);

        $sql_query = "SELECT MAX(ExamID) AS MaxID FROM Exam";

        $result = mysql_query($sql_query);

        if(!$result){
            die(json_encode(array("Error" => "First Invalid result")));
        }

        $row = mysql_fetch_assoc($result);

        $examID = intval($row["MaxID"]) + 1;

        foreach($obj["questionIDs"] as $key => $value){

            $query = "INSERT INTO Exam (ExamID, QuestionID, ExamName) " . 
                     "VALUES ('" . $examID . "', '" . $value . "', '" . $examName . "')";

            $result = mysql_query($query);
            
            if(!$result){
                 die(json_encode(array("Error" => "Invalid result")));
            }
        }
        
        echo "success";

        mysql_close($con);

    } else {
        die(json_encode(array("Error" => "Invalid POST request")));
    }
?>
