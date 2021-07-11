<?php
 header('Content-Type: application/json');


require_once 'DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);

$target_path = "salon_images/";

error_reporting(-1);
ini_set('display_errors', 'On');



 //var_dump($_POST);
if ( isset($_POST['mobile']) && isset($_POST['password'])){
 

    $mobile=$_POST['mobile'];
    $password=$_POST['password'];
  
    $mobile=test_input($mobile);
    $password=test_input($password);

    $role=$_POST['role'];
     $role=test_input($role);
   
        $user = $db->signin($role,$mobile,$password);
        if ($user) {
            $response["user"]["role"] =$user["Role"];
            if($user["Role"]==1){
                $response["user"]["Name"]=$user["Name"];
                      $response["user"]["ID"]=$user["ID"];
                         $response["user"]["Photo"]='http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'];
              }else{
                       $response["user"]["Name"]=$user["parlour_name"];
                             $response["user"]["ID"]=$user["ID"];
                            $response["user"]["Photo"]='http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'];
                            
              }
     
            $response["user"]["ID"]=$user["ID"];
            echo json_encode($response);
        }else{
            $response["user"]["role"] =0;
            echo json_encode($response);
        }

} else {
    $response["error"] = 0;
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