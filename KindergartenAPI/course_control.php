<?php

require_once 'connection.php';
header('Content-Type: application/json');


class Course{

	private $db;
	private $connection;

	function __construct(){
		$this->db = new DB_Connection();
		$this->connection = $this->db->get_connection();
	}

	public function get_all_courses(){
		$query = "select * from courses;";
		$result = mysqli_query($this->connection, $query);
		$rows = array();
		while($row = mysqli_fetch_assoc($result)){
			$rows[] = $row;
		}
		print json_encode($rows);
		mysqli_close($this->connection);
	}

	public function get_courses_by_year($year){
		if($year > 0){
			$query = "select * from courses where Year = '$year';";
			$result = mysqli_query($this->connection, $query);
		}else{
			$query = "select * from courses;";
			$result = mysqli_query($this->connection, $query);
		}
		
		$rows = array();
		while($row = mysqli_fetch_assoc($result)){
			$rows[] = $row;
		}
		print json_encode($rows);
		mysqli_close($this->connection);
	}

}


$course = new Course();
if(isset($_POST['Year'])){
	$year = $_POST['Year'];

	if(!empty($year)){
		$course -> get_courses_by_year($year);
	}
}


?> 