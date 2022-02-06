<?php
 

 include_once("connectionDb.php");
 create();

 
 
function create()
{
 global $connect;
  
 $sql=$_POST['sql'];
  
 mysqli_query($connect, $sql) or die (mysql_error($connect));
 mysqli_close($connect);
 
 if($connect){
	echo"basarili kayıt";
}else{
	echo "not esadasdas";
}
  
}
 
?>