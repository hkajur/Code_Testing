<?php
    if($_SERVER["REQUEST_METHOD"] == "POST"){
    
        $questionID = $_POST["questionID"];
        $choiceID = $_POST["choiceID"];

        $con = mysql_connect("sql.njit.edu", "caj9", "qEpO163u6") 
                or die(json_encode(array(
                    "Backend_Login" =>  "Failed",
                    "Error" => "Mysql connection error")));
                        
        // Selects the database that you want to use
        $selectdb = mysql_select_db("caj9", $con);

        $sql_query = "SELECT Choice.Choice FROM Choice WHERE QuestionID = " . $questionID . " AND ChoiceID = " . $choiceID;

        $result = mysql_query($sql_query);
        if(!$result){
            die("Nothing");
        }

        if(mysql_num_rows($result) == 0)
            die(json_encode(array("Error" => "Invalid request")));

        $row = mysql_fetch_assoc($result);
        echo $row["Choice"];

    } else {
        die(json_encode(array("Error" => "Invalid Post")));
    }
?>
