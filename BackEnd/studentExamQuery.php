<?php

    class choiceInfo {
    
        public $choiceID;
        public $choice;

        public function __construct($id, $ch){
            $this->choiceID = $id;
            $this->choice = $ch;
        }
    }

    class questionInfo {

        public $questionID;
        public $question_type;
        public $question;
        public $choices;

        public function __construct($id, $type, $ques){
            $this->questionID = $id;
            $this->question_type = $type;
            $this->question = $ques;
            $this->choices = array();
        }
    }

    class examInfo {
        
        public $examID;
        public $examName;
        public $questions;

        public function __construct($id, $name){
            $this->examID = $id;
            $this->examName = $name;
            $this->questions = array();
        }
    }


    if($_SERVER["REQUEST_METHOD"] == "POST"){ 
           
        $examID = $_POST["examID"];
        $userID = $_POST["userID"];
        
        /*
         * Make a connection to MySql Database
         * If the connection fails, then 
         * return a JSON indication that there
         * is a MySQL connection error
         */

        $con = mysql_connect("sql.njit.edu", "caj9", "qEpO163u6") 
                or die(json_encode(array(
                    "Backend_Login" =>  "Failed",
                    "Reason" => "Mysql connection error")));
                        
        // Selects the database that you want to use
        $selectdb = mysql_select_db("caj9", $con);

        // Get Exam name
    
        $sql_examName = "SELECT ExamName FROM Exam WHERE ExamID = " . $examID;
    
        $result = mysql_query($sql_examName);

        if(!$result){
            die(json_encode(array("Error" => "Invalid request")));
        }

        $row = mysql_fetch_assoc($result);

        $examName = $row["ExamName"];

        // Query that you want to run
        // In this case, we want to check if username and
        // password are valid again our database
        
        $sql_exam_ques = "SELECT Exam.ExamID, QuestionBank.Question, QuestionBank.QuestionType, QuestionBank.QuestionID"
                    . " FROM Exam, QuestionBank"
                    . " WHERE Exam.QuestionID = QuestionBank.QuestionID"
                    . " AND Exam.ExamID = " . urldecode($examID);
                

        // Run the query and store the result into result variable
        $result = mysql_query($sql_exam_ques);
  
        
        // Check if the query is a valid MySQL query
        // If the query is invalid, it will return invalid JSON
        // This will help with the debugging in future
        
        if(!$result){
                die("Invalid request");
        }

        $exam = new examInfo($examID, $examName); 

        $index = 0;

        while($row = mysql_fetch_assoc($result)){
                $exam->questions[$index++] = 
                        new questionInfo($row["QuestionID"], $row["QuestionType"], $row["Question"]);

        }

        $size = $index;
        $index = 0;

        while($index < $size){
               
             $sql_choices = "SELECT Choice.ChoiceID, Choice.Choice" . 
                            " FROM QuestionBank, Choice" . 
                            " WHERE QuestionBank.QuestionID = Choice.QuestionID" .
                            " AND QuestionBank.QuestionID = " . $exam->questions[$index]->questionID .
                            " ORDER BY QuestionBank.QuestionID"; 

            $result = mysql_query($sql_choices);

            $j = 0;
        
            while($row = mysql_fetch_assoc($result)){
                    $exam->questions[$index]->choices[$j++] = 
                    new choiceInfo($row["ChoiceID"], $row["Choice"]);
            }

            $index++;
        }

        // Close the connection to MySQL
        mysql_close($con);

        // Encode the JSON and return it
        echo json_encode($exam);
    
    } else {
        die(json_encode(array(
            "Backend_Login" => "Failed",
            "Reason" => "Invalid post request")));
    }
?>
