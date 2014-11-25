<?php

    class teacherInfo {

        public $examID;
        public $examName;
        public $examReleased;

        public function __construct($id, $name, $rel){
            $this->examID = $id;
            $this->examName = $name;
            $this->examReleased = $rel;
        }
    }

    if($_SERVER["REQUEST_METHOD"] == "POST"){ 
    
            $userID = $_POST["userID"];

                
            $con = mysql_connect("sql.njit.edu", "caj9", "qEpO163u6") 
                    or die(json_encode(array(
                    "Backend_Login" =>  "Failed",
                    "Error" => "Mysql connection error")));


            $selectdb = mysql_select_db("caj9", $con);
            
            $sql_query = "SELECT Distinct(TeacherExam.ExamID), TeacherExam.ExamReleased, Exam.ExamName FROM TeacherExam, Exam WHERE Exam.ExamID = TeacherExam.ExamID AND UserID = " . $userID;

            $result = mysql_query($sql_query);

            if(!$result){
                    die(json_encode(array(
                            "Backend_Login" => "Failed",
                            "Error" => "Invalid result")));
            }     

            $index = 0;

            $exams = array("exams" => array());

            while($row = mysql_fetch_assoc($result)){
                $exams["exams"][$index++] = 
                        new teacherInfo($row["ExamID"], $row["ExamName"], $row["ExamReleased"]);
            }

            echo json_encode($exams);
            mysql_close($con);
    }
?>
