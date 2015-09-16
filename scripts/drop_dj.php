drop_dj.php
<?php
 /*
 * This function will drop DJs from the DJ table when they are done broadcasting, as
 * well as cascade and remove entries in the listener table with corresponding sessionID.
 */
 
 $response = array();

 if(isset($_POST['userHash']))
 {
	$userHash = $_POST['userHash'];
	
	//Connect to db, because abstraction is for pussies.
	require_once __DIR__ . '/db_config.php';
	$db = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE, DB_PORT) or die(mysqli_error());
	
	$result = mysqli_query($db, "DELETE FROM dj where userHash = " + $userHash + ";");
	
	if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
 
        // echoing JSON response
        echo json_encode($response);
	}
	mysqli_close($db);
 }
?>