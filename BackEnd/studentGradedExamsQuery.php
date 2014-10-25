<?php

    class examInfo {
        public $examID;
        public $examName;
        public $grade;
    
        public function __construct($id, $name, $gr){
                $this->examID = $id;
                $this->examName = $name;
                $this->grade = $gr;
        }
    }

    class gradeExamsInfo{
        public $exams;

        public function __construct(){
            $this->exams = array();
        }
    }

    if($_SERVER["REQUEST_METHOD"] == "POST"){
        
        $userID = $_POST["userID"];

        $con = mysql_connect("sql.njit.edu", "caj9", "qEpO163u6") 
                or die(json_encode(array(
                    "Backend_Login" =>  "Failed",
                    "Reason" => "Mysql connection error")));
                        
        // Selects the database that you want to use
        $selectdb = mysql_select_db("caj9", $con);

        $sqlGradedExams = "SELECT Distinct(Exam.ExamID), Grades.Grade, ". 
                "Exam.ExamName FROM Grades, Exam WHERE UserID = " . 
                $userID . " AND Exam.ExamID = Grades.ExamID";

        $result = mysql_query($sqlGradedExams);

        if(!$result){
            die(json_encode(array("Error" => "Invalid result")));
        }
        
        $grades = new gradeExamsInfo();

        $index = 0;

        while($row = mysql_fetch_assoc($result)){
                $grades->exams[$index++] = 
                    new examInfo($row["ExamID"], $row["ExamName"], $row["Grade"]);
        }

        echo json_encode($grades);

        mysql_close($con);
                
    } else {
            die(json_encode(array(
                    "Error" => "Invalid POST request")));
    }
?>
