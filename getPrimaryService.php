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



  $response = array("service"=>array());



   

 
$sql="SELECT `ID`, `Name`, `Photo`, `isActive`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `Date`, `Time`, `User`, `IP` FROM `primary_services` WHERE ID='$id'";
  	


$result = $con->query($sql);


  while ($user = mysqli_fetch_assoc($result)) {

$jsonRow=array(
               "ID"=>$user['ID'],

                "Name"=>$user['Name'],
                   "Ladies"=>$user['Ladies'],
                      "Gents"=>$user['Gents'],
                         "Bridal"=>$user['Bridal'],
  
             
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