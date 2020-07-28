<?php

require_once 'connection.php';
header('Content-Type: application/json ');

    class UploadImage{
    
        function __construct(){
            $this->db = new DB_Connection();
            $this->connection = $this->db->get_connection();
        }
        
        public function downloadProfileImage($email){
            $query = "SELECT profilepicture FROM users WHERE email = '$email';";
            $result = mysqli_query($this->connection, $query);
			$row = mysqli_fetch_assoc($result);
			print json_encode($row);
			mysqli_close($this->connection);
        }
        
        public function getGallery(){
            $query = "SELECT name FROM gallery;";
            $result = mysqli_query($this->connection, $query);
            while($row = mysqli_fetch_assoc($result)){
                $rows[] = $row;
            }
            echo json_encode($rows);
            mysqli_close($this->connection);
        }
        
        public function uploadProfileImage($image, $email){
            $target_dir = "pictures";
            /*$image = $_POST["image"];
            $email = $_POST["email"];*/
            
            if(!file_exists($target_dir)){
            	mkdir($target_dir, 0777, true);
            }
            
            //set random image filename with time
            $imgname = rand(). "_". time() . ".jpeg";
            $target_dir = $target_dir ."/". $imgname;
            if(file_put_contents($target_dir, base64_decode($image))){
            	echo json_encode(["Message" => "The file has been uploaded.","Status" => "OK"]);
            	
            	$query = "UPDATE users SET profilepicture = '$imgname' WHERE email = '$email';";
            	mysqli_query($this->connection, $query);
            	
            	
            }else{
            	echo json_encode(["Message" => "Sorry, there was an error uploading your file.","Status" => "Error"]);
            }
        }
    }
    
    $uploadImage = new UploadImage();
    if(isset($_POST['email'], $_POST['image'])){
        $email = $_POST['email'];
        $image = $_POST['image'];
        if(!empty($email) && !empty($image)){
            $uploadImage -> uploadProfileImage($image, $email);
        }
    }
    else if(isset($_POST['emailP'])){
        $email = $_POST['emailP'];
        if(!empty($email)){
            $uploadImage -> downloadProfileImage($email);
        }
    }
    else if(isset($_POST['getall'])){
        $get = $_POST['getall'];
        if(!empty($get)){
            $uploadImage -> getGallery();
        }
    }

?>