<?php


header('Content-Type: application/json');
        require_once 'DB_Connect.php';
        // connecting to database
        $db = new Db_Connect();
        $con = $db->connect();



if(!$con){
   echo "Could not connect to DBMS";       
 }else {
   if (isset($_POST['id'])){


 
    $ID= $_POST['id'];
   
    $ID=test_input($ID);

    $city= $_POST['city'];
   
    $city=test_input($city);


   $server_ip="139.59.38.160";

  $response = array("parlours"=>array(),"secondaryservice"=>array(),"service"=>array(),"salons"=>array(),"popsalons"=>array(),"popularStylist"=>array(),"allStylist"=>array(),"crew"=>array(),"mostpopular"=>array());

  $result =$con->query("SELECT `ID`, `Name`, `Photo`, `isActive`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `Date`, `Time`, `User`, `IP` FROM `primary_services` WHERE ID='$ID'");
          
         if ($result->num_rows > 0) {
          while($row = $result->fetch_assoc()) {
          $cID=$row["Name"];
        }
         }

        if(strlen($city)!=0){
     $sql="SELECT   sr.serviceRating,sr.serviceTotalRating,sr.ID, sr.parlour_name,sr.Photo,sr.parlour_address,sr.ID AS ParlourID,f.Name,sr.latitude,sr.longitude, s.Service AS SecondaryService, sr.discountAmt FROM `servicesdetails` sd INNER JOIN final_services f ON f.ID=sd.FinalServiceID INNER JOIN secondary_service s ON f.SecondaryServiceID=s.ID INNER JOIN primary_services p ON p.ID=s.IDPrimaryService INNER JOIN salon_registration sr ON sr.ID=sd.SalonID WHERE p.ID='$ID' AND p.isActive=1 AND sr.isVerified=1 AND s.isActive=1 AND (UPPER(sr.parlour_city) LIKE '%$city%' OR UPPER(sr.parlour_locality) LIKE '%$city%') AND sr.PopularSalon=1 GROUP BY sr.ID ";
    }else{
     $sql="SELECT   sr.serviceRating,sr.serviceTotalRating,sr.ID, sr.parlour_name,sr.Photo,sr.parlour_address,sr.ID AS ParlourID,f.Name,sr.latitude,sr.longitude, s.Service AS SecondaryService, sr.discountAmt FROM `servicesdetails` sd INNER JOIN final_services f ON f.ID=sd.FinalServiceID INNER JOIN secondary_service s ON f.SecondaryServiceID=s.ID INNER JOIN primary_services p ON p.ID=s.IDPrimaryService INNER JOIN salon_registration sr ON sr.ID=sd.SalonID WHERE p.ID='$ID' AND p.isActive=1 AND sr.isVerified=1 AND s.isActive=1 AND sr.PopularSalon=1 GROUP BY sr.ID ";
   }
    


   $result = $con->query($sql);


  while ($user = mysqli_fetch_assoc($result)) {

$jsonRow=array(
               "ID"=>$user['ID'],

                "ParlourID"=>$user['ParlourID'],
                 "latitude"=>$user['latitude'],
                "longitude"=>$user['longitude'],
               "parlour_name"=>$user['parlour_name'],
                    "parlour_mobile"=>$user['parlour_mobile'],
                "parlour_address"=>$user['parlour_address'],
                "SecondaryService"=>$user['SecondaryService'],
               "Name"=>$user['Name'],
                 "discountAmt"=>$user['discountAmt'],
                     "latitude"=>$user['latitude'],
                      "longitude"=>$user['longitude'],
                        "serviceRating"=>$user['serviceRating'],
                             "serviceTotalRating"=>$user['serviceTotalRating'], 
                "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],
             
             );


array_push($response["popsalons"], $jsonRow);

}


  




  if(strlen($city)!=0){
          $mostpopular=mysqli_query($con, "SELECT c.ID, c.crew_name, c.crew_detail, c.crew_pic,p.parlour_mobile ,p.parlour_name ,  c.service , c.available,c.Rating, c.TotalRating, c.ParlourID  FROM `specialist_detail` c INNER JOIN salon_registration p ON p.ID=c.ParlourID WHERE  c.service LIKE '%$cID%' AND (UPPER(p.parlour_city) LIKE '%$city%' OR UPPER(p.parlour_locality) LIKE '%$city%') ORDER BY Rating DESC");
}else{
     $mostpopular=mysqli_query($con, "SELECT c.ID, c.crew_name, c.crew_detail, c.crew_pic,p.parlour_mobile ,p.parlour_name ,  c.service , c.available,c.Rating, c.TotalRating, c.ParlourID FROM `specialist_detail` c INNER JOIN salon_registration p ON p.ID=c.ParlourID WHERE  c.service LIKE '%$cID%'  ORDER BY Rating DESC");
}


  while ($user = mysqli_fetch_assoc($mostpopular)) {
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
array_push($response["mostpopular"], $jsonRow);
}




   if(strlen($city)!=0){
    $crew=mysqli_query($con, "SELECT s.ID, s.crew_name, s.crew_detail, s.crew_pic,s.ParlourID,r.parlour_name  AS `parlour_name`,  s.service , s.available,s.Rating, s.TotalRating FROM `specialist_detail`s INNER JOIN salon_registration r ON s.ParlourID=r.ID  WHERE  r.isVerified=1 AND s.available=1 AND (UPPER(r.parlour_city) LIKE '%$city%' OR UPPER(r.parlour_locality) LIKE '%$city%') ORDER BY s.Rating DESC");
}else{
    $crew=mysqli_query($con, "SELECT s.ID, s.crew_name, s.crew_detail, s.crew_pic,s.ParlourID,r.parlour_name  AS `parlour_name`,  s.service , s.available,s.Rating, s.TotalRating FROM `specialist_detail`s INNER JOIN salon_registration r ON s.ParlourID=r.ID  WHERE  r.isVerified=1 AND s.available=1 ORDER BY s.Rating DESC ");
}
    


  while ($user = mysqli_fetch_assoc($crew)) {

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


array_push($response["crew"], $jsonRow);


}

    $parlours=mysqli_query($conn, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`,serviceTotalRating, `Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0");


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
                                "city"=>$user['parlour_city'],
                            "locality"=>$user['parlour_locality'], 
             "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],
             );
array_push($response["parlours"], $jsonRow);
}

 
$sql="SELECT `ID`, `IDPrimaryService`, `Service`, `Photo`, `isActive`, `Ladies`, `Gents`, `Bridal`, `Kids`, `Tattoo`, `Date` FROM `secondary_service` WHERE `IDPrimaryService`='$ID' AND isActive=1";
    


$result = $con->query($sql);


  while ($user = mysqli_fetch_assoc($result)) {

$jsonRow=array(
               "ID"=>$user['ID'],

                "Service"=>$user['Service'],
                 "Ladies"=>$user['Ladies'],
                "Gents"=>$user['Gents'],
               "Bridal"=>$user['Bridal'],
                "Kids"=>$user['Kids'],
        
             
             );


array_push($response["secondaryservice"], $jsonRow);

}
   

         if(strlen($city)!=0){
$sql="SELECT   sr.serviceRating,sr.serviceTotalRating,sr.ID, sr.parlour_name,sr.Photo,sr.parlour_address,sr.ID AS ParlourID,f.Name,sr.latitude,sr.longitude, s.Service AS SecondaryService FROM `servicesdetails` sd INNER JOIN final_services f ON f.ID=sd.FinalServiceID INNER JOIN secondary_service s ON f.SecondaryServiceID=s.ID INNER JOIN primary_services p ON p.ID=s.IDPrimaryService INNER JOIN salon_registration sr ON sr.ID=sd.SalonID WHERE p.ID='$ID' AND p.isActive=1 AND sr.isVerified=1 AND s.isActive=1 AND (UPPER(sr.parlour_city) LIKE '%$city%' OR UPPER(sr.parlour_locality) LIKE '%$city%') ";
}else{
  $sql="SELECT   sr.serviceRating,sr.serviceTotalRating,sr.ID, sr.parlour_name,sr.Photo,sr.parlour_address,sr.ID AS ParlourID,f.Name,sr.latitude,sr.longitude, s.Service AS SecondaryService FROM `servicesdetails` sd INNER JOIN final_services f ON f.ID=sd.FinalServiceID INNER JOIN secondary_service s ON f.SecondaryServiceID=s.ID INNER JOIN primary_services p ON p.ID=s.IDPrimaryService INNER JOIN salon_registration sr ON sr.ID=sd.SalonID WHERE p.ID='$ID' AND p.isActive=1 AND sr.isVerified=1 AND s.isActive=1 ";
}
  	


$result = $con->query($sql);


  while ($user = mysqli_fetch_assoc($result)) {

$jsonRow=array(
               "ID"=>$user['ID'],

                "ParlourID"=>$user['ParlourID'],
                 "latitude"=>$user['latitude'],
                "longitude"=>$user['longitude'],
               "parlour_name"=>$user['parlour_name'],
                "parlour_address"=>$user['parlour_address'],
                "SecondaryService"=>$user['SecondaryService'],
               "Name"=>$user['Name'],
                        "serviceRating"=>$user['serviceRating'],
                             "serviceTotalRating"=>$user['serviceTotalRating'], 
                "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],
             
             );


array_push($response["service"], $jsonRow);

}

 $popularStylist=mysqli_query($con, "SELECT `ID`, `crew_name`, `crew_detail`, `crew_pic`,`ParlourID`,(SELECT parlour_name FROM  salon_registration WHERE ID=s.ParlourID) AS `parlour_name`,  `service` , `available`,`Rating`, `TotalRating` FROM `specialist_detail`s WHERE  `isPopular`=1 ORDER BY Rating DESC");



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


$allStylist=mysqli_query($con, "SELECT `ID`, `crew_name`, `crew_detail`, `crew_pic`, `ParlourID`,(SELECT parlour_name FROM  salon_registration WHERE ID=s.ParlourID) AS `parlour_name`,  `service` , `available`,`Rating`, `TotalRating` FROM `specialist_detail`s ORDER BY Rating DESC ");



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


   if($ID==1){
    if(strlen($city)!=0){
    $salons=mysqli_query($con, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`,`serviceTotalRating`, `Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND discountAmt!=0 AND (UPPER(parlour_city) LIKE '%$city%' OR UPPER(parlour_locality) LIKE '%$city%')  ORDER BY discountAmt DESC ");
    }else{
          $salons=mysqli_query($con, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`,`serviceTotalRating`, `Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND discountAmt!=0  ORDER BY discountAmt DESC ");
    }



 while ($user = mysqli_fetch_assoc($salons)) {
$jsonRow=array(
               "ID"=>$user['ID'],
              "parlour_name"=>$user['parlour_name'],
                  "parlour_mobile"=>$user['parlour_mobile'],
                           "latitude"=>$user['latitude'],
                      "longitude"=>$user['longitude'],
                       "parlour_address"=>$user['parlour_address'],
                              "discountAmt"=>$user['discountAmt'],
                                    "serviceRating"=>$user['serviceRating'],
                             "serviceTotalRating"=>$user['serviceTotalRating'],  
                                       "city"=>$user['parlour_city'],
                            "locality"=>$user['parlour_locality'], 
             "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],
             );
array_push($response["salons"], $jsonRow);
}
   }elseif ($ID==2) {
        if(strlen($city)!=0){
    $salons=mysqli_query($con, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`,`serviceTotalRating`, `Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0  AND (UPPER(parlour_city) LIKE '%$city%' OR UPPER(parlour_locality) LIKE '%$city%')  ORDER BY serviceRating DESC ");
    }else{
     $salons=mysqli_query($con, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`,`serviceTotalRating`, `Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 AND PopularSalon!=0  ORDER BY serviceRating DESC");
   }


 while ($user = mysqli_fetch_assoc($salons)) {
$jsonRow=array(
               "ID"=>$user['ID'],
              "parlour_name"=>$user['parlour_name'],
                  "parlour_mobile"=>$user['parlour_mobile'],
                           "latitude"=>$user['latitude'],
                      "longitude"=>$user['longitude'],
                       "parlour_address"=>$user['parlour_address'],
                              "discountAmt"=>$user['discountAmt'],
                                    "serviceRating"=>$user['serviceRating'],
                             "serviceTotalRating"=>$user['serviceTotalRating'], 
                                       "city"=>$user['parlour_city'],
                            "locality"=>$user['parlour_locality'],  
             "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],
             );
array_push($response["salons"], $jsonRow);
}
   }    else{
        if(strlen($city)!=0){
    $salons=mysqli_query($con, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`,`serviceTotalRating`, `Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0  AND (UPPER(parlour_city) LIKE '%$city%' OR UPPER(parlour_locality) LIKE '%$city%') ORDER BY serviceRating DESC ");
    }else{
    $salons=mysqli_query($con, "SELECT  `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, `service_location`, `latitude`, `longitude`, `isVerified`, `VerifiedBy`, `VerifiedDate`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `discountType`, `discountAmt`, `PopularSalon`, `serviceRating`,`serviceTotalRating`, `Reference_Code`, `FirebaseToken`, `loginDate`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` WHERE isVerified=1 AND isDeleted=0 AND isHold=0 ORDER BY serviceRating DESC");
  }


 while ($user = mysqli_fetch_assoc($salons)) {
$jsonRow=array(
               "ID"=>$user['ID'],
              "parlour_name"=>$user['parlour_name'],
                  "parlour_mobile"=>$user['parlour_mobile'],
                           "latitude"=>$user['latitude'],
                      "longitude"=>$user['longitude'],
                       "parlour_address"=>$user['parlour_address'],
                              "parlour_address"=>$user['parlour_address'],
                                  "discountAmt"=>$user['discountAmt'],
                                        "serviceRating"=>$user['serviceRating'],
                             "serviceTotalRating"=>$user['serviceTotalRating'], 
                                       "city"=>$user['parlour_city'],
                            "locality"=>$user['parlour_locality'],  
             "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'salon_images'.'/'.$user['Photo'],
             );
array_push($response["salons"], $jsonRow);
}
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