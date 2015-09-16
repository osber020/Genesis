get_listeners_and_score.php
<?php
 /*
  * This function will retrieve the number of listeners and the current session score forward_static_call
  * a DJ as they are broadcasting.
  */
  
  $response = array();
  
  if(isset($_POST['sessionID']))
  {
	$sessionID = $_POST['sessionID'];
	
	require_once __DIR__ . '/db_config.php';
		$db = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE, DB_PORT) or die(mysqli_error());
		
		$result = mysqli_query($db, "UPDATE dj
											SET numListeners = (SELECT COUNT(distinct userHash)
																	 FROM listener
																	 WHERE sessionID = " + $sessionID + ") +
												WHERE sessionID = " + $sessionID + ";" +
									"SELECT numListeners, score
									FROM dj
									WHERE sessionID = " + $sessionID + ";");
									
	if (!empty($result)) 
	{
        // check for empty result
        if (mysqli_num_rows($result) > 0) 
		{
 
            $result = mysqli_fetch_array($result);
			$stats = array();
			$stats["numListeners"] = $result["numListeners"];
			$stats["score"] = $result["score"];
			
			$response["stats"] = array();
			array_push($response["stas"], $profile);
			echo json_encode($response);
		}
		
	}
	mysqli_close($db);
  }
?>