<?php

    /**
     * Function isQuestionProgramming
     * ==============================
     * 
     * Returns true if the question ID belongs to the lst
     * Otherwise return false
     *
     **/
    function isQuestionProgramming($questionID, $lst){

        foreach($lst as $k => $v)
            if($v == $questionID)
                return true;
        return false;
    }

    function decodeCommaSep($str){
        $arr = explode(",", $str);
    
        $myStr = "";
        foreach($arr as $key => $val){
            $myStr = $myStr . "\"$val\" ";
        }
        
        return $myStr;
    }
    
    function isInteger($str){
    
        if(preg_match("/^[\d]*$/", $str))
                return true;
        return false;
    }

    function isDouble($str){
        if(preg_match("/^[\d]+\.[\d]+$/", $str))
                return true;
        return false;
    }

    /**
     * Check If Request Method is POST and run
     * Otherwise die and reply a error JSON
     **/
    if($_SERVER["REQUEST_METHOD"] == "POST"){

        $json = file_get_contents('php://input');
        $json_obj = json_decode($json, true);

        $userID = $json_obj["userID"];
        $examID = $json_obj["examID"];

        $URL = "http://afsaccess1.njit.edu/~vk255/Code_Testing/BackEnd/getProgramQuestionQuery.php";
        $ch = curl_init($URL);
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

        $json_output = json_decode($page, true);
        $progQuestionArray = $json_output["questionIDs"];

        $ind = 0;

        foreach($json_obj["questionAnswer"] as $key => $value){
                
            $qID = $value["questionID"];

            if(isQuestionProgramming($qID, $progQuestionArray)){
                    
                $userAnswer = $value["userAnswer"];

                $URL = "http://afsaccess1.njit.edu/~vk255/Code_Testing/BackEnd/programQuestionTextQuery.php";

                $ch = curl_init($URL); 

                $postfields = array("questionID" => $qID);

                curl_setopt($ch, CURLOPT_URL, $URL);
                curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
                curl_setopt($ch, CURLOPT_POST, count($postfields));
                curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
                curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($postfields));

                $questionTextResp = curl_exec($ch);

                if(curl_errno($ch)){
                    die(json_encode(array("Error" => curl_error($ch))));
                }

                $questionJSON = json_decode($questionTextResp, true);
                $questionText = $questionJSON["questionText"];

                curl_close($ch);

                $matchArray = array();
                preg_match("/^.*named ([a-zA-Z0-9]*).*/", $questionText, $matchArray);
                $methodName = $matchArray[1];
                
                $URL = "http://afsaccess1.njit.edu/~vk255/Code_Testing/BackEnd/programQuestionQuery.php";

                $ch = curl_init($URL); 

                $postfields = array("questionID" => $qID);

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

                $json_question_io = json_decode($page, true);

                $lengthOfInput = count($json_question_io["Input"]);

                $index = 0;

                $wrapperText = file_get_contents("wrapper.java");
                $wrapperText = $wrapperText . " " . $userAnswer . " }";
                $getArgs = $json_question_io["Input"][0];

                $numOfArgs = count(explode(",", $getArgs));

                if($numOfArgs == 1){
                    $wrapperText = preg_replace("/function\(arg0, arg1, arg2\)/", "\"Compile Time Err\"", $wrapperText);
                } else {
                        $wrapperText = preg_replace("/function\(arg0\)/", "\"Compile Time Err\"", $wrapperText);

                        $argsArray = explode(",", $getArgs);

                        foreach($argsArray as $k => $v){
                            if(isInteger($v)){
                                $wrapperText = preg_replace("/String arg$k = args\[$k\]/", "int arg$k = Integer.parseInt(args[$k])", $wrapperText);
                            } else if(isDouble($v)){
                                $wrapperText = preg_replace("/String arg$k = args\[$k\]/", "int arg$k = Double.parseDouble(args[$k])", $wrapperText);
                            }
                        }
                }
                $wrapperText = preg_replace("/function/", "$methodName", $wrapperText);

                $tmpfname = tempnam("/tmp", "FOO");
                $tmpfnameExtJ = $tmpfname . ".java";
                $tmpfnameExtC = $tmpfname . ".class";
                
                $fname = str_replace("/tmp/", "", $tmpfname);
                $fnameExtC = $fname . ".class";
                
                $wrapperText = preg_replace("/(public[ ]+class)[ ]+[a-zA-Z_$][a-zA-Z0-9_$]*/", "$1 $fname", $wrapperText);
                
                $handle = fopen($tmpfnameExtJ, "w");
                fwrite($handle, $wrapperText);  
                fclose($handle);


                $handle2 = fopen("kmy.out", "w");
                fwrite($handle2, $wrapperText);
                fclose($handle2); 
                $errorMsg = array(); 

                $compileProg = exec("javac $tmpfnameExtJ 2>&1", $errorMsg);

                $compErr = false;

                if(strlen($compileProg) != 0){
                    $compErr = true;
                }

                $temp = "";
                
                while($index < $lengthOfInput){

                    $arg = $json_question_io["Input"][$index];
                    $actualAnswer = $json_question_io["Output"][$index];


                    $temp = $temp . "Input: " . $arg;

                    $arg = decodeCommaSep($arg);    
                    
                    if($compErr){
                        $userAnswer = $errorMsg[0];
                    } else {
                        $userAnswer = exec("cd /tmp/ && java $fname $arg 2>&1");
                    }

                    $temp = $temp . " Output: " . $userAnswer . ";";

                    $URL = "http://afsaccess1.njit.edu/~vk255/Code_Testing/BackEnd/insertProgramStudentAnswer.php";

                    $ch2 = curl_init($URL); 

                    $postfields = array(
                            "examID" => $examID,
                            "questionID" => $qID,                           
                            "userID" => $userID,
                            "userAnswer" => $userAnswer);

                    curl_setopt($ch2, CURLOPT_URL, $URL);
                    curl_setopt($ch2, CURLOPT_RETURNTRANSFER, 1);
                    curl_setopt($ch2, CURLOPT_POST, count($postfields));
                    curl_setopt($ch2, CURLOPT_FOLLOWLOCATION, true);
                    curl_setopt($ch2, CURLOPT_POSTFIELDS, http_build_query($postfields));

                    $insertProgramPage = curl_exec($ch2);

                    if(curl_errno($ch2)){
                        die(json_encode(array("Error" => curl_error($ch))));
                    }

                    curl_close($ch2); 
                    $index++;

                }

                $json_obj["questionAnswer"][$ind]["userAnswer"] = $temp;
            
                unlink($tmpfname);
                unlink($tmpfnameExtJ);
                unlink($tmpfnameExtC);
            }
            
            $ind++;
        }

        $final_json = json_encode($json_obj);

        $URL = "http://afsaccess1.njit.edu/~vk255/Code_Testing/BackEnd/studentSubmitQuery.php";

        $ch = curl_init($URL);

        curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "POST");                                                                     
        curl_setopt($ch, CURLOPT_HTTPHEADER, array('Accept: application/json', 'Content-Length: ' . strlen($final_json)));
        curl_setopt($ch, CURLOPT_POST, 1);
        curl_setopt($ch, CURLOPT_POSTFIELDS, $final_json);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

        $page = curl_exec($ch);

        if(curl_errno($ch)){
            die(json_encode(array("Error" => curl_error($ch))));
        }

        echo $page;
        curl_close($ch);
        
    } else {
        die(json_encode(array(
            "Error" => "Not posted"
        )));
    }

?>
