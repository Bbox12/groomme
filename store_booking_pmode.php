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
   $payment = isset($_POST['payment']) ? $_POST['payment'] : '';



   
   

   

    $order=test_input($order);
    $payment=test_input($payment);

     
         $user = $db-> add_order_payment($order,$payment);
      

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