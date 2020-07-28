<?php

$target_dir = "pictures/";
$image = $_POST["image"];

$imgcode = file_get_contents($target_dir, base64_decode($image));
echo json_encode($imgcode);

?>