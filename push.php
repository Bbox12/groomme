push.php
<?php
 

class Push {
 
    // push message title
    private $title;
    private $message;
    private $image;
    private $data;
    private $is_background;
       private $unique;
 
    function __construct() {
         
    }
 
    public function setTitle($title) {
        $this->title = $title;
    }
 
    public function setMessage($message) {
        $this->message = $message;
    }
 
    public function setImage($imageUrl) {
        $this->image = $imageUrl;
    }
  public function setunique($unique) {
        $this->unique = $unique;
    }
 
    
    public function setIsBackground($is_background) {
        $this->is_background = $is_background;
    }
 
    public function getPush() {
        $res = array();
        $res['data']['title'] = $this->title;
        $res['data']['is_background'] = $this->is_background;
        $res['data']['message'] = $this->message;
        $res['data']['image'] = $this->image;
         $res['data']['unique'] = $this->unique;
        $res['data']['timestamp'] = date('Y-m-d G:i:s');
        return $res;
    }
 
}