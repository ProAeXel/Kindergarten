<?php

require_once 'connection.php';
header('Content-Type: application/json ');



class GenerateCode{
	private $db;
	private $connection;

	function __construct(){
		$this->db = new DB_Connection();
		$this->connection = $this->db->get_connection();
	}


	public function addCode($code, $used)
	{
		$query = "INSERT INTO code(code, used) VALUES ('$code', '$used');";
		$is_inserted = mysqli_query($this->connection, $query);
		if($is_inserted == 1){
			$json['success'] = 'Code added.';
		}else{
			$json['error'] = 'Failed to add code.';
		}
		echo json_encode($json);
		mysqli_close($this->connection);
	}
}


$generateCode = new GenerateCode();
if(isset($_POST['code'], $_POST['used'])){
	$code = $_POST['code'];
	$used = $_POST['used'];
	if(!empty($code) && !empty($used)){
		$generateCode -> addCode($code, $used);
	}
}

?>