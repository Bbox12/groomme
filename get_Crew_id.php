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

  $response = array("users"=>array(),"service"=>array());


   
   $image = 'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'crew_images'.'/'.'image.jpg';
 
$sql="SELECT `ID`, `crew_name`, `crew_detail`, `crew_pic`, `ParlourID`, `service` , `available` FROM `specialist_detail`s WHERE ID='$ID'";
  	


$result = $con->query($sql);


  while ($user = mysqli_fetch_assoc($result)) {

$jsonRow=array(
               "ID"=>$user['ID'],
               "name"=>$user['crew_name'],
               "detail"=>$user['crew_detail'],
              "ParlourID"=>$user['ParlourID'],
              "available"=>$user['available'],
             "pic"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'crew_images'.'/'.$user['crew_pic'],
             "service"=>$user['service'],           
             );


array_push($response["users"], $jsonRow);


}

$sql_="SELECT `ID`, `Name`, `Photo`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `Date`, `Time`, `User`, `IP` FROM `primary_services`";
$result_= $con->query($sql_);

 while ($user = mysqli_fetch_assoc($result_)) {


$jsonRow=array(
               "id"=>$user["ID"],
               "Name"=>$user["Name"],
           
             );
array_push($response["service"], $jsonRow);

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