<?php


 include_once("connectionDb.php");
 show();

 
function show()
{
 global $connect;
 

 
 $query = "Select * FROM users; "; //database table names
 
 
 $result = mysqli_query($connect, $query);
 $number_of_rows = mysqli_num_rows($result);
  
 $temp_array  = array();
  
 if($number_of_rows > 0) {
  while ($row = mysqli_fetch_assoc($result)) {
   $temp_array[] = $row;
 
 }
 }
  
 header('Content-Type: application/json');
 echo json_encode(array("showUser"=>$temp_array)); //'showUser' This parameter send by android. if the parameter is to be changed, it should be changed in the java codes as well.
 mysqli_close($connect);
}

 
?>
