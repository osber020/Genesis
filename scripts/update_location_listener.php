update_location_listener
<?php

 /*
  * This function is meant to update the current location of a listener for finding local DJ's
  */
  
  $response = array();
  
  if(isset($_POST['xlocation']) && isset($_POST['zlocation']))
  {
	$xlocation = $_POST['xlocation'];
	$zlocation = $_POST['zlocation'];
	
	require_once __DIR__ . '/db_config.php';
	$db = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE, DB_PORT) or die(mysqli_error());
		
	$result = mysqli_query($db, "UPDATE listener SET xlocation = " + $xlocation + ", zlocation = " + $zlocation + " WHERE userHash = " + $userHash + ");");
  
	if ($result) {
			// successfully inserted into database
			$response["success"] = 1; 
			// echoing JSON response
			echo json_encode($response);
	}
	mysqli_close($db);
  }
?>