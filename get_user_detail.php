<?php


header('Content-Type: application/json');
        require_once 'DB_Connect.php';
        // connecting to database
        $db = new Db_Connect();
        $conn = $db->connect();

 
if(!$conn){
   echo "Could not connect to DBMS"; 
    }else { 


if (isset($_POST['_mId'])){
 
    $mobile= $_POST['_mId'];
   
    $mobile=test_input($mobile);

date_default_timezone_set("Asia/Kolkata");
$hour=date("H");
$date=date("Y-m-d");


try{
$server_ip="139.59.38.160";
  $response = array("login"=>array(),"version"=>array());
    
     $result =$conn->query("SELECT ID FROM  user_details WHERE Phone_No=$mobile");
          
         if ($result->num_rows > 0) {
          while($row = $result->fetch_assoc()) {
          $uID=$row["ID"];
        }
         }

  $version=mysqli_query($conn, "SELECT `ID`, `Version`, `Importance`, `Date`, `Time`, `User`, `IP` FROM `app_version`");

          while ($user1 = mysqli_fetch_assoc($version)) {
   $jsonRow_201=array(
  "Version"=>$user1["Version"],
  "Importance"=>$user1["Importance"],

             );
array_push($response["version"], $jsonRow_201);
  
} 
         
               $login=mysqli_query($conn, "SELECT `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `Reference_Code`, `Firebase_token`, `AppInstallation_Date`, `AppInstallation_Time`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE parlour_mobile=$mobile ");


       

while ($user1 = mysqli_fetch_assoc($login)) {

$jsonRow_201=array(
             "ID"=>$user['ID'],
               "parlour_name"=>$user['parlour_name'],
               "parlour_about"=>$user['parlour_about'],
              "parlour_email"=>$user['parlour_email'],
             "pic"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],
             "parlour_address"=>$user['parlour_address'], 
                  "parlour_pin"=>$user['parlour_pin'],
               "parlour_locality"=>$user['parlour_locality'],
              "parlour_mobile"=>$user['parlour_mobile'],  
               "parlour_city"=>$user['parlour_city'],
               "parlour_registration"=>$user['parlour_registration'],
              "isVerified"=>$user['isVerified'],   
                 "latitude"=>$user['latitude'],
              "longitude"=>$user['longitude'],  
               "service_location"=>$user['service_location'],
               "isSechedule"=>$user['isSechedule'],
              "isSpecialist"=>$user['isSpecialist'],   
              "isLocation"=>$user['isLocation'],
              "isServiceAt"=>$user['isServiceAt'],   
 );

array_push($response["login"], $jsonRow_201);
  
}

   echo json_encode($response);    

} catch(Error $e) {
    $trace = $e->getTrace();
    echo $e->getMessage().' in '.$e->getFile().' on line '.$e->getLine().' called from '.$trace[0]['file'].' on line '.$trace[0]['line'];
}

}
}
 
    
 
function test_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}
?>