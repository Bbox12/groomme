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
 
if (isset($_POST["mobile"]) && isset($_POST["from"])) {
   
  
    $mobile = isset($_POST['mobile']) ? $_POST['mobile'] : '';
   

   


    $from=isset($_POST['from'])? $_POST['from'] :'';
    
  $from=test_input($from);

  $mobile=test_input($mobile);



  

  $result = $db->DeleteServiceID($mobile,$from);


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