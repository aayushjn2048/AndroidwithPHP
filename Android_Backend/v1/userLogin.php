<?php
require_once'../includes/DbOperations.php';
$response=array();
$db=new DbOperations();
$response['userInfo'] = $db->getInfo();
//$response['error']=false;
//$data = array();
if($_SERVER['REQUEST_METHOD']=='POST'){
    if(isset($_POST['username']) and isset($_POST['password'])){
        //$db=new DbOperations();
        if($db->userLogin($_POST['username'],$_POST['password'])){
            $user=$db->getUserByUsername($_POST['username']);
           
            //$response['userInfo'] = $db->getInfo();
            $response['error']=false;
            $response['id']=$user['id'];
            $response['username']=$user['username'];
            $response['password']=$user['password'];
            $response['name']=$user['name'];
            $response['email']=$user['email'];
            $response['phone']=$user['phone'];
            $response['groupname']=$user['groupname'];
            
            $response['message'] = "Maza aa gaya!!!";
        

        }else{
            $response['error']=true;
            $response['message']="INVALID username or password";
        }

    }else{
        $response['error']=true;
        $response['message']="requored fields are missing";
    }
}
echo json_encode($response);
?>