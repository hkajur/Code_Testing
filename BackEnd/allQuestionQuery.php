<?php

    class questionInfo {
            
        public $questionID;
        public $questionType;
        public $question;

        public function __construct($id, $ques, $quesType){
            $this->questionID = $id;
            $this->question = $ques;
            $this->questionType = $quesType;
        }
    }
    class ques{
            public $questions;

            public function __construct(){
                $this->questions = array();
            }
    }

    if($_SERVER["REQUEST_METHOD"] == "POST"){
    
        $userID = $_POST["userID"];

        $con = mysql_connect("sql.njit.edu", "caj9", "qEpO163u6") 
                or die(json_encode(array(
                    "Backend_Login" =>  "Failed",
                    "Error" => "Mysql connection error")));
                        
        // Selects the database that you want to use
        $selectdb = mysql_select_db("caj9", $con);


        $sql_query = "SELECT Question, QuestionType, QuestionID FROM QuestionBank";

        $result = mysql_query($sql_query);

        if(!$result){
            die(json_encode(array("Error" => "Invalid result")));
        }

        $ques = new ques();
        
        $index = 0;
       
        while($row = mysql_fetch_assoc($result)){
                $ques->questions[$index++] = 
                    new questionInfo($row["QuestionID"], $row["Question"], $row["QuestionType"]);
        }

        echo json_encode($ques);
    
        mysql_close($con);

    } else {
            die(json_encode(array(
                    "Error" => "Invalid POST request")));
    } 
?>
