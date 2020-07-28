<?php

require_once 'connection.php';
header('Content-Type: application/json ');

	class About{

		private $db;
		private $connection;

		function __construct(){
			$this->db = new DB_Connection();
			$this->connection = $this->db->get_connection();
		}
		
		public function getInfo(){
		    $query = "SELECT * FROM about;";
		    $result = mysqli_query($this->connection, $query);
		    if(mysqli_num_rows($result) > 0){
				$row = mysqli_fetch_assoc($result);
				print json_encode($row);
			}
			mysqli_close($this->connection);
		}
		public function updateAbout($description, $address, $phone, $fax)
    	{
    		$query = "UPDATE about SET description = '$description', address = '$address', phone = '$phone', fax = '$fax';";
    		$is_inserted = mysqli_query($this->connection, $query);
    		if($is_inserted == 1){
    			$json['success'] = 'Successfully updated.';
    		}else{
    			$json['error'] = 'Failed to update.';
    		}
    		echo json_encode($json);
    		mysqli_close($this->connection);
    	}
	}
	
	
	
	$info = new About();
	if(isset($_POST['getinfo']))
	{
		$getinformation = $_POST['getinfo'];
		if(!empty($getinformation))
		{
			$info -> getInfo();
		}
	}
	else if(isset($_POST['description'],$_POST['address'], $_POST['phone'], $_POST['fax'])){
	    $description = $_POST['description'];
	    $address = $_POST['address'];
	    $phone = $_POST['phone'];
	    $fax = $_POST['fax'];
	    if(!empty($description) && !empty($address) && !empty($phone) && !empty($fax)){
	        $info -> updateAbout($description,$address,$phone,$fax);
	    }
	}
?>