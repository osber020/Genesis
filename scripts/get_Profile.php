get_profile.php
<?php

 /*
  * This function will retrieve individual user information for a user when they sign in
  */
  
  $response = array();

 if(isset($_POST['userHash']))
 {
	$userHash = $_POST['userHash'];
	
	//Connect to db, because abstraction is for pussies.
	require_once __DIR__ . '/db_config.php';
	$db = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE, DB_PORT) or die(mysqli_error());
	
	$result = mysqli_query($db, "select * from profiles where userHash = " + $userHash + ";");
	
	if (!empty($result)) 
	{
        // check for empty result
        if (mysqli_num_rows($result) > 0) 
		{
 
            $result = mysqli_fetch_array($result);
			$profile = array();
			$profile["bio"] = $result["bio"];
			$profile["allTimeScore"] = $result["allTimeScore"];
			$profile["maxListeners"] = $result["maxListeners"];
			$profile["handle"] = $result["handle"];
			
			$response["profile"] = array();
			array_push($response["profile"], $profile);
			echo json_encode($response);
		}
		
	}
	mysqli_close($db);
 }
?>