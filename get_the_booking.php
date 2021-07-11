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
   if (isset($_POST['_mId'])){


 
    $mID= $_POST['_mId'];
   
    $mID=test_input($mID);

     $otp= $_POST['otp'];
   
    $otp=test_input($otp);



  $response = array("pastbookings"=>array());



  $result =$con->query("SELECT ID FROM  user_details WHERE ID='$mID'");
          
         if ($result->num_rows > 0) {
          while($row = $result->fetch_assoc()) {
          $uID=$row["ID"];
        }
         }

if($uID!=0){


        $sql="SELECT b.ID, b.OrderDate, b.OrderID, b.IDuser, b.IDsalon, sd.crew_name, t.Slot, b.IDserviceAt, b.Discount, b.NoofItems, b.Payable, b.addressd, b.houseno, b.landmark, b.Date, b.Time,b.isAccepted,b.isRunning,b.isCompleted,b.isCancelled, u.PhoneNo, u.Name, u.Email,u.Photo, sr.parlour_name, sr.Photo AS parlour_photo ,sr.parlour_mobile as parlour_mobile    FROM `store_booking` b INNER JOIN user_details u   ON u.ID=b.IDuser INNER JOIN timeslot t ON t.ID=b.IDslot INNER JOIN specialist_detail sd ON sd.ID=b.IDspecialist INNER JOIN salon_registration sr ON sr.ID=b.IDsalon WHERE b.IDuser='$uID' AND b.isAccepted=1 AND b.isCancelled=0 AND b.isRunning=0 AND b.isCompleted=1";
    


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


array_push($response["pastbookings"], $jsonRow);


}
         

}else{
    $result =$con->query("SELECT ID FROM  salon_registration WHERE ID='$mID'");
          
         if ($result->num_rows > 0) {
          while($row = $result->fetch_assoc()) {
          $uID=$row["ID"];
        }
         }



       

        $sql="SELECT b.ID, b.OrderDate, b.OrderID, b.IDuser, b.IDsalon, sd.crew_name, t.Slot, b.IDserviceAt, b.Discount, b.NoofItems, b.Payable, b.addressd, b.houseno, b.landmark, b.Date, b.Time,b.isAccepted,b.isRunning,b.isCompleted,b.isCancelled, u.PhoneNo, u.Name, u.Email,u.Photo, sr.parlour_name, sr.Photo AS parlour_photo ,sr.parlour_mobile as parlour_mobile    FROM `store_booking` b INNER JOIN user_details u   ON u.ID=b.IDuser INNER JOIN timeslot t ON t.ID=b.IDslot INNER JOIN specialist_detail sd ON sd.ID=b.IDspecialist INNER JOIN salon_registration sr ON sr.ID=b.IDsalon WHERE b.IDsalon='$uID' AND b.isAccepted=1 AND b.isCancelled=0 AND b.isRunning=1 AND b.isCompleted=0";
    


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
                                "isAccepted"=>$user['isAccepted'],  
               "isRunning"=>$user['isRunning'],
               "isCompleted"=>$user['isCompleted'], 
                  "isCancelled"=>$user['isCancelled'], 
             );


array_push($response["pastbookings"], $jsonRow);


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