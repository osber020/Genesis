put_dj.php
<?php
	
/*
 * This function will place a newly broadcasting DJ into the DJ table
 */	
 
 $response = array();
 
 if(isset($_POST['songHash']) && isset($_POST['sessionID']) && isset($_POST['userHash']) && isset($_POST['xlocation']) && isset($_POST['zlocation'])
   && isset($_POST['xMax']) && isset($_POST['xMin']) && isset($_POST['zMax']) && isset($_POST['zMin']))
 {
	$songHash = $_POST['songHash'];
	$sessionID = $_POST(['sessionID']);
	$userHash = $_Post(['userHash']);
	$xlocation = $_POST(['xlocation']);
	$zlocation = $_POST(['zlocation']);
	$xMax = $_POST(['xMax']);
	$xMin = $_POST(['xMin']);
	$zMax = $_POST(['zMax']);
	$zMin = $_POST(['zMin']);
	
	require_once __DIR__ . '/db_config.php';
	$db = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE, DB_PORT) or die(mysqli_error());
	
	$result = mysqli_query($db, "INSERT INTO dj
						VALUES(" $userHash + ", " + $xlocation + ", " + $zlocation ", " +
						$xMax + ", " $xMin + ", " $zMax + ", " + $zMin + "," +
						$numListners + ", " + $sessionID + ", " + $songHash + ", " $handle + ", " + $score + ");");
	
	if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
 
        // echoing JSON response
        echo json_encode($response);
	}
	mysqli_close($db);
 }

?>