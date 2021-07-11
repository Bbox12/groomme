<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);
 header('Content-Type: application/json');


require_once 'DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);

var_dump($_POST);

if (isset($_POST['mobile'])){
 
    $name= $_POST['name'];
    $bday= $_POST['bday'];
    $mobile = $_POST['mobile'];
    $gender = $_POST['gender'];
    $email= $_POST['email'];
 
    $mobile=test_input($mobile);
    $name=test_input($name);
    $gender=test_input($gender);
    $email=test_input($email);
    $bday=test_input($bday);

      $ID= $_POST['ID'];
    $ID=test_input($ID);

        $refer=generateRandomString();
            $refer=test_input($refer);

        $IP= $_POST['IP'];
 
    $IP=test_input($IP);
   
        $image = $_FILES['image']['name'];
        $image=test_input($image);

       
        $target_path = "user/";
        $target_path1 = $target_path . basename($_FILES['image']['name']);

        try {
        // Throws exception incase file is not being moved
        if (!move_uploaded_file($_FILES['image']['tmp_name'], $target_path1)) {
            // make error flag true
            $response['error'] = true;
            $response["message"] = "error!Failed";
        }else{

            $res = $db->upadateUser($ID,$name,$email,$mobile,$gender,$bday,$image,$IP,$refer);

        if ($res) {   
        $response["error"] = false;
        $response["message"] = "Success";
    } else  {
       
        $response["error"] = true;
        $response["message"] = "error";
    }
          
                
        } 
        }
 
      catch (Exception $e) {
        // Exception occurred. Make error flag true
        $response['error'] = true;
        $response['message'] = $e->getMessage();
    }

   

} else {
    // File parameter is missing
    $response['error'] = true;
    $response['message'] = 'Not received any file!';
}
     echo json_encode($response); 
       


function test_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}

function generateRandomString($length = 6) {
    $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    $charactersLength = strlen($characters);
    $randomString = '';
    for ($i = 0; $i < $length; $i++) {
        $randomString .= $characters[rand(0, $charactersLength - 1)];
    }
    return $randomString;
}
?>