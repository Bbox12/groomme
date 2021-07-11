<?php
 header('Content-Type: application/json');


require_once 'DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
date_default_timezone_set(TIMEZONE);
        $hour=date("H:i");
        $date=date("Y-m-d");


        var_dump($_POST);

if (isset($_POST['order'])){
 
   $order =isset($_POST['order']) ? $_POST['order'] : '';
   $address = isset($_POST['address']) ? $_POST['address'] : '';
   $house=isset($_POST['house']) ? $_POST['house'] : '';
   $landmark=isset($_POST['landmark']) ? $_POST['landmark'] : '';
   $latitude=isset($_POST['latitude']) ? $_POST['latitude'] : '';
   $longitude=isset($_POST['longitude']) ? $_POST['longitude'] : '';


   
   

   

    $order=test_input($order);
    $house=test_input($house);
    $address=test_input($address);
    $landmark=test_input($landmark);
    $latitude=test_input($latitude);
    $longitude=test_input($longitude);
     
         $user = $db-> add_order_address($order,$house,$address,$landmark,$latitude,$longitude);
      

        if ($user) {
            // user stored successfully
            $response["error"] = FALSE;
      
            echo json_encode($response);
        } else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Failed!";
            echo json_encode($response);
        }
    
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters missing!";
    echo json_encode($response);
}

function test_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}
?>