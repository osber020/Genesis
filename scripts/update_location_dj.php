update_locatioin_dj.php
<?php

 /*
  * This function is meant to update the current location of a listener for finding local DJ's
  */
  
  $response = array();
  
  if(isset($_POST['xlocation']) && isset($_POST['zlocation']) && isset($_POST['xMax']) && isset($_POST['xMin']) 
			&& isset($_POST['zMax']) && isset($_POST['zMin']) && isset($_POST('userHash')))
  {
	$xlocation = $_POST['xlocation'];
	$zlocation = $_POST['zlocation'];
	$xMax = $_POST['xMax'];
	$xMin = $_POST['xMin'];
	$zMax = $_POST['zMax'];
	$zMin = $_POST['zMin'];
	$userHash = $_POST['userHash'];
	
	require_once __DIR__ . '/db_config.php';
	$db = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE, DB_PORT) or die(mysqli_error());
		
	$result = mysqli_query($db, "UPDATE listener SET xlocation = " + $xlocation + 
												  ", zlocation = " + $zlocation +  
												  ", xMax = " + $xMax + 
												  ", xMin = " + $xMin + 
												  ", zMax = " + $zMax + 
												  ", zMin = " + $zMin +
								" WHERE userHash = " + $userHash + ");");  
	if ($result) {
			// successfully inserted into database
			$response["success"] = 1; 
			// echoing JSON response
			echo json_encode($response);
	}
	mysqli_close($db);
  }
?>