<?php
 
	require_once('dbConnect.php');
	
	$sql = "select id from images";
	
	$res = mysqli_query($con,$sql);
	
	
	$result = array();
	
	$url = "http://simplifiedcoding.16mb.com/ImageUpload/getImage.php?id=";
	while($row = mysqli_fetch_array($res)){
		array_push($result,array('url'=>$url.$row['id']));
	}
	
	echo json_encode(array("result"=>$result));
	
	mysqli_close($con);