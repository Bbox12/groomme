<?php


header('Content-Type: application/json');
        require_once 'DB_Connect.php';
        // connecting to database
        $db = new Db_Connect();
        $con = $db->connect();



if(!$con){
   echo "Could not connect to DBMS";       
 }else {
   if (isset($_POST['mobile'])){


 
    $mobile= $_POST['mobile'];
   
    $mobile=test_input($mobile);


    $salon= $_POST['salon'];
   
    $salon=test_input($salon);
    


   $server_ip="139.59.38.160";

   $response = array("msg"=>array());

   $result =$con->query("SELECT ID FROM  salon_registration WHERE ID='$salon'");
          
         if ($result->num_rows > 0) {
          while($row = $result->fetch_assoc()) {
          $sID=$row["ID"];
        }
         }

          $result =$con->query("SELECT ID FROM  user_details WHERE ID='$mobile'");
          
         if ($result->num_rows > 0) {
          while($row = $result->fetch_assoc()) {
          $cID=$row["ID"];
        }
         }






             $sql="SELECT m.ID,m.Name,m.Msg,m.Date,m.PhoneNo,m.IDUser,m.IDSalon FROM `message` m INNER JOIN store_booking s ON s.ID=m.IDBooking WHERE s.IDuser='$cID' AND s.IDsalon='$sID' ORDER BY m.ID DESC";
    


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