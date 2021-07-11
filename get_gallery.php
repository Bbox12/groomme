<?php


header('Content-Type: application/json');
        require_once 'DB_Connect.php';
        // connecting to database
        $db = new Db_Connect();
        $con = $db->connect();




 
    $ID= $_POST['id'];
   
    $ID=test_input($ID);

    


 $server_ip="139.59.38.160";

  $response = array("users"=>array());

  $result =$con->query("SELECT ID FROM  salon_registration WHERE ID='$ID'");
          
         if ($result->num_rows > 0) {
          while($row = $result->fetch_assoc()) {
          $uID=$row["ID"];
        }
         }


 
$sql="SELECT `ID`, `IDsalon`, `Photo`, `Details`, `Date` FROM `salon_gallery` WHERE IDsalon='$uID'";
  	


$result = $con->query($sql);


  while ($user = mysqli_fetch_assoc($result)) {

$jsonRow=array(
               "ID"=>$user['ID'],
               "detail"=>$user['Details'],
              "ParlourID"=>$user['IDsalon'],
             "pic"=>'http://' . $server_ip . '/' . 'Groom' . '/' .'App'.'/'. 'gallery'.'/'.$user['Photo'],        
             );


array_push($response["users"], $jsonRow);


}




 echo json_encode($response);




 

 
 function test_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}     



?>