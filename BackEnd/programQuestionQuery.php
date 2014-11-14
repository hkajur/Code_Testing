<?php
    if($_SERVER["REQUEST_METHOD"] == "POST"){
    
        $questionID = $_POST["questionID"];

        $con = mysql_connect("sql.njit.edu", "caj9", "qEpO163u6") 
                or die(json_encode(array(
                    "Backend_Login" =>  "Failed",
                    "Error" => "Mysql connection error")));
                        
        // Selects the database that you want to use
        $selectdb = mysql_select_db("caj9", $con);
        $sql_query = "SELECT Input, Output FROM programQuestions WHERE QuestionID = " . $questionID;

        $result = mysql_query($sql_query);

        if(!$result){
            die(json_encode(array("Error" => "Invalid request")));
        }

        $array = array(
            "questionID" => $questionID,
            "Input" => array(), 
            "Output" => array());

        $index = 0;

        while($row = mysql_fetch_assoc($result)){
            $array["Input"][$index] = $row["Input"];
            $array["Output"][$index] = $row["Output"];
            $index++;
        }

        echo json_encode($array);

    } else {
        die(json_encode(array("Error" => "Invalid Post")));
    }
?>
