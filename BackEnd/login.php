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

    class userInfo{
       
        public $userID;
        public $userName;
        public $userType;
        public $Backend_Login;
        public $exams;

        public function __construct(){
            $this->exams = array();
        }
    }

    if($_SERVER["REQUEST_METHOD"] == "POST"){ 
           
        $username = $_POST["username"];
        $password = $_POST["password"];

        /*
         * Make a connection to MySql Database
         * If the connection fails, then 
         * return a JSON indication that there
         * is a MySQL connection error
         */

        $con = mysql_connect("sql.njit.edu", "caj9", "qEpO163u6") 
                or die(json_encode(array(
                    "Backend_Login" =>  "Failed",
                    "Error" => "Mysql connection error")));
                        
        // Selects the database that you want to use
        $selectdb = mysql_select_db("caj9", $con);

        // Query that you want to run
        // In this case, we want to check if username and
        // password are valid again our database
        
        $sql_login = "SELECT * FROM Users WHERE UserName = '" 
                    . urldecode($username) . "' AND UserPassword = '"
	                . urldecode($password) . "'";

        // Run the query and store the result into result variable
        $result = mysql_query($sql_login);
  
        $user = new userInfo();
        
        // Check if the query is a valid MySQL query
        // If the query is invalid, it will return invalid JSON
        // This will help with the debugging in future
        
        if(!$result){
            $user->Backend_Login = "Invalid query";
            die(json_encode($array));
        }

        $row = mysql_fetch_assoc($result);

        $user->userType = $row["UserType"];
        $user->userID = $row["UserID"];
        $user->userName = $row["UserName"];

        // Get the num of rows that the result produced
        $num_rows = mysql_num_rows($result);

        // If the rows are 1 then return Success JSON
        // Otherwise return Failed JSON
        
        if($num_rows == 1){
            $user->Backend_Login = "Success";
        }
        else {
            die(json_encode(array(
                "Backend_Login" => "Failed", 
                "Error" => "Invalid query")));
        }

        if($user->userType == "Student"){

            // Select statement to get all the exams that user has taken
            $sql_exams_taken = "SELECT Distinct(ExamId), ExamName FROM Exam " .
                               "WHERE Exam.ExamID IN " .
                               "(SELECT Distinct(Exam.ExamID) " .
                               "FROM Exam, StudentExamAnswers " .
                               "WHERE StudentExamAnswers.ExamID = Exam.ExamID " .
                               "AND UserID = " . $user->userID . " )";

            // Execute the query
            $result = mysql_query($sql_exams_taken);
    
            // If result is not successful, return invalid
            if(!$result){
                die(json_encode(array(
                    "Backend_Login" => "Failed", 
                    "Error" => "Invalid query")));
            }

            $index = 0;

            while($row = mysql_fetch_assoc($result)){
                $user->exams[$index++] = 
                new studentInfo($row["ExamId"], $row["ExamName"], "True");
            }
            
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
            
            while($row = mysql_fetch_assoc($result)){
                $user->exams[$index++] = 
                new studentInfo($row["ExamId"], $row["ExamName"], "False");
            }

        } else {

            $sql_query = "SELECT Distinct(TeacherExam.ExamID), TeacherExam.ExamReleased, Exam.ExamName FROM TeacherExam, Exam WHERE Exam.ExamID = TeacherExam.ExamID AND UserID = " . $user->userID;

            $result = mysql_query($sql_query);

            if(!$result){
                    die(json_encode(array(
                            "Backend_Login" => "Failed",
                            "Error" => "Invalid result")));
            }     

            $index = 0;

            while($row = mysql_fetch_assoc($result)){
                $user->exams[$index++] = 
                        new teacherInfo($row["ExamID"], $row["ExamName"], $row["ExamReleased"]);
            }

        }
        // Close the connection to MySQL
        mysql_close($con);

        // Encode the JSON and return it
        echo json_encode($user);
    
    } else {
        die(json_encode(array(
            "Backend_Login" => "Failed",
            "Error" => "Invalid post request")));
    }
?>
