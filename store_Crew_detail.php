<?php
 
// Path to move uploaded files

require_once 'DB_Functions.php';
$db = new DB_Functions();



// array for final json respone
$response = array();

 $response = array("error" => FALSE);
 
// getting server ip address
 $server_ip="139.59.38.160";

 
 
if (isset($_FILES['image']['name'])) {
   
    $description = isset($_POST['detail']) ? $_POST['detail'] : '';
    $user = isset($_POST['user']) ? $_POST['user'] : '';
    $name = isset($_POST['name']) ? $_POST['name'] : '';
    $mobile = isset($_POST['mobile']) ? $_POST['mobile'] : '';
    $email = isset($_POST['email']) ? $_POST['email'] : '';
    $position=isset($_POST['position'])? $_POST['position'] :'';
    $service=isset($_POST['service'])? $_POST['service'] :'';
    $available=isset($_POST['available'])? $_POST['available'] :'';
     $delete=isset($_POST["delete"])? $_POST["delete"]:'';
    
    $file_path=$_FILES['image']['name'];

    
  $user=test_input($user);
  $description=test_input($description);
  $name=test_input($name);
  $mobile=test_input($mobile);
  $email=test_($email);
  $position=test_input($position);
  $service=test_input($service);
  $available=test_input($available);
  $delete=test_input($delete);
   
 
 
  
    $target_path = "uploads/crew/";
      $target_path2 = $target_path . basename($_FILES['image']['name']);
    try {
        // Throws exception incase file is not being moved
        if (!move_uploaded_file($_FILES['image']['tmp_name'], $target_path2)) {
            // make error flag true
            $response['error'] = true;
            $response['message'] = 'Could not move the file!';
        }else{
 
        // File successfully uploaded
        $response['message'] = 'File uploaded successfully!';
        $response['error'] = false;
      }
    }catch (Exception $e) {
        // Exception occurred. Make error flag true
        $response['error'] = true;
        $response['message'] = $e->getMessage();
    }


$user = $db->storeCrewImage($user,$name,$email,$mobile,$description,$file_path,$position,$service,$available,$delete);

  if ($user) {
            // user stored successfully
            $response["error"] = FALSE;
          
            
         
        } else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in uploading!";
            echo json_encode($response);
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