<?php
//Login Code
	session_start();
        require_once 'MySQL.php';
        @date_default_timezone_set("GMT");
        
        $text = $HTTP_RAW_POST_DATA;       
        if($text != null){
            $xml = simplexml_load_string($text);
            
            // Get all the values from the XML
            $message = $xml->text;
            $to = $xml->to->id;
            $from = $xml->from;
            $mysql = new MySQL();
            $answer = $mysql->postMessage($message, $from, $to);
            $writer = new XMLWriter();
            $writer->openMemory();
            $writer->startDocument('1.0'); 
            $writer->setIndent(4); 
            $writer->startElement('response');
            
            if($answer){
                 $writer->writeElement('success', "true");
            }
            else{
                 $writer->writeElement('success', "false");
            }
            
            $writer->endElement();
            $writer->endDocument();
        
            //Write the buffer to our file
            echo $writer->flush();
            
            
        }
        

?>
