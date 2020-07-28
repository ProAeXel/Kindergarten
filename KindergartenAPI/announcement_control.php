<?php
require_once 'connection.php';
header('Content-Type: application/json ');



class Announcement{

	private $db;
		private $connection;

	function __construct(){
		$this->db = new DB_Connection();
		$this->connection = $this->db->get_connection();
	}


	public function addAnnouncement($title, $text)
	{
		$query = "INSERT INTO announcements(title, atext) VALUES ('$title', '$text');";
		$is_inserted = mysqli_query($this->connection, $query);
		if($is_inserted == 1){
			$json['success'] = 'Announcement added.';
		}else{
			$json['error'] = 'Failed to add the announcement';
		}
		echo json_encode($json);
		mysqli_close($this->connection);
	}
	public function getAnnouncements(){
		$query = "SELECT * FROM announcements;";
		$result = mysqli_query($this->connection, $query);
		while($row = mysqli_fetch_assoc($result))
		{
			$rows[] = $row;
		}
		echo json_encode($rows);
		mysqli_close($this->connection);
	}
	public function getAnnouncementById($id){
		$query = "SELECT * FROM announcements WHERE id = '$id';";
		$result = mysqli_query($this->connection, $query);
		$row = mysqli_fetch_assoc($result);
		print json_encode($row);
		mysqli_close($this->connection);
	}
	public function getLastAnnouncements(){
		$query = "SELECT * FROM announcements ORDER BY id DESC LIMIT 3;";
		$result = mysqli_query($this->connection, $query);
		while($row = mysqli_fetch_assoc($result))
		{
			$rows[] = $row;
		}
		echo json_encode($rows);
		mysqli_close($this->connection);
	}




}

$announcement = new Announcement();
if(isset($_POST['title'], $_POST['atext'])){
$title = $_POST['title'];
$atext = $_POST['atext'];
	if(!empty($title) && !empty($announcement)){
			$announcement -> addAnnouncement($title, $atext);
		}	
}
else if(isset($_POST['check'])){
	$number = $_POST['check'];
	if($number == 1)
		$announcement -> getAnnouncements();
		else if($number == 3)
			$announcement -> getLastAnnouncements();

}else if(isset($_POST['id'])){
	$id = $_POST['id'];
	$announcement -> getAnnouncementById($id);
}


?>