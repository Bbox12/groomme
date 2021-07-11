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
    $users = isset($_POST['user']) ? $_POST['user'] : '';
    $name = isset($_POST['name']) ? $_POST['name'] : '';
    $service=isset($_POST['service'])? $_POST['service'] :'';
       $ID=isset($_POST['ID'])? $_POST['ID'] :'';
         
   
    $file_path=$_FILES['image']['name'];


      $ID=test_input($ID);
  $users=test_input($users);
  $description=test_input($description);
  $name=test_input($name);
  $service=test_input($service);
 
  
    $target_path = "crew_images/";
      $target_path2 = $target_path . basename($_FILES['image']['name']);
    try {
        // Throws exception incase file is not being moved
        if (!move_uploaded_file($_FILES['image']['tmp_name'], $target_path2)) {
            // make error flag true
            $response['errors'] = true;
            $response['message'] = 'Could not move the file!';
        }else{
 
        // File successfully uploaded
        $response['message'] = 'File uploaded successfully!';
        $response['errors'] = false;
        $result = $db->storeCrewImage($users,$name,$description,$file_path,$service,$ID);
  if ($result) {
            $response["error"] = FALSE;
        } else {
            $response["error"] = TRUE;
        }
      }
    }catch (Exception $e) {
        // Exception occurred. Make error flag true
        $response['errors'] = true;
        $response['message'] = $e->getMessage();
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