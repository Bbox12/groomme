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
   if (isset($_POST['otp'])){


 
    $otp= $_POST['otp'];
   
    $otp=test_input($otp);



  $response = array("bookingservices"=>array());




$result =$con->query("SELECT ID FROM  store_booking WHERE OrderID='$otp'");
          
         if ($result->num_rows > 0) {
          while($row = $result->fetch_assoc()) {
          $oID=$row["ID"];
        }
         }

 $sql="SELECT b.IDservice AS ID,f.Name, b.Price, b.Duration FROM `booking_details_service` b INNER JOIN servicesdetails s ON s.ID=b.IDservice INNER JOIN  final_services f ON f.ID=s.FinalServiceID WHERE b.IDbooking='$oID'";
    


$result = $con->query($sql);


  while ($user = mysqli_fetch_assoc($result)) {

$jsonRow=array(
               "ID"=>$user['ID'],
               "Name"=>$user['Name'],
               "Price"=>$user['Price'],
              "Duration"=>$user['Duration'],
          
             );


array_push($response["bookingservices"], $jsonRow);


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