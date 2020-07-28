<?php

require_once 'connection.php';
header('Content-Type: application/json ');

	class User{

		private $db;
		private $connection;

		function __construct(){
			$this->db = new DB_Connection();
			$this->connection = $this->db->get_connection();
		}

		public function does_user_exists($email, $password){

			$query = "select status,fname,lname,scholarship from users where email = '$email' and password = '$password';";
			$result = mysqli_query($this->connection, $query);
			if(mysqli_num_rows($result) > 0){
				$row = mysqli_fetch_assoc($result);
				print json_encode($row);
			}
			mysqli_close($this->connection);
		}


		public function register_user($email, $password, $code, $firstname, $lastname, $phone, $address){
			if($code[0] == '1' || $code[0] == 'A')
			{
				$scholarship = '1';
			}else if($code[0] == '2' || $code[0] == 'B')
			{
				$scholarship = '2';
			}else if($code[0] == '3' || $code[0] == 'C')
			{
				$scholarship = '3';
			}
			$dbcodequery = "SELECT Code FROM code WHERE Code = '$code' AND Used = 'unused';";
			$result = mysqli_query($this->connection, $dbcodequery);
			$dbcode = mysqli_fetch_row($result);
			
			if($code == $dbcode[0]){
				$query = "INSERT INTO users(email, password, status, scholarship, fname, lname, phone, address) VALUES ('$email', '$password', 'user', '$scholarship', '$firstname', '$lastname',
				'$phone', '$address'); ";
				$codequery = "UPDATE code SET Used = 'used';";
				mysqli_query($this->connection, $codequery);
				$is_inserted = mysqli_query($this->connection, $query);
				if($is_inserted == 1){
					$json['success'] = 'Account created';
					echo json_encode($json);
				}
			
			}else{
				$json['failed'] = 'Invalid code. Contact our system admin.';
				echo json_encode($json);
			}
			mysqli_close($this->connection);
		}


		public function get_rank($email)
		{
			$query = "select status from users where email='$email';";
			$result = mysqli_query($this->connection, $query);
			$row = mysqli_fetch_assoc($result);
			print json_encode($row);
			mysqli_close($this->connection);
		}

		public function getUserDetails()
		{
			$query = "SELECT fname,lname,phone,email FROM users;";
			$result = mysqli_query($this->connection, $query);
			while($row = mysqli_fetch_assoc($result))
			{
				$rows[] = $row;
			}
			echo json_encode($rows);
			mysqli_close($this->connection);
		}

		public function getEducatorDetails()
		{
			$query = 'SELECT u.email, u.fname,u.lname,e.description, e.group FROM users u, educators e WHERE u.id = e.educatorId;';
			$result = mysqli_query($this->connection, $query);
			while($row = mysqli_fetch_assoc($result))
			{
				$rows[] = $row;
			}
			echo json_encode($rows);
			mysqli_close($this->connection);
		}

		public function getUserInformation($email)
		{
			$query = "SELECT * FROM users WHERE email = '$email';";
			$result = mysqli_query($this->connection, $query);
			while($row = mysqli_fetch_assoc($result))
			{
				$rows[] = $row;
			}
			echo json_encode($rows);
			mysqli_close($this->connection);
		}

		public function updateInformation($fName, $lName, $phone, $address, $email)
		{
			$query = "UPDATE users SET fname = '$fName', lname = '$lName', phone = '$phone', address = '$address' WHERE email = '$email';";
			$is_updated = mysqli_query($this->connection, $query);
			if($is_updated == 1){
				$json['success'] = 'Profile has been updated';
			}
			print json_encode($json);
			mysqli_close($this->connection);
		}
		public function changePassword($currentpassword, $password, $email)
		{
			$encrypted_password = md5($currentpassword);
			$query = "SELECT password FROM users WHERE email = '$email';";
			$result = mysqli_query($this->connection, $query);
			$row = mysqli_fetch_row($result);
			if($row[0] == $encrypted_password)
			{
				$newencrypted_password = md5($password);
				$query = "UPDATE users SET password = '$newencrypted_password' WHERE email = '$email';";
				$is_updated = mysqli_query($this->connection, $query);
				if($is_updated == 1){
					$json['success'] = "Password changed.";
				}
			}else{
				$json['failed'] = "Your current password does not match.";
			}
			print json_encode($json);
			mysqli_close($this->connection);
		}

	}


	$user = new User();
	if(isset($_POST['email'], $_POST['password'])){
		$email = $_POST['email'];
		$password = $_POST['password'];

		if(!empty($email) && !empty($password)){

			$encrypted_password = md5($password);
			$user -> does_user_exists($email, $encrypted_password);

		}
		else{

			echo json_encode("You must fill both fields");

		}
	}
	else if(isset($_POST['emailR'], $_POST['passwordR'], $_POST['confirmedpassword'], $_POST['code'], $_POST['fname'], $_POST['lname'], $_POST['phone'], $_POST['address'])){
		$email = $_POST['emailR'];
		$password = $_POST['passwordR'];
		$confirmedpassword = $_POST['confirmedpassword'];
		$code = $_POST['code'];
		$firstname = $_POST['fname'];
		$lastname = $_POST['lname'];
		$phone = $_POST['phone'];
		$address = $_POST['address'];

		if(!empty($email) && !empty($password) && !empty($confirmedpassword) && !empty($code) && !empty($phone) && !empty($address) && !empty($firstname) && !empty($lastname)){
			if($password == $confirmedpassword){
			$encrypted_password = md5($password);
			$user -> register_user($email, $encrypted_password, $code, $firstname, $lastname, $phone, $address);
			}
		}
		else{
			echo json_encode("Please check your fields");
		}
	}
	else if(isset($_POST['email']))
	{
		$email = $_POST['email'];
		if(!empty($email))
		{
			$user -> get_rank($email);
		}
	}
	else if(isset($_POST['rank']))
	{
		$rank = $_POST['rank'];
		if(!empty($rank))
		{
			if($rank == "educator"){
				$user -> getEducatorDetails();
			}else if($rank == "user"){
				$user -> getUserDetails();
			}
			
		}
	}
	else if(isset($_POST['infoemail'],$_POST['information']))
	{
		$email = $_POST['infoemail'];
		$information = $_POST['information'];
		if(!empty($email) && $information == 1)
		{
			$user -> getUserInformation($email);
		}
	}
	else if(isset($_POST['emailforchangepassword'], $_POST['currentpassword'], $_POST['newpassword']))
	{
		$email = $_POST['emailforchangepassword'];
		$currentpassword = $_POST['currentpassword'];
		$newpassword = $_POST['newpassword'];
		$user -> changePassword($currentpassword, $newpassword, $email);
	}
	else if(isset($_POST['emailforprofileedit'], $_POST['firstname'], $_POST['lastname'], $_POST['phone'], $_POST['address']))
	{
		$email = $_POST['emailforprofileedit'];
		$fName = $_POST['firstname'];
		$lName = $_POST['lastname'];
		$phone = $_POST['phone'];
		$address = $_POST['address'];
	
		$user -> updateInformation($fName, $lName, $phone, $address, $email);
			
	}
	


?>