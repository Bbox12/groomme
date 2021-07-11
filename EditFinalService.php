<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js" lang="en">
<!--<![endif]-->

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Final Service</title>
    <meta name="description" content="Sufee Admin - HTML5 Admin Template">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="apple-touch-icon" href="apple-icon.png">
    <link rel="shortcut icon" href="favicon.ico">


    <link rel="stylesheet" href="vendors/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="vendors/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="vendors/themify-icons/css/themify-icons.css">
    <link rel="stylesheet" href="vendors/flag-icon-css/css/flag-icon.min.css">
    <link rel="stylesheet" href="vendors/selectFX/css/cs-skin-elastic.css">
  
    <link rel="stylesheet" href="main.css">
    <link rel="stylesheet" href="assets/css/style.css">

    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800' rel='stylesheet' type='text/css'>

 <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>


    <link type="text/css" rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500">
    <style>
      #locationField, #controls {
        position: relative;
        width: 200px;
      }
      #autocomplete {
        position: absolute;
        top: 0px;
        left: 0px;
        width: 99%;
      }
      .label {
        text-align: right;
        font-weight: bold;
        width: 100px;
        color: #303030;
        font-family: "Roboto";
      }
      #address {
        border: 1px solid #000090;
        background-color: #f0f9ff;
        width: 480px;
        padding-right: 2px;
      }
      #address td {
        font-size: 10pt;
      }
      .field {
        width: 99%;
      }
      .slimField {
        width: 80px;
      }
      .wideField {
        width: 200px;
      }
      #locationField {
        height: 20px;
        margin-bottom: 2px;
      }
    </style>

<script type="text/javascript">
function myFunction() {
  var myVarFromPhp = '<?php session_start();echo $_SESSION["email"] ?>';
    var error = '<?php session_start();echo $_SESSION["error"] ?>';
  if(myVarFromPhp==''){
window.location.replace("http://139.59.38.160/Groom/Dashboard/page-login.php");
  }else{
     if(error!=''){
      if(error==1){
alert("Suucessfully stored information.");
  }else{
    if(error==2){
alert("Error in Storing information");
  }else{
    alert("Please check the information");
  }
  }
}

var id='id='+<?php echo $_GET["id"] ?>;


 var xmlhttp;
    if (window.XMLHttpRequest) {
    xmlhttp=new XMLHttpRequest();
  } else { 
    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
 xmlhttp.onreadystatechange=function() {
 
    if (this.readyState==4 && this.status==200) {
          
         var arr = JSON.parse(this.responseText);
         console.log(this.responseText);  
         var i;
             if(arr.length!=0){
                           
                               for( i=0;i<arr.length;i++){

                                      document.getElementById('ID').value=arr[i].ID;
                            
                                  document.getElementById('Name').value=arr[i].Name;
                                 document.getElementById('role').value=arr[i].SecondaryServiceID;
                    
                               }
             }
  }
}
 xmlhttp.open("POST","getFinalService.php",true);
 xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
 xmlhttp.send(id);

  }
};
</script>
</head>

<body onload="myFunction()">
<?php session_start();
       $_SESSION["error"]='';?>

 <aside id="left-panel" class="left-panel">
        <nav class="navbar navbar-expand-sm navbar-default">

            <div class="navbar-header">
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#main-menu" aria-controls="main-menu" aria-expanded="false" aria-label="Toggle navigation">
                    <i class="fa fa-bars"></i>
                </button>
                <a class="navbar-brand" href="./"><img src="images/logo.png" alt="Logo"></a>
                <a class="navbar-brand hidden" href="./"><img src="images/logo.png" alt="Logo"></a>
            </div>

            <div id="main-menu" class="main-menu collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <li class="active">
                        <a href="admin.php"> <i class="menu-icon fa fa-dashboard"></i>Dashboard </a>
                    </li>

                      <h3 class="menu-title">Salon Page</h3>
                     <li class="menu-item-has-children dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <i class="menu-icon fa fa-th"></i>Add </a>
                        <ul class="sub-menu children dropdown-menu">
                             <li><i class="menu-icon fa fa-pencil-square-o"></i><a href="VerifySalon.php">Verify Salon</a></li>
                               <li><i class="menu-icon fa fa-sign-in"></i><a href="PushNotification.php">Push Notification</a></li>
                                 <li><i class="menu-icon fa fa-pencil-square-o"></i><a href="stafftracker.html">Google Map </a></li>
                                   <li><i class="menu-icon fa fa-pencil-square-o"></i><a href="AssignPopularSalon.php">Popular Salon</a></li>
                        </ul>
                    </li>


                      <li class="menu-item-has-children dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <i class="menu-icon fa fa-list"></i>Service</a>
                        <ul class="sub-menu children dropdown-menu">
                            <li><i class="menu-icon fa fa-th"></i><a href="AddPrimaryService.php">Primary Service</a></li>
                            <li><i class="menu-icon fa fa-th"></i><a href="AddSecondaryService.php">Secondary Service</a></li>
                            <li><i class="menu-icon fa fa-th"></i><a href="AddFinalService.php">Final Service</a></li>
               
                        </ul>
                    </li>


                          <li class="menu-item-has-children dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <i class="menu-icon fa fa-list"></i>Policy</a>
                        <ul class="sub-menu children dropdown-menu">
                            <li><i class="menu-icon fa fa-th"></i><a href="NewPolicy.php">New</a></li>
                            <li><i class="menu-icon fa fa-th"></i><a href="UpdatePolicy.php">Update</a></li>
                        </ul>
                    </li>


                   
                   <h3 class="menu-title">Operation</h3>
                     <li class="menu-item-has-children dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <i class="menu-icon fa fa-th"></i>Staff</a>
                        <ul class="sub-menu children dropdown-menu">
                             <li><i class="menu-icon fa fa-th"></i><a href="staff_registration_admin.php">Registration</a></li>
                            <li><i class="menu-icon fa fa-th"></i><a href="VerifyStaff.php">Verify</a></li>
                               <li><i class="menu-icon fa fa-th"></i><a href="DeleteStaff.php">Delete</a></li>
                        </ul>
                    </li>
         
  <h3 class="menu-title">Extras</h3><!-- /.menu-title -->
                    <li class="menu ">
                        <a href="page-login.php" ><i class="menu-icon fa fa-sign-out"></i> LOGOUT</a>
                       
                    </li>      
                </ul>
            </div>
        </nav>
    </aside>

    <div id="right-panel" class="right-panel">

        <!-- Header-->
        <header id="header" class="header">

            <div class="header-menu">

                <div class="col-sm-7">
                    <a id="menuToggle" class="menutoggle pull-left"><i class="fa fa fa-tasks"></i></a>
                    <div class="header-left">
                       
   <div class="dropdown for-notification">
                            <button class="btn btn-secondary dropdown-toggle" type="button" id="notification" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <i class="fa fa-bell"></i>
                            
                            </button>
                           
                        </div>

                        <div class="dropdown for-message">
                            <button class="btn btn-secondary dropdown-toggle" type="button"
                                id="message"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <i class="ti-email"></i>
                            
                            </button>
                            <div class="dropdown-menu" aria-labelledby="message">
                                <p class="red">You have no Mails</p>
                               
                           
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-5">
                    <div class="user-area dropdown float-right">
                           <a href="page-login.php" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><?php  session_start(); echo $_SESSION["email"]."|".$_SESSION["name"];?></a>
                        </a>
                    </div>
                </div>
          </div>

        </header>




        <div class="breadcrumbs">
            <div class="col-sm-4">
                <div class="page-header float-left">
                    <div class="page-title">
                        <h1>Dashboard</h1>
                    </div>
                </div>
            </div>
            <div class="col-sm-8">
                <div class="page-header float-right">
                    <div class="page-title">
                        <ol class="breadcrumb text-right">
                            <li><a href="index.php">Dashboard</a></li>
                            <li><a href="#">Service</a></li>
                            <li class="active">Add Final Service</li>
                        </ol>
                    </div>
                </div>
            </div>
        </div>

        <div class="content mt-3">
            <div class="animated fadeIn">


                <div class="row">
          
                                                  

                  
                                              <div class="col-lg-12">
                                                 <div class="card">
                                                    <div class="card-header">
                                                        <strong>New  Service</strong>
                                                    </div>
                                                    <div class="card-body card-block">
                                                        <form action="editFinalWorkingSite.php" method="post" enctype="multipart/form-data" target="_self" class="form-horizontal">
                                                            
                                              
                                       

                                                    <div class="card-body card-block">


                                                    
                                                 <div class="row input-group">

                                                      <div class="col col-md-3"><label for="text-input" class=" input-sm form-control-sm form-control-label">ID</label></div>
                             <div class="col-9 col-md-9"><input type="text" id="ID" name="ID" placeholder="Enter the name" class="input-sm form-control-sm form-control"  readonly></div>
                                                       <div class="col col-md-3"><label for="text-input" class=" input-sm form-control-sm form-control-label">Secondary Service*</label></div>
                             <div class="col-8 col-md-8">  <select  id="role" name="role" class="input-sm form-control-sm form-control"  required >
   <option value="0">--Select--</option>

                                                                             <?php  
                        
                                 require_once 'DB_Connect.php';
        $db = new Db_Connect();
        $conn = $db->connect();
        if(!$conn){
        echo "Could not connect to DBMS"; 
         }else {  $sql =$conn->query("SELECT `ID`, Service AS `Name` FROM `secondary_service` ");
        foreach($sql as $row) {
    ?>

 <option value="<?php echo $row['ID']; ?>">  
                                         <?php 
                                      
                                         echo $row['Name'];?>  
   </option>  
                      <?php
}
}
?>

                                                                        </select></div>
                                                                    
                             <div class="col col-md-3"><label for="text-input" class=" input-sm form-control-sm form-control-label">Service&nbspName</label></div>
                             <div class="col-9 col-md-9"><input type="text" id="Name" name="Name" placeholder="Enter the name" class="input-sm form-control-sm form-control"  required></div>
                            <div class="col col-md-3"><label for="text-input" class=" input-sm form-control-sm form-control-label">Browse Image</label></div>
                              <div class="col-8 col-md-8"><input type="file" id="photo" name="photo" class="input-sm form-control-sm form-control-file"></div>
                     
                         
                         </div>
                              <div class="row input-group">
                                    <div class="col col-md-3"><label for="text-input" class=" input-sm form-control-sm form-control-label">For</label></div>                                  
 
                                                            
                                                                    <div class="col col-md-9">
                                                                        <div class="form-check-inline form-check">
                                                                            <label for="inline-checkbox1" class="form-check-label " style="padding-right: 20px;">
                                                                                <input type="checkbox" id="inline-checkbox1" name="husbandry[]" value="Ladies" class="form-check-input">Ladies
                                                                            </label>
                                                                            <label for="inline-checkbox2" class="form-check-label " style="padding-right: 20px;">
                                                                                <input type="checkbox" id="inline-checkbox2" name="husbandry[]" value="Gents" class="form-check-input">Gents
                                                                            </label>
                                                                            <label for="inline-checkbox3" class="form-check-label " style="padding-right: 20px;">
                                                                                <input type="checkbox" id="inline-checkbox3" name="husbandry[]" value="Kids" class="form-check-input">Kids
                                                                            </label>
                                                                              <label for="inline-checkbox3" class="form-check-label " style="padding-right: 20px;">
                                                                                <input type="checkbox" id="inline-checkbox4" name="husbandry[]" value="Bridal" class="form-check-input">Bridal
                                                                            </label>
                                                                        
                                                                        </div>
                                                                    </div>
                                                       

                         
                         </div>
                      
                                                     
                                                          
                                                     

                                               
                                                        <button type="submit" class="btn btn-primary btn-sm">
                                                            <i class="fa fa-dot-circle-o"></i> Submit
                                                        </button>
                                                        <button type="reset" class="btn btn-danger btn-sm">
                                                            <i class="fa fa-ban"></i> Reset
                                                        </button>
                                                  
                                                          </form>
                                                            </div>
                                                </div>
                                             </div>
                                           </div>
                  
                                      
                                    </div><!-- .content -->
                                </div><!-- /#right-panel -->
                                <!-- Right Panel -->


          
                            <script src="vendors/jquery/dist/jquery.min.js"></script>
                            <script src="vendors/popper.js/dist/umd/popper.min.js"></script>
                            <script src="vendors/jquery-validation/dist/jquery.validate.min.js"></script>
                            <script src="vendors/jquery-validation-unobtrusive/dist/jquery.validate.unobtrusive.min.js"></script>
                            <script src="vendors/bootstrap/dist/js/bootstrap.min.js"></script>
                            <script src="assets/js/main.js"></script>
                            <script src="main.js"></script>
</body>
</html>
