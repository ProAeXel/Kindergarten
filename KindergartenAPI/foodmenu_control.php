<?php 
require_once 'connection.php';
header('Content-Type: application/json ');

class Food{

	private $db;
		private $connection;

	function __construct(){
		$this->db = new DB_Connection();
		$this->connection = $this->db->get_connection();
	}



	public function addMenu($snack, $maincourse, $desert, $menunumber)
	{
		if($menunumber == 1)
		{
			$query = "DELETE FROM menus WHERE menunumber = '$menunumber';";
		}
		else if($menunumber == 2)
		{
			$query = "DELETE FROM menus WHERE menunumber = '$menunumber';";
		}
		mysqli_query($this->connection, $query);
		$query = "INSERT INTO menus(snack, maincourse, desert, menunumber) VALUES ('$snack', '$maincourse', '$desert', '$menunumber');";
		$is_inserted = mysqli_query($this->connection, $query);
		if($is_inserted == 1){
			$json['success'] = 'Menu added';
		}else{
			$json['error'] = 'Failed to add the menu';
		}
		print json_encode($json);
		mysqli_close($this->connection);
	}
	public function getMenus()
	{
		$query = "SELECT * FROM menus;";
		$result = mysqli_query($this->connection, $query);
		while($row = mysqli_fetch_assoc($result))
		{
			$rows[] = $row;
		}
		print json_encode($rows);
		mysqli_close($this->connection);
	}
	public function chosenMenu($menunumber)
	{
		$query = "UPDATE menus SET amount=amount+1 WHERE menunumber = '$menunumber';";
		$is_inserted = mysqli_query($this->connection, $query);
		if($is_inserted == 1){
			$json['success'] = 'Menu selected successfully';
		}else{
			$json['error'] = 'Failed to select menu';
		}
		print json_encode($json);
		mysqli_close($this->connection);
	}


}

$obj = new Food();

if(isset($_POST['snack'], $_POST['maincourse'], $_POST['desert'], $_POST['menunumber']))
{ 
	$snack = $_POST['snack'];
	$maincourse = $_POST['maincourse'];
	$desert = $_POST['desert'];
	$menunumber = $_POST['menunumber'];
	if(!empty($snack) && !empty($maincourse) && !empty($desert) && !empty($menunumber)){
		$obj -> addMenu($snack,$maincourse,$desert,$menunumber);
	}
	
}else if(isset($_POST['check'])){
	$obj -> getMenus();
}else if(isset($_POST['menunumber'])){
	$menunumber = $_POST['menunumber'];
	if(!empty($menunumber)){
		$obj -> chosenMenu($menunumber);
	}
	
}

?>