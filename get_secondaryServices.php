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

    


 $server_ip="139.59.38.160";

  $response = array("service"=>array(),"secondaryservice"=>array());



 
$sql="SELECT `ID`, `IDPrimaryService`, `Service`, `Ladies`, `Gents`, `Bridal`, `Kids`, `Tattoo`, `Date` FROM `secondary_service` WHERE IDPrimaryService='$ID' AND isActive=1";
  	


$result = $con->query($sql);


  while ($user = mysqli_fetch_assoc($result)) {

$jsonRow=array(
               "ID"=>$user['ID'],
               "Service"=>$user['Service'],
             );


array_push($response["service"], $jsonRow);

}

$sql="SELECT `ID`, `SecondaryServiceID`, `Name`, `Image`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `Highlighted1`, `Highlighted2`, `Highlighted3`, `Highlighted4`, `Highlighted5`, `Date`, `Time`, `User`, `IP` FROM `final_services` WHERE isActive=1";
    


$result = $con->query($sql);


  while ($user = mysqli_fetch_assoc($result)) {

$jsonRow=array(
               "ID"=>$user['ID'],
               "Name"=>$user['Name'],
                 "SecondaryServiceID"=>$user['SecondaryServiceID'],
             );


array_push($response["secondaryservice"], $jsonRow);

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