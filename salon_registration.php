<?php
 header('Content-Type: application/json');


require_once 'DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);

$target_path = "salon_images/";

error_reporting(-1);
ini_set('display_errors', 'On');
 
if (isset($_POST['ID']) &&  isset($_POST['address'] )&& isset($_POST['mobile']) && isset($_POST['zip'])){
 
    // receiving the post params
    $name = $_POST['name'];
    $email = $_POST['email'];
    $address=$_POST['address'];
    $mobile=$_POST['mobile'];
    $registration=$_POST['registration'];
    $city=$_POST['city'];
        $about = $_POST['about'];
            $about=test_input($about);

                 $data = $_POST['data'];
            $data=test_input($data);
    $ID = $_POST['ID'];
    $ID=test_input($ID);
    $name=test_input($name);
    $email=test_input($email);
    $address=test_c($address);
    $mobile=test_input($mobile);
    $registration=test_input($registration);
    $city=test_input($city);

    $locality=$_POST['locality'];
    $locality=test_input($locality);
    $zip=$_POST['zip'];
    $zip=test_input($zip);
    $IP=$_POST['IP'];
    $IP=test_input($IP);
      $file_path=$_FILES['image']['name'];
       $target_path0 = $target_path . basename($_FILES["image"]["name"]);
 

       
      try {
        if (!move_uploaded_file($_FILES['image']['tmp_name'], $target_path0)) {
            // make error flag true
            $response['error'] = true;
            $response['message'] = 'Could not move the file!';
        }else{
 
        $response['message'] = 'File uploaded successfully!';


    }
}

        catch (Exception $e) {
        $response['error'] = true;
        $response['message'] = $e->getMessage();
    }
        $user = $db->storeUser($ID,$about,$name,$email,$address,$mobile,$registration,$city,$locality,$zip,$IP,$file_path,$data);
        if ($user) {
            // user stored successfully
            $response["error"] = FALSE;
            echo json_encode($response);
        } else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in registration!";
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