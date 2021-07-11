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

     if (isset($_POST['position'])){

     	$position=$_POST['position'];
   
}else{
	$position=0;
	
}



 $server_ip="139.59.38.160";

$response = array("users"=>array(),"service"=>array());

  $result =$con->query("SELECT ID FROM  parlour_detail WHERE parlour_mobile='$ID'");
          
         if ($result->num_rows > 0) {
          while($row = $result->fetch_assoc()) {
          $uID=$row["ID"];
        }
         }
   $check=0;
   $image = 'http://' . $server_ip . '/' . 'AndroidFileUpload' . '/' .'android_login_api'.'/'. 'uploads'.'/'.'image.jpg';
 

  if($position==0){
  	  $sql="SELECT `idcrew_detail`, `crew_name`, `crew_email`, `crew_mobile`, `crew_detail`, `crew_pic`, `position`, `ParlourID`, `service`, `available` FROM `crew_detail` WHERE ParlourID='$uID'";
  	}else{
  		  $sql="SELECT `idcrew_detail`, `crew_name`, `crew_email`, `crew_mobile`, `crew_detail`, `crew_pic`, `position`, `ParlourID`, `service`, `available` FROM `crew_detail` WHERE ParlourID='$uID' AND idcrew_detail='$position'";
  	}


$result = $con->query($sql);


  while ($user = mysqli_fetch_assoc($result)) {

$jsonRow=array(
               "id"=>$user['idcrew_detail'],
               "name"=>$user['crew_name'],
               "email"=>$user['crew_email'],
               "mobile"=>$user['crew_mobile'],
               "detail"=>$user['crew_detail'],
               "position"=>$user['position'],
              "relation"=>$user['parlour_detail_idparlour_detail'],
             "pic"=>'http://' . $server_ip . '/' . 'AndroidFileUpload' . '/' .'android_login_api'.'/'. 'uploads'.'/'.'crew'.'/'.$user['crew_pic'],
             "service"=>$user['service'],
             "available"=>$user['available'],
           
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