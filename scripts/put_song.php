put_song.php
<?php

/*
 * This function will update the DJ table to hold the song currently being played by
 * the DJ
 */
 
 $response = array();

 if(isset($_POST['songHash']) && isset($_POST['sessionID']))
 {
	$songHash = $_POST['songHash'];
	$sessionID = $_POST(['sessionID']);
	
	//Connect to db, because abstraction is for pussies.
	require_once __DIR__ . '/db_config.php';
	$db = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE, DB_PORT) or die(mysqli_error());
	
	$result = mysqli_query($db, "UPDATE dj SET songHash = " + $songHash + 
								" WHERE sessionID = " + sessionID + ";");
	
	if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
 
        // echoing JSON response
        echo json_encode($response);
	}
	mysqli_close($db);
 }
?>