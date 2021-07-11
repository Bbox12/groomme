<?php
 header('Content-Type: application/json');


require_once 'DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);

$target_path = "salon_images/";

error_reporting(-1);
ini_set('display_errors', 'On');



 
if ( isset($_POST['role']) && isset($_POST['password'])){
 
    // receiving the post params
    $name = $_POST['username'];
    $email = $_POST['email'];
    $mobile=$_POST['mobile'];
    $password=$_POST['password'];
    $role=$_POST['role'];

    $role=test_input($role);

    $name=test_input($name);
    $email=test_input($email);
    $mobile=test_input($mobile);
    $password=test_input($password);
   
      $file_path=$_FILES['image']['name'];
       $target_path0 = $target_path . basename($_FILES["image"]["name"]);
 

       
      try {
        if (!move_uploaded_file($_FILES['image']['tmp_name'], $target_path0)) {
            // make error flag true
            $response['error'] = true;
            $response['message'] = 'Could not move the file!';
        }
}

        catch (Exception $e) {
        $response['error'] = true;
        $response['message'] = $e->getMessage();
    }
        $user = $db->signup($role,$name,$email,$mobile,$password,$file_path);
        if ($user) {
        
            $response["error"] = FALSE;
            $response["user"]["role"] =$user["Role"];
            if($user["Role"]==1){
                $response["user"]["Name"]=$user["Name"];
                      $response["user"]["ID"]=$user["ID"];
              }else{
                       $response["user"]["Name"]=$user["parlour_name"];
                             $response["user"]["ID"]=$user["ID"];
              }
     
            $response["user"]["ID"]=$user["ID"];
            echo json_encode($response);
        } else {
            // user failed to store
           $response["user"]["role"] =0;
            echo json_encode($response);
        }

} else {
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

function test_c($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  $data=strtoupper($data);
  return $data;
}
?>