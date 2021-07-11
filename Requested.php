<!doctype html>
<html class="no-js" lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Groom Dashboard</title>
    <meta name="description" content="Groom">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="apple-touch-icon" href="apple-icon.png">
    <link rel="shortcut icon" href="favicon.ico">

    <link rel="stylesheet" href="vendors/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="vendors/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="vendors/themify-icons/css/themify-icons.css">
    <link rel="stylesheet" href="vendors/flag-icon-css/css/flag-icon.min.css">
    <link rel="stylesheet" href="vendors/selectFX/css/cs-skin-elastic.css">
    <link rel="stylesheet" href="vendors/jqvmap/dist/jqvmap.min.css">


    <link rel="stylesheet" href="assets/css/style.css">

    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800' rel='stylesheet' type='text/css'>

    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <script src="https://cdn.anychart.com/js/latest/anychart.min.js"></script>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <style type="text/css">
        #graph {
  width: 100%;
  height: 128px;
}
    </style>
 <script type="text/javascript">
function myFunction() {
  var myVarFromPhp = '<?php session_start();echo $_SESSION["email"] ?>';
  
  if(myVarFromPhp==''){
window.location.replace("http://139.59.38.160/Groom/Dashboard/page-login.php");
  }
};
</script>
</head>

<body onload="myFunction()">

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

        <div class="content mt-3" id="dataTable1">
            <div class="animated fadeIn">
                <div class="row">

                    <div class="col-md-12">
                        <div class="card" >
                         <div class="card-header">
                                <strong class="card-title">Appointments</strong>
                            </div>
                            <div class="card-body" >
                                <table id="first" class="table table-striped table-bordered">
                                    <thead>
                                        <tr>
                                                     <th>Sl No</th>
                                                      <th>Date</th>
                                                           <th>OTP</th>
                                            <th>Customer name</th>
                                           <th>Customer Email</th>
                                               <th>Customer Email</th>
                                             <th>Salon Name</th>
                                               <th>Salon Phone</th>
                                      
                                                  <th>Specialist</th>
                                                         <th>Time</th>
                                            <th>Request date</th>
                  
                                                     <th>Request Time</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                               <?php
                      require_once 'DB_Connect.php';
                            $id=$_GET['id'];
                      $db = new Db_Connect();
                      $conn = $db->connect();
                

                         if($id==1){
                               $users =$conn->query("SELECT b.ID, b.OrderDate, b.OrderID, b.IDuser, b.IDsalon, sd.crew_name, t.Slot,t.ActualTime, b.IDserviceAt, b.Discount, b.NoofItems, b.Payable, b.addressd, b.houseno, b.landmark, b.Date, b.Time,b.isAccepted,b.isRunning,b.isCompleted,b.isCancelled, u.PhoneNo, u.Name,u.PhoneNo, u.Email,u.Photo, sr.parlour_name, sr.Photo AS parlour_photo ,sr.parlour_mobile as parlour_mobile,sr.latitude AS Parlour_latitude,sr.longitude AS Parlour_longitude,b.Latitude AS Home_latitude,b.Longitude AS Home_longitude    FROM `store_booking` b INNER JOIN user_details u   ON u.ID=b.IDuser INNER JOIN timeslot t ON t.ID=b.IDslot INNER JOIN specialist_detail sd ON sd.ID=b.IDspecialist INNER JOIN salon_registration sr ON sr.ID=b.IDsalon  ");
                             }elseif($id==2){
                                   $users =$conn->query("SELECT b.ID, b.OrderDate, b.OrderID, b.IDuser, b.IDsalon, sd.crew_name, t.Slot,t.ActualTime, b.IDserviceAt, b.Discount, b.NoofItems, b.Payable, b.addressd, b.houseno, b.landmark, b.Date, b.Time,b.isAccepted,b.isRunning,b.isCompleted,b.isCancelled, u.PhoneNo, u.Name,u.PhoneNo, u.Email,u.Photo, sr.parlour_name, sr.Photo AS parlour_photo ,sr.parlour_mobile as parlour_mobile,sr.latitude AS Parlour_latitude,sr.longitude AS Parlour_longitude,b.Latitude AS Home_latitude,b.Longitude AS Home_longitude    FROM `store_booking` b INNER JOIN user_details u   ON u.ID=b.IDuser INNER JOIN timeslot t ON t.ID=b.IDslot INNER JOIN specialist_detail sd ON sd.ID=b.IDspecialist INNER JOIN salon_registration sr ON sr.ID=b.IDsalon WHERE b.isAccepted=0");
                             }elseif($id==3){
                                   $users =$conn->query("SELECT b.ID, b.OrderDate, b.OrderID, b.IDuser, b.IDsalon, sd.crew_name, t.Slot,t.ActualTime, b.IDserviceAt, b.Discount, b.NoofItems, b.Payable, b.addressd, b.houseno, b.landmark, b.Date, b.Time,b.isAccepted,b.isRunning,b.isCompleted,b.isCancelled, u.PhoneNo, u.Name,u.PhoneNo, u.Email,u.Photo, sr.parlour_name, sr.Photo AS parlour_photo ,sr.parlour_mobile as parlour_mobile,sr.latitude AS Parlour_latitude,sr.longitude AS Parlour_longitude,b.Latitude AS Home_latitude,b.Longitude AS Home_longitude    FROM `store_booking` b INNER JOIN user_details u   ON u.ID=b.IDuser INNER JOIN timeslot t ON t.ID=b.IDslot INNER JOIN specialist_detail sd ON sd.ID=b.IDspecialist INNER JOIN salon_registration sr ON sr.ID=b.IDsalon  WHERE b.isAccepted=1 AND b.isRunning=0 AND   b.isCompleted=0 AND   b.isCancelled=0");
                             }elseif($id==4){
                                   $users =$conn->query("SELECT b.ID, b.OrderDate, b.OrderID, b.IDuser, b.IDsalon, sd.crew_name, t.Slot,t.ActualTime, b.IDserviceAt, b.Discount, b.NoofItems, b.Payable, b.addressd, b.houseno, b.landmark, b.Date, b.Time,b.isAccepted,b.isRunning,b.isCompleted,b.isCancelled, u.PhoneNo, u.Name,u.PhoneNo, u.Email,u.Photo, sr.parlour_name, sr.Photo AS parlour_photo ,sr.parlour_mobile as parlour_mobile,sr.latitude AS Parlour_latitude,sr.longitude AS Parlour_longitude,b.Latitude AS Home_latitude,b.Longitude AS Home_longitude    FROM `store_booking` b INNER JOIN user_details u   ON u.ID=b.IDuser INNER JOIN timeslot t ON t.ID=b.IDslot INNER JOIN specialist_detail sd ON sd.ID=b.IDspecialist INNER JOIN salon_registration sr ON sr.ID=b.IDsalon  WHERE b.isAccepted=1 AND b.isRunning=1 AND  b.isCompleted=0 AND   b.isCancelled=0");
                             }
                             elseif($id==5){
                                   $users =$conn->query("SELECT b.ID, b.OrderDate, b.OrderID, b.IDuser, b.IDsalon, sd.crew_name, t.Slot,t.ActualTime, b.IDserviceAt, b.Discount, b.NoofItems, b.Payable, b.addressd, b.houseno, b.landmark, b.Date, b.Time,b.isAccepted,b.isRunning,b.isCompleted,b.isCancelled, u.PhoneNo, u.Name,u.PhoneNo, u.Email,u.Photo, sr.parlour_name, sr.Photo AS parlour_photo ,sr.parlour_mobile as parlour_mobile,sr.latitude AS Parlour_latitude,sr.longitude AS Parlour_longitude,b.Latitude AS Home_latitude,b.Longitude AS Home_longitude    FROM `store_booking` b INNER JOIN user_details u   ON u.ID=b.IDuser INNER JOIN timeslot t ON t.ID=b.IDslot INNER JOIN specialist_detail sd ON sd.ID=b.IDspecialist INNER JOIN salon_registration sr ON sr.ID=b.IDsalon  WHERE b.isAccepted=1 AND b.isRunning=0 AND  b.isCompleted=0 AND   b.isCancelled=1");
                             }
                  

  
                        if(!empty($users)): $count = 0; foreach($users as $user): $count++;

                    ?>
                    <tr>
                
                          <td><?php echo $count; ?></td>
                
                      <td><?php echo $user['OrderDate']; ?></td>
                        <td><a href="OrderDetails.php?id=<?php echo $user['ID']; ?>" style="display:block;color: blue;"><u><?php echo $user['OrderID']; ?></u></a></td>
                            <td><?php echo $user['Name']; ?></td>
                          <td><?php echo $user['Email']; ?></td>
                              <td><?php echo $user['PhoneNo']; ?></td>
                          <td><?php echo $user['parlour_name']; ?></td>
                        
                                <td><?php echo $user['parlour_mobile']; ?></td>
                                      <td><?php echo $user['crew_name']; ?></td>
                                            <td><?php echo $user['ActualTime']; ?></td>
                     <td><?php echo $user['Date']; ?></td>
                       <td><?php echo $user['Time']; ?></td>
                    </tr>
                    <?php endforeach; else: ?>
                    <tr><td colspan="5">No user(s) found......</td></tr>
                    <?php endif; ?>
                                    </tbody>
                                </table>

                            </div>
                       
                        </div>
                    </div>


                </div>
            </div><!-- .animated -->
        </div><!-- .content -->


    



    



    <script src="vendors/jquery/dist/jquery.min.js"></script>
    <script src="vendors/popper.js/dist/umd/popper.min.js"></script>
    <script src="vendors/bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="assets/js/main.js"></script>


    <script src="vendors/datatables.net/js/jquery.dataTables.min.js"></script>
    <script src="vendors/datatables.net-bs4/js/dataTables.bootstrap4.min.js"></script>
    <script src="vendors/datatables.net-buttons/js/dataTables.buttons.min.js"></script>
    <script src="vendors/datatables.net-buttons-bs4/js/buttons.bootstrap4.min.js"></script>
    <script src="vendors/jszip/dist/jszip.min.js"></script>
    <script src="vendors/pdfmake/build/pdfmake.min.js"></script>
    <script src="vendors/pdfmake/build/vfs_fonts.js"></script>
    <script src="vendors/datatables.net-buttons/js/buttons.html5.min.js"></script>
    <script src="vendors/datatables.net-buttons/js/buttons.print.min.js"></script>
    <script src="vendors/datatables.net-buttons/js/buttons.colVis.min.js"></script>
    <script src="assets/js/init-scripts/data-table/datatables-init.js"></script>


</body>

</html>
