put_profile.php
<?php
/*
 * This function will update the profiles table to add a new user profile.
 */

	$response = array();
 
 if(isset($_POST['userHash']) && isset($_POST['handle']) && isset($_POST['bio']))
 {
	$userHash = $_POST['userHash'];
	$handle = $_POST['handle'];
	$bio = $_POST['bio'];
	
	
	//Connect to db, because abstraction is for pussies.
	require_once __DIR__ . '/db_config.php';
	$db = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE, DB_PORT) or die(mysqli_error());
	
	$result = mysqli_query($db, "INSERT INTO profiles
						VALUES (" + $userHash + ", " + $bio + ",  0, 0, " + $handle + " );");
	
	if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
 
        // echoing JSON response
        echo json_encode($response);
	}
	mysqli_close($db);
 }
?>