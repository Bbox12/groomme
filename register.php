<?php
 header('Content-Type: application/json');


require_once 'DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);

error_reporting(-1);
ini_set('display_errors', 'On');
 
if (isset($_POST['name']) && isset($_POST['email']) && isset($_POST['parlourname'])&& isset($_POST['address'] )&& isset($_POST['mobile']) && isset($_POST['pin'])&& isset($_POST['latitude']) && isset($_POST['longitude'])){
 
    // receiving the post params
    $name = $_POST['name'];
    $email = $_POST['email'];
    $address=$_POST['address'];
    $mobile=$_POST['mobile'];
    $pin=$_POST['pin'];
    $longitude=$_POST['longitude'];
    $latitude=$_POST['latitude'];
    

    $name=test_input($name);
    $email=test_input($email);
    $address=test_c($address);
    $mobile=test_input($mobile);
    $pin=test_input($pin);
    $longitude=test_input($longitude);
    $latitude=test_input($latitude);

    $parlourname=$_POST['parlourname'];
    $parlourname=test_input($parlourname);
 
    // check if user is already existed with the same email
    if ($db->isUserExisted($email)) {
        // user already existed
        $response["error"] = TRUE;
        $response["error_msg"] = "User already existed with " . $email;
        echo json_encode($response);
    } else {
        // create a new user
        $user = $db->storeUser($name,$email,$address,$mobile,$pin,$latitude,$longitude,$parlourname);
        if ($user) {
            // user stored successfully
            $response["error"] = FALSE;
           
            
            echo json_encode($response);
        } else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in registration!";
            echo json_encode($response);
        }
    }
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters (name, email or password) is missing!";
    echo json_encode($response);
}

function test_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}

function test_c($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  $data=strtoupper($data);
  return $data;
}
?>