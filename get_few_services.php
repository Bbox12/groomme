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

  $response = array("fewservice"=>array(),"service"=>array(),"secondaryservice"=>array());

 $result =$con->query("SELECT ID FROM  salon_registration WHERE parlour_mobile='$ID'");
          
         if ($result->num_rows > 0) {
          while($row = $result->fetch_assoc()) {
          $uID=$row["ID"];
        }
         }

  $sql="SELECT s.ID,s.SalonID,f.Name,ss.Service,s.Details_salon,s.Price_salon,s.Time_salon,s.Details_home,s.Price_home,s.Time_home,s.Highlighted1,s.Highlighted2,s.Highlighted3,s.Highlighted4,s.Highlighted5,s.Date,s.Time,s.User,s.IP FROM servicesdetails s INNER JOIN final_services f ON s.FinalServiceID=f.ID INNER JOIN secondary_service ss ON f.SecondaryServiceID=ss.ID WHERE s.SalonID='$uID' LIMIT 5";
    


$result = $con->query($sql);


  while ($user = mysqli_fetch_assoc($result)) {

$jsonRow=array(
               "ID"=>$user['ID'],
               "Service"=>$user['Service'],
                "Details_salon"=>$user['Details_salon'],
               "Price_salon"=>$user['Price_salon'],
                "Price_home"=>$user['Price_home'],
               "Details_home"=>$user['Details_home'],
                "Time_salon"=>$user['Time_salon'],
               "Time_home"=>$user['Time_home'],
                "Name"=>$user['Name'],
            
             );


array_push($response["fewservice"], $jsonRow);

} 

 
$sql="SELECT s.ID,s.SalonID,f.Name,ss.Service,s.Details_salon,s.Price_salon,s.Time_salon,s.Details_home,s.Price_home,s.Time_home,s.Highlighted1,s.Highlighted2,s.Highlighted3,s.Highlighted4,s.Highlighted5,s.Date,s.Time,s.User,s.IP FROM servicesdetails s INNER JOIN final_services f ON s.FinalServiceID=f.ID INNER JOIN secondary_service ss ON f.SecondaryServiceID=ss.ID WHERE s.SalonID='$uID'";
  	


$result = $con->query($sql);


  while ($user = mysqli_fetch_assoc($result)) {

$jsonRow=array(
               "ID"=>$user['ID'],
               "Service"=>$user['Service'],
                "Details_salon"=>$user['Details_salon'],
               "Price_salon"=>$user['Price_salon'],
                "Price_home"=>$user['Price_home'],
               "Details_home"=>$user['Details_home'],
                "Time_salon"=>$user['Time_salon'],
               "Time_home"=>$user['Time_home'],
                "Name"=>$user['Name'],
            
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