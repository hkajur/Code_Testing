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
               or die("{ \"Backend_Login\": \"MySQL_Connection_Err\" }");

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

        // Check if the query is a valid MySQL query
        // If the query is invalid, it will return invalid JSON
        // This will help with the debugging in future
        
        if(!$result)
            die("{ \"Backend_Login\": { \"Invalid query\" }"); 

        // Get the num of rows that the result produced
        $num_rows = mysql_num_rows($result);

        // If the rows are 1 then return Success JSON
        // Otherwise return Failed JSON
        
        if($num_rows == 1)
            echo "{ \"Backend_Login\": \"Success\" }";
        else 
            echo "{ \"Backend_Login\": \"Failed\" }";
        

        // Close the connection to MySQL
        mysql_close($con);

    } else {
        die("Error: Didn't post to page");
    }
?>
