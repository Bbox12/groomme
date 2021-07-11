<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);
 header('Content-Type: application/json');
 session_start();

require_once 'DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);


var_dump($_POST);

if (isset($_POST['category'])){

$User= $_SESSION["email"];
$IP= $_SERVER['REMOTE_ADDR'];
$User= test_input($User);
$IP= test_input($IP);


$msg= $_POST['category'];
$msg= test_input($msg);


 if(!empty($_FILES['photo']['name'])){
  $Photo= $_FILES['photo']['name'];
}





$Photo= test_input($Photo);
echo $Photo;

$target_path = "category/";
        $target_path1 = $target_path . basename($_FILES['photo']['name']);

        try {
        // Throws exception incase file is not being moved
        if (!move_uploaded_file($_FILES['photo']['tmp_name'], $target_path1)) {
            // make error flag true
            $response['error'] = true;
            $response['message'] = 'Could not move the file!';
        }
      }  catch (Exception $e) {category
        // Exception occurred. Make error flag true
        $response['error'] = true;
        $response['message'] = $e->getMessage();
    }


   $user = $db->sentCategory($msg,$Photo,$User,$IP);
         if ($user) {   
        $response["error"] = false;
        header('Location: http://139.59.38.160/Groom/Dashboard/admin.php'); 
    } else  {
       
        $response["error"] = true;
        $response["message"] = "error";
              header('Location: http://139.59.38.160/Groom/Dashboard/admin.php'); 
    }



} else {
    // File parameter is missing
    $response['error'] = true;
    $response['message'] = 'Not received any file!';
          header('Location: http://139.59.38.160/Groom/Dashboard/admin.php'); 
}
     echo json_encode($response); 
       


function test_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}
?>