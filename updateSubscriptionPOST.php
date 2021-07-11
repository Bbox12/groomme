<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);
 header('Content-Type: application/json');
 session_start();

require_once 'DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);

var_dump($_POST);

if (isset($_POST['Title'])){



$id=0;
 
 
$Valid=$_POST["Valid"];
$Valid=test_input($Valid);
if (isset($_POST['Title'])){
$Title=$_POST["Title"];
$Title=test_input($Title);
}
$Details=$_POST["Details"];
$Details=test_input($Details);

$Price=$_POST["Price"];
$Price=test_input($Price);

$User= $_SESSION["email"];
$IP= $_SERVER['REMOTE_ADDR'];
$User= test_input($User);
$IP= test_input($IP);
if (isset($_POST['id'])){
$id=$_POST["id"];
$id=test_input($id);
}else{
  $id=0;
}

if (isset($_POST['nTitle'])){
$nTitle=$_POST["nTitle"];
$nTitle=test_input($nTitle);
}



     $user = $db->updateSubscription($Title,$nTitle,$Details,$Valid,$Price,$User,$IP,$id);
             if ($user) {  
          $_SESSION["error"]=1;
        $response["error"] = false;
        header('Location: http://139.59.38.160/Groom/Dashboard/UpdateSubscription.php');
    } else  {
            $_SESSION["error"]=2;
        $response["error"] = true;
  header('Location: http://139.59.38.160/Groom/Dashboard/UpdateSubscription.php');
    }

  
 

    



} else {
     $_SESSION["error"]=2;
    $response['error'] = true;
      header('Location: http://139.59.38.160/Groom/Dashboard/NewSubscription.php');
}
     echo json_encode($response); 
       


function test_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}
?>