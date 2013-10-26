<?php
//SQL Code
        require_once "{$_SERVER['DOCUMENT_ROOT']}/testing/Constants.php";
        
        class MySQL{
		private $conn;
		
		function __construct(){
			$this->conn = new mysqli(DB_SERVER, DB_USER, DB_PASSWORD, DB_NAME) or die("There was a problem connecting to the database");
		
		}
                
                function varifyUsernameAndPassword ($username, $password){
                    $query = "SELECT userID, userName FROM users WHERE userName = ? AND password = ? LIMIT 1";
                    if($stm = $this->conn->prepare($query)){
                        $stm->bind_param('ss', $username,$password);
			$stm->execute();
			$stm->bind_result($id, $username);
                        if($stm->fetch()){
                            $_SESSION['userID'] = $id;
                            $_SESSION['userName'] = $username;
                            $stm->close();
                            return true;
                        }
                    }
                    return false;
                }
                
                
                function getMembers($userID){
                    $query = "SELECT userID, userName FROM users WHERE userID != ?";
                     if($stm = $this->conn->prepare($query)){
                        $stm->bind_param('s', $userID);
			$stm->execute();
                        $stm->bind_result($result1 , $result2);
                        $array = array();
                        while($stm->fetch()){
                            array_push($array, array($result1, $result2));
                        }
                        $stm->close();
                        return $array;
                     }
                     return null;
                }
                
                function postMessage($message, $from, $to){
                    $query = "INSERT INTO message VALUES(NULL, ?)";
                    $query2 = "SELECT messageID FROM message ORDER BY messageID DESC LIMIT 1";
                    $query3 = "INSERT INTO connector VALUES(?, ?, ?, 0, NULL)";
                    
                    if($stm = $this->conn->prepare($query)){
                        $stm->bind_param('s', $message);
                        $stm->execute();                                        
                        $stm->close();
                        if($stm2 = $this->conn->prepare($query2)){
                            $stm2->execute();
                            $stm2->bind_result($result);
                            $stm2->fetch();
                            $stm2->close();
                            $res = intval($result);
                            
                            if($stms = $this->conn->prepare($query3)){
                                $f = intval($from);
                                $t = intval($to);
                                $stms->bind_param('iii', $res, $f, $t);
                                $stms->execute();
                                $stms->close();
                                return true;
                            }
                        }
                      	
                    }
                    
                    return false;
                }
                
                function getNewMessages($myauth){
                    $query = "SELECT message.messageText, users.userName FROM users, message, connector WHERE connector.to = ? AND connector.seen = 0 AND message.messageID = connector.id AND connector.from = users.userID";
                    $query2 = "UPDATE connector SET seen = 1 WHERE connector.to = ? AND connector.seen = 0";
                    if($stm = $this->conn->prepare($query)){
                        $stm->bind_param('s', $myauth);
			$stm->execute();
                        $stm->bind_result($result1 , $result2);
                        $array = array();
                        while($stm->fetch()){
                            array_push($array, array($result1, $result2));
                        }
                        $stm->close();
                        if($stm2 = $this->conn->prepare($query2)){
                            $stm2->bind_param('s', $myauth);
                            $stm2->execute();
                            $stm2->close();
                            return $array;
                        }
                        
                        
                    }
                    return null;
                    
                }
                
                
        }
	
?>
