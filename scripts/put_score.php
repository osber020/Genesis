put_score.php
<?php
	
	/*
	 * This function is used to increment or decrement the current score of a DJ's broadcast.
	 */
	
	$response = array();
	
	if(isset($_POST['input']) && isset($_POST['sessionID']))
	{
		$input = $_POST['input'];
		$sessionID = $_POST['sessionID'];
		
		require_once __DIR__ . '/db_config.php';
		$db = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE, DB_PORT) or die(mysqli_error());
		
		$result = mysqli_query($db, "UPDATE dj SET score = score + " + $input + "WHERE sessionID = " + $sessionID  ");");
		
		if ($result) {
			// successfully inserted into database
			$response["success"] = 1; 
			// echoing JSON response
			echo json_encode($response);
		}
		mysqli_close($db);
	}
?>