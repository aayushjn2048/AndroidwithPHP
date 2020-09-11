<?php
require_once'../includes/DbOperations.php';
$response=array();
if($_SERVER['REQUEST_METHOD']=='POST'){
    if(isset($_POST['username']) and 
    isset($_POST['password']) and 
    isset($_POST['name']) and 
    isset($_POST['email']) and 
    isset($_POST['phone']) and 
    isset($_POST['groupname'])){
        //operate the data further

        $db= new DbOperations();
        $result=$db->createUser(
                                $_POST['username'],
                                $_POST['password'],
                                $_POST['name'],
                                $_POST['email'],
                                $_POST['phone'],
                                $_POST['groupname']
                                );
        if($result==1){
            $response['error']=false;
            $response['message']="User registered successfully";
        }elseif($result==2){
            $response['error']=true;
            $response['message']="some error occurred plese try again";
        }elseif($result==0){
            $response['error']=true;
            $response['message']="User alredy exist! use diffrent username and email !!";
        }
        }else{
        $response['error']=true;
        $response['message']="Required fields are missing";
    }
}else{
    $response['error']=true;
    $response['message']="Inalid Request";
}
echo json_encode($response);
?>
