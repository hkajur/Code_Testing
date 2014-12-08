<?php
    
    class studentInfo {
        
        public $examID;
        public $examName;
        public $examTaken;

        public function __construct($id, $name, $taken){
            $this->examID = $id;
            $this->examName = $name;
            $this->examTaken = $taken;
        }
    }

    class userInfo{
       
        public $userID;
        public $exams;

        public function __construct(){
            $this->exams = array();
        }
    }

    if($_SERVER["REQUEST_METHOD"] == "POST"){ 

        $con = mysql_connect("sql.njit.edu", "caj9", "qEpO163u6") 
                or die(json_encode(array(
                    "Backend_Login" =>  "Failed",
                    "Error" => "Unable to connect to the database")));
                        
        // Selects the database that you want to use
        $selectdb = mysql_select_db("caj9", $con);
       
        $userID = $_POST["userID"];

        $user = new userInfo();
        
        $user->userID = $userID;

        // Select statement to get all the exams that user has not taken
        $sql_exams_taken = "SELECT Distinct(ExamId), ExamName FROM Exam " .
                           "WHERE Exam.ExamID NOT IN " .
                           "(SELECT Distinct(Exam.ExamID) " .
                           "FROM Exam, StudentExamAnswers " .
                           "WHERE StudentExamAnswers.ExamID = Exam.ExamID " .
                           "AND UserID = " . $user->userID . " )";

        $result = mysql_query($sql_exams_taken);

        if(!$result){
            die(json_encode(array(
                "Backend_Login" => "Failed", 
                "Error" => "Invalid query")));
        }

        $index = 0; 
        while($row = mysql_fetch_assoc($result)){
            $user->exams[$index++] = 
            new studentInfo($row["ExamId"], $row["ExamName"], "False");
        }

        echo json_encode($user);


    } else {
        die(json_encode(array(
            "Backend_Login" => "Failed",
            "Error" => "Invalid post request")));
    }

?>
