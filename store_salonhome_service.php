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
 
if (isset($_POST["shprice"]) && isset($_POST["hprice"])) {
   
  
    $mobile = isset($_POST['mobile']) ? $_POST['mobile'] : '';
    $service = isset($_POST['service']) ? $_POST['service'] : '';



    $sdescription = isset($_POST['shdetail']) ? $_POST['shdetail'] : '';
    $sprice=isset($_POST['shprice'])? $_POST['shprice'] :'';
    $stime=isset($_POST['shtime'])? $_POST['shtime'] :'';
  $sdescription=test_input($sdescription);
  $sprice=test_input($sprice);
  $stime=test_input($stime);


   $hdescription = isset($_POST['hdetail']) ? $_POST['hdetail'] : '';
    $hprice=isset($_POST['hprice'])? $_POST['hprice'] :'';
    $htime=isset($_POST['htime'])? $_POST['htime'] :'';
  $hdescription=test_input($hdescription);
  $hprice=test_input($hprice);
  $htime=test_input($htime);



    $IP=isset($_POST['IP'])? $_POST['IP'] :'';
    $from=isset($_POST['from'])? $_POST['from'] :'';
    
  $from=test_input($from);
  $IP=test_input($IP);
  $mobile=test_input($mobile);
  $service=test_input($service);


  

  $result = $db->storeBothService($mobile,$service,$sdescription,$sprice,$stime,$hdescription,$hprice,$htime,$IP,$from);


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