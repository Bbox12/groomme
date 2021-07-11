<?php


header('Content-Type: application/json');
        require_once 'DB_Connect.php';
        // connecting to database
        $db = new Db_Connect();
        $con = $db->connect();


 date_default_timezone_set('Africa/Johannesburg');
        $hour=date("H:i:s");
        $date=date("d-m-Y");
         $server_ip="139.59.38.160";

if(!$con){
   echo "Could not connect to DBMS";       
 }else {
   if (isset($_POST['id'])){


 
    $ID= $_POST['id'];
   
    $ID=test_input($ID);


    $role= $_POST['role'];
   
    $role=test_input($role);



    if (isset($_POST['token'])){
    $token= $_POST['token'];
    $token=test_input($token);
     $update=mysqli_query($con, "UPDATE salon_registration SET FirebaseToken='$token',loginDate='$date' WHERE ID='$ID' AND isDeleted=0 ");

}else{
  $token="none";
}



//        echo("Error description: " . mysqli_error($con));


  $response = array("users"=>array(),"timings"=>array(),"version"=>array(),"crew"=>array(),"timeslot"=>array(),"bookings"=>array(),"login"=>array(),"salonpresentbooking"=>array(),"customerpresentbooking"=>array(),"review"=>array(),"sumreview"=>array(),"msg"=>array());



    if (isset($_POST['otp'])){
    $otp= $_POST['otp'];
    $otp=test_input($otp);



  $result =$con->query("SELECT ID FROM  store_booking WHERE OrderID='$otp'");
          
         if ($result->num_rows > 0) {
          while($row = $result->fetch_assoc()) {
          $cID=$row["ID"];
        }
         }


$sql="SELECT `ID`, `IDBooking`, `Name`,`PhoneNo`,`IDUser`,`IDSalon`, `Msg`, `Date` FROM `message` WHERE IDBooking='$cID'";
    


$result = $con->query($sql);


  while ($user = mysqli_fetch_assoc($result)) {

$jsonRow=array(
               "ID"=>$user['ID'],
               "Name"=>$user['Name'],
                  "PhoneNo"=>$user['PhoneNo'],
                               "IDUser"=>$user['IDUser'],
              "IDSalon"=>$user['IDSalon'],
               "Msg"=>$user['Msg'],
              "Date"=>$user['Date'],
             
             );


array_push($response["msg"], $jsonRow);


}

}else{
  $otp="none";
}




  $result =$con->query("SELECT ID FROM  salon_registration WHERE ID='$ID'");
          
         if ($result->num_rows > 0) {
          while($row = $result->fetch_assoc()) {
          $uID=$row["ID"];
        }
         $result =$con->query("UPDATE salon_registration SET   loginDate='$date' WHERE parlour_mobile='$uID'");
         }


$sql="SELECT `ID`, `parlour_name`, `parlour_about`, `Photo`, `parlour_email`, `parlour_mobile`, `parlour_registration`, `parlour_address`, `parlour_city`, `parlour_locality`, `parlour_pin`, (SELECT Location FROM service_location_at WHERE ID=p.service_location ) AS `service_location` , `latitude`, `longitude`, `isVerified`, `isHold`, `isDeleted`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`,`serviceRating`,`serviceTotalRating`, `discountType`, `discountAmt`, `Reference_Code`, `FirebaseToken`, `AppInstallation_Date`, `AppInstallation_Time`, `isSechedule`, `isSpecialist`, `isLocation`, `isServiceAt`, `Date`, `Time`, `IP` FROM `salon_registration` p WHERE ID='$uID'";
    


$result = $con->query($sql);


  while ($user = mysqli_fetch_assoc($result)) {

$jsonRow=array(
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
    "discountAmt"=>$user['discountAmt'],  
     "serviceRating"=>$user['serviceRating'],  
      "serviceTotalRating"=>$user['serviceTotalRating'],  
             );


array_push($response["users"], $jsonRow);


}


         $sql="SELECT b.ID, b.OrderDate, b.OrderID, b.IDuser, b.IDsalon, sd.crew_name, t.Slot,t.ActualTime, b.IDserviceAt, b.Discount, b.NoofItems, b.Payable, b.addressd, b.houseno, b.landmark, b.Date, b.Time,b.Time,b.isAccepted,b.isRunning,b.isCompleted,b.isCancelled, u.PhoneNo, u.Name, u.Email,u.Photo, sr.parlour_name, sr.Photo AS parlour_photo ,sr.parlour_mobile as parlour_mobile,sr.latitude AS Parlour_latitude,sr.longitude AS Parlour_longitude,b.Latitude AS Home_latitude,b.Longitude AS Home_longitude    FROM `store_booking` b INNER JOIN user_details u   ON u.ID=b.IDuser INNER JOIN timeslot t ON t.ID=b.IDslot INNER JOIN specialist_detail sd ON sd.ID=b.IDspecialist INNER JOIN salon_registration sr ON sr.ID=b.IDsalon WHERE b.IDSalon='$uID' AND b.isAccepted=1 AND b.isCancelled=0 AND b.isCompleted=0";
    


$result = $con->query($sql);


  while ($user = mysqli_fetch_assoc($result)) {

$jsonRow=array(
               "ID"=>$user['ID'],
               "OTP"=>$user['OrderID'],
               "Discount"=>$user['Discount'],
              "NoofItems"=>$user['NoofItems'],
             "Payable"=>$user['Payable'], 
                  "addressd"=>$user['addressd'],
               "houseno"=>$user['houseno'],
              "landmark"=>$user['landmark'],  
               "OrderDate"=>$user['OrderDate'],
               "crew_name"=>$user['crew_name'],
                     "ActualTime"=>$user['ActualTime'],
                       "parlour_name"=>$user['parlour_name'],
                               "parlour_mobile"=>$user['parlour_mobile'],
              "Slot"=>$user['Slot'],  
                "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'.'user'.'/'.$user["Photo"], 
                   "PPhoto"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'.'salon_images'.'/'.$user["parlour_photo"], 
                       "IDserviceAt"=>$user['IDserviceAt'],  
                             "name"=>$user['Name'],  
                              "isAccepted"=>$user['isAccepted'],  
               "isRunning"=>$user['isRunning'],
               "isCompleted"=>$user['isCompleted'], 
                  "isCancelled"=>$user['isCancelled'],  
                           "Parlour_latitude"=>$user['Parlour_latitude'],
              "Parlour_longitude"=>$user['Parlour_longitude'],  
               "Home_latitude"=>$user['Home_latitude'],
               "Home_longitude"=>$user['Home_longitude'], 
             );


array_push($response["salonpresentbooking"], $jsonRow);


}



         $sql="SELECT b.ID, b.OrderDate, b.OrderID, b.IDuser, b.IDsalon, sd.crew_name, t.Slot,t.ActualTime, b.IDserviceAt, b.Discount, b.NoofItems, b.Payable, b.addressd, b.houseno, b.landmark, b.Date, b.Time,b.isAccepted,b.isRunning,b.isCompleted,b.isCancelled, u.PhoneNo, u.Name,u.PhoneNo, u.Email,u.Photo, sr.parlour_name, sr.Photo AS parlour_photo ,sr.parlour_mobile as parlour_mobile,sr.latitude AS Parlour_latitude,sr.longitude AS Parlour_longitude,b.Latitude AS Home_latitude,b.Longitude AS Home_longitude    FROM `store_booking` b INNER JOIN user_details u   ON u.ID=b.IDuser INNER JOIN timeslot t ON t.ID=b.IDslot INNER JOIN specialist_detail sd ON sd.ID=b.IDspecialist INNER JOIN salon_registration sr ON sr.ID=b.IDsalon WHERE b.OrderID='$ID'  ";
    


$result = $con->query($sql);


  while ($user = mysqli_fetch_assoc($result)) {

$jsonRow=array(
               "ID"=>$user['ID'],
               "OTP"=>$user['OrderID'],
               "Discount"=>$user['Discount'],
              "NoofItems"=>$user['NoofItems'],
             "Payable"=>$user['Payable'], 
                  "addressd"=>$user['addressd'],
               "houseno"=>$user['houseno'],
              "landmark"=>$user['landmark'],  
               "OrderDate"=>$user['OrderDate'],
               "crew_name"=>$user['crew_name'],
                     "ActualTime"=>$user['ActualTime'],
                       "parlour_name"=>$user['parlour_name'],
                               "parlour_mobile"=>$user['parlour_mobile'],
              "Slot"=>$user['Slot'],  
                "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'.'user'.'/'.$user["Photo"], 
                   "PPhoto"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'.'salon_images'.'/'.$user["parlour_photo"], 
                       "IDserviceAt"=>$user['IDserviceAt'],  
                             "name"=>$user['Name'], 
                                       "PhoneNo"=>$user['PhoneNo'], 
                                                               "isAccepted"=>$user['isAccepted'],  
               "isRunning"=>$user['isRunning'],
               "isCompleted"=>$user['isCompleted'], 
                  "isCancelled"=>$user['isCancelled'], 
                            "Parlour_latitude"=>$user['Parlour_latitude'],
              "Parlour_longitude"=>$user['Parlour_longitude'],  
               "Home_latitude"=>$user['Home_latitude'],
               "Home_longitude"=>$user['Home_longitude'],   
             );


array_push($response["bookings"], $jsonRow);


}

  
$login=$con->query( "SELECT SUM(r.rating_c) AS rating_c,SUM(r.rating_s) AS rating_s FROM storeReview r INNER JOIN store_booking b ON b.ID=r.IDBooking WHERE b.IDSalon='$uID'");

          while ($user1 = mysqli_fetch_assoc($login)) {

$jsonRow_201=array(
             
                             "rating_c"=>$user1["rating_c"],
                             "rating_s"=>$user1["rating_s"],
                  
                          
                                 
 );

array_push($response["sumreview"], $jsonRow_201);
  
}

$login=$con->query( "SELECT u.Name,r.SalonReview ,r.rating_c,r.rating_s FROM storeReview r INNER JOIN store_booking b ON b.ID=r.IDBooking INNER JOIN user_details u ON u.ID=b.IDuser WHERE b.IDSalon='$uID'");

          while ($user1 = mysqli_fetch_assoc($login)) {

$jsonRow_201=array(
             
                             "Name"=>$user1["Name"],
                         "rating_c"=>$user1["rating_c"],
                             "rating_s"=>$user1["rating_s"],
                    
                          "SalonReview"=>$user1["SalonReview"],
                          
                                 
 );

array_push($response["review"], $jsonRow_201);
  
}



$sql="SELECT `ID`, `salonID`, `sun_open_close`, `sun_open_time`, `sun_close_time`, `mon_open_close`, `mon_open_time`, `mon_close_time`, `tue_open_close`, `tue_open_time`, `tue_close_time`, `wed_open_close`, `wed_open_time`, `wed_close_time`, `thr_open_close`, `thr_open_time`, `thr_close_time`, `fri_open_close`, `fri_open_time`, `fri_close_time`, `sat_open_close`, `sat_open_time`, `sat_close_time`, `Date`, `Time` FROM `schedule_parlour` WHERE salonID='$uID' ORDER BY ID DESC LIMIT 1";

$result = $con->query($sql);


  while ($user = mysqli_fetch_assoc($result)) {

$jsonRow=array(
               "ID"=>$user['ID'],
               "sun_open_close"=>$user['sun_open_close'],
               "sun_open_time"=>$user['sun_open_time'],
              "sun_close_time"=>$user['sun_close_time'],
             "mon_open_close"=>$user['mon_open_close'], 
                  "mon_open_time"=>$user['mon_open_time'],
               "mon_close_time"=>$user['mon_close_time'],
              "tue_open_close"=>$user['tue_open_close'],  
               "tue_open_time"=>$user['tue_open_time'],
               "tue_close_time"=>$user['tue_close_time'],
              "wed_open_close"=>$user['wed_open_close'],   
                 "wed_open_time"=>$user['wed_open_time'],
              "wed_close_time"=>$user['wed_close_time'],  
               "thr_open_close"=>$user['thr_open_close'],
               "thr_open_time"=>$user['thr_open_time'],
              "thr_close_time"=>$user['thr_close_time'],   
              "fri_open_close"=>$user['fri_open_close'],
              "fri_open_time"=>$user['fri_open_time'],   

              "fri_close_time"=>$user['fri_close_time'],  
               "sat_open_close"=>$user['sat_open_close'],
               "sat_open_time"=>$user['sat_open_time'],
              "sat_close_time"=>$user['sat_close_time'],   
              
             );


array_push($response["timings"], $jsonRow);


}


$sql="SELECT `ID`, `crew_name`, `crew_detail`, `crew_pic`, (SELECT parlour_mobile FROM  salon_registration WHERE ID=s.ParlourID) AS `ParlourID`,  `service` , `available` FROM `specialist_detail`s WHERE ParlourID='$uID'";
    


$result = $con->query($sql);


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


$sql="SELECT `ID`, `Slot`,`ActualTime`, `Date`, `IDSalon` FROM `timeslot`";
    


$result = $con->query($sql);


  while ($user = mysqli_fetch_assoc($result)) {

$jsonRow=array(
               "ID"=>$user['ID'],
               "Slot"=>$user['Slot'],
                     "ActualTime"=>$user['ActualTime'],
                      
             );


array_push($response["timeslot"], $jsonRow);


}


}

$result =$con->query("SELECT ID FROM  user_details WHERE ID='$ID'");
          
         if ($result->num_rows > 0) {
          while($row = $result->fetch_assoc()) {
          $cID=$row["ID"];
        }
                 $sql="SELECT b.ID, b.OrderDate, b.OrderID, b.IDuser, b.IDsalon, sd.crew_name, t.Slot,t.ActualTime, b.IDserviceAt, b.Discount, b.NoofItems, b.Payable, b.addressd, b.houseno, b.landmark, b.Date, b.Time,b.Time,b.isAccepted,b.isRunning,b.isCompleted,b.isCancelled, u.PhoneNo, u.Name, u.Email,u.Photo, sr.parlour_name, sr.Photo AS parlour_photo ,sr.parlour_mobile as parlour_mobile    FROM `store_booking` b INNER JOIN user_details u   ON u.ID=b.IDuser INNER JOIN timeslot t ON t.ID=b.IDslot INNER JOIN specialist_detail sd ON sd.ID=b.IDspecialist INNER JOIN salon_registration sr ON sr.ID=b.IDsalon WHERE b.IDuser='$cID'  AND b.isCompleted=0 AND b.isRunning=0 AND b.isCancelled=0 AND b.UserReview=0  ";
    


$result = $con->query($sql);


  while ($user = mysqli_fetch_assoc($result)) {

$jsonRow=array(
               "ID"=>$user['ID'],
               "OTP"=>$user['OrderID'],
               "Discount"=>$user['Discount'],
              "NoofItems"=>$user['NoofItems'],
             "Payable"=>$user['Payable'], 
                  "addressd"=>$user['addressd'],
               "houseno"=>$user['houseno'],
              "landmark"=>$user['landmark'],  
               "OrderDate"=>$user['OrderDate'],
               "crew_name"=>$user['crew_name'],
                     "ActualTime"=>$user['ActualTime'],
                       "parlour_name"=>$user['parlour_name'],
                               "parlour_mobile"=>$user['parlour_mobile'],
              "Slot"=>$user['Slot'],  
                "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'.'user'.'/'.$user["Photo"], 
                   "PPhoto"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'.'salon_images'.'/'.$user["parlour_photo"], 
                       "IDserviceAt"=>$user['IDserviceAt'],  
                             "name"=>$user['Name'], 
                              "isAccepted"=>$user['isAccepted'],  
               "isRunning"=>$user['isRunning'],
               "isCompleted"=>$user['isCompleted'], 
                  "isCancelled"=>$user['isCancelled'],   
             );


array_push($response["customerpresentbooking"], $jsonRow);


}
         }

         $login=$con->query( "SELECT `ID`, `Name`, `Email`, `Photo`, `PhoneNo`,`Gender`,`DOB`, `Latitude`, `Longitude`, `Is_Blocked`, `User_Referrence_Code`, `FirebaseToken`, `Date`, `Time`, `User`, `IP` FROM `user_details` WHERE ID='$ID'");

          while ($user1 = mysqli_fetch_assoc($login)) {

$jsonRow_201=array(
             
                             "Name"=>$user1["Name"],
                             "PhoneNo"=>$user1["PhoneNo"],
                         "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'.'user'.'/'.$user1["Photo"],
                          "Gender"=>$user1["Gender"],
                               "Email"=>$user1["Email"],
                                "Date_of_Birth"=>$user1["DOB"],
                                "User_Referrence_Code"=>$user1["User_Referrence_Code"],
                                 "date"=>$date,
                                 
 );

array_push($response["login"], $jsonRow_201);
  
}









 
        

  $sql="SELECT `ID`, `Version`, `Importance`,`cancellationcharge` FROM `app_version`";
  	




$result = $con->query($sql);

          while ($user1 = mysqli_fetch_assoc($result)) {
   $jsonRow_201=array(
  "Version"=>$user1["Version"],
  "Importance"=>$user1["Importance"],
    "cancellationcharge"=>$user1["cancellationcharge"],
  "date"=>$date,
  "time"=>$hour,
  "distance"=>'10',
    "youtube"=>'yOF28NBLF08',
     "youtubecategory"=>'1',
     "_titleYoutube"=>'STAY SAFE FROM COVID-19',
         "Ladies"=>'0',
     "SEX"=>'0',
             );
array_push($response["version"], $jsonRow_201);
  
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