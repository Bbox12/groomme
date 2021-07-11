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



  $response = array();



   

 
$sql="SELECT `ID`, `SecondaryServiceID`, `Name`, `Image`, `isActive`, `Ladies`, `Gents`, `Bridal`, `Tattoo`, `Kids`, `Highlighted1`, `Highlighted2`, `Highlighted3`, `Highlighted4`, `Highlighted5`, `Date`, `Time`, `User`, `IP` FROM `final_services` WHERE ID='$ID'";
  	


$result = $con->query($sql);


  while ($user = mysqli_fetch_assoc($result)) {

$jsonRow_201=array(
               "ID"=>$user['ID'],
                     "SecondaryServiceID"=>$user['SecondaryServiceID'],
                "Name"=>$user['Name'],
                   "Ladies"=>$user['Ladies'],
                      "Gents"=>$user['Gents'],
                         "Bridal"=>$user['Bridal'],
                       "Kids"=>$user['Kids'],
             );


array_push($response, $jsonRow_201);

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