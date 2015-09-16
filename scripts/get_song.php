get_song.php
<?php
 /*
  * This function will retrieve the next song that the DJ is currently listening to and the
  * listener will hear next.
  */
  
  $response = array();

 if(isset($_POST['sessionID']))
 {
	$sessionID = $_POST['sessionID'];
	
	//Connect to db, because abstraction is for pussies.
	require_once __DIR__ . '/db_config.php';
	$db = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE, DB_PORT) or die(mysqli_error());
	
	$result = mysqli_query($db, "SELECT songHash FROM dj WHERE sessionID = " + $sessionID + ";");
	
	if (!empty($result)) 
	{
        // check for empty result
        if (mysqli_num_rows($result) > 0) 
		{
            $result = mysqli_fetch_array($result);
			$profile = array();
			$profile["songHash"] = $result["songHash"];
			$response["next"] = array();
			array_push($response["next"], $profile);
			echo json_encode($response);
		}
	}
	mysqli_close($db);
 }
?>