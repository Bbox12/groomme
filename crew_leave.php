<?php
 
// Path to move uploaded files

require_once 'DB_Functions.php';
$db = new DB_Functions();



// array for final json respone
$response = array();

 $response = array("error" => FALSE);
 
// getting server ip address
 $server_ip="139.59.38.160";

 var_dump($_POST);
 
if (isset($_POST['ID'])) {
   

       $ID=isset($_POST['ID'])? $_POST['ID'] :'';
           $mobile = isset($_POST['mobile']) ? $_POST['mobile'] : '';
             $mobile=test_input($mobile);

                  $available = isset($_POST['available']) ? $_POST['available'] : '';
             $available=test_input($available);
             
      $ID=test_input($ID);

         $result = $db->storeCrewLeave($mobile,$available,$ID);
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