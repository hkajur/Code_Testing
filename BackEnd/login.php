<?php

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
                        "Backend_Login" =>  "MySQL_Connection_Err"
                      )));
                        
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
  
        $array = array();

        // Check if the query is a valid MySQL query
        // If the query is invalid, it will return invalid JSON
        // This will help with the debugging in future
        
        if(!$result){
            $array["Backend_Login"] = "Invalid query";
            die(json_encode($array));
        }

        $row = mysql_fetch_assoc($result);

        $array["userType"] = $row["UserType"];

        // Get the num of rows that the result produced
        $num_rows = mysql_num_rows($result);

        // If the rows are 1 then return Success JSON
        // Otherwise return Failed JSON
        
        if($num_rows == 1){
            $array["Backend_Login"] = "Success";
            echo json_encode($array);
        }
        else {
            $array["Backend_Login"] = "Failed";
            echo json_encode($array);
        }
        

        // Close the connection to MySQL
        mysql_close($con);

    } else {
            die(json_encode(array("Backend_Login" => "Error")));
            
    }
?>
