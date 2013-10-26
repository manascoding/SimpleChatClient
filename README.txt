Simple Chat Client that runs in the terminal. Contains a PHP server back end. 
However, does not contain the MySQL database that is needed to run this. 

The Java code is in the Java_Client_Code folder. The Java code contains unit tests and
the code itself. The code is under a package called messenger. 

The PHP code is in the PHP_Server_Code. 

This is the SQL code that shows how the databases were set up.
Connector: CREATE TABLE `connector` ( `id` int(11) NOT NULL, `from` int(11) NOT NULL, `to` int(11) NOT NULL, `seen` tinyint(1) NOT NULL, `cid` int(11) NOT NULL AUTO_INCREMENT, PRIMARY KEY (`cid`) ) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=latin1;

Message: CREATE TABLE `message` ( `messageID` int(11) NOT NULL AUTO_INCREMENT, `messageText` text NOT NULL, PRIMARY KEY (`messageID`) ) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=latin1;

Users: CREATE TABLE `users` ( `userID` int(11) NOT NULL AUTO_INCREMENT, `userName` char(30) NOT NULL, `password` char(50) NOT NULL, PRIMARY KEY (`userID`) ) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;