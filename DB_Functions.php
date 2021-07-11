<?php



class DB_Functions {
 
    private $conn;
 
    // constructor
    function __construct() {
        require_once 'DB_Connect.php';
        // connecting to database
        $db = new Db_Connect();
        $this->conn = $db->connect();
    }
 
    // destructor
    function __destruct() {
         
    }



    public function upadateUser($ID,$name,$email,$mobile,$gender,$bday,$image,$IP,$refer){

    
        $ParlourID=0;
        date_default_timezone_set('Asia/Kolkata');
        $hour=date("H:i:s");
        $date=date("Y-m-d");
        $result=false;

        $stmt=$this->conn->prepare("SELECT ID FROM user_details WHERE ID=?  ");
        $stmt->bind_param("i",$ID);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ParlourID=$user["ID"];

        if($ParlourID>0){
              $stmt=$this->conn->prepare("UPDATE `user_details` SET `Name`=?,`Email`=?,`Photo`=?,`PhoneNo`=?,`Gender`=?,`DOB`=?,`Date`=?,`Time`=?,`User_Referrence_Code`=? WHERE ID=? ");
        $stmt->bind_param("sssssssssi",$name,$email,$image,$mobile,$gender,$bday,$date,$hour,$refer,$ParlourID);
        $stmt->execute();
          $error= $stmt->errno;
    printf("bbb: %d.\n", $error);
    $stmt->close();

            if($error==0){
     $result=true;
               
      
        }

    }


         
                return $result;
        
           
    }


    public function add_message($otp,$uID,$msg,$who){

    
        $ParlourID=0;
        date_default_timezone_set('Asia/Kolkata');
        $hour=date("H:i:s");
        $date=date("Y-m-d");
        $result=false;
        $ID=0;

        $stmt=$this->conn->prepare("SELECT ID FROM store_booking WHERE OrderID=? ");
        $stmt->bind_param("i",$otp);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $OTPID=$user["ID"];

        if($who==1){
              $stmt=$this->conn->prepare("SELECT ID,Name,PhoneNo FROM user_details WHERE ID=? ");
        $stmt->bind_param("i",$uID);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ParlourID=$user["ID"];  
        $PhoneNo=$user["PhoneNo"];  
        $Name=$user["Name"]; 

   if($ParlourID!=0){
       $stmt = $this->conn->prepare("INSERT INTO `message`(`IDBooking`, `IDUser`, `Name`, `Msg`, `Date`) VALUES (?,?,?,?,?)");
            $stmt->bind_param("iisss",$OTPID,$uID,$Name,$msg);
            $result = $stmt->execute();
    $error= $stmt->errno;
    printf("bbb: %d.\n", $error);
    $stmt->close();

            if($error==0){
     $result=true;
               
      
        }
   } 
    }else{
        $stmt=$this->conn->prepare("SELECT ID,parlour_name,parlour_mobile FROM salon_registration WHERE ID=? ");
        $stmt->bind_param("i",$name);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ParlourID=$user["ID"]; 
        $Name=$user["parlour_name"];  
           if($ParlourID!=0){
       $stmt = $this->conn->prepare("INSERT INTO `message`(`IDBooking`, `IDUser`, `Name`, `Msg`, `Date`) VALUES (?,?,?,?,?)");
            $stmt->bind_param("iisss",$OTPID,$uID,$Name,$msg);
            $result = $stmt->execute();
    $error= $stmt->errno;
    printf("bbb: %d.\n", $error);
    $stmt->close();

            if($error==0){
     $result=true;
               
      
        }
   }  
    }


         
                return $result;
        
           
    }


      public function addreviewFromSalon($mobile,$otp,$rating_c,$image_1,$image_2,$image_3,$image_4,$message){

        $ParlourID=0;
        $OTPID=0;
        $IDspecialist=0;
        $IDBooking=0;
        date_default_timezone_set(TIMEZONE);
        $hour=date("H:i:s");
        $date=date("Y-m-d");
        $result=false;
     

        $stmt=$this->conn->prepare("SELECT ID FROM salon_registration WHERE ID=? ");
        $stmt->bind_param("i",$mobile);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ParlourID=$user["ID"];

       
  
        if($ParlourID!=0){
       
        $stmt=$this->conn->prepare("SELECT ID,IDuser FROM store_booking WHERE OrderID=? AND IDsalon=? ");
        $stmt->bind_param("ii",$otp,$ParlourID);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $OTPID=$user["ID"];
        $IDuser=$user["IDuser"];

        if($OTPID!=0){

        $stmt=$this->conn->prepare("UPDATE store_booking SET isRunning=0, isCompleted=1,EndDate=?,EndTime=? WHERE  ID=? ");
        $stmt->bind_param("ssi",$date,$hour,$OTPID);
        $stmt->execute();
        $errno=$stmt->errno;
        $stmt->close();
     if( $errno==0){
        

        $stmt=$this->conn->prepare("SELECT ID FROM storeReview WHERE IDBooking=?  ");
        $stmt->bind_param("i",$OTPID);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $IDBooking=$user["ID"];
                 printf("fff: %d\n", $IDBooking); 
        if($IDBooking==0){
                 $stmt=$this->conn->prepare("INSERT INTO `storeReview`( `IDBooking`,`userRating`, UserReview,`salonImage1`,`salonImage2`, `salonImage3`, `salonImage4`, `Date`, `Time`) VALUES(?,?,?,?,?,?,?,?,?)");
        $stmt->bind_param("idsssssss",$OTPID,$rating_c,$message,$image_1,$image_2,$image_3,$image_4,$date,$hour);
        $stmt->execute();
             $errno=$stmt->errno;
        $stmt->close(); 
    }else{
         $stmt=$this->conn->prepare("UPDATE `storeReview` SET `userRating`=?, UserReview=?,`salonImage1`=?,`salonImage2`=?, `salonImage3`=?, `salonImage4`=? WHERE ID=?");
        $stmt->bind_param("dssssss",$rating_c,$message,$image_1,$image_2,$image_3,$image_4,$IDBooking);
        $stmt->execute();
             $errno=$stmt->errno;
        $stmt->close(); 
    }
  
     printf("land: %d\n", $errno); 
   
        $stmt=$this->conn->prepare("SELECT Rating,TotalRating FROM user_details WHERE ID=?  ");
        $stmt->bind_param("i",$IDuser);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $TotalRating=$user["TotalRating"]+1;
        $Rating=$user["TotalRating"]*$user["Rating"]+$rating_c;
        $FinalRating=$Rating/$TotalRating;

        $stmt=$this->conn->prepare("UPDATE user_details SET Rating=?, TotalRating=? WHERE ID=?");
        $stmt->bind_param("dii",$FinalRating,$TotalRating,$IDuser);
        $stmt->execute();
             $errno=$stmt->errno;
        $stmt->close();
        
         if( $errno==0){
            $result=true;
         }
        }else {
             $result=false;
        } 
        
       
   
    }
   
    }
 
 
          
 
            return $result;
        
     } 

      public function addreview($mobile,$otp,$rating_c,$rating_s,$image_1,$image_2,$image_3,$image_4,$message){

        $ParlourID=0;
        $OTPID=0;
        $IDspecialist=0;
               $userID=0;
        date_default_timezone_set(TIMEZONE);
        $hour=date("H:i:s");
        $date=date("Y-m-d");
        $result=false;
     

        $stmt=$this->conn->prepare("SELECT ID FROM user_details WHERE ID=? ");
        $stmt->bind_param("i",$mobile);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $userID=$user["ID"];

        printf("userID: %d\n", $userID); 
  
        if($userID!=0){
       
        $stmt=$this->conn->prepare("SELECT ID,IDspecialist,IDsalon FROM store_booking WHERE OrderID=? AND IDuser=? ");
        $stmt->bind_param("ii",$otp,$userID);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $OTPID=$user["ID"];
        $IDspecialist=$user["IDspecialist"];
        $ParlourID=$user["IDsalon"];

      printf("ParlourID: %d\n", $ParlourID); 

        if($OTPID!=0){

      
        $stmt=$this->conn->prepare("SELECT ID FROM storeReview WHERE IDBooking=?  ");
        $stmt->bind_param("i",$OTPID);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $IDBooking=$user["ID"];
        printf("IDBooking: %d\n", $IDBooking); 
       if($IDBooking==0){
        $stmt=$this->conn->prepare("INSERT INTO `storeReview`( `IDBooking`,  `rating_c`, `rating_s`, `review`, `Image1`, `Image2`, `Image3`, `Image4`, `Date`, `Time`) VALUES(?,?,?,?,?,?,?,?,?,?)");
        $stmt->bind_param("iddsssssss",$OTPID,$rating_c,$rating_s,$message,$image_1,$image_2,$image_3,$image_4,$date,$hour);
        $stmt->execute();
        $errno=$stmt->errno;
        $stmt->close();
        }else{
                   $stmt=$this->conn->prepare("UPDATE `storeReview`SET `rating_c`=?, `rating_s`=?, `SalonReview`=?, `Image1`=?, `Image2`=?, `Image3`=?, `Image4`=? WHERE ID=?");
        $stmt->bind_param("ddsssssi",$rating_c,$rating_s,$message,$image_1,$image_2,$image_3,$image_4,$IDBooking);
        $stmt->execute();
        $errno=$stmt->errno;
        $stmt->close(); 
        }

              printf("errno: %d\n", $errno); 
        if( $errno==0){
      
        $stmt=$this->conn->prepare("SELECT Rating,TotalRating FROM specialist_detail WHERE ID=?  ");
        $stmt->bind_param("i",$IDspecialist);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
     
        $TotalRating=$user["TotalRating"]+1;
        $Rating=$user["TotalRating"]*$user["Rating"]+$rating_c;
        $FinalRating=$Rating/$TotalRating;

        $stmt=$this->conn->prepare("UPDATE specialist_detail SET Rating=?, TotalRating=? WHERE ID=?");
        $stmt->bind_param("dii",$FinalRating,$TotalRating,$IDspecialist);
        $stmt->execute();
        $errno=$stmt->errno;
        $stmt->close();

   
        $stmt=$this->conn->prepare("SELECT  serviceRating,serviceTotalRating FROM salon_registration WHERE ID=?  ");
        $stmt->bind_param("i",$ParlourID);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
      
        $TotalRating=$user["serviceTotalRating"]+1;
          $Rating=$user["serviceTotalRating"]*$user["serviceRating"]+$rating_s;

        $FinalRating=$Rating/$TotalRating;

        $stmt=$this->conn->prepare("UPDATE salon_registration SET serviceRating=?, serviceTotalRating=? WHERE ID=?");
        $stmt->bind_param("dii",$FinalRating,$TotalRating,$ParlourID);
        $stmt->execute();
        $errno=$stmt->errno;
        $stmt=$this->conn->prepare("UPDATE store_booking SET UserReview=1 WHERE  ID=? ");
        $stmt->bind_param("i",$OTPID);
        $stmt->execute();
        $errno=$stmt->errno;
        $stmt->close();

          if( $errno==0){
            $result=true;
         }


        
        }else {
             $result=false;
        } 
        
       
   }
    return $result;
    }
   
    }
 
 
     


    public function salonRunning($order){

        $ParlourID=0;
        $duplicateID=0;
        date_default_timezone_set(TIMEZONE);
        $hour=date("H:i:s");
        $date=date("Y-m-d");
        $result=false;
     

        $stmt=$this->conn->prepare("SELECT ID FROM store_booking WHERE OrderID=? ");
        $stmt->bind_param("i",$order);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ParlourID=$user["ID"];

       
  
        if($ParlourID!=0){
       
      
           $stmt = $this->conn->prepare("UPDATE store_booking SET isRunning=1,StartDate=?,StartTime=?  WHERE ID=?") ;

        $stmt->bind_param("ssi",$date,$hour,$ParlourID);
        $result = $stmt->execute();
        if( $stmt->errno==0){
                $result=true;
        
        }else {
             $result=false;
        } 
         printf("Error: %s.\n", $stmt->error); 
        $stmt->close(); 
    
    
   
    }
 
 
          
 
            return $result;
        
     } 


      public function salonAccepted($order){

        $ParlourID=0;
        $duplicateID=0;
        date_default_timezone_set(TIMEZONE);
        $hour=date("H:i:s");
        $date=date("Y-m-d");
        $result=false;
     

        $stmt=$this->conn->prepare("SELECT ID FROM store_booking WHERE OrderID=? ");
        $stmt->bind_param("i",$order);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ParlourID=$user["ID"];

       
  
        if($ParlourID!=0){
       
      
           $stmt = $this->conn->prepare("UPDATE store_booking SET isAccepted=1,AccpetedDate=?,  AccpetedTime=?  WHERE ID=?") ;

        $stmt->bind_param("ssi",$date,$hour,$ParlourID);
        $result = $stmt->execute();
        if( $stmt->errno==0){
                $result=true;
        
        }else {
             $result=false;
        } 
         printf("Error: %s.\n", $stmt->error); 
        $stmt->close(); 
    
    
   
    }
 
 
          
 
            return $result;
        
     } 


      public function customerCancelled($order,$msg){

        $ParlourID=0;
        $duplicateID=0;
        date_default_timezone_set(TIMEZONE);
        $hour=date("H:i:s");
        $date=date("Y-m-d");
        $result=false;
     

        $stmt=$this->conn->prepare("SELECT ID FROM store_booking WHERE OrderID=? ");
        $stmt->bind_param("i",$order);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ParlourID=$user["ID"];
     printf("ParlourID: %d.\n", $ParlourID); 
       
  
        if($ParlourID!=0){
       
      
           $stmt = $this->conn->prepare("UPDATE store_booking SET isCancelled=1, CancelledBy=2,reasonCancelled=?  WHERE ID=?") ;

        $stmt->bind_param("si",$msg,$ParlourID);
        $result = $stmt->execute();
             printf("ParlourID: %d.\n", $stmt->errno); 
        if( $stmt->errno==0){
                $result=true;
        
        }else {
             $result=false;
        } 
         printf("Error: %s.\n", $stmt->error); 
        $stmt->close(); 
    
    
   
    }
 
 
          
 
            return $result;
        
     } 



      public function salonCancelled($order,$msg){

        $ParlourID=0;
        $duplicateID=0;
        date_default_timezone_set(TIMEZONE);
        $hour=date("H:i:s");
        $date=date("Y-m-d");
        $result=false;
     

        $stmt=$this->conn->prepare("SELECT ID FROM store_booking WHERE OrderID=? ");
        $stmt->bind_param("i",$order);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ParlourID=$user["ID"];

       
  
        if($ParlourID!=0){
       
      
           $stmt = $this->conn->prepare("UPDATE store_booking SET isCancelled=1, CancelledBy=2,reasonCancelled=? WHERE ID=?") ;

        $stmt->bind_param("si",$msg,$ParlourID);
        $result = $stmt->execute();
        if( $stmt->errno==0){
                $result=true;
        
        }else {
             $result=false;
        } 
         printf("Error: %s.\n", $stmt->error); 
        $stmt->close(); 
    
    
   
    }
 
 
          
 
            return $result;
        
     } 


       public function add_order_payment($order,$payment){

        date_default_timezone_set(TIMEZONE);
        $hour=date("H:i");
        $date=date("Y-m-d");
    

        $stmt=$this->conn->prepare("SELECT ID FROM store_booking WHERE OrderID=?");
        $stmt->bind_param("i", $order);
        $stmt->execute();
        $user = $stmt->get_result()->fetch_assoc();
        $stmt->close();
        $cID=$user["ID"];

     

     if($cID!=0){ 
           $stmt=$this->conn->prepare("UPDATE `store_booking` SET `PaymentMode`=? WHERE ID=?");
            $stmt->bind_param("ii",$payment,$cID);
            $stmt->execute();
            printf("resultkal: %d\n", $stmt->errno); 
             if($stmt->errno==0){
         
                $result=true;
               
            }
            $stmt->close(); 
       
           
        }

       return $result;

       
    }

       public function add_order_address($order,$house,$address,$landmark,$latitude,$longitude){

        date_default_timezone_set(TIMEZONE);
        $hour=date("H:i");
        $date=date("Y-m-d");
    

        $stmt=$this->conn->prepare("SELECT ID FROM store_booking WHERE OrderID=?");
        $stmt->bind_param("i", $order);
        $stmt->execute();
        $user = $stmt->get_result()->fetch_assoc();
        $stmt->close();
        $cID=$user["ID"];

        
     if($cID!=0){ 
           $stmt=$this->conn->prepare("UPDATE `store_booking` SET `addressd`=?, `houseno`=?, `landmark`=?, `Latitude`=?, `Longitude`=? WHERE ID=?");
            $stmt->bind_param("sssddi",$address,$house,$landmark,$latitude,$longitude,$cID);
            $result=$stmt->execute();
                printf("resultkal: %d\n", $stmt->errno); 
            $last_id = $bookingID;
             if($stmt->errno==0){
         
                $result=true;
               
            }
                 printf("last_id: %d\n", $last_id); 
            $stmt->close(); 
       
           
        }

       return $result;

       
    }


  public function add_order_price($mobile,$data,$total,$salon,$specialist,$dates,$discount,$slot,$NoofItems,$home){

        date_default_timezone_set(TIMEZONE);
        $hour=date("H:i");
        $date=date("Y-m-d");
        $otp=rand(100000,999999);
        $result=false;
        $stmt=$this->conn->prepare("SELECT ID FROM user_details WHERE ID=?");
        $stmt->bind_param("s", $mobile);
        $stmt->execute();
        $user = $stmt->get_result()->fetch_assoc();
        $stmt->close();
        $uID=$user["ID"];

        $stmt=$this->conn->prepare("SELECT ID FROM salon_registration WHERE ID=?");
        $stmt->bind_param("i", $salon);
        $stmt->execute();
        $user = $stmt->get_result()->fetch_assoc();
        $stmt->close();
        $cID=$user["ID"];


     if($cID!=0){ 

        $stmt=$this->conn->prepare("SELECT ID FROM store_booking WHERE IDuser=? AND IDsalon=? AND isRunning!=1 AND isCancelled!=1  AND isCompleted!=1");
        $stmt->bind_param("ii", $uID,$cID);
        $stmt->execute();
        $user = $stmt->get_result()->fetch_assoc();
        $stmt->close();
        $bookingID=$user["ID"];

            if($bookingID==0){
                 $stmt=$this->conn->prepare("INSERT INTO `store_booking`(`OrderDate`, `OrderID`, `IDuser`, `IDsalon`, `IDspecialist`, `IDslot`, `IDserviceAt`, `Discount`, `NoofItems`, `Payable`,`Date`, `Time`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
            $stmt->bind_param("siiiiiididss",$dates,$otp,$uID,$cID,$specialist,$slot,$home,$discount,$NoofItems,$total,$date,$hour);
            $result=$stmt->execute();
                printf("resultkal: %d\n", $stmt->errno); 
            $last_id = $stmt->insert_id;
             if($stmt->errno==0){
                $result=true;
            }
                 printf("last_id: %d\n", $last_id); 
            $stmt->close();
        }else{
      
           $stmt=$this->conn->prepare("UPDATE `store_booking` SET `OrderDate`=?, `OrderID`=?, `IDspecialist`=?, `IDslot`=?, `IDserviceAt`=?, `NoofItems`=?, `Payable`=?,`isAccepted`=0, `Date`=?, `Time`=? WHERE ID=?");
            $stmt->bind_param("siiiiidssi",$dates,$otp,$specialist,$slot,$home,$NoofItems,$total,$date,$hour,$bookingID);
            $result=$stmt->execute();
                printf("resultkal: %d\n", $stmt->errno); 
            $last_id = $bookingID;
             if($stmt->errno==0){
                       $stmt=$this->conn->prepare("DELETE FROM `booking_details_service` WHERE IDbooking=?");
        $stmt->bind_param("i", $bookingID);
        $stmt->execute();
        $stmt->close();
                $result=true;
               

            }
                 printf("last_id: %d\n", $last_id); 
            $stmt->close(); 
        }
           
        }

       

            if($last_id!=0){


    $myArray=array();
        if(strpos($data, ",")!==false){
        $myArray = explode(',', $data);
    
    }else{
         $myArray = $day;
        $laterDate=$day;
    
    }
 
 
        if(count($myArray)>1){
            $dataArray=array();
        $myArray = explode(',', $data);
        foreach($myArray as $laterDate){
               if(strpos($laterDate, "_")!==false){

                echo $laterDate;
        $dataArray = explode('_', $laterDate);

       
            $stmt=$this->conn->prepare("INSERT INTO `booking_details_service`(`IDbooking`, `IDservice`,`Price`,`Duration`, `Date`, `Time`) VALUES  (?,?,?,?,?,?)");
            $stmt->bind_param("iidiss",$last_id,$dataArray[0],$dataArray[2],$dataArray[3],$date,$hour);
            $result=$stmt->execute();
                  printf("mul: %d\n", $stmt->error); 
             if($stmt->errno==0){
                $result=true;
            }
        

                           
        }
           }
       }
           else{
         $myArray = $data;
         if(strpos($data, "_")!==false){
        $dataArray = explode('_', $data);

          
      
           $stmt=$this->conn->prepare("INSERT INTO `booking_details_service`(`IDbooking`, `IDservice`,`Price`,`Duration`, `Date`, `Time`) VALUES  (?,?,?,?,?,?)");
            $stmt->bind_param("iidiss",$last_id,$dataArray[0],$dataArray[2],$dataArray[3],$date,$hour);
            $result=$stmt->execute();
                  printf("kal: %s.\n", $stmt->error); 
             if($stmt->errno==0){
                $result=true;
            }
        
            $stmt->close();
       
          

                            }
            }

        }

          printf("resultkal: %s\n", $result); 
        if($result){
             $stmt=$this->conn->prepare("SELECT `ID`, `OrderDate`, `OrderID`, `IDuser`,(SELECT parlour_name FROM salon_registration WHERE ID=s.IDsalon) AS `IDsalon`, `IDspecialist`, `IDslot`, `IDserviceAt`, `Discount`, `NoofItems`, `Payable`, `Date`, `Time` FROM `store_booking` s WHERE OrderID=?");
        $stmt->bind_param("i", $otp);
        $stmt->execute();
        $user = $stmt->get_result()->fetch_assoc();
        $stmt->close();
         return $user;
        }else{

            return false;
        }

   

       
    }



   public function forgotpwd($mobile,$password){

        date_default_timezone_set(TIMEZONE);
        $hour=date("H:i");
        $date=date("Y-m-d");
        $ParlourID=0;
        $result=0;
        $Password=sha1($password);
        $stmt=$this->conn->prepare("SELECT ID FROM salon_registration WHERE parlour_mobile=?  ");
        $stmt->bind_param("ss",$mobile,$email);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ParlourID=$user["ID"]; 

        if($ParlourID!=0){
        $stmt=$this->conn->prepare("UPDATE salon_registration SET Password=? WHERE ID=? ");
        $stmt->bind_param("si",$Password,$ParlourID);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ParlourID=$user["ID"]; 
        $Name=$user["parlour_name"]; 
         if( $ParlourID!=0){
                 $user["error"]=1;
                $user["Name"]=$Name;
        
        }else {
               $user["error"]=3;
        
        } 
        $stmt->close(); 
    }
        else{
        $stmt=$this->conn->prepare("SELECT ID FROM user_details WHERE PhoneNo=?  ");
        $stmt->bind_param("ss",$mobile,$email);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ParlourID=$user["ID"]; 

        if($ParlourID!=0){
        $stmt=$this->conn->prepare("UPDATE user_details SET Password=? WHERE ID=? ");
        $stmt->bind_param("si",$Password,$ParlourID);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ParlourID=$user["ID"]; 
                $Name=$user["Name"]; 

         if( $ParlourID!=0){
                //$result=2;
                $user["error"]=2;
                $user["Name"]=$Name;
        
        }else {
            $user["error"]=3;
    
        } 
        $stmt->close(); 
    }
}
       
       
       return $user; 
    }

     public function signin($role,$mobile,$password){

        date_default_timezone_set(TIMEZONE);
        $hour=date("H:i");
        $date=date("Y-m-d");
        $ParlourID=0;
        $user=0;
        $email=$mobile;
        $Password=sha1($password);
        $PID=0; 
        if($role==0){


        $stmt=$this->conn->prepare("SELECT ID FROM salon_registration WHERE (parlour_mobile=? OR parlour_email=?)  ");
        $stmt->bind_param("ss",$mobile,$email);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ParlourID=$user["ID"]; 

        if($ParlourID!=0){
        $stmt=$this->conn->prepare("SELECT ID,parlour_name FROM salon_registration WHERE Password=? AND ID=? ");
        $stmt->bind_param("si",$Password,$ParlourID);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
         $PID=$user["ID"]; 
         if( $PID!=0){
              $stmt=$this->conn->prepare("SELECT * FROM salon_registration WHERE ID=? ");
        $stmt->bind_param("i",$ParlourID);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        }else {
                 $user=0;
        
        } 

    }
        else{
        $stmt=$this->conn->prepare("SELECT ID FROM user_details WHERE (PhoneNo=? OR Email=? ) ");
        $stmt->bind_param("ss",$mobile,$email);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ParlourID=$user["ID"]; 
      
        if($ParlourID!=0){
        $stmt=$this->conn->prepare("SELECT ID,Name FROM user_details WHERE Password=? AND ID=? ");
        $stmt->bind_param("si",$Password,$ParlourID);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
                   $PID=$user["ID"]; 
         if( $PID!=0){
        $stmt=$this->conn->prepare("SELECT * FROM user_details WHERE ID=? ");
        $stmt->bind_param("i",$ParlourID);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
   
        
        }else {
            $user=0;
    
        } 
     
    }

}
}else{
         $stmt=$this->conn->prepare("SELECT ID FROM salon_registration WHERE (parlour_mobile=? OR parlour_email=?)  ");
        $stmt->bind_param("ss",$mobile,$email);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ParlourID=$user["ID"]; 

        if($ParlourID!=0){

        $stmt=$this->conn->prepare("UPDATE salon_registration SET Password=? WHERE ID=? ");
        $stmt->bind_param("si",$Password,$ParlourID);
        $stmt->execute();

        $stmt->close();

        $stmt=$this->conn->prepare("SELECT * FROM salon_registration WHERE ID=? ");
        $stmt->bind_param("i",$ParlourID);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();


        }else{
        $stmt=$this->conn->prepare("SELECT ID FROM user_details WHERE (PhoneNo=? OR Email=?)  ");
        $stmt->bind_param("ss",$mobile,$email);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ParlourID=$user["ID"]; 

        if($ParlourID!=0){

        $stmt=$this->conn->prepare("UPDATE user_details SET Password=? WHERE ID=? ");
        $stmt->bind_param("si",$Password,$ParlourID);
        $stmt->execute();
        $stmt->close();

          $stmt=$this->conn->prepare("SELECT * FROM user_details WHERE ID=? ");
        $stmt->bind_param("i",$ParlourID);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
   
        } 
        }
}
       
       
       return $user; 
    }


     public function signup($role,$name,$email,$mobile,$password,$file_path){

        date_default_timezone_set(TIMEZONE);
        $hour=date("H:i");
        $date=date("Y-m-d");
        $ParlourID=0;
        $result=false;
        $ParlourID=0; 
        $Password=sha1($password);

        if($role==2){
                     if($mobile=="NA"){
        $stmt=$this->conn->prepare("SELECT ID FROM salon_registration WHERE parlour_email=?  ");
        $stmt->bind_param("s",$email);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ParlourID=$user["ID"];   
    }else if($email=="NA"){
                $stmt=$this->conn->prepare("SELECT ID FROM salon_registration WHERE parlour_mobile=?  ");
        $stmt->bind_param("s",$mobile);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ParlourID=$user["ID"];   
    }else{
        $stmt=$this->conn->prepare("SELECT ID FROM salon_registration WHERE (parlour_mobile=? OR parlour_email=? ) ");
        $stmt->bind_param("ss",$mobile,$email);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ParlourID=$user["ID"]; 
    }
        

     

              if($ParlourID==0){
              $stmt = $this->conn->prepare("INSERT INTO `salon_registration`(`parlour_name`,`Photo`, `parlour_email`, `parlour_mobile`,`Password`, `AppInstallation_Date`, `AppInstallation_Time`, `Date`, `Time`) VALUES(?,?,?,?,?,?,?,?,?)");
        $stmt->bind_param("sssssssss", $name,$file_path,$email,$mobile,$Password,$date,$hour,$date,$hour);
        $result = $stmt->execute();
        $last_id = $stmt->insert_id;
        $error= $stmt->errno;
        printf("Erppror: %d.\n", $stmt->errno);
         if( $stmt->errno==0){
                $result=true;
        
        }else {
             $result=false;
        } 
        $stmt->close(); 
    }else{
         $stmt = $this->conn->prepare("UPDATE `salon_registration`SET `parlour_name`=?,`Photo`=?, `parlour_email`=?, `parlour_mobile`=?,`Password`=?, `AppInstallation_Date`=?, `AppInstallation_Time`=?, `Date`=?, `Time`=? WHERE ID=?");
        $stmt->bind_param("sssssssssi", $name,$file_path,$email,$mobile,$Password,$date,$hour,$date,$hour,$ParlourID);
        $result = $stmt->execute();
        $last_id = $ParlourID;
        $error= $stmt->errno;
        printf("Erppror: %d.\n", $stmt->errno);
         if( $stmt->errno==0){
                $result=true;
        
        }else {
             $result=false;
        } 
        $stmt->close(); 
    }
        }elseif($role==1){
            if($mobile=="NA"){
                $stmt=$this->conn->prepare("SELECT ID FROM user_details WHERE Email=?  ");
        $stmt->bind_param("s",$email);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ParlourID=$user["ID"];   
    }else if($email=="NA"){
                $stmt=$this->conn->prepare("SELECT ID FROM user_details WHERE PhoneNo=?  ");
        $stmt->bind_param("s",$mobile);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ParlourID=$user["ID"];   
    }else{
          $stmt=$this->conn->prepare("SELECT ID FROM user_details WHERE (PhoneNo=? OR Email=? ) ");
        $stmt->bind_param("ss",$mobile,$email);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ParlourID=$user["ID"]; 
    }
      
        printf("ID: %d.\n", $ParlourID);

              if($ParlourID==0){

                echo $name; echo $email; echo $file_path; echo $mobile; echo $Password; echo $date; echo $hour;
              $stmt = $this->conn->prepare("INSERT INTO `user_details`( `Name`, `Email`, `Photo`, `PhoneNo`,`Password`, `Date`, `Time`) VALUES(?,?,?,?,?,?,?)");
        $stmt->bind_param("sssssss", $name,$email,$file_path,$mobile,$Password,$date,$hour);
        $result = $stmt->execute();
        $last_id = $stmt->insert_id;
        printf("Erppror: %d.\n", $stmt->errno);
         if( $stmt->errno==0){
                $result=true;
        
        }else {
             $result=false;
        } 
        $stmt->close(); 
    }else{
        $stmt = $this->conn->prepare("UPDATE `user_details`SET `Name`=?, `Email`=?, `Photo`=?, `PhoneNo`=?,`Password`=?, `Date`=?, `Time`=? WHERE ID=?");
        $stmt->bind_param("sssssssi", $name,$email,$file_path,$mobile,$Password,$date,$hour,$ParlourID);
        $result = $stmt->execute();
        $last_id = $ParlourID;
        printf("Erppror: %d.\n", $stmt->errno);
         if( $stmt->errno==0){
                $result=true;
        
        }else {
             $result=false;
        } 
        $stmt->close();  
    }
        }
           printf("result: %d.\n", $result);
               printf("last_id: %d.\n", $last_id);
       if($result){
        if($role==2){
               $stmt=$this->conn->prepare("SELECT * FROM salon_registration WHERE ID=? ");
        $stmt->bind_param("i",$last_id);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
    }else{
 $stmt=$this->conn->prepare("SELECT * FROM user_details WHERE ID=? ");
        $stmt->bind_param("i",$last_id);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
    }
       }

       return $user;

       
    }



      public function storeUser($FD,$about,$name,$email,$address,$mobile,$registration,$city,$locality,$zip,$IP,$file_path,$data){


        $ParlourID=0;
        date_default_timezone_set(TIMEZONE);
        $hour=date("H:i:s");
        $date=date("Y-m-d");
        $result=false;



        $stmt=$this->conn->prepare("SELECT ID FROM salon_registration WHERE ID=?  ");
        $stmt->bind_param("i",$FD);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ParlourID=$user["ID"];

            echo $ParlourID;
   



        if($ParlourID!=0){
          $stmt = $this->conn->prepare("UPDATE `salon_registration` SET `parlour_name`=?,`parlour_about`=?, `Photo`=?, `parlour_email`=?, `parlour_registration`=?, `parlour_address`=?, `parlour_city`=?, `parlour_locality`=?, `parlour_pin`=?,`AppInstallation_Date`=?, `AppInstallation_Time`=?, `Date`=?, `Time`=?, `IP`=? WHERE ID=?");
        $stmt->bind_param("ssssssssssssssi",$name,$about,$file_path,$email,$registration,$address,$city,$locality,$zip,$date,$hour,$date,$hour,$IP,$ParlourID);
        $result = $stmt->execute();
        $last_id = $ParlourID;
        $error= $stmt->errno;
        printf("ParlourID: %d.\n", $stmt->errno);
        $stmt->close();
    }

  
            if($last_id!=0){


    $myArray=array();
        if(strpos($data, ",")!==false){
        $myArray = explode(',', $data);
    
    }else{
         $myArray = $data;
        $laterDate=$data;
    
    }
 
 
        if(count($myArray)>1){
            $dataArray=array();
        $myArray = explode(',', $data);
        foreach($myArray as $laterDate){
               if(strpos($laterDate, "1")!==false){
            $stmt=$this->conn->prepare("UPDATE `salon_registration` SET `Ladies`=1 WHERE ID=?");
            $stmt->bind_param("i",$last_id);
            $stmt->execute();
                  printf("mul: %d\n", $stmt->error); 
            
              $stmt->close();             
        }elseif  (strpos($laterDate, "2")!==false){

            
       
            $stmt=$this->conn->prepare("UPDATE `salon_registration` SET `Gents`=1 WHERE ID=?");
            $stmt->bind_param("i",$last_id);
                    $stmt->execute();
                  printf("mul: %d\n", $stmt->error); 
          
              $stmt->close();              
        }elseif (strpos($laterDate, "3")!==false){

            
       
            $stmt=$this->conn->prepare("UPDATE `salon_registration` SET `Ladies`=1, Gents=1 WHERE ID=?");
            $stmt->bind_param("i",$last_id);
                    $stmt->execute();
                  printf("mul: %d\n", $stmt->error); 
          
              $stmt->close();              
        }elseif (strpos($laterDate, "4")!==false){

            
       
            $stmt=$this->conn->prepare("UPDATE `salon_registration` SET `Kids`=1 WHERE ID=?");
            $stmt->bind_param("i",$last_id);
                    $stmt->execute();
                  printf("mul: %d\n", $stmt->error); 
      
              $stmt->close();              
        } elseif(strpos($laterDate, "5")!==false){

            
       
            $stmt=$this->conn->prepare("UPDATE `salon_registration` SET `Bridal`=1 WHERE ID=?");
            $stmt->bind_param("i",$last_id);
                    $stmt->execute();
                  printf("mul: %d\n", $stmt->error); 
          
              $stmt->close();              
        }
           }
       }
           else{
         $laterDate = $data;
        if(strpos($laterDate, "1")!==false){

            
       
            $stmt=$this->conn->prepare("UPDATE `salon_registration` SET `Ladies`=1 WHERE ID=?");
            $stmt->bind_param("i",$last_id);
                    $stmt->execute();
                  printf("mul: %d\n", $stmt->error); 
             if($stmt->errno==0){
                $result=true;
            }
              $stmt->close();                
        }elseif  (strpos($laterDate, "2")!==false){

            
       
            $stmt=$this->conn->prepare("UPDATE `salon_registration` SET `Gents`=1 WHERE ID=?");
            $stmt->bind_param("i",$last_id);
                   $stmt->execute();
                  printf("mul: %d\n", $stmt->error); 
     
              $stmt->close();               
        }elseif  (strpos($laterDate, "3")!==false){

            
       
            $stmt=$this->conn->prepare("UPDATE `salon_registration` SET `Ladies`=1, Gents=1 WHERE ID=?");
            $stmt->bind_param("i",$last_id);
                   $stmt->execute();
                  printf("mul: %d\n", $stmt->error); 
         
              $stmt->close();              
        }elseif (strpos($laterDate, "4")!==false){

            
       
            $stmt=$this->conn->prepare("UPDATE `salon_registration` SET `Kids`=1 WHERE ID=?");
            $stmt->bind_param("i",$last_id);
                    $stmt->execute();
                  printf("mul: %d\n", $stmt->error); 
        
              $stmt->close();              
        } elseif(strpos($laterDate, "5")!==false){

            
       
            $stmt=$this->conn->prepare("UPDATE `salon_registration` SET `Bridal`=1 WHERE ID=?");
            $stmt->bind_param("i",$last_id);
            $stmt->execute();
            printf("mul: %d\n", $stmt->error); 
            $stmt->close();               
        }
            }

        }

            $stmt = $this->conn->prepare("SELECT * FROM salon_registration WHERE parlour_mobile = ? AND parlour_registration=?");
            $stmt->bind_param("ss", $mobile,$registration);
            $stmt->execute();
             if( $stmt->errno==0){
                $result=true;
            }else{
            $result=false; 
            }
            $stmt->close();
  
         
       

           return $result;
    }

  public function add_order($mobile,$ID,$data,$total,$salon,$specialist,$dates,$discount,$slot,$NoofItems,$home,$address,$house,$land,$pmode){

        date_default_timezone_set(TIMEZONE);
        $hour=date("H:i");
        $date=date("Y-m-d");
        $otp=rand(100000,999999);
        $result=false;
        $stmt=$this->conn->prepare("SELECT ID FROM user_details WHERE PhoneNo=?");
        $stmt->bind_param("s", $mobile);
        $stmt->execute();
        $user = $stmt->get_result()->fetch_assoc();
        $stmt->close();
        $uID=$user["ID"];

        $stmt=$this->conn->prepare("SELECT ID FROM salon_registration WHERE ID=?");
        $stmt->bind_param("i", $salon);
        $stmt->execute();
        $user = $stmt->get_result()->fetch_assoc();
        $stmt->close();
        $cID=$user["ID"];

        if(strlen($land)==0){
            $land="NA";
           
        }
        if(strlen($house)==0){
            $house="NA";
           
        }
        if(strlen($address)==0){
            $address="NA";
           
        }
         printf("add: %s\n", $address); 
                       printf("home: %s\n", $house); 
                                  printf("land: %s\n", $land); 

     if($cID!=0){ 

        $stmt=$this->conn->prepare("SELECT ID FROM store_booking WHERE IDuser=? AND IDsalon=? AND isRunning!=1 AND isCancelled!=1  AND isCompleted!=1");
        $stmt->bind_param("ii", $uID,$cID);
        $stmt->execute();
        $user = $stmt->get_result()->fetch_assoc();
        $stmt->close();
        $bookingID=$user["ID"];

            if($bookingID==0){
                 $stmt=$this->conn->prepare("INSERT INTO `store_booking`(`OrderDate`, `OrderID`, `IDuser`, `IDsalon`, `IDspecialist`, `IDslot`, `IDserviceAt`, `Discount`, `NoofItems`, `Payable`,`PaymentMode`, `addressd`, `houseno`, `landmark`, `Date`, `Time`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            $stmt->bind_param("siiiiiididisssss",$dates,$otp,$uID,$cID,$specialist,$slot,$home,$discount,$NoofItems,$total,$pmode,$address,$house,$land,$date,$hour);
            $result=$stmt->execute();
                printf("resultkal: %d\n", $stmt->errno); 
            $last_id = $stmt->insert_id;
             if($stmt->errno==0){
                $result=true;
            }
                 printf("last_id: %d\n", $last_id); 
            $stmt->close();
        }else{
      
           $stmt=$this->conn->prepare("UPDATE `store_booking` SET `OrderDate`=?, `OrderID`=?, `IDspecialist`=?, `IDslot`=?, `NoofItems`=?, `Payable`=?,`PaymentMode`=?, `addressd`=?, `houseno`=?, `landmark`=?,`isAccepted`=0, `Date`=?, `Time`=? WHERE ID=?");
            $stmt->bind_param("siiiidisssssi",$dates,$otp,$specialist,$slot,$NoofItems,$total,$pmode,$address,$house,$land,$date,$hour,$bookingID);
            $result=$stmt->execute();
                printf("resultkal: %d\n", $stmt->errno); 
            $last_id = $bookingID;
             if($stmt->errno==0){
                       $stmt=$this->conn->prepare("DELETE FROM `booking_details_service` WHERE IDbooking=?");
        $stmt->bind_param("i", $bookingID);
        $stmt->execute();
        $stmt->close();
                $result=true;
               

            }
                 printf("last_id: %d\n", $last_id); 
            $stmt->close(); 
        }
           
        }

       

            if($last_id!=0){


    $myArray=array();
        if(strpos($data, ",")!==false){
        $myArray = explode(',', $data);
    
    }else{
         $myArray = $day;
        $laterDate=$day;
    
    }
 
 
        if(count($myArray)>1){
            $dataArray=array();
        $myArray = explode(',', $data);
        foreach($myArray as $laterDate){
               if(strpos($laterDate, "_")!==false){

                echo $laterDate;
        $dataArray = explode('_', $laterDate);

       
            $stmt=$this->conn->prepare("INSERT INTO `booking_details_service`(`IDbooking`, `IDservice`,`Price`,`Duration`, `Date`, `Time`) VALUES  (?,?,?,?,?,?)");
            $stmt->bind_param("iidiss",$last_id,$dataArray[0],$dataArray[2],$dataArray[3],$date,$hour);
            $result=$stmt->execute();
                  printf("mul: %d\n", $stmt->error); 
             if($stmt->errno==0){
                $result=true;
            }
        

                           
        }
           }
       }
           else{
         $myArray = $data;
         if(strpos($data, "_")!==false){
        $dataArray = explode('_', $data);

          
      
           $stmt=$this->conn->prepare("INSERT INTO `booking_details_service`(`IDbooking`, `IDservice`,`Price`,`Duration`, `Date`, `Time`) VALUES  (?,?,?,?,?,?)");
            $stmt->bind_param("iidss",$last_id,$dataArray[0],$dataArray[2],$dataArray[3],$date,$hour);
            $result=$stmt->execute();
                  printf("kal: %s.\n", $stmt->error); 
             if($stmt->errno==0){
                $result=true;
            }
        
            $stmt->close();
       
          

                            }
            }

        }

          printf("resultkal: %s\n", $result); 
        if($result){
             $stmt=$this->conn->prepare("SELECT `ID`, `OrderDate`, `OrderID`, `IDuser`,(SELECT parlour_name FROM salon_registration WHERE ID=s.IDsalon) AS `IDsalon`, `IDspecialist`, `IDslot`, `IDserviceAt`, `Discount`, `NoofItems`, `Payable`, `Date`, `Time` FROM `store_booking` s WHERE OrderID=?");
        $stmt->bind_param("i", $otp);
        $stmt->execute();
        $user = $stmt->get_result()->fetch_assoc();
        $stmt->close();
         return $user;
        }else{

            return false;
        }

   

       
    }

      public function DeleteServiceID($mobile,$from){

        $ParlourID=0;
        $duplicateID=0;
        date_default_timezone_set(TIMEZONE);
        $hour=date("H:i:s");
        $date=date("Y-m-d");
        $result=false;
     

        $stmt=$this->conn->prepare("SELECT ID FROM salon_registration WHERE ID=? ");
        $stmt->bind_param("i",$mobile);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ParlourID=$user["ID"];

       
  
        if($ParlourID!=0){
            if($from!=0){
      
           $stmt = $this->conn->prepare("DELETE FROM servicesdetails WHERE ID=?") ;

        $stmt->bind_param("i",$from);
        $result = $stmt->execute();
        if( $stmt->errno==0){
                $result=true;
        
        }else {
             $result=false;
        } 
         printf("Error: %s.\n", $stmt->error); 
        $stmt->close(); 
    
            }
   
    }
 
 
          
 
            return $result;
        
     } 





      public function storeBothServiceID($mobile,$sdescription,$sprice,$stime,$hdescription,$hprice,$htime,$IP,$from){

        $ParlourID=0;
        $duplicateID=0;
        date_default_timezone_set(TIMEZONE);
        $hour=date("H:i:s");
        $date=date("Y-m-d");
        $result=false;
     

        $stmt=$this->conn->prepare("SELECT ID FROM salon_registration WHERE ID=? ");
        $stmt->bind_param("i",$mobile);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ParlourID=$user["ID"];

       
  
        if($ParlourID!=0){
            if($from!=0){
      
           $stmt = $this->conn->prepare("UPDATE `servicesdetails` SET `Details_salon`=?,`Price_salon`=?,`Time_salon`=?,`Details_home`=?,`Price_home`=?,`Time_home`=?,`Date`=?,`Time`=?,`User`=?,`IP`=? WHERE ID=?") ;

        $stmt->bind_param("ssssssssssi",$sdescription,$sprice,$stime,$hdescription,$hprice,$htime,$date,$hour,$mobile,$IP,$from);
        $result = $stmt->execute();
        if( $stmt->errno==0){
                $result=true;
        
        }else {
             $result=false;
        } 
         printf("Error: %s.\n", $stmt->error); 
        $stmt->close(); 
    
            }
   
    }
 
 
          
 
            return $result;
        
     } 



     public function storeCrewLeave($mobile,$available,$ID){

        $ParlourID=0;
        $result=false;
        $stmt=$this->conn->prepare("SELECT ID FROM salon_registration WHERE ID=? ");
        $stmt->bind_param("i",$mobile);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ParlourID=$user["ID"];
     
        if($ParlourID!=0){
          if($ID!=0){
        $stmt = $this->conn->prepare("UPDATE specialist_detail SET  `available`=? WHERE  ID=?") ;
        $stmt->bind_param("ii",$available,$ID);
        $result = $stmt->execute();
        if( $stmt->errno==0){
                $result=true;
        
        }
         printf("Error: %s.\n", $stmt->error); 
        $stmt->close();
            
        }
    }
 

 
            return $result;
        
     } 

     public function storeCrewDelete($mobile,$ID){

        $ParlourID=0;

        $stmt=$this->conn->prepare("SELECT ID FROM salon_registration WHERE ID=? ");
        $stmt->bind_param("i",$mobile);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ParlourID=$user["ID"];
     
        if($ParlourID!=0){
         if($ID!=0){
$stmt = $this->conn->prepare("DELETE FROM `specialist_detail` WHERE  ID=?") ;

        $stmt->bind_param("i",$ID);
        $result = $stmt->execute();
        if( $stmt->errno==0){
                $result=true;
        
        }else {
             $result=false;
        } 
         printf("Error: %s.\n", $stmt->error); 
        $stmt->close();
            }
        
    }
 

 
            return $result;
        
     } 



      public function storeBothService($mobile,$service,$sdescription,$sprice,$stime,$hdescription,$hprice,$htime,$IP,$from){

        $ParlourID=0;
        $duplicateID=0;
        date_default_timezone_set(TIMEZONE);
        $hour=date("H:i:s");
        $date=date("Y-m-d");
        $result=false;

              printf("Service: %s.\n", $service); 
        $stmt=$this->conn->prepare("SELECT ID FROM final_services WHERE Name=?");
        $stmt->bind_param("s",$service);
        $stmt->execute();
        $user = $stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ID=$user["ID"];

        $stmt=$this->conn->prepare("SELECT ID FROM salon_registration WHERE ID=? ");
        $stmt->bind_param("i",$mobile);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ParlourID=$user["ID"];

        $stmt=$this->conn->prepare("SELECT ID FROM servicesdetails WHERE SalonID=? AND FinalServiceID=? ");
        $stmt->bind_param("ii",$ParlourID,$ID);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $duplicateID=$user["ID"];
  
        if($ParlourID!=0){
            if($duplicateID==0){
      

                      $stmt = $this->conn->prepare("INSERT INTO `servicesdetails`(`SalonID`, `FinalServiceID`, `Details_salon`, `Price_salon`, `Time_salon`, `Details_home`, `Price_home`, `Time_home`,`Date`, `Time`, `User`, `IP`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ") ;

        $stmt->bind_param("iissssssssss",$ParlourID,$ID,$sdescription,$sprice,$stime,$hdescription,$hprice,$htime,$date,$hour,$mobile,$IP);
        $result = $stmt->execute();
        if( $stmt->errno==0){
                $result=true;
        
        }else {
             $result=false;
        } 
         printf("Error: %s.\n", $stmt->error); 
        $stmt->close();
    
            }else{
                   
              $stmt = $this->conn->prepare("UPDATE `servicesdetails` SET `Details_salon`=?,`Price_salon`=?,`Time_salon`=?,`Details_home`=?,`Price_home`=?,`Time_home`=?,`Date`=?,`Time`=?,`User`=?,`IP`=? WHERE ID=?") ;

        $stmt->bind_param("ssssssssssi",$sdescription,$sprice,$stime,$hdescription,$hprice,$htime,$date,$hour,$mobile,$IP,$duplicateID);
        $result = $stmt->execute();
        if( $stmt->errno==0){
                $result=true;
        
        }else {
             $result=false;
        } 
         printf("Error: %s.\n", $stmt->error); 
        $stmt->close(); 
       
            }
   
    }
 
 
          
 
            return $result;
        
     } 





        public function storeSalonService($mobile,$service,$description,$price,$time,$IP,$from){

        $ParlourID=0;
        $duplicateID=0;
        date_default_timezone_set(TIMEZONE);
        $hour=date("H:i:s");
        $date=date("Y-m-d");
        $result=false;
        $stmt=$this->conn->prepare("SELECT ID FROM final_services WHERE Name=?");
        $stmt->bind_param("s",$service);
        $stmt->execute();
        $user = $stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ID=$user["ID"];

        $stmt=$this->conn->prepare("SELECT ID FROM salon_registration WHERE ID=? ");
        $stmt->bind_param("i",$mobile);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ParlourID=$user["ID"];

      

        $stmt=$this->conn->prepare("SELECT ID FROM servicesdetails WHERE SalonID=? AND FinalServiceID=? ");
        $stmt->bind_param("ii",$ParlourID,$ID);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $duplicateID=$user["ID"];
  
        if($ParlourID!=0){
            if($duplicateID==0){
                if($from==1){

                      $stmt = $this->conn->prepare("INSERT INTO `servicesdetails`(`SalonID`, `FinalServiceID`, `Details_salon`, `Price_salon`, `Time_salon`,`Date`, `Time`, `User`, `IP`) VALUES (?,?,?,?,?,?,?,?,?) ") ;

        $stmt->bind_param("iisssssss",$ParlourID,$ID,$description,$price,$time,$date,$hour,$mobile,$IP);
        $result = $stmt->execute();
        if( $stmt->errno==0){
                $result=true;
        
        }else {
             $result=false;
        } 
         printf("Error: %s.\n", $stmt->error); 
        $stmt->close();
    }elseif ($from==2) {
     
                      $stmt = $this->conn->prepare("INSERT INTO `servicesdetails`(`SalonID`, `FinalServiceID`, `Details_home`, `Price_home`, `Time_home`,`Date`, `Time`, `User`, `IP`) VALUES (?,?,?,?,?,?,?,?,?) ") ;

        $stmt->bind_param("iisssssss",$ParlourID,$ID,$description,$price,$time,$date,$hour,$mobile,$IP);
        $result = $stmt->execute();
        if( $stmt->errno==0){
                $result=true;
        
        }else {
             $result=false;
        } 
         printf("Error: %s.\n", $stmt->error); 
        $stmt->close();
    }
            }else{
                     if($from==1){
              $stmt = $this->conn->prepare("UPDATE `servicesdetails` SET `Details_salon`=?,`Price_salon`=?,`Time_salon`=?,`Date`=?,`Time`=?,`User`=?,`IP`=? WHERE ID=?") ;

        $stmt->bind_param("sssssssi",$description,$price,$time,$date,$hour,$mobile,$IP,$duplicateID);
        $result = $stmt->execute();
        if( $stmt->errno==0){
                $result=true;
        
        }else {
             $result=false;
        } 
         printf("Error: %s.\n", $stmt->error); 
        $stmt->close(); 
        }elseif ($from==2) {
         $stmt = $this->conn->prepare("UPDATE `servicesdetails` SET `Details_home`=?,`Price_home`=?,`Time_home`=?,`Date`=?,`Time`=?,`User`=?,`IP`=? WHERE ID=?") ;

        $stmt->bind_param("sssssssi",$description,$price,$time,$date,$hour,$mobile,$IP,$duplicateID);
        $result = $stmt->execute();
        if( $stmt->errno==0){
                $result=true;
        
        }else {
             $result=false;
        } 
         printf("Error: %s.\n", $stmt->error); 
        $stmt->close(); 
        } 
            }
   
    }
 
 
 
            return $result;
        
     } 



         public function storeGallery($mobile,$description,$file_path){

        $ParlourID=0;

        $result=false;

        $stmt=$this->conn->prepare("SELECT ID FROM salon_registration WHERE ID=? ");
        $stmt->bind_param("i",$mobile);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ParlourID=$user["ID"];
        echo $ParlourID;
        if($ParlourID!=0){

         $stmt = $this->conn->prepare("INSERT INTO `salon_gallery`(`IDsalon`, `Photo`, `Details`) VALUES (?,?,?)") ;

        $stmt->bind_param("iss", $ParlourID,$file_path,$description);
        $stmt->execute();
        if( $stmt->errno==0){
                $result=true;
        
        }else {
             $result=false;
        } 
         printf("Error: %s.\n", $stmt->error); 
        $stmt->close();
    }
 
 
 
            return $result;
        
     } 



     public function storeCrewImage($mobile,$name,$description,$file_path,$service,$ID){

        $ParlourID=0;

        $stmt=$this->conn->prepare("SELECT ID FROM primary_services WHERE Name=?");
        $stmt->bind_param("s",$service);
        $stmt->execute();
        $user = $stmt->get_result()->fetch_assoc();
        $stmt->close();
        $primary_services=$user["ID"];

        $stmt=$this->conn->prepare("SELECT ID FROM salon_registration WHERE ID=? ");
        $stmt->bind_param("i",$mobile);
        $stmt->execute();
        $user=$stmt->get_result()->fetch_assoc();
        $stmt->close();
        $ParlourID=$user["ID"];
        echo $ParlourID;
        if($ParlourID!=0){
         if($ID!=0){
   
                 $stmt = $this->conn->prepare("UPDATE `specialist_detail` SET `crew_name`=?,`crew_detail`=?,`crew_pic`=?,`ParlourID`=?,`service`=? WHERE ID=?") ;

        $stmt->bind_param("sssisi", $name,$description,$file_path,$ParlourID,$service,$ID);
        $result = $stmt->execute();
        if( $stmt->errno==0){
                $result=true;
        
        }else {
             $result=false;
        } 
         printf("Error: %s.\n", $stmt->error); 
        $stmt->close();  
            
     
         }else{
             $stmt = $this->conn->prepare("INSERT INTO `specialist_detail`( `crew_name`, `crew_detail`, `crew_pic`, `ParlourID`, `service`, `available`) VALUES (?,?,?,?,?,1)") ;

        $stmt->bind_param("sssis", $name,$description,$file_path,$ParlourID,$service);
        $result = $stmt->execute();
        if( $stmt->errno==0){
                $result=true;
        
        }else {
             $result=false;
        } 
         printf("Error: %s.\n", $stmt->error); 
        $stmt->close();
         }
        
    }
 
 
            if($result){
                  $stmt = $this->conn->prepare("UPDATE `salon_registration` SET `isSpecialist`=1 WHERE ID=?");
            $stmt->bind_param("i",$ParlourID);
            $stmt->execute();
              if( $stmt->errno==0){
                $result=true;
        
        } 
         $stmt->close();
            }
 
            return $result;
        
     } 

     

  public function storeSchedule(

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
                $sat_close_time
              ){
        $parlour_id=0;
          date_default_timezone_set(TIMEZONE);
        $hour=date("H:i:s");
        $date=date("Y-m-d");
        $result=false;
        $stmt=$this->conn->prepare("SELECT ID FROM salon_registration WHERE ID=?");
        $stmt->bind_param("i", $parlour);
        $stmt->execute();
        $user = $stmt->get_result()->fetch_assoc();
        $stmt->close();
        $parlour_id=$user["ID"];
        

       

        if($parlour_id>0){
          

         $stmt = $this->conn->prepare("INSERT INTO `schedule_parlour`(`salonID`, `sun_open_close`, `sun_open_time`, `sun_close_time`, `mon_open_close`, `mon_open_time`, `mon_close_time`, `tue_open_close`, `tue_open_time`, `tue_close_time`, `wed_open_close`, `wed_open_time`, `wed_close_time`, `thr_open_close`, `thr_open_time`, `thr_close_time`, `fri_open_close`, `fri_open_time`, `fri_close_time`, `sat_open_close`, `sat_open_time`, `sat_close_time`, `Date`, `Time`) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)") ;

        $stmt->bind_param("iissississississississss",$parlour_id,$sun_open_close,$sun_open_time,$sun_close_time,$mon_open_close,$mon_open_time,$mon_close_time,$tue_open_close,$tue_open_time,
                $tue_close_time,$wed_open_close,$wed_open_time,$wed_close_time,$thr_open_close,$thr_open_time,$thr_close_time,$fri_open_close,$fri_open_time,$fri_close_time,$sat_open_close,$sat_open_time,$sat_close_time,$date,$hour
               );

        $result = $stmt->execute();
          printf("Error: %s.\n", $stmt->error); 
        if( $stmt->errno==0){
                $result=true;
        
        }else {
             $result=false;
        } 
        $stmt->close();
 
 }
        // check for successful store
        if($result){
            $stmt = $this->conn->prepare("UPDATE `salon_registration` SET `isSechedule`=1 WHERE ID=?");
            $stmt->bind_param("i",$parlour_id);
            $stmt->execute();
                      printf("Errors: %d.\n", $parlour_id); 
              if( $stmt->errno==0){
                $result=true;
        
        } 
         $stmt->close();
        } else {
              $result=false;
        }

        return $result;
     }

          public function RemoveServiceLatLong($mobile){
        $uID=0;
        $stmt = $this->conn->prepare("SELECT ID from salon_registration  WHERE ID = ?");
        $stmt->bind_param("i", $mobile);
        $stmt->execute();
        $user = $stmt->get_result()->fetch_assoc();
        $uID=$user['ID'];
        $stmt->store_result();
         $stmt->close();
 
        if ($uID > 0) {
                  $stmt = $this->conn->prepare("UPDATE `salon_registration` SET `isLocation`=0 WHERE ID=?");
            $stmt->bind_param("i",$uID);
            $stmt->execute();
              if( $stmt->errno==0){
                $result=true;
        
        } 
         $stmt->close();
    }else {
          
            $result=false;
        }
             

              return $result;
    }

     public function storeServiceLatLong($mobile,$lat,$long,$locality,$city){
        $uID=0;
        $stmt = $this->conn->prepare("SELECT ID from salon_registration  WHERE ID = ?");
        $stmt->bind_param("i", $mobile);
        $stmt->execute();
        $user = $stmt->get_result()->fetch_assoc();
        $uID=$user['ID'];
        $stmt->store_result();
         $stmt->close();
 
        if ($uID > 0) {
            $stmt = $this->conn->prepare("UPDATE `salon_registration` SET `latitude`=?,longitude=?,`parlour_city`=?,`parlour_locality`=? WHERE ID=?");
            $stmt->bind_param("ddssi",$lat,$long,$city,$locality,$uID);
            $stmt->execute();
              if( $stmt->errno==0){
                $result=true;
        
        } 
         $stmt->close();
    }else {
          
            $result=false;
        }
         


                if($result){
                      $stmt = $this->conn->prepare("UPDATE `salon_registration` SET `isLocation`=1 WHERE ID=?");
            $stmt->bind_param("i",$uID);
            $stmt->execute();
              if( $stmt->errno==0){
                $result=true;
        
        } 
         $stmt->close();
                }



              return $result;
    }
      public function storeServiceLocation($mobile,$service) {
        $uID=0;
        $stmt = $this->conn->prepare("SELECT ID from salon_registration  WHERE ID = ?");
        $stmt->bind_param("i", $mobile);
        $stmt->execute();
        $user = $stmt->get_result()->fetch_assoc();
        $uID=$user['ID'];
        $stmt->store_result();
        $stmt->close();
 
        if ($uID > 0) {
            $stmt = $this->conn->prepare("UPDATE `salon_registration` SET `service_location`=?,`isServiceAt`=1 WHERE ID=?");
            $stmt->bind_param("ii",$service,$uID);
            $stmt->execute();
              if( $stmt->errno==0){
                $result=true;
        
        } 
         $stmt->close();
    }else {
          
            $result=false;
        }

      

              return $result;
    }

    public function UpdateVerifyadmin($id,$value,$User){

    
        
        date_default_timezone_set('Africa/Johannesburg');
        $hour=date("H:i:s");
        $date=date("Y-m-d");
        $result=false;
        $ID=0;

            $stmt = $this->conn->prepare("SELECT ID FROM `parlour_detail` WHERE ID=?");
            $stmt->bind_param("i",$id);
            $stmt->execute();
            $user = $stmt->get_result()->fetch_assoc();
            $ID=$user['ID'];
            $stmt->close();
   

           
    if( $ID!=0){


    $stmt = $this->conn->prepare("UPDATE `parlour_detail` SET isVerified=? WHERE  ID=?");
    $stmt->bind_param("ii",$value,$ID);
    $stmt->execute();
    $error= $stmt->errno;
    printf("bbb: %d.\n", $error);
    $stmt->close();

            if($error==0){
     $result=true;
               
      
        }
    }

         
                return $result;
        
           
    }
}
?>