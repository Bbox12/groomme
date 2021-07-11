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

    if(isset($_POST['ladies'])){
      $Category=$_POST['ladies'];
    }else{
       $Category=0;
    }


 $server_ip="139.59.38.160";

  $response = array("fewservice"=>array(),"service"=>array(),"secondaryservice"=>array(),"category"=>array());

 $result =$con->query("SELECT ID,Ladies,Gents,Kids,Bridal FROM  salon_registration WHERE ID='$ID'");
          
         if ($result->num_rows > 0) {
          while($row = $result->fetch_assoc()) {
          $uID=$row["ID"];
          $Ladies=$row["Ladies"];
            $Gents=$row["Gents"];
              $Bridal=$row["Bridal"];
                $Kids=$row["Kids"];
        }
         }

    
if($Ladies==1 && $Gents==0){


$jsonRow=array(
               "ID"=>"1",
               "Category"=>"Ladies",
             );


array_push($response["category"], $jsonRow);
}
if($Ladies==0 && $Gents==1){


$jsonRow=array(
               "ID"=>"2",
               "Category"=>"Gents",
             );


array_push($response["category"], $jsonRow);
}
if($Kids==1){


$jsonRow=array(
               "ID"=>"3",
               "Category"=>"Kids",
             );


array_push($response["category"], $jsonRow);
}
if($Bridal==1){


$jsonRow=array(
               "ID"=>"4",
               "Category"=>"Bridal",
             );


array_push($response["category"], $jsonRow);
}



if($Ladies==1 && $Gents==1){


$jsonRow=array(
               "ID"=>"5",
               "Category"=>"Unisex",
             );


array_push($response["category"], $jsonRow);
}



  $sql="SELECT s.ID,s.SalonID,f.Name,ss.Service,s.Details_salon,s.Price_salon,s.Time_salon,s.Details_home,s.Price_home,s.Time_home,s.Highlighted1,s.Highlighted2,s.Highlighted3,s.Highlighted4,s.Highlighted5,s.Date,s.Time,s.User,s.IP FROM servicesdetails s INNER JOIN final_services f ON s.FinalServiceID=f.ID INNER JOIN secondary_service ss ON f.SecondaryServiceID=ss.ID WHERE s.SalonID='$uID' ORDER BY ID DESC LIMIT 3";

    


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


 if($Category==0){
$sql="SELECT p.ID AS Primary,s.ID,s.SalonID,f.Name,ss.Service,s.Details_salon,s.Price_salon,s.Time_salon,s.Details_home,s.Price_home,s.Time_home,s.Highlighted1,s.Highlighted2,s.Highlighted3,s.Highlighted4,s.Highlighted5,s.Date,s.Time,s.User,s.IP FROM servicesdetails s INNER JOIN final_services f ON s.FinalServiceID=f.ID INNER JOIN secondary_service ss ON f.SecondaryServiceID=ss.ID INNER JOIN primary_services p ON p.ID=ss.IDPrimaryService WHERE s.SalonID='$uID' ORDER BY ss.Service";
  	


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
                "Primary"=>$user['Primary'],
             );


array_push($response["service"], $jsonRow);

}
}elseif ($Category==1){
$sql="SELECT p.ID AS Primary,s.ID,s.SalonID,f.Name,ss.Service,s.Details_salon,s.Price_salon,s.Time_salon,s.Details_home,s.Price_home,s.Time_home,s.Highlighted1,s.Highlighted2,s.Highlighted3,s.Highlighted4,s.Highlighted5,s.Date,s.Time,s.User,s.IP FROM servicesdetails s INNER JOIN final_services f ON s.FinalServiceID=f.ID INNER JOIN secondary_service ss ON f.SecondaryServiceID=ss.ID INNER JOIN primary_services p ON p.ID=ss.IDPrimaryService WHERE s.SalonID='$uID' AND f.Ladies=1 ORDER BY ss.Service";
    


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
               "Primary"=>$user['Primary'],
             );


array_push($response["service"], $jsonRow);

}
}elseif ($Category==2){
 $sql="SELECT p.ID AS Primary,s.ID,s.SalonID,f.Name,ss.Service,s.Details_salon,s.Price_salon,s.Time_salon,s.Details_home,s.Price_home,s.Time_home,s.Highlighted1,s.Highlighted2,s.Highlighted3,s.Highlighted4,s.Highlighted5,s.Date,s.Time,s.User,s.IP FROM servicesdetails s INNER JOIN final_services f ON s.FinalServiceID=f.ID INNER JOIN secondary_service ss ON f.SecondaryServiceID=ss.ID INNER JOIN primary_services p ON p.ID=ss.IDPrimaryService WHERE s.SalonID='$uID' AND f.Gents=1 ORDER BY ss.Service";
    


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
               "Primary"=>$user['Primary'],
             );


array_push($response["service"], $jsonRow);

}
}elseif ($Category==3){
 $sql="SELECT p.ID AS Primary,s.ID,s.SalonID,f.Name,ss.Service,s.Details_salon,s.Price_salon,s.Time_salon,s.Details_home,s.Price_home,s.Time_home,s.Highlighted1,s.Highlighted2,s.Highlighted3,s.Highlighted4,s.Highlighted5,s.Date,s.Time,s.User,s.IP FROM servicesdetails s INNER JOIN final_services f ON s.FinalServiceID=f.ID INNER JOIN secondary_service ss ON f.SecondaryServiceID=ss.ID INNER JOIN primary_services p ON p.ID=ss.IDPrimaryService WHERE s.SalonID='$uID' AND f.Kids=1 ORDER BY ss.Service";
    


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
               "Primary"=>$user['Primary'],
             );


array_push($response["service"], $jsonRow);

}
}elseif ($Category==4){
 $sql="SELECT p.ID AS Primary,s.ID,s.SalonID,f.Name,ss.Service,s.Details_salon,s.Price_salon,s.Time_salon,s.Details_home,s.Price_home,s.Time_home,s.Highlighted1,s.Highlighted2,s.Highlighted3,s.Highlighted4,s.Highlighted5,s.Date,s.Time,s.User,s.IP FROM servicesdetails s INNER JOIN final_services f ON s.FinalServiceID=f.ID INNER JOIN secondary_service ss ON f.SecondaryServiceID=ss.ID INNER JOIN primary_services p ON p.ID=ss.IDPrimaryService WHERE s.SalonID='$uID' AND f.Bridal=1 ORDER BY ss.Service";
    


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
               "Primary"=>$user['Primary'],
             );


array_push($response["service"], $jsonRow);

}
}elseif ($Category==5){
 $sql="SELECT p.ID AS Primary,s.ID,s.SalonID,f.Name,ss.Service,s.Details_salon,s.Price_salon,s.Time_salon,s.Details_home,s.Price_home,s.Time_home,s.Highlighted1,s.Highlighted2,s.Highlighted3,s.Highlighted4,s.Highlighted5,s.Date,s.Time,s.User,s.IP FROM servicesdetails s INNER JOIN final_services f ON s.FinalServiceID=f.ID INNER JOIN secondary_service ss ON f.SecondaryServiceID=ss.ID INNER JOIN primary_services p ON p.ID=ss.IDPrimaryService WHERE s.SalonID='$uID' AND f.Gents=1 AND f.Ladies=1 ORDER BY ss.Service";
    


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
               "Primary"=>$user['Primary'],
             );


array_push($response["service"], $jsonRow);

}
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