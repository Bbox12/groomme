<!DOCTYPE html>
<html lang="en">
<head>
<script src="https://www.gstatic.com/firebasejs/4.10.1/firebase-app.js"></script>
<script src="https://www.gstatic.com/firebasejs/4.10.1/firebase.js"></script>
<script src="https://www.gstatic.com/firebasejs/5.0.1/firebase-database.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>


<script type="text/javascript">
 $(document).ready(function(){
   var regID;
    var otp = "<?php echo $_GET["otp"];?>";
    var msg ='<?php echo $_GET["msg"];?>';
      var to ='<?php echo $_GET["to"];?>';




  var config = {
    apiKey: "AIzaSyCGc89BTLGGhq_0fE4MPQzW2li8OVksCGI",
    databaseURL: "https://groomme-269613.firebaseio.com/",
    projectId: "groomme-269613",
  };
  firebase.initializeApp(config);


  var databaseFire = firebase.database().ref('RegID').child(to);
  databaseFire.once('value', function(childSnapshot) {


          regID=childSnapshot.val();

          //alert(regID);
     
 
              
         dataString =  'title='+ "Request for appointment from" +'&message='+msg+
                  '&push_type='+ "individual" +'&regId='+ regID+
                  '&include_image='+ "FALSE" +'&image='+""+'&unique='+ otp;
    
  
                  if (window.XMLHttpRequest) {
 
    xmlhttp=new XMLHttpRequest();
  } else { 
    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
 xmlhttp.onreadystatechange=function() {
    if (this.readyState==4 && this.status==200) {

    	      // alert(this.responseText);
    
  }
}
 xmlhttp.open("POST","fcm_sent.php",true);
 xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
 xmlhttp.send(dataString);
            
          
    
        });
 
});


</script>



</head>

</html>
