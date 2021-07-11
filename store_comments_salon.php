<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);
 header('Content-Type: application/json');


require_once 'DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);

if (isset($_POST['mobile'])){
 
    $mobile= $_POST['mobile'];
    $otp= $_POST['otp'];
    $message = $_POST['message'];
       $rating_c = $_POST['rating_c'];

     if (empty($_FILES['image_1']['name'])) {
       $image_1 = "profile_image.png";
          }else{
      $image_1 = $_FILES['image_1']['name'];
  }



     if (empty($_FILES['image_2']['name'])) {
       $image_2 = "profile_image.png";
          }else{
      $image_2 = $_FILES['image_2']['name'];
  }
     if (empty($_FILES['image_3']['name'])) {
       $image_3 = "profile_image.png";
          }else{
      $image_3 = $_FILES['image_3']['name'];
  }

     if (empty($_FILES['image_4']['name'])) {
       $image_4 = "profile_image.png";
          }else{
      $image_4 = $_FILES['image_4']['name'];
  }

   
    $mobile=test_input($mobile);
    $message=test_input($message);
    $rating_c=test_input($rating_c);
    $otp=test_input($otp);
    

          if (!empty($_FILES['image_1']['name'])) {
  
    $target_path = "review/";
    $target_path2 = $target_path . basename($_FILES['image_1']['name']);

        try {
        // Throws exception incase file is not being moved
        if (!move_uploaded_file($_FILES['image_1']['tmp_name'], $target_path2)) {
            // make error flag true
            $response['error'] = true;
            $response['message'] = 'Could not move the file!';
        }else{

        $response['error'] = "Suucess";
        }
 
       
    }
      catch (Exception $e) {
        // Exception occurred. Make error flag true
        $response['error'] = true;
        $response['message'] = $e->getMessage();
    }
}

   if (!empty($_FILES['image_2']['name'])) {
    $target_path = "review/";
    $target_path2 = $target_path . basename($_FILES['image_2']['name']);

        try {
        // Throws exception incase file is not being moved
        if (!move_uploaded_file($_FILES['image_2']['tmp_name'], $target_path2)) {
            // make error flag true
            $response['error'] = true;
            $response['message'] = 'Could not move the file!';
        }else{

        $response['error'] = "Suucess";
        }
 
       
    }
      catch (Exception $e) {
        // Exception occurred. Make error flag true
        $response['error'] = true;
        $response['message'] = $e->getMessage();
    }
}
   if (!empty($_FILES['image_3']['name'])) {
    $target_path = "review/";
    $target_path2 = $target_path . basename($_FILES['image_3']['name']);

        try {
        // Throws exception incase file is not being moved
        if (!move_uploaded_file($_FILES['image_3']['tmp_name'], $target_path2)) {
            // make error flag true
            $response['error'] = true;
            $response['message'] = 'Could not move the file!';
        }else{

        $response['error'] = "Suucess";
        }
 
       
    }
      catch (Exception $e) {
        // Exception occurred. Make error flag true
        $response['error'] = true;
        $response['message'] = $e->getMessage();
    }
}
   if (!empty($_FILES['image_4']['name'])) {
    $target_path = "review/";
    $target_path2 = $target_path . basename($_FILES['image_4']['name']);

        try {
        // Throws exception incase file is not being moved
        if (!move_uploaded_file($_FILES['image_4']['tmp_name'], $target_path2)) {
            // make error flag true
            $response['error'] = true;
            $response['message'] = 'Could not move the file!';
        }else{

        $response['error'] = "Suucess";
        }
 
       
    }
      catch (Exception $e) {
        // Exception occurred. Make error flag true
        $response['error'] = true;
        $response['message'] = $e->getMessage();
    }
}
    

     $user = $db->addreviewFromSalon($mobile,$otp,$rating_c,$image_1,$image_2,$image_3,$image_4,$message);

        if ($user) {   
        $response["error"] = false;

    } else  {
       
        $response["error"] = true;

    }

} else {
    // File parameter is missing
    $response['error'] = true;
    $response['message'] = 'Not received any file!';
}
     echo json_encode($response); 
       


function test_input($data) {
  $data = trim($data);
  $data = htmlspecialchars($data);
  return $data;
}
?>