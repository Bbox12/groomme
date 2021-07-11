<?php

header('Content-Type: application/json');
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
require_once 'DB_Connect.php';
        $db = new Db_Connect();
        $conn = $db->connect();


if(!$conn){
   echo "Could not connect to DBMS";       
 }else {
  
  $from=$home=$ID=0;

 
  if(isset($_POST["id"])){
    $ID= $_POST['id'];
    $ID=test_input($ID);
  }else{
    $ID=0;
  }


  if(isset($_POST["from"])){
    $from=$_POST["from"];
  }

  if(isset($_POST["home"])){
    $home=$_POST["home"];
  }

  if(isset($_POST["city"])){
    $city= $_POST['city'];
    $city=test_input($city);
  }


 $server_ip="139.59.38.160";

$response = array("salons"=>array());



if($home==0 && $from==0){ 

  if($ID!=0){

     $sql="SELECT   sr.serviceRating,sr.serviceTotalRating,sr.ID, sr.parlour_name,sr.Photo,sr.parlour_address,sr.parlour_mobile AS ParlourID,f.Name,sr.latitude,sr.longitude,sr.parlour_city,sr.parlour_locality, s.Service AS SecondaryService, sr.discountAmt FROM `servicesdetails` sd INNER JOIN final_services f ON f.ID=sd.FinalServiceID INNER JOIN secondary_service s ON f.SecondaryServiceID=s.ID INNER JOIN primary_services p ON p.ID=s.IDPrimaryService INNER JOIN salon_registration sr ON sr.ID=sd.SalonID WHERE p.ID='$ID' AND p.isActive=1 AND sr.isVerified=1 AND s.isActive=1  GROUP BY sr.ID ";
  

   $result = $conn->query($sql);


  while ($user = mysqli_fetch_assoc($result)) {

$jsonRow=array(
               "ID"=>$user['ID'],

                       "city"=>$user['parlour_city'],
                        "locality"=>$user['parlour_locality'],
                            
             );


array_push($response["salons"], $jsonRow);

}

}else{

       $salons=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`, serviceTotalRating,`Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 ");
 

 while ($user = mysqli_fetch_assoc($salons)) {
$jsonRow=array(
               "ID"=>$user['ID'],
              "parlour_name"=>$user['parlour_name'],
                 "address"=>$user['parlour_address'].",".$user['parlour_city'],
                  "parlour_mobile"=>$user['parlour_mobile'],
                           "latitude"=>$user['latitude'],
                      "longitude"=>$user['longitude'],
                        "serviceRating"=>$user['serviceRating'],
                          "discountAmt"=>$user['discountAmt'],
                                "city"=>$user['parlour_city'],
                        "locality"=>$user['parlour_locality'],
                            "serviceTotalRating"=>$user['serviceTotalRating'],  
             "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],
             );
array_push($response["salons"], $jsonRow);
}
}
}


if($home!=0){

       $salons=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`, serviceTotalRating,`Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND service_location='$home'");



 while ($user = mysqli_fetch_assoc($salons)) {
$jsonRow=array(
               "ID"=>$user['ID'],
              "parlour_name"=>$user['parlour_name'],
                 "address"=>$user['parlour_address'].",".$user['parlour_city'],
                  "parlour_mobile"=>$user['parlour_mobile'],
                           "latitude"=>$user['latitude'],
                      "longitude"=>$user['longitude'],
                        "serviceRating"=>$user['serviceRating'],
                          "discountAmt"=>$user['discountAmt'],
                                "city"=>$user['parlour_city'],
                        "locality"=>$user['parlour_locality'],
                            "serviceTotalRating"=>$user['serviceTotalRating'],  
             "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],
             );
array_push($response["salons"], $jsonRow);
}

}

if($from==1){

        $salons=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`, serviceTotalRating,`Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND Ladies=1 AND Gents=0");



 while ($user = mysqli_fetch_assoc($salons)) {
$jsonRow=array(
               "ID"=>$user['ID'],
              "parlour_name"=>$user['parlour_name'],
                  "parlour_mobile"=>$user['parlour_mobile'],
                            "address"=>$user['parlour_address'].",".$user['parlour_city'],
                           "latitude"=>$user['latitude'],
                      "longitude"=>$user['longitude'],
                      "discountAmt"=>$user['discountAmt'],
                        "serviceRating"=>$user['serviceRating'],
                              "city"=>$user['parlour_city'],
                        "locality"=>$user['parlour_locality'],
                            "serviceTotalRating"=>$user['serviceTotalRating'],  
             "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],
             );
array_push($response["salons"], $jsonRow);
}

}elseif($from==2){


        $salons=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`, serviceTotalRating,`Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND Ladies=0 AND Gents=1");


 while ($user = mysqli_fetch_assoc($salons)) {
$jsonRow=array(
               "ID"=>$user['ID'],
              "parlour_name"=>$user['parlour_name'],
                  "parlour_mobile"=>$user['parlour_mobile'],
                            "address"=>$user['parlour_address'].",".$user['parlour_city'],
                           "latitude"=>$user['latitude'],
                      "longitude"=>$user['longitude'],
                           "discountAmt"=>$user['discountAmt'],
                        "serviceRating"=>$user['serviceRating'],
                              "city"=>$user['parlour_city'],
                        "locality"=>$user['parlour_locality'],
                            "serviceTotalRating"=>$user['serviceTotalRating'],  
             "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],
             );
array_push($response["salons"], $jsonRow);
}

}elseif ($from==3){

    $salons=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`,serviceTotalRating, `Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND Gents=1 AND Ladies=1");


 while ($user = mysqli_fetch_assoc($salons)) {
$jsonRow=array(
               "ID"=>$user['ID'],
              "parlour_name"=>$user['parlour_name'],
                  "parlour_mobile"=>$user['parlour_mobile'],
                           "latitude"=>$user['latitude'],
                                     "address"=>$user['parlour_address'].",".$user['parlour_city'],
                      "longitude"=>$user['longitude'],
                           "discountAmt"=>$user['discountAmt'],
                        "serviceRating"=>$user['serviceRating'],
                              "city"=>$user['parlour_city'],
                        "locality"=>$user['parlour_locality'],
                            "serviceTotalRating"=>$user['serviceTotalRating'],  
             "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],
             );
array_push($response["salons"], $jsonRow);
}

}elseif ($from==4){


        $salons=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`, serviceTotalRating,`Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND Kids=1 ");


 while ($user = mysqli_fetch_assoc($salons)) {
$jsonRow=array(
               "ID"=>$user['ID'],
              "parlour_name"=>$user['parlour_name'],
                  "parlour_mobile"=>$user['parlour_mobile'],
                           "latitude"=>$user['latitude'],
                                     "address"=>$user['parlour_address'].",".$user['parlour_city'],
                      "longitude"=>$user['longitude'],
                           "discountAmt"=>$user['discountAmt'],
                        "serviceRating"=>$user['serviceRating'],
                              "city"=>$user['parlour_city'],
                        "locality"=>$user['parlour_locality'],
                            "serviceTotalRating"=>$user['serviceTotalRating'],  
             "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],
             );
array_push($response["salons"], $jsonRow);
}

}elseif ($from==5){


        $salons=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`, serviceTotalRating,`Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND Bridal=1 ");



 while ($user = mysqli_fetch_assoc($salons)) {
$jsonRow=array(
               "ID"=>$user['ID'],
              "parlour_name"=>$user['parlour_name'],
                  "parlour_mobile"=>$user['parlour_mobile'],
                           "latitude"=>$user['latitude'],
                                     "address"=>$user['parlour_address'].",".$user['parlour_city'],
                      "longitude"=>$user['longitude'],
                           "discountAmt"=>$user['discountAmt'],
                        "serviceRating"=>$user['serviceRating'],
                              "city"=>$user['parlour_city'],
                        "locality"=>$user['parlour_locality'],
                            "serviceTotalRating"=>$user['serviceTotalRating'],  
             "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],
             );
array_push($response["salons"], $jsonRow);
}

}




echo json_encode($response);


}

  function test_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}

?>