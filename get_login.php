<?php


header('Content-Type: application/json');
        require_once 'DB_Connect.php';
        // connecting to database
        $db = new Db_Connect();
        $conn = $db->connect();

 
if(!$conn){
   echo "Could not connect to DBMS"; 
    }else { 


if (isset($_POST['_mId'])){
 
    $mobile= $_POST['_mId'];
    
   
    $mobile=test_input($mobile);
  
    $date=date("Y-m-d");


try{
$server_ip="139.59.38.160";
  $response = array("login"=>array());
  $uID=0;


  

          $login=$conn->query( "SELECT `ID`, `Name`, `Email`, `Photo`, `PhoneNo`,`Gender`,`DOB`, `Latitude`, `Longitude`, `Is_Blocked`, `User_Referrence_Code`, `FirebaseToken`, `Date`, `Time`, `User`, `IP` FROM `user_details` WHERE ID='$mobile'");

          while ($user1 = mysqli_fetch_assoc($login)) {

$jsonRow_201=array(
             
                             "Name"=>$user1["Name"],
                             "PhoneNo"=>$user1["PhoneNo"],
                         "Photo"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'.'salon_images'.'/'.$user1["Photo"],
                          "Gender"=>$user1["Gender"],
                               "Email"=>$user1["Email"],
                                "Date_of_Birth"=>$user1["DOB"],
        
                                 "date"=>$date,
                                 
 );

array_push($response["login"], $jsonRow_201);
  
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