<?php
 header('Content-Type: application/json');


require_once 'DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
date_default_timezone_set(TIMEZONE);
        $hour=date("H:i");
        $date=date("Y-m-d");


        var_dump($_POST);

if (isset($_POST['mobile']) && isset($_POST['data'] )&& isset($_POST['total'] )){
 
    $address=$land= $house= '';
   $mobile =isset($_POST['mobile']) ? $_POST['mobile'] : '';
   $ID = isset($_POST['ID']) ? $_POST['ID'] : '';
   $total = isset($_POST['total']) ? $_POST['total'] : '';
   $salon=isset($_POST['salon']) ? $_POST['salon'] : '';
   $data=isset($_POST['data']) ? $_POST['data'] : '';
   $specialist=isset($_POST['specialist']) ? $_POST['specialist'] : '';
   $date=isset($_POST['date']) ? $_POST['date'] : '';
   $discount=isset($_POST['discount']) ? $_POST['discount'] : '';
   $slot=isset($_POST['slot']) ? $_POST['slot'] : '';
      $NoofItems=isset($_POST['NoofItems']) ? $_POST['NoofItems'] : '';
        $home=isset($_POST['home']) ? $_POST['home'] : '';
 $pmode=isset($_POST['pmode']) ? $_POST['pmode'] : '';


       if(isset($_POST['address'])){
       	   $address=isset($_POST['address']) ? $_POST['address'] : '';
       	}else{
       		   $address= '';
       	}
     
     if(isset($_POST['house'])){
       	   $house=isset($_POST['house']) ? $_POST['house'] : '';
       	}else{
       		   $house= '';
       	}
       if(isset($_POST['land'])){
       	   $land=isset($_POST['land']) ? $_POST['land'] : '';
       	}else{
       		   $land= '';
       	}
          $address=test_input($address);
    $house=test_input($house);
    $home=test_input($home);
      $pmode=test_input($pmode);

   
   
    $NoofItems=test_input($NoofItems);
   
    $slot=test_input($slot);
    $mobile=test_input($mobile);
    $salon=test_input($salon);
    $total=test_input($total);
    $ID=test_input($ID);
    $data=test_input($data);
    $specialist=test_input($specialist);
    $date=test_input($date);
       $discount=test_input($discount);
       $slot=test_input($slot);
      
         $user = $db-> add_order($mobile,$ID,$data,$total,$salon,$specialist,$date,$discount,$slot,$NoofItems,$home,$address,$house,$land,$pmode);
      

        if ($user) {
            // user stored successfully
            $response["error"] = FALSE;
            $response["user"]["OrderID"] = $user["OrderID"];
            $response["user"]["IDsalon"] = $user["IDsalon"];
            echo json_encode($response);
        } else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Failed!";
            echo json_encode($response);
        }
    
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters missing!";
    echo json_encode($response);
}

function test_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}
?>