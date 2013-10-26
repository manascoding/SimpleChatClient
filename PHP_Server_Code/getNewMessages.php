<?php
//Login Code
	session_start();
        require_once 'MySQL.php';
        @date_default_timezone_set("GMT");
        header("Content-type: text/xml");
        
        $myauth = $_POST['id'];
                
        
        if($myauth != null){
            $mysql = new MySQL();
            $array = $mysql->getNewMessages($myauth);
            
            if($array !=  null){
                $writer = new XMLWriter();
        
                $writer->openMemory();
                $writer->startDocument('1.0'); 
                $writer->setIndent(4); 
                $writer->startElement('response');
                
                for($x = 0; $x < sizeof($array); $x++){
                    $writer->writeElement('text', $array[$x][0]);
                    $writer->writeElement('name', $array[$x][1]);
                }
            
            $writer->endElement();
            $writer->endDocument();
        
            //Write the buffer to our file
            echo $writer->flush();
                
            }
        }
        
        
        
        

?>
