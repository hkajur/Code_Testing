<?php

    function getProgrammingQuestions($examID){
        
        $ch = curl_init();

        $URL = "http://afsaccess1.njit.edu/~caj9/Code_Testing/BackEnd/getProgramQuestionQuery.php";

        $postfields = array("examID" => $examID);

        curl_setopt($ch, CURLOPT_URL, $URL);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_POST, count($postfields));
        curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($postfields));

        $page = curl_exec($ch); // make request

        if(curl_errno($ch))
            echo 'Curl error: ' . curl_error($ch);

        return $page; 
    }

    function isProgrammingQuestion($val, $array){
            
        foreach($array as $key => $value)
            if($value == $val)
                return true;

        return false;
    }

    if($_SERVER["REQUEST_METHOD"] == "POST"){ 

        $json = file_get_contents('php://input');
        $obj = json_decode($json, true);

        /*
         * Make a connection to MySql Database
         * If the connection fails, then 
         * return a JSON indication that there
         * is a MySQL connection error
         */

        $con = mysql_connect("sql.njit.edu", "caj9", "qEpO163u6") 
                or die(json_encode(array(
                    "examSubmit" =>  "Failed",
                    "Error" => "Mysql connection error")));
                        
        // Selects the database that you want to use
        $selectdb = mysql_select_db("caj9", $con);

        $userID = $obj["userID"];
        $examID = $obj["examID"];

        $progQuestions = getProgrammingQuestions($examID);
        
        $progJson = json_decode($progQuestions, true);

        $progLst = $progJson["questionIDs"];


        foreach($obj["questionAnswer"] as $key => $val){

            $pQuestionID = ""; 
            
            if(isProgrammingQuestion($val["questionID"], $progLst)){
                $pQuestionID = $val["questionID"];
            }

            $insertExam = "INSERT INTO StudentExamAnswers (ExamID, QuestionID, UserID, Answer) " .
                        "VALUES ('" . $examID . "', '" . 
                        $val["questionID"] . "', '" . 
                        $userID . "', '" . 
                        $val["userAnswer"] . "')";

            $result = mysql_query($insertExam); 
            
            if(!$result){
                    die(json_encode(array(
                            "examSubmit" => "Failed",
                            "Error" => "Invalid request")));
            }
        }

        // Encode the JSON and return it
        echo json_encode(array("examSubmit" => "Success"));

        mysql_close($con); 
    } else {
        die(json_encode(array(
            "examSubmit" => "Failed",
            "Error" => "Invalid post request")));
    }
?>
