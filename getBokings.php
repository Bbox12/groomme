<?php


header('Content-Type: application/json');
        require_once 'DB_Connect.php';
        // connecting to database
        $db = new Db_Connect();
        $conn = $db->connect();
        

if(!$conn){
   echo "Could not connect to DBMS"; 
    }else { 
if (isset($_POST['mID'])){
 
    $mobile= $_POST['mID'];
   
    $mobile=test_input($mobile);
   $server_ip="139.59.38.160";

  
  $role= $_POST['role'];
   
    $role=test_input($role);

try{

$response = array("bookings"=>array());
   


       $pastbookingsdates=$conn->query( "SELECT OrderDate,COUNT(OrderDate) AS TotalBooking FROM `store_booking` WHERE isCompleted=1 GROUP BY OrderDate");

while ($user = mysqli_fetch_assoc($pastbookingsdates)) {

$jsonRow_201=array(

    "OrderDate"=>$user['OrderDate'],
             
               "TotalBooking"=>$user['TotalBooking'],
              
 );

array_push($response["bookings"], $jsonRow_201);
  
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