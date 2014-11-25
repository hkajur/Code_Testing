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
        public $points;
        public $choices;

        public function __construct($id, $type, $ques, $points){
            $this->questionID = $id;
            $this->question_type = $type;
            $this->question = $ques;
            $this->points = $points;
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
                    "accessExam" =>  "Failed",
                    "Error" => "Unable to make a mysql connection")));
                        
        // Selects the database that you want to use
        $selectdb = mysql_select_db("caj9", $con);

        // Get Exam name
    
        $sql_examName = "SELECT ExamName FROM Exam WHERE ExamID = " . $examID;
    
        $result = mysql_query($sql_examName);

        if(!$result){
                die(json_encode(array(
                    "accessExam" => "Failed",
                    "Error" => "Invalid Query to the Database")));
        }

        $row = mysql_fetch_assoc($result);

        $examName = $row["ExamName"];

        if(empty($examName)){
                die(json_encode(array(
                    "accessExam" => "Failed",
                    "Error" => "No such exam exists")));
        }

        // Query that you want to run
        // In this case, we want to check if username and
        // password are valid again our database
        
        $sql_exam_ques = "SELECT QuestionBank.Points, Exam.ExamID, QuestionBank.Question, QuestionBank.QuestionType, QuestionBank.QuestionID"
                        . " FROM Exam, QuestionBank"
                        . " WHERE Exam.QuestionID = QuestionBank.QuestionID"
                        . " AND Exam.ExamID = " . urldecode($examID);
                

        // Run the query and store the result into result variable
        $result = mysql_query($sql_exam_ques);
  
        
        // Check if the query is a valid MySQL query
        // If the query is invalid, it will return invalid JSON
        // This will help with the debugging in future
        
        if(!$result){
                die(json_encode(array(
                    "accessExam" => "Failed",
                    "Error" => "Invalid request")));
        }

        $exam = new examInfo($examID, $examName); 

        if(mysql_num_rows($result) == 0){
            $exam->examID = null;    
                die(json_encode(array(
                    "accessExam" => "Failed",
                    "Error" => "No Questions inside the exam")));
        }

        $index = 0;

        while($row = mysql_fetch_assoc($result)){
                $exam->questions[$index++] = 
                        new questionInfo($row["QuestionID"], $row["QuestionType"], $row["Question"], $row["Points"]);

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
            "accessExam" => "Failed",
            "Error" => "Invalid post request")));
    }
?>
