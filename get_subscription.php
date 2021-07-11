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
  

  $response = array("subscription"=>array());






 $sql="SELECT `ID`, `Name`, `Details`, `Validity`, `Price`, `User`, `Date`, `Time` FROM `subscribe`";
    


$result = $con->query($sql);


  while ($user = mysqli_fetch_assoc($result)) {

$jsonRow=array(
               "ID"=>$user['ID'],
               "Name"=>$user['Name'],
               "Price"=>$user['Price'],
              "Validity"=>$user['Validity'],
                 "Details"=>$user['Details'],
  
             );


array_push($response["subscription"], $jsonRow);


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