<?php


header('Content-Type: application/json');
$con=mysqli_connect("localhost","root","9954059912aA","parlour_detail");

if(!$con){
   echo "Could not connect to DBMS";       
 }else {

 $server_ip="139.59.38.160";

$response = array("users"=>array());


 

 $result = mysqli_query($con, "SELECT * FROM parlour_service");
    
if($result){
 while ($user = mysqli_fetch_assoc($result)) {
 
 
$jsonRow=array(
               "id"=>$user["idservice_details"],
               "detail"=>$user["pservice"],
              
  
             "pic"=>'http://' . $server_ip . '/' . 'AndroidFileUpload' . '/' .'android_login_api'.'/'. 'uploads'.'/'.'Service'.'/'.$user["pservice_pic"],
           
             );


array_push($response["users"], $jsonRow);

}



}


 echo json_encode($response);

}

 

?>