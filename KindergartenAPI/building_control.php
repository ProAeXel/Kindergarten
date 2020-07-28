<?php

require_once 'connection.php';
header('Content-Type: application/json');

class Building{

	private $db;
	private $connection;

	function __construct(){
		$this->db = new DB_Connection();
		$this->connection = $this->db->get_connection();
	}

	public function get_buildings(){
		$query = "select * from buildings;";
		$result = mysqli_query($this->connection, $query);
		$rows = array();
		while($row = mysqli_fetch_assoc($result)){
			$rows[] = $row;
		}
		print json_encode($rows);
		mysqli_close($this->connection);
	}
}

$building = new Building();
$building -> get_buildings();

?>