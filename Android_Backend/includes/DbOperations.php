<?php
    class DbOperations{
        private $con;
        function __construct(){
            require_once dirname(__FILE__).'/DbConnect.php';
            $db=new Dbconnect();
            $this->con=$db->connect();
        }


        /* CRUD -> c-create,r-read,u-update,d-delete*/

        public function createUser($username,$pass,$name,$email,$phone,$groupname){
            if($this->isUserExist($username,$email)){
                return 0;
            }else{
            $password=md5($pass);
            $stmt = $this->con->prepare("INSERT INTO `users` 
                                            (`id`, `username`, `password`, `name`, `email`, `phone`, `groupname`) 
                                            VALUES (NULL, ?, ?, ?, ?, ?,?);");
            $stmt->bind_param("ssssis",$username,$password,$name,$email,$phone,$groupname);

            if($stmt->execute()){
                return 1;
            }else{
                return 2;
            }
          }
        }


        public function userLogin($username,$pass){
            $password=md5($pass);
            $stmt=$this->con->prepare("SELECT id FROM users WHERE username =? AND password=?");
            $stmt->bind_param("ss",$username,$password);
            $stmt->execute();
            $stmt->store_result();
            return $stmt->num_rows > 0;
        }


        public function getUserByUsername($username){
            $stmt=$this->con->prepare("SELECT * FROM users WHERE username = ?");
            $stmt->bind_param("s",$username);
            $stmt->execute();
            return $stmt->get_result()->fetch_assoc();

        }

        public function getInfo()
        {
            $stmt = $this->con->prepare("SELECT `name`, `email`, `phone`, `groupname` FROM `users` WHERE 1");
            $stmt->execute();
            $stmt->bind_result($name,$email,$phone,$groupname);
            $userInfoList = array();
            while($stmt->fetch())
            {
                $temp = array();
                $temp['name'] = $name;
                $temp['email'] = $email;
                $temp['phone'] = $phone;
                $temp['groupname'] = $groupname;
                array_push($userInfoList,$temp);
            }
            //echo json_encode($userInfoList);
            return $userInfoList;
        }

        public function isUserExist($username,$email){
            $stmt=$this->con->prepare("SELECT id FROM users WHERE username=? OR email =?");
            $stmt->bind_param("ss",$username,$email);  //the '?' in prepare function is like %d in C and 
            //"ss" in bind_param is cross checking the 
            //data type of input that we are going to send in prepare function.
            $stmt->execute();
            $stmt->store_result();
            return $stmt->num_rows>0;
        }

       /* function getAllData($groupname){
            $query = $this->con->prepare("SELECT name,email FROM users WHERE groupname = ?");
            $query->bind_param("s",$groupname);
            $query->execute();
            return $query->get_result()->fetch_assoc();     
        }

        echo '<pre>';
        print_r(getAllData($groupname));
        echo '</pre>';*/

    }


?>