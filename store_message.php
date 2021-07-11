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

if (isset($_POST['name'])){

 
$name=$_POST["name"];
$name=test_input($name);
$msg=$_POST["msg"];
$msg=test_input($msg);
$otp=$_POST["otp"];
$otp=test_input($otp);
$who=$_POST["who"];
$who=test_input($who);



     $user = $db->add_message($otp,$name,$msg,$who);

        if ($user) {   
        $response["error"] = false;
    } else  {
       
        $response["error"] = true;

    }

} else {
    // File parameter is missing
    $response['error'] = true;
}
     echo json_encode($response); 
       


function test_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}
?>