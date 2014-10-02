<?php

    if($_SERVER["REQUEST_METHOD"] == "POST"){ 
           
        $username = $_POST["username"];
        $password = $_POST["password"];

   	if($username == ''){
		echo "User Name is Empty";
	}
        $con = mysql_connect("sql.njit.edu", "caj9", "qEpO163u6") 
               or die("Unsucessful Database Connection");

        $selectdb = mysql_select_db("caj9", $con);

        $sql_login = "SELECT * FROM Users WHERE UserName = '" . urldecode($username) . "' AND UserPassword = '"
	. urldecode($password) . "'";

        $result = mysql_query($sql_login);

        if(!$result){
            die("Error: Invalid query"); 
        } 

        $num_rows = mysql_num_rows($result);

        if($num_rows == 1){
            echo "{ \"Backend_Login\": \"Success\"";
        } else {
            echo "{ \"Backend_Login\": \"Failed\" }";
        }        
    } else {
        die("Error: Didn't post to page");
    }
?>
