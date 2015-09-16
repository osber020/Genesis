put_listener.php
<?php
 /*
  * This function exists to place a listener obbject into the listener table when a user_error
  * tunes into a DJ
  */
  
  $response = array();
  
  if(isset($_POST['userHash']) && isset($_POST['xlocation']) && isset($_POST['zlocation']) && isset($_POST['sessionID']))
  {
	$userHash = $_POST['userHash'];
	$xlocation = $_POST['xlocation'];
	$zlocation = $_POST['zlocation'];
	$sessionID = $_POST['sessionID'];
	
	require_once __DIR__ . '/db_config.php';
	$db = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE, DB_PORT) or die(mysqli_error());
	
	$result = mysqli_query($db, "INSERT INTO listener
					VALUES(" + $userHash + ", " + $xlocation + ", " + $zlocation + ", " + $sessionID + ");")
					
	if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
 
        // echoing JSON response
        echo json_encode($response);
	}
	mysqli_close($db);
  }
	
?>