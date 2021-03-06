<?php

    class questionInfo {
           
        public $questionID;
        public $question;
        public $questionType;
        public $studentAnswer;
        public $correctAnswer;
        public $points;
        public $earnedPoints;

        public $userCorrect;
        public $comment;


        public function __construct($p, $eP, $id, $q, $type, $sAns, $cAns, $com){
                $this->points = $p; 
                $this->earnedPoints = $eP;
            $this->questionID = $id;
            $this->question = $q;
            $this->questionType = $type;
            $this->studentAnswer = $sAns;
            $this->correctAnswer = $cAns;
        }

        public function setComment($com){
            $this->comment = $com;
        }

        public function checkCorrect(){
                
            if($this->studentAnswer == $this->correctAnswer)
                $this->userCorrect = "True";
            else {
                    $this->userCorrect = "False";
                if($this->questionType != "PM")
                    $this->earnedPoints = 0;
            }

        }
    }

    class examEval {
        public $exam;

        public function __construct(){
            $this->exam = array();
        }
    }

    if($_SERVER["REQUEST_METHOD"] == "POST"){
        
        $userID = $_POST["userID"]; 
        $examID = $_POST["examID"]; 

        $con = mysql_connect("sql.njit.edu", "caj9", "qEpO163u6") 
                or die(json_encode(array(
                    "Backend_Login" =>  "Failed",
                    "Reason" => "Mysql connection error")));
        
        $selectdb = mysql_select_db("caj9", $con);
        
        $sql_query = "SELECT QuestionBank.Points, Choice.ChoiceComment, QuestionBank.QuestionID, QuestionBank.Question, QuestionBank.QuestionType, QuestionBank.Answer as correctAnswer, StudentExamAnswers.Answer as studentAnswer FROM QuestionBank, StudentExamAnswers, Choice WHERE QuestionBank.questionID = StudentExamAnswers.questionID AND Choice.QuestionID = QuestionBank.QuestionID AND Choice.Choice = StudentExamAnswers.Answer AND ExamID = " . $examID . " AND StudentExamAnswers.userID = " . $userID;

        $result = mysql_query($sql_query);

        if(!$result){
            die(json_encode(array("Error" => "Result invalid")));
        } 

        $index = 0;

        $studentExam = new examEval();

        while($row = mysql_fetch_assoc($result)){
                
                $studentExam->exam[$index] = 
                        new questionInfo(
                        $row["Points"], 
                        $row["Points"], 
                        $row["QuestionID"], 
                        $row["Question"], 
                        $row["QuestionType"], 
                        $row["studentAnswer"], 
                        $row["correctAnswer"]);

                $str = $studentExam->exam[$index]->questionType;
      
                if($str == "MC" || $str == "TF"){
                    $studentExam->exam[$index]->setComment($row["ChoiceComment"]);
                }
                
                $studentExam->exam[$index]->checkCorrect();
                
                $index++;

        }

        $query = "SELECT QuestionBank.Points, QuestionBank.QuestionID, QuestionBank.Question, QuestionBank.QuestionType, QuestionBank.Answer as correctAnswer, StudentExamAnswers.Answer as studentAnswer FROM QuestionBank, StudentExamAnswers WHERE QuestionBank.questionID = StudentExamAnswers.questionID AND QuestionBank.QuestionType = 'FB' AND ExamID = " . $examID . " AND StudentExamAnswers.userID = " . $userID;

        $result = mysql_query($query);

        if(!$result){
            die(json_encode(array("Error" => "Result invalid")));
        }


        while($row = mysql_fetch_assoc($result)){

                $studentExam->exam[$index] = 
                        new questionInfo(
                        $row["Points"], 
                        $row["Points"], 
                        $row["QuestionID"], 
                        $row["Question"], 
                        $row["QuestionType"], 
                        $row["studentAnswer"], 
                        $row["correctAnswer"]);
                
                $studentExam->exam[$index]->checkCorrect();

                $index++;
        }

        $query = "SELECT QuestionBank.Points, QuestionBank.QuestionID, QuestionBank.Question, QuestionBank.QuestionType, QuestionBank.Answer as correctAnswer, StudentExamAnswers.Answer as studentAnswer FROM QuestionBank, StudentExamAnswers WHERE QuestionBank.questionID = StudentExamAnswers.questionID AND QuestionBank.QuestionType = 'PM' AND ExamID = " . $examID . " AND StudentExamAnswers.userID = " . $userID;

        $result = mysql_query($query);

        if(!$result){
            die(json_encode(array("Error" => "Result invalid")));
        }


        while($row = mysql_fetch_assoc($result)){
    
            $studentArr = explode(";", $row["studentAnswer"]);
            $actualArr = explode(";", $row["correctAnswer"]);

            $len = count($studentArr);
    
            $i = 0;
            $nRight = 0;

            while($i < $len - 1){

                if($studentArr[$i] == $actualArr[$i]){
                    $nRight++;
                } 
                $i++;
            }

            $tot = $row["Points"] * ($nRight / ($len - 1));

            $studentExam->exam[$index] = 
                        new questionInfo(
                        $row["Points"], 
                        $tot,
                        $row["QuestionID"], 
                        $row["Question"], 
                        $row["QuestionType"], 
                        $row["studentAnswer"], 
                        $row["correctAnswer"]);
                
            $studentExam->exam[$index]->checkCorrect();

            $index++;
        }
        echo json_encode($studentExam);

        mysql_close($con);

    } else {
        die(json_encode(array(
            "Backend_Login" => "Failed",
            "Reason" => "Invalid post request")));
    }
