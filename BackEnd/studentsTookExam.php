<?php

    if($_SERVER["REQUEST_METHOD"] == "POST"){
            
        $examID = $_POST["examID"];

        $con = mysql_connect("sql.njit.edu", "caj9", "qEpO163u6") 
                or die(json_encode(array(
                    "Backend_Login" =>  "Failed",
                    "Error" => "Mysql connection error")));
                        
        // Selects the database that you want to use
        $selectdb = mysql_select_db("caj9", $con);

        $sql_query = "SELECT Distinct(UserID) " . 
                     " FROM StudentExamAnswers " .
                     " WHERE ExamID = " . $examID;

        $result = mysql_query($sql_query);

        if(!$result){
            die(json_encode(
                array("Error" => "Invalid result")));
        }

        if(mysql_num_rows($result) == 0){
            die(json_encode(
                array("userIDs" => array())));
        }
        
        $index = 0;
    
        $usersArray = array();

        while($row = mysql_fetch_assoc($result)){

              $usersArray[$index++] = $row["UserID"];
        
        }

        echo json_encode(array("userIDs" => $usersArray));

        mysql_close($con);

    } else {
        die(json_encode(array("Error" => "Invalid POST request")));
    }
?>
