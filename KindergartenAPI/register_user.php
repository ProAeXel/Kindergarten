<?php

if ($_SERVER['REQUEST_METHOD'] =='POST'){

    
    $email = $_POST['email'];
    $password = $_POST['password'];
    $cpassword = $_POST['confirmedpassword'];
    $code = $_POST['code'];

    $password = md5($password);

    require_once 'connection.php';

    $sql = "INSERT INTO users (email, password, status) VALUES ('$name', '$email', '1')";

    if ( mysqli_query($conn, $sql) ) {
        $result["success"] = "1";
        $result["message"] = "success";

        echo json_encode($result);
        mysqli_close($conn);

    } else {

        $result["success"] = "0";
        $result["message"] = "error";

        echo json_encode($result);
        mysqli_close($conn);
    }
}

?>