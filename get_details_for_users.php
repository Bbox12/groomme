<?php

header('Content-Type: application/json');
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
require_once 'DB_Connect.php';
        $db = new Db_Connect();
        $conn = $db->connect();

    $city= 0;
if(!$conn){
   echo "Could not connect to DBMS";       
 }else {
  
  $from=$home=0;

 
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

$response = array("parlours"=>array(),"primaryservices"=>array(),"quotes"=>array(),"popular"=>array(),"new"=>array(),"popularStylist"=>array(),"ads"=>array(),"deals"=>array(),"distance"=>array(),"selfie"=>array(),"allStylist"=>array(),"category"=>array(),"crew"=>array(),"salons"=>array(),"map"=>array(),"home"=>array());


if($home!=0){
     if(strlen($city)!=0){
    $salons=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`, serviceTotalRating,`Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND (UPPER(parlour_city) LIKE '%$city%' OR UPPER(parlour_locality) LIKE '%$city%') AND service_location='$home'");
  }else{
       $salons=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`, serviceTotalRating,`Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND service_location='$home'");
  }


 while ($user = mysqli_fetch_assoc($salons)) {
$jsonRow=array(
               "ID"=>$user['ID'],
              "parlour_name"=>$user['parlour_name'],
                 
                  "parlour_mobile"=>$user['parlour_mobile'],
                           "latitude"=>$user['latitude'],
                      "longitude"=>$user['longitude'],
                        "serviceRating"=>$user['serviceRating'],
                        "address"=>$user['parlour_address'].",".$user['parlour_city'],
                          "discountAmt"=>$user['discountAmt'],
                            "serviceTotalRating"=>$user['serviceTotalRating'],  
             "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],
             );
array_push($response["home"], $jsonRow);
}

}

if($from==1){
   if(strlen($city)!=0){
    $salons=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`, serviceTotalRating,`Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND Ladies=1 AND Gents=0 AND (UPPER(parlour_city) LIKE '%$city%' OR UPPER(parlour_locality) LIKE '%$city%')");
  }else{
        $salons=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`, serviceTotalRating,`Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND Ladies=1 AND Gents=0");
  }


 while ($user = mysqli_fetch_assoc($salons)) {
$jsonRow=array(
               "ID"=>$user['ID'],
              "parlour_name"=>$user['parlour_name'],
                  "parlour_mobile"=>$user['parlour_mobile'],
                          "address"=>$user['parlour_address'].",".$user['parlour_city'],
                          "discountAmt"=>$user['discountAmt'],
                           "latitude"=>$user['latitude'],
                      "longitude"=>$user['longitude'],
                      "discountAmt"=>$user['discountAmt'],
                        "serviceRating"=>$user['serviceRating'],
                            "serviceTotalRating"=>$user['serviceTotalRating'],  
             "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],
             );
array_push($response["map"], $jsonRow);
}

}elseif($from==2){

  if(strlen($city)!=0){
    $salons=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`, serviceTotalRating,`Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND Ladies=0 AND Gents=1 AND (UPPER(parlour_city) LIKE '%$city%' OR UPPER(parlour_locality) LIKE '%$city%')");
  }else{
        $salons=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`, serviceTotalRating,`Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND Ladies=0 AND Gents=1");
  }

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
                            "serviceTotalRating"=>$user['serviceTotalRating'],  
             "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],
             );
array_push($response["map"], $jsonRow);
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
                            "serviceTotalRating"=>$user['serviceTotalRating'],  
             "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],
             );
array_push($response["map"], $jsonRow);
}

}elseif ($from==4){

  if(strlen($city)!=0){
    $salons=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`, serviceTotalRating,`Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND Kids=1  AND (UPPER(parlour_city) LIKE '%$city%' OR UPPER(parlour_locality) LIKE '%$city%')");
  }else{
        $salons=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`, serviceTotalRating,`Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND Kids=1 ");
  }

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
                            "serviceTotalRating"=>$user['serviceTotalRating'],  
             "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],
             );
array_push($response["map"], $jsonRow);
}

}elseif ($from==5){

   if(strlen($city)!=0){
    $salons=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`, serviceTotalRating,`Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND Bridal=1  AND (UPPER(parlour_city) LIKE '%$city%' OR UPPER(parlour_locality) LIKE '%$city%')");
  }else{
        $salons=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`, serviceTotalRating,`Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND Bridal=1 ");
  }


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
                            "serviceTotalRating"=>$user['serviceTotalRating'],  
             "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],
             );
array_push($response["map"], $jsonRow);
}

}

   if(strlen($city)!=0){
$sql="SELECT s.ID, s.crew_name, s.crew_detail, s.crew_pic,r.parlour_mobile AS `ParlourID`,r.parlour_name  AS `parlour_name`,  s.service , s.available,s.Rating, s.TotalRating FROM `specialist_detail`s INNER JOIN salon_registration r ON s.ParlourID=r.ID  WHERE  r.isVerified=1 AND s.available=1 AND (UPPER(r.parlour_city) LIKE '%$city%' OR UPPER(r.parlour_locality) LIKE '%$city%') ORDER BY s.Rating DESC ";
}else{
$sql="SELECT s.ID, s.crew_name, s.crew_detail, s.crew_pic,r.parlour_mobile AS `ParlourID`,r.parlour_name  AS `parlour_name`,  s.service , s.available,s.Rating, s.TotalRating FROM `specialist_detail`s INNER JOIN salon_registration r ON s.ParlourID=r.ID  WHERE  r.isVerified=1 AND s.available=1 ORDER BY s.Rating DESC";
}
    
$result = $conn->query($sql);

  while ($user = mysqli_fetch_assoc($result)) {

$jsonRow=array(
               "ID"=>$user['ID'],
               "name"=>$user['crew_name'],
               "detail"=>$user['crew_detail'],
              "ParlourID"=>$user['ParlourID'],

             "pic"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'crew_images'.'/'.$user['crew_pic'],
             "service"=>$user['service'],           
             );


array_push($response["crew"], $jsonRow);


}

   if(strlen($city)!=0){
    $salons=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`,serviceTotalRating, `Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND (UPPER(parlour_city) LIKE '%$city%' OR UPPER(parlour_locality) LIKE '%$city%')");
  }else{
        $salons=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`,serviceTotalRating, `Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0");
  }


 while ($user = mysqli_fetch_assoc($salons)) {
$jsonRow=array(
               "ID"=>$user['ID'],
              "parlour_name"=>$user['parlour_name'],
                  "parlour_mobile"=>$user['parlour_mobile'],
                           "latitude"=>$user['latitude'],
                      "longitude"=>$user['longitude'],
                                   "address"=>$user['parlour_address'].",".$user['parlour_city'],
                                "discountAmt"=>$user['discountAmt'],
                        "serviceRating"=>$user['serviceRating'],
                            "serviceTotalRating"=>$user['serviceTotalRating'],  
             "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],
             );
array_push($response["salons"], $jsonRow);
}

   if(strlen($city)!=0){
    $parlours=mysqli_query($conn, "SELECT COUNT(ID) AS noofsalon, `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`,serviceTotalRating, `Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND (UPPER(parlour_city) LIKE '%$city%' OR UPPER(parlour_locality) LIKE '%$city%')");
  }else{
       $parlours=mysqli_query($conn, "SELECT COUNT(ID) AS noofsalon, `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`,serviceTotalRating, `Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 ");
  }


 while ($user = mysqli_fetch_assoc($parlours)) {
$jsonRow=array(
               "ID"=>$user['ID'],
                "noofsalon"=>$user['noofsalon'],
              "parlour_name"=>$user['parlour_name'],
                  "parlour_mobile"=>$user['parlour_mobile'],
                     "latitude"=>$user['latitude'],
                      "longitude"=>$user['longitude'],
                         "address"=>$user['parlour_address'].",".$user['parlour_city'],
                                "discountAmt"=>$user['discountAmt'],
                        "serviceRating"=>$user['serviceRating'],
                            "serviceTotalRating"=>$user['serviceTotalRating'],  
             "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],
             );
array_push($response["parlours"], $jsonRow);
}



 $ads=mysqli_query($conn, "SELECT `ID`, `Photo`, `Date`, `Time`, `IP` FROM `viewpager` ");


 while ($user = mysqli_fetch_assoc($ads)) {
$jsonRow=array(
               "ID"=>$user['ID'],
             "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'gallery'.'/'.$user['Photo'],
             );
array_push($response["ads"], $jsonRow);
}
    
      

 $primaryservices=mysqli_query($conn, "SELECT p.ID, p.Name, p.Photo  FROM `primary_services` p  WHERE p.isActive=1");





  while ($user = mysqli_fetch_assoc($primaryservices)) {
$jsonRow=array(
               "ID"=>$user['ID'],
               "Name"=>$user['Name'],
             "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'Dashboard'.'/'. 'service'.'/'.$user['Photo'],
           
             );
array_push($response["primaryservices"], $jsonRow);
}

$quotess=mysqli_query($conn, "SELECT `ID`, `Feedback` FROM `userfeedback` ");


 while ($user = mysqli_fetch_assoc($quotess)) {
$jsonRow=array(
               "ID"=>$user['ID'],
                "Feedback"=>$user['Feedback'],
          
             );
array_push($response["quotes"], $jsonRow);
}

   if(strlen($city)!=0){
 $popular=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`,`serviceTotalRating`, `Reference_Code`,serviceTotalRating, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND PopularSalon=1 AND (UPPER(parlour_city) LIKE '%$city%' OR UPPER(parlour_locality) LIKE '%$city%')");
}else{
   $popular=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`,`serviceTotalRating`, `Reference_Code`,serviceTotalRating, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND PopularSalon=1");
}



  while ($user = mysqli_fetch_assoc($popular)) {
$jsonRow=array(
               "ID"=>$user['ID'],
                "parlour_name"=>$user['parlour_name'],
                  "parlour_address"=>$user['parlour_address'].",".$user['parlour_city'],
                       "parlour_mobile"=>$user['parlour_mobile'],
                              "latitude"=>$user['latitude'],
                      "longitude"=>$user['longitude'],
                         "address"=>$user['parlour_address'].",".$user['parlour_city'],
                                "discountAmt"=>$user['discountAmt'],
                        "serviceRating"=>$user['serviceRating'],
                             "serviceTotalRating"=>$user['serviceTotalRating'],  
                   "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],

             );
array_push($response["popular"], $jsonRow);
}
   if(strlen($city)!=0){
 $new=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`, `Reference_Code`,`serviceTotalRating`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND isNew=1 AND (UPPER(parlour_city) LIKE '%$city%' OR UPPER(parlour_locality) LIKE '%$city%')");
}else{
  $new=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`, `Reference_Code`,`serviceTotalRating`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND isNew=1"); 
}



  while ($user = mysqli_fetch_assoc($new)) {
$jsonRow=array(
               "ID"=>$user['ID'],
                "parlour_name"=>$user['parlour_name'],
                  "parlour_address"=>$user['parlour_address'].",".$user['parlour_city'],
                       "parlour_mobile"=>$user['parlour_mobile'],
                              "latitude"=>$user['latitude'],
                      "longitude"=>$user['longitude'],
                             "serviceRating"=>$user['serviceRating'],
                                  "address"=>$user['parlour_address'].",".$user['parlour_city'],
                                "discountAmt"=>$user['discountAmt'],
                             "serviceTotalRating"=>$user['serviceTotalRating'],  
                   "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],

             );
array_push($response["new"], $jsonRow);
}
   if(strlen($city)!=0){
 $allStylist=mysqli_query($conn, "SELECT s.ID, s.crew_name, s.crew_detail, s.crew_pic,r.parlour_mobile AS `ParlourID`,r.parlour_name,r.parlour_address,r.parlour_city ,  s.service , s.available,s.Rating, s.TotalRating FROM `specialist_detail`s INNER JOIN salon_registration r ON s.ParlourID=r.ID  WHERE  r.isVerified=1 AND s.available=1 AND (UPPER(r.parlour_city) LIKE '%$city%' OR UPPER(r.parlour_locality) LIKE '%$city%') ORDER BY ID DESC LIMIT 5");
}else{
   $allStylist=mysqli_query($conn, "SELECT s.ID, s.crew_name, s.crew_detail, s.crew_pic,r.parlour_mobile AS `ParlourID`,r.parlour_name  AS `parlour_name`,r.parlour_address,r.parlour_city ,  s.service , s.available,s.Rating, s.TotalRating FROM `specialist_detail`s INNER JOIN salon_registration r ON s.ParlourID=r.ID  WHERE  r.isVerified=1 AND s.available=1 ORDER BY ID DESC LIMIT 5");
}



  while ($user = mysqli_fetch_assoc($allStylist)) {
$jsonRow=array(
               "ID"=>$user['ID'],
                "ParlourID"=>$user['ParlourID'],
                  "crew_name"=>$user['crew_name'],
                       "service"=>$user['service'],
                                 "parlour_name"=>$user['parlour_name'],
                                            "Rating"=>$user['Rating'],
                                               "address"=>$user['parlour_address'].",".$user['parlour_city'],
                             "TotalRating"=>$user['TotalRating'], 
                   "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'crew_images'.'/'.$user['crew_pic'],

             );
array_push($response["allStylist"], $jsonRow);
}
  if(strlen($city)!=0){
 $popularStylist=mysqli_query($conn, "SELECT s.ID, s.crew_name, s.crew_detail, s.crew_pic,r.parlour_mobile AS `ParlourID`,r.parlour_name  AS `parlour_name`, r.parlour_name,r.parlour_address,r.parlour_city , s.service , s.available,s.Rating, s.TotalRating FROM `specialist_detail`s INNER JOIN salon_registration r ON s.ParlourID=r.ID  WHERE  s.isPopular=1 AND r.isVerified=1 AND s.available=1 AND (UPPER(r.parlour_city) LIKE '%$city%' OR UPPER(r.parlour_locality) LIKE '%$city%') AND s.TotalRating!=0");
}else{
   $popularStylist=mysqli_query($conn, "SELECT s.ID, s.crew_name, s.crew_detail, s.crew_pic,r.parlour_mobile AS `ParlourID`,r.parlour_name  AS `parlour_name`,r.parlour_name,r.parlour_address,r.parlour_city ,  s.service , s.available,s.Rating, s.TotalRating FROM `specialist_detail`s INNER JOIN salon_registration r ON s.ParlourID=r.ID  WHERE  s.isPopular=1 AND r.isVerified=1 AND s.available=1 AND s.TotalRating!=0");
}



  while ($user = mysqli_fetch_assoc($popularStylist)) {
$jsonRow=array(
               "ID"=>$user['ID'],
                "ParlourID"=>$user['ParlourID'],
                  "crew_name"=>$user['crew_name'],
             "address"=>$user['parlour_address'].",".$user['parlour_city'],
                       "service"=>$user['service'],
                                    "parlour_name"=>$user['parlour_name'],
                                       "Rating"=>$user['Rating'],
                             "TotalRating"=>$user['TotalRating'],  
                   "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'crew_images'.'/'.$user['crew_pic'],

             );
array_push($response["popularStylist"], $jsonRow);
}

 $deals=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`, `Reference_Code`,`serviceTotalRating`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND discountAmt!=0");



  while ($user = mysqli_fetch_assoc($deals)) {
$jsonRow=array(
              "ID"=>$user['ID'],
                "parlour_name"=>$user['parlour_name'],
                  "discountAmt"=>$user['discountAmt'],
                          "address"=>$user['parlour_address'].",".$user['parlour_city'],
                       "parlour_mobile"=>$user['parlour_mobile'],
                       "serviceRating"=>$user['serviceRating'],
                                  "latitude"=>$user['latitude'],
                      "longitude"=>$user['longitude'],
                             "serviceTotalRating"=>$user['serviceTotalRating'],  
                   "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],

             );
array_push($response["deals"], $jsonRow);
}

   if(strlen($city)!=0){
 $distance=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`, `Reference_Code`,`serviceTotalRating`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND (UPPER(parlour_city) LIKE '%$city%' OR UPPER(parlour_locality) LIKE '%$city%') ");
}else{
  $distance=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`, `Reference_Code`,`serviceTotalRating`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 ");
}



  while ($user = mysqli_fetch_assoc($distance)) {
$jsonRow=array(
              "ID"=>$user['ID'],
                           "address"=>$user['parlour_address'].",".$user['parlour_city'],
                                "discountAmt"=>$user['discountAmt'],
                "parlour_name"=>$user['parlour_name'],
                  "latitude"=>$user['latitude'],
                      "longitude"=>$user['longitude'],
                             "serviceRating"=>$user['serviceRating'],
                             "serviceTotalRating"=>$user['serviceTotalRating'],  
                           "parlour_mobile"=>$user['parlour_mobile'],
                   "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],

             );
array_push($response["distance"], $jsonRow);
}

$selfie=mysqli_query($conn, "SELECT `ID`, `IDUser`,(SELECT parlour_name FROM salon_registration WHERE ID=u.IDSalon) AS `IDSalon`, `Photo`, `Date`, `Time`, `IP` FROM `user_selfie` u ORDER BY ID LIMIT 4 ");



  while ($user = mysqli_fetch_assoc($selfie)) {
$jsonRow=array(
              "ID"=>$user['ID'],
                "parlour_name"=>$user['IDSalon'],
                   "Photo"=>$user['Photo'],

             );
array_push($response["selfie"], $jsonRow);
}

$category=mysqli_query($conn, "SELECT `ID`, `Category`, `Photo`, `Date` FROM `salon_category` ");



  while ($user = mysqli_fetch_assoc($category)) {
$jsonRow=array(
              "ID"=>$user['ID'],
                "Category"=>$user['Category'],
                   "Photo"=>$user['Photo'],

             );
array_push($response["category"], $jsonRow);
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