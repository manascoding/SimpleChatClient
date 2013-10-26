<?php
//Login Code
	session_start();
        require_once 'MySQL.php';
        @date_default_timezone_set("GMT"); 
        header("Content-type: text/xml");
        
        $username = $_POST['username'];
        $password = $_POST['password'];
        

        $mysql = new MySQL();
        // Should use function like sha1() to at least Hash the password
        $credentials = $mysql->varifyUsernameAndPassword($username, $password);
        
        $writer = new XMLWriter();
        
        $writer->openMemory();
        $writer->startDocument('1.0'); 
        $writer->setIndent(4); 
        $writer->startElement('response');
        //Check if we have the correct credentials
        if ($credentials){
            $writer->writeElement('authorized', 'true');
            $writer->writeElement('id', $_SESSION['userID']);
        }
        else{
            $writer->writeElement('authorized', 'false');
        }
         $writer->endElement();
        $writer->endDocument();
        
        //Write the buffer to our file
        echo $writer->flush(); 
        
        
        

?>
