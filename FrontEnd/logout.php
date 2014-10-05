<?php
    session_start();
    unset($_SESSION["user"]);
    unset($_SESSION["usertype"]);
    session_destroy();
    header("Location: index.php");
?>
