<?php

header('Content-Type: application/json');
        require_once 'DB_Connect.php';
        // connecting to database
        $db = new Db_Connect();
        $con = $db->connect();

if(!$con){
   echo "Could not connect to DBMS";       
 }else {

 $server_ip="139.59.38.160";

$response = array("service"=>array());


 

 $result = mysqli_query($con, "SELECT `ID`, `Name`, `Photo`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `Date`, `Time`, `User`, `IP` FROM `primary_services`");
    

 while ($user = mysqli_fetch_assoc($result)) {
 
 
$jsonRow=array(
               "ID"=>$user["ID"],
                "pic"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'Dashboard'.'/'. 'service'.'/'.$user['Photo'],
               "name"=>$user["Name"],
            
           
             );


array_push($response["service"], $jsonRow);

}






 echo json_encode($response);

}

 

?>