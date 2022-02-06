<?php
 

 include_once("connectionDb.php");
 create();

 
 
function create()
{
 	global $connect;
  
 	$sql=$_POST['sql']; //'sql' This parameter send by android. if the parameter is to be changed, it should be changed in the java codes as well.
  
 	mysqli_query($connect, $sql) or die (mysql_error($connect));
 	mysqli_close($connect);
 
 	if($connect){
		echo"successful connection";
	}else{
		echo "connection failed";
	}
  
}
 
?>
