
        <?php
        // Enabling error reporting
        error_reporting(-1);
        ini_set('display_errors', 'On');
 
        require_once __DIR__ . '/firebase.php';
        require_once __DIR__ . '/push.php';
 
        $firebase = new Firebase();
        $push = new Push();
 
        // optional payload
       
       
        // notification title
        $title = isset($_POST['title']) ? $_POST['title'] : '';

         $image = isset($_POST['image']) ? $_POST['image'] : '';
         
        // notification message
        $message = isset($_POST['message']) ? $_POST['message'] : '';
         
        // push type - single user / topic
        $push_type = isset($_POST['push_type']) ? $_POST['push_type'] : '';
         
        $include_image = isset($_POST['include_image'])? $_POST['include_image'] : '';

           $unique = isset($_POST['unique']) ? $_POST['unique'] : '';
    
    $push->setunique($unique);
        $push->setTitle($title);
        $push->setMessage($message);
        if ($include_image=='TRUE') {
            $push->setImage($image);
        } else {
            $push->setImage('');
        }
        $push->setIsBackground(FALSE);
        
        $json = '';
        $response = '';
 
       
            $json = $push->getPush();
            $regId = isset($_POST['regId']) ? $_POST['regId'] : '';
            $response = $firebase->send($regId, $json);
            echo  $response;
        
        ?>
     