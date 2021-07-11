<?php
 
// Path to move uploaded files

require_once 'DB_Functions.php';
$db = new DB_Functions();



// array for final json respone
$response = array();

 $response = array("error" => FALSE);

 
if (isset($_POST['parlour'])  ) {
   
 
    
   
             $parlour=   isset($_POST['parlour']) ? $_POST['parlour'] : '';
                
             
                $sun_open_close= isset($_POST['sun_open_close']) ? $_POST['sun_open_close'] : '';
                $sun_open_time=   isset($_POST['sun_open_time']) ? $_POST['sun_open_time'] : '';
                $sun_close_time=isset($_POST['sun_close_time']) ? $_POST['sun_close_time'] : '';
              
                $mon_open_close= isset($_POST['mon_open_close']) ? $_POST['mon_open_close'] : '';
                $mon_open_time=   isset($_POST['mon_open_time']) ? $_POST['mon_open_time'] : '';
                $mon_close_time=isset($_POST['mon_close_time']) ? $_POST['mon_close_time'] : '';
               
                $tue_open_close= isset($_POST['tue_open_close']) ? $_POST['tue_open_close'] : '';
                $tue_open_time=   isset($_POST['tue_open_time']) ? $_POST['tue_open_time'] : '';
                $tue_close_time=isset($_POST['tue_close_time']) ? $_POST['tue_close_time'] : '';
              
                $wed_open_close= isset($_POST['wed_open_close']) ? $_POST['wed_open_close'] : '';
                $wed_open_time=   isset($_POST['wed_open_time']) ? $_POST['wed_open_time'] : '';
                $wed_close_time=isset($_POST['wed_close_time']) ? $_POST['wed_close_time'] : '';
              
                $thr_open_close= isset($_POST['thr_open_close']) ? $_POST['thr_open_close'] : '';
                $thr_open_time=   isset($_POST['thr_open_time']) ? $_POST['thr_open_time'] : '';
                $thr_close_time=isset($_POST['thr_close_time']) ? $_POST['thr_close_time'] : '';
              
                $fri_open_close= isset($_POST['fri_open_close']) ? $_POST['fri_open_close'] : '';
                $fri_open_time=   isset($_POST['fri_open_time']) ? $_POST['fri_open_time'] : '';
                $fri_close_time=isset($_POST['fri_close_time']) ? $_POST['fri_close_time'] : '';
               
                $sat_open_close= isset($_POST['sat_open_close']) ? $_POST['sat_open_close'] : '';
                $sat_open_time=   isset($_POST['sat_open_time']) ? $_POST['sat_open_time'] : '';
                $sat_close_time=isset($_POST['sat_close_time']) ? $_POST['sat_close_time'] : '';
               

                $parlour=   test_in($parlour);
              
             
                $sun_open_close= test_input($sun_open_close);
                $sun_open_time=   test_input($sun_open_time);
                $sun_close_time=test_input($sun_close_time);
              

                 $mon_open_close= test_input($mon_open_close);
                $mon_open_time=   test_input($mon_open_time);
                $mon_close_time=test_input($mon_close_time);
                $tue_open_close= test_input($tue_open_close);
                $tue_open_time=   test_input($tue_open_time);
                $tue_close_time=test_input($tue_close_time);
                  $wed_open_close= test_input($wed_open_close);
                $wed_open_time=   test_input($wed_open_time);
                $wed_close_time=test_input($wed_close_time);
                 $thr_open_close= test_input($thr_open_close);
                $thr_open_time=   test_input($thr_open_time);
                $thr_close_time=test_input($thr_close_time);
                  $fri_open_close= test_input($fri_open_close);
                $fri_open_time=   test_input($fri_open_time);
                $fri_close_time=test_input($fri_close_time);
                 $sat_open_close= test_input($sat_open_close);
                $sat_open_time=   test_input($sat_open_time);
                $sat_close_time=test_input($sat_close_time);
              
   

$user = $db->storeSchedule(
                $parlour,
          
              
                $sun_open_close,
                $sun_open_time,
                $sun_close_time,
               
                $mon_open_close,
                $mon_open_time,
                $mon_close_time,
               
                $tue_open_close,
                $tue_open_time,
                $tue_close_time,
               
                $wed_open_close,
                $wed_open_time,
                $wed_close_time,
               
                $thr_open_close,
                $thr_open_time,
                $thr_close_time,
               
                $fri_open_close,
                $fri_open_time,
                $fri_close_time,
              
                $sat_open_close,
                $sat_open_time,
                $sat_close_time,
              );

  if ($user) {
            // user stored successfully
            $response["error"] = FALSE;
            $response["user"]["name"] = $user["parlour"];
    
          
           
            
         
        } else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred !";
            echo json_encode($response);
        }
    


} else {
    // File parameter is missing
    $response['error'] = true;
    $response['message'] = 'Not received any data!';
}
     echo json_encode($response);   


function test_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  $data=strtoupper($data);
  return $data;
}

function test_in($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}
?>