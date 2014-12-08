<?php

    if($_SERVER["REQUEST_METHOD"] == "POST"){
    
        $userID = $_POST["userID"];
        $examID = $_POST["examID"];
    
        $URL = "http://afsaccess1.njit.edu/~caj9/Code_Testing/BackEnd/studentsTookExam.php";
                
        $ch = curl_init();

        $postfields = array("examID" => $examID);

        curl_setopt($ch, CURLOPT_URL, $URL);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_POST, count($postfields));
        curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($postfields));
        $page = curl_exec($ch);

        if(curl_errno($ch)){
            die(json_encode(array("Error" => curl_error($ch))));
        }
    
        curl_close($ch);

        $obj = json_decode($page, true);

        foreach($obj["userIDs"] as $ind => $studentUserID){
     
            $URL = "http://afsaccess1.njit.edu/~caj9/Code_Testing/BackEnd/studentExamInfo.php";

            $ch = curl_init();
            
            $postfields = array(
                    "userID" => $studentUserID, 
                    "examID" => $examID);

            curl_setopt($ch, CURLOPT_URL, $URL);
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
            curl_setopt($ch, CURLOPT_POST, count($postfields));
            curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
            curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($postfields));
            $page = curl_exec($ch);
            
            if(curl_errno($ch)){
                die(json_encode(array("Error" => curl_error($ch))));
            }
            
            $obj = json_decode($page, true);
        
            $questionRight = doubleval($obj["numCorrect"]);
            $totalQuestions = doubleval($obj["totalQuestions"]);

            if($totalQuestions == 0){
                die(json_encode(array("Error" => "Num of questions = 0")));
            } else {
                $grade = ($questionRight/$totalQuestions)*100;
            }

            curl_close($ch);
            
            $postfields = array(
                    "userID" => $studentUserID, 
                    "examID" => $examID,
                    "grade" => $grade);

            $URL = "http://afsaccess1.njit.edu/~caj9/Code_Testing/BackEnd/insertGrade.php";

            $ch = curl_init();
    
            curl_setopt($ch, CURLOPT_URL, $URL);
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
            curl_setopt($ch, CURLOPT_POST, count($postfields));
            curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
            curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($postfields));
            $page = curl_exec($ch);

            if(curl_errno($ch)){
                die(json_encode(array("Error" => curl_error($ch))));
            }

            curl_close($ch);

        }

        $URL = "http://afsaccess1.njit.edu/~caj9/Code_Testing/BackEnd/updateTeacherExam.php";

        $postfields = array(
                        "userID" => $userID,
                        "examID" => $examID);

        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $URL);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_POST, count($postfields));
        curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($postfields));

        $page = curl_exec($ch);

        if(curl_errno($ch)){
            die(json_encode(array("Error" => curl_error($ch))));
        }

        echo $page;
        
    } else {
        die(json_encode(
            array("Error" => "Invalid POST Request")));
    }
?>
