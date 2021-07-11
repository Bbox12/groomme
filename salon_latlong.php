<?php
 header('Content-Type: application/json');


require_once 'DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);

$target_path = "salon_images/";

error_reporting(-1);
ini_set('display_errors', 'On');
 
if (isset($_POST['lat']) && isset($_POST['mobile']) ){
 
    // receiving the post params
    $lat = $_POST['lat'];
    $mobile = $_POST['mobile'];
    
    $long = $_POST['long'];
    $long=test_input($long);
    $lat=test_input($lat);
    $mobile=test_input($mobile);
   
  $locality = $_POST['locality'];
    $locality=test_input($locality);
      $city = $_POST['city'];
    $city=test_input($city);
    
        $user = $db->storeServiceLatLong($mobile,$lat,$long,$locality,$city);
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
 else {
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


?>