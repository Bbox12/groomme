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

if (isset($_POST['mobile']) && isset($_POST['data'] )&& isset($_POST['total'] )){
 
   $mobile =isset($_POST['mobile']) ? $_POST['mobile'] : '';
   $total = isset($_POST['total']) ? $_POST['total'] : '';
   $salon=isset($_POST['salon']) ? $_POST['salon'] : '';
   $data=isset($_POST['data']) ? $_POST['data'] : '';
   $specialist=isset($_POST['specialist']) ? $_POST['specialist'] : '';
   $date=isset($_POST['date']) ? $_POST['date'] : '';
   $discount=isset($_POST['discount']) ? $_POST['discount'] : '';
   $slot=isset($_POST['slot']) ? $_POST['slot'] : '';
   $NoofItems=isset($_POST['NoofItems']) ? $_POST['NoofItems'] : '';
   $NoofItems=test_input($NoofItems);
   $home=isset($_POST['home']) ? $_POST['home'] : '';
   $home=test_input($home);

   
   

   
    $slot=test_input($slot);
    $mobile=test_input($mobile);
    $salon=test_input($salon);
    $total=test_input($total);

    $data=test_input($data);
    $specialist=test_input($specialist);
    $date=test_input($date);
       $discount=test_input($discount);
       $slot=test_input($slot);
      
         $user = $db-> add_order_price($mobile,$data,$total,$salon,$specialist,$date,$discount,$slot,$NoofItems,$home);
      

        if ($user) {
            // user stored successfully
            $response["error"] = FALSE;
            $response["user"]["OrderID"] = $user["OrderID"];
            $response["user"]["IDsalon"] = $user["IDsalon"];
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