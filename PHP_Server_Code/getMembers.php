<?php
//Login Code
	session_start();
        require_once 'MySQL.php';
        @date_default_timezone_set("GMT");
        header("Content-type: text/xml");
        
        $userID = $_GET['userID'];
        
        if($userID != null){
            $mysql = new MySQL();
            $members = $mysql->getMembers($userID);
            
            $writer = new XMLWriter();
        
            $writer->openMemory();
            $writer->startDocument('1.0'); 
            $writer->setIndent(4); 
            $writer->startElement('response');
                       
            for($x = 0; $x < sizeof($members); $x++){
                $writer->writeElement('memberid', $members[$x][0]);
                $writer->writeElement('membername', $members[$x][1]);
            }
            
            $writer->endElement();
            $writer->endDocument();
        
            //Write the buffer to our file
            echo $writer->flush();
            
        }
        
        
        
        
        

?>
