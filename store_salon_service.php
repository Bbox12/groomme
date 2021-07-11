<?php
 
// Path to move uploaded files

require_once 'DB_Functions.php';
$db = new DB_Functions();



// array for final json respone
$response = array();

 $response = array("error" => FALSE);
 
// getting server ip address
 $server_ip="139.59.38.160";
 
if (isset($_POST["price"])) {
   
    $description = isset($_POST['detail']) ? $_POST['detail'] : '';
    $mobile = isset($_POST['mobile']) ? $_POST['mobile'] : '';
    $service = isset($_POST['service']) ? $_POST['service'] : '';
    $price=isset($_POST['price'])? $_POST['price'] :'';
    $time=isset($_POST['time'])? $_POST['time'] :'';
    $IP=isset($_POST['IP'])? $_POST['IP'] :'';
    $from=isset($_POST['from'])? $_POST['from'] :'';
    
  $from=test_input($from);
  $IP=test_input($IP);
  $mobile=test_input($mobile);
  $description=test_input($description);
  $service=test_input($service);
  $price=test_input($price);
  $time=test_input($time);
  


  $result = $db->storeSalonService($mobile,$service,$description,$price,$time,$IP,$from);


  if ($result) {
            $response["error"] = FALSE;
        } else {
            $response["error"] = TRUE;
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
  $data=strtoupper($data);
  return $data;
}

function test_($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}
?>