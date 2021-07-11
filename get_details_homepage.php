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
  
  $from=$home=0;

 
  if(isset($_POST["from"])){
    $from=$_POST["from"];
  }

  if(isset($_POST["home"])){
    $home=$_POST["home"];
  }

 $server_ip="139.59.38.160";

$response = array("parlours"=>array(),"primaryservices"=>array(),"quotes"=>array(),"popular"=>array(),"new"=>array(),"popularStylist"=>array(),"ads"=>array(),"deals"=>array(),"distance"=>array(),"selfie"=>array(),"allStylist"=>array(),"category"=>array(),"crew"=>array(),"salons"=>array(),"map"=>array(),"home"=>array());


if($home!=0){

    $salons=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`, serviceTotalRating,`Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND service_location='$home'");


 while ($user = mysqli_fetch_assoc($salons)) {
$jsonRow=array(
               "ID"=>$user['ID'],
              "parlour_name"=>$user['parlour_name'],
                  "parlour_mobile"=>$user['parlour_mobile'],
                           "latitude"=>$user['latitude'],
                      "longitude"=>$user['longitude'],
                        "serviceRating"=>$user['serviceRating'],
                            "serviceTotalRating"=>$user['serviceTotalRating'],  
             "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],
             );
array_push($response["home"], $jsonRow);
}

}

if($from==1){

    $salons=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`, serviceTotalRating,`Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND Ladies=1 AND Gents=0");


 while ($user = mysqli_fetch_assoc($salons)) {
$jsonRow=array(
               "ID"=>$user['ID'],
              "parlour_name"=>$user['parlour_name'],
                  "parlour_mobile"=>$user['parlour_mobile'],
                           "latitude"=>$user['latitude'],
                      "longitude"=>$user['longitude'],
                        "serviceRating"=>$user['serviceRating'],
                            "serviceTotalRating"=>$user['serviceTotalRating'],  
             "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],
             );
array_push($response["map"], $jsonRow);
}

}elseif($from==2){

    $salons=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`,serviceTotalRating, `Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND Gents=1 AND Ladies=0");


 while ($user = mysqli_fetch_assoc($salons)) {
$jsonRow=array(
               "ID"=>$user['ID'],
              "parlour_name"=>$user['parlour_name'],
                  "parlour_mobile"=>$user['parlour_mobile'],
                           "latitude"=>$user['latitude'],
                      "longitude"=>$user['longitude'],
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
                      "longitude"=>$user['longitude'],
                        "serviceRating"=>$user['serviceRating'],
                            "serviceTotalRating"=>$user['serviceTotalRating'],  
             "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],
             );
array_push($response["map"], $jsonRow);
}

}elseif ($from==4){

    $salons=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`,serviceTotalRating, `Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND  Kids=1");


 while ($user = mysqli_fetch_assoc($salons)) {
$jsonRow=array(
               "ID"=>$user['ID'],
              "parlour_name"=>$user['parlour_name'],
                  "parlour_mobile"=>$user['parlour_mobile'],
                           "latitude"=>$user['latitude'],
                      "longitude"=>$user['longitude'],
                        "serviceRating"=>$user['serviceRating'],
                            "serviceTotalRating"=>$user['serviceTotalRating'],  
             "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],
             );
array_push($response["map"], $jsonRow);
}

}elseif ($from==5){

    $salons=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`,serviceTotalRating, `Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND  Bridal=1");


 while ($user = mysqli_fetch_assoc($salons)) {
$jsonRow=array(
               "ID"=>$user['ID'],
              "parlour_name"=>$user['parlour_name'],
                  "parlour_mobile"=>$user['parlour_mobile'],
                           "latitude"=>$user['latitude'],
                      "longitude"=>$user['longitude'],
                        "serviceRating"=>$user['serviceRating'],
                            "serviceTotalRating"=>$user['serviceTotalRating'],  
             "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],
             );
array_push($response["map"], $jsonRow);
}

}


$sql="SELECT s.ID, s.crew_name, s.crew_detail, s.crew_pic,r.ID AS `ParlourID`,r.parlour_name  AS `parlour_name`,  s.service , s.available,s.Rating, s.TotalRating FROM `specialist_detail`s INNER JOIN salon_registration r ON s.ParlourID=r.ID  WHERE  r.isVerified=1 AND s.available=1";
    
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


    $salons=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`,serviceTotalRating, `Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0");


 while ($user = mysqli_fetch_assoc($salons)) {
$jsonRow=array(
               "ID"=>$user['ID'],
              "parlour_name"=>$user['parlour_name'],
                  "parlour_mobile"=>$user['parlour_mobile'],
                           "latitude"=>$user['latitude'],
                      "longitude"=>$user['longitude'],
                        "serviceRating"=>$user['serviceRating'],
                            "serviceTotalRating"=>$user['serviceTotalRating'],  
             "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],
             );
array_push($response["salons"], $jsonRow);
}


    $parlours=mysqli_query($conn, "SELECT COUNT(ID) AS noofsalon, `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`,serviceTotalRating, `Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0");


 while ($user = mysqli_fetch_assoc($parlours)) {
$jsonRow=array(
               "ID"=>$user['ID'],
                "noofsalon"=>$user['noofsalon'],
              "parlour_name"=>$user['parlour_name'],
                  "parlour_mobile"=>$user['parlour_mobile'],
                     "latitude"=>$user['latitude'],
                      "longitude"=>$user['longitude'],
                        "serviceRating"=>$user['serviceRating'],
                            "serviceTotalRating"=>$user['serviceTotalRating'],  
             "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],
               "distance"=>'5',
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


 $popular=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`,`serviceTotalRating`, `Reference_Code`,serviceTotalRating, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND PopularSalon=1 ORDER BY ID LIMIT 4");



  while ($user = mysqli_fetch_assoc($popular)) {
$jsonRow=array(
               "ID"=>$user['ID'],
                "parlour_name"=>$user['parlour_name'],
                  "parlour_address"=>$user['parlour_address'],
                       "parlour_mobile"=>$user['parlour_mobile'],
                              "latitude"=>$user['latitude'],
                      "longitude"=>$user['longitude'],
                        "serviceRating"=>$user['serviceRating'],
                             "serviceTotalRating"=>$user['serviceTotalRating'],  
                   "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],

             );
array_push($response["popular"], $jsonRow);
}

 $new=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`, `Reference_Code`,`serviceTotalRating`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND isNew=1 ORDER BY ID LIMIT 4");



  while ($user = mysqli_fetch_assoc($new)) {
$jsonRow=array(
               "ID"=>$user['ID'],
                "parlour_name"=>$user['parlour_name'],
                  "parlour_address"=>$user['parlour_address'],
                       "parlour_mobile"=>$user['parlour_mobile'],
                              "latitude"=>$user['latitude'],
                      "longitude"=>$user['longitude'],
                             "serviceRating"=>$user['serviceRating'],
                             "serviceTotalRating"=>$user['serviceTotalRating'],  
                   "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],

             );
array_push($response["new"], $jsonRow);
}

 $allStylist=mysqli_query($conn, "SELECT s.ID, s.crew_name, s.crew_detail, s.crew_pic,r.ID AS `ParlourID`,r.parlour_name  AS `parlour_name`,  s.service , s.available,s.Rating, s.TotalRating FROM `specialist_detail`s INNER JOIN salon_registration r ON s.ParlourID=r.ID  WHERE  r.isVerified=1 AND s.available=1 ORDER BY ID DESC LIMIT 5");



  while ($user = mysqli_fetch_assoc($allStylist)) {
$jsonRow=array(
               "ID"=>$user['ID'],
                "ParlourID"=>$user['ParlourID'],
                  "crew_name"=>$user['crew_name'],
                       "service"=>$user['service'],
                                 "parlour_name"=>$user['parlour_name'],
                                            "Rating"=>$user['Rating'],
                             "TotalRating"=>$user['TotalRating'], 
                   "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'crew_images'.'/'.$user['crew_pic'],

             );
array_push($response["allStylist"], $jsonRow);
}

 $popularStylist=mysqli_query($conn, "SELECT s.ID, s.crew_name, s.crew_detail, s.crew_pic,r.ID AS `ParlourID`,r.parlour_name  AS `parlour_name`,  s.service , s.available,s.Rating, s.TotalRating FROM `specialist_detail`s INNER JOIN salon_registration r ON s.ParlourID=r.ID  WHERE  s.isPopular=1 AND r.isVerified=1 AND s.available=1");



  while ($user = mysqli_fetch_assoc($popularStylist)) {
$jsonRow=array(
               "ID"=>$user['ID'],
                "ParlourID"=>$user['ParlourID'],
                  "crew_name"=>$user['crew_name'],
                       "service"=>$user['service'],
                                    "parlour_name"=>$user['parlour_name'],
                                       "Rating"=>$user['Rating'],
                             "TotalRating"=>$user['TotalRating'],  
                   "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'crew_images'.'/'.$user['crew_pic'],

             );
array_push($response["popularStylist"], $jsonRow);
}

 $deals=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`, `Reference_Code`,`serviceTotalRating`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND discountAmt!=0 ORDER BY ID LIMIT 4");



  while ($user = mysqli_fetch_assoc($deals)) {
$jsonRow=array(
              "ID"=>$user['ID'],
                "parlour_name"=>$user['parlour_name'],
                  "discountAmt"=>$user['discountAmt'],
                       "parlour_mobile"=>$user['parlour_mobile'],
                       "serviceRating"=>$user['serviceRating'],
                                  "latitude"=>$user['latitude'],
                      "longitude"=>$user['longitude'],
                             "serviceTotalRating"=>$user['serviceTotalRating'],  
                   "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],

             );
array_push($response["deals"], $jsonRow);
}


 $distance=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`, `Reference_Code`,`serviceTotalRating`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 ");



  while ($user = mysqli_fetch_assoc($distance)) {
$jsonRow=array(
              "ID"=>$user['ID'],
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