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
    <title>New Subscription</title>
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



<script type="text/javascript">
function myFunction() {
  var myVarFromPhp = '<?php session_start();echo $_SESSION["email"] ?>';
    var error = '<?php session_start();echo $_SESSION["error"] ?>';
  if(myVarFromPhp==''){
window.location.replace("http://139.59.38.160/Groom/Dashboard/page-login.php");
  }else{
      if(error==1){
alert("Suucessfully stored information.");
  }else{
    if(error==1){
alert("Error in Storing information");
  }
  }
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
                           <li><i class="menu-icon fa fa-pencil-square-o"></i><a href="AddCategory.php">Salon Category</a></li>
                             <li><i class="menu-icon fa fa-pencil-square-o"></i><a href="VerifySalon.php?id=1">Verify Salon</a></li>
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


                          <li class="menu-item-has-children dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <i class="menu-icon fa fa-list"></i>Subscription</a>
                        <ul class="sub-menu children dropdown-menu">
                            <li><i class="menu-icon fa fa-th"></i><a href="NewSubscription.php">New</a></li>
                            <li><i class="menu-icon fa fa-th"></i><a href="UpdateSubscription.php">Update</a></li>
                             <li><i class="menu-icon fa fa-th"></i><a href="DeleteSubscription.php">Delete</a></li>
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
                        <button class="search-trigger"><i class="fa fa-search"></i></button>
                        <div class="form-inline">
                            <form class="search-form">
                                <input class="form-control mr-sm-2" type="text" placeholder="Search ..." aria-label="Search">
                                <button class="search-close" type="submit"><i class="fa fa-close"></i></button>
                            </form>
                        </div>
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
                            <li><a href="#">Subscription</a></li>
                            <li class="active">New Subscription</li>
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
                                                        <strong>Update Subscription</strong>
                                                    </div>
                                                    <div class="card-body card-block">
                                                        <form action="updateSubscriptionPOST.php" method="post" enctype="multipart/form-data" target="_self" class="form-horizontal">
                                                            
                                              
                                       

                                                    <div class="card-body card-block">


                                                    
                                                 <div class="row input-group">
                                                                    
                             <div class="col col-md-3"><label for="text-input" class=" input-sm form-control-sm form-control-label">Title</label></div>
                            <div class="col-6 col-md-6">  <select  id="Title" name="Title" class="input-sm form-control-sm form-control"  required >
   <option value="0">--Select--</option>

                                                                             <?php  
                        
                                 require_once 'DB_Connect.php';
        $db = new Db_Connect();
        $conn = $db->connect();
        if(!$conn){
        echo "Could not connect to DBMS"; 
         }else {  $sql =$conn->query("SELECT `ID`, `Name` FROM `subscribe` ");
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
                         
                         </div>

                                <div class="row input-group">
                                                                    
                              <div class="col col-md-3"><label for="text-input" class=" input-sm form-control-sm form-control-label">New Title</label></div>
                            <div class="col-6 col-md-6"><input  type="text" id="nTitle" name="nTitle"  class="input-sm form-control-sm form-control" required=""/></div>
                           
                            
                                                            </div>
                           
                                                            <div class="row input-group">
                                                                    
                              <div class="col col-md-3"><label for="text-input" class=" input-sm form-control-sm form-control-label">Details</label></div>
                            <div class="col-6 col-md-6"><textarea type="text" lines=4 id="Details" name="Details"  class="input-sm form-control-sm form-control" required=""></textarea></div>
                           
                            
                                                            </div>
                                                     
                                                          
                                                                      <div class="row input-group" style="margin-top: 20px;">
                                                                    
                             <div class="col col-md-3"><label for="text-input" class=" input-sm form-control-sm form-control-label">Valid for </label></div>
                              <div class="col-9 col-md-9"><input type="text" id="Valid" name="Valid" placeholder="No of days" class="input-sm form-control-sm form-control" ></div>
                           
                                                            </div>
                                                             
                                                    
                                                      <div class="row input-group" style="margin-top: 20px;">
                                                                    
                             <div class="col col-md-3"><label for="text-input" class=" input-sm form-control-sm form-control-label">Price</label></div>
                              <div class="col-9 col-md-9"><input type="text" id="Price" name="Price" placeholder="Price" class="input-sm form-control-sm form-control" ></div>
                           
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
                                 
                                        </div><!-- .animated -->
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
