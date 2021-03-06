<?php
 header('Content-Type: application/json');


require_once 'DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);


error_reporting(-1);
ini_set('display_errors', 'On');
 
if (isset($_POST['service']) && isset($_POST['mobile']) ){
 
    // receiving the post params
    $service = $_POST['service'];
    $mobile = $_POST['mobile'];
    
    

    $service=test_input($service);
    $mobile=test_input($mobile);
   

    
        $user = $db->storeServiceLocation($mobile,$service);
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