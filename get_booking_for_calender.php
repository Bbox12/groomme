<?php


header('Content-Type: application/json');
        require_once 'DB_Connect.php';
        // connecting to database
        $db = new Db_Connect();
        $conn = $db->connect();
        

if(!$conn){
   echo "Could not connect to DBMS"; 
    }else { 
if (isset($_POST['mID'])){
 
    $mobile= $_POST['mID'];
   
    $mobile=test_input($mobile);
   $server_ip="139.59.38.160";

  
  $role= $_POST['role'];
   
    $role=test_input($role);

try{

$response = array("pastbookingsdates"=>array(),"pastbookings"=>array());
   if ($role==1) {

    $result =$conn->query("SELECT ID FROM  user_details WHERE ID='$mobile'");
          
         if ($result->num_rows > 0) {
          while($row = $result->fetch_assoc()) {
          $uID=$row["ID"];
        }
      }

       $pastbookingsdates=$conn->query( "SELECT b.OrderDate FROM `store_booking` b INNER JOIN user_details u   ON u.ID=b.IDuser INNER JOIN timeslot t ON t.ID=b.IDslot INNER JOIN specialist_detail sd ON sd.ID=b.IDspecialist INNER JOIN salon_registration sr ON sr.ID=b.IDsalon WHERE b.IDuser='$uID' GROUP BY b.OrderDate");

while ($user = mysqli_fetch_assoc($pastbookingsdates)) {

$jsonRow_201=array(
             
               "OrderDate"=>$user['OrderDate'],
              
 );

array_push($response["pastbookingsdates"], $jsonRow_201);
  
}

         $_students_attendence_record=$conn->query( "SELECT b.ID, b.OrderDate, b.OrderID, b.IDuser, b.IDsalon, sd.crew_name,sd.crew_pic, t.Slot, b.IDserviceAt, b.Discount, b.NoofItems, b.Payable, b.addressd, b.houseno, b.landmark, b.Date, b.Time,b.isAccepted,b.isRunning,b.isCompleted, b.isCancelled, u.PhoneNo, u.Name, u.Email,u.Photo, sr.parlour_name, sr.Photo AS parlour_photo ,sr.parlour_mobile as parlour_mobile    FROM `store_booking` b INNER JOIN user_details u   ON u.ID=b.IDuser INNER JOIN timeslot t ON t.ID=b.IDslot INNER JOIN specialist_detail sd ON sd.ID=b.IDspecialist INNER JOIN salon_registration sr ON sr.ID=b.IDsalon WHERE b.IDuser='$uID' ");

while ($user = mysqli_fetch_assoc($_students_attendence_record)) {

$jsonRow_201=array(
             
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
                  "crew_pic"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'.'crew_images'.'/'.$user["crew_pic"], 
                "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'.'user'.'/'.$user["Photo"], 
                   "PPhoto"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'.'salon_images'.'/'.$user["parlour_photo"], 
                       "IDserviceAt"=>$user['IDserviceAt'],  
                             "name"=>$user['Name'], 
                                              "isAccepted"=>$user['isAccepted'],  
               "isRunning"=>$user['isRunning'],
               "isCompleted"=>$user['isCompleted'],  
                 "isCancelled"=>$user['isCancelled'], 
 );

array_push($response["pastbookings"], $jsonRow_201);
  
}
         }else{
           $result =$conn->query("SELECT ID FROM  salon_registration WHERE ID='$mobile'");
          
         if ($result->num_rows > 0) {
          while($row = $result->fetch_assoc()) {
          $uID=$row["ID"];
        }

          $pastbookingsdates=$conn->query( "SELECT b.OrderDate FROM `store_booking` b INNER JOIN user_details u   ON u.ID=b.IDuser INNER JOIN timeslot t ON t.ID=b.IDslot INNER JOIN specialist_detail sd ON sd.ID=b.IDspecialist INNER JOIN salon_registration sr ON sr.ID=b.IDsalon WHERE b.IDsalon='$uID' GROUP BY b.OrderDate");

while ($user = mysqli_fetch_assoc($pastbookingsdates)) {

$jsonRow_201=array(
             
               "OrderDate"=>$user['OrderDate'],
              
 );

array_push($response["pastbookingsdates"], $jsonRow_201);
  
}

   
        $_students_attendence_record=$conn->query( "SELECT b.ID, b.OrderDate, b.OrderID, b.IDuser, b.IDsalon, sd.crew_name,sd.crew_pic, t.Slot, b.IDserviceAt, b.Discount, b.NoofItems, b.Payable, b.addressd, b.houseno, b.landmark, b.Date, b.Time,b.isAccepted,b.isRunning,b.isCompleted, b.isCancelled, u.PhoneNo, u.Name, u.Email,u.Photo, sr.parlour_name, sr.Photo AS parlour_photo ,sr.parlour_mobile as parlour_mobile    FROM `store_booking` b INNER JOIN user_details u   ON u.ID=b.IDuser INNER JOIN timeslot t ON t.ID=b.IDslot INNER JOIN specialist_detail sd ON sd.ID=b.IDspecialist INNER JOIN salon_registration sr ON sr.ID=b.IDsalon WHERE b.IDsalon='$uID' ");

while ($user = mysqli_fetch_assoc($_students_attendence_record)) {

$jsonRow_201=array(
             
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
                  "crew_pic"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'.'crew_images'.'/'.$user["crew_pic"], 
                "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'.'user'.'/'.$user["Photo"], 
                   "PPhoto"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'.'salon_images'.'/'.$user["parlour_photo"], 
                       "IDserviceAt"=>$user['IDserviceAt'],  
                             "name"=>$user['Name'], 
                                              "isAccepted"=>$user['isAccepted'],  
               "isRunning"=>$user['isRunning'],
               "isCompleted"=>$user['isCompleted'],  
                 "isCancelled"=>$user['isCancelled'], 
                                 
 );

array_push($response["pastbookings"], $jsonRow_201);
         }
         }
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