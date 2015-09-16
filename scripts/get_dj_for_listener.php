get_dj_for_listener.php
<?php

 /*
  * This function will retrieve DJ's broadcasting over the area that the listener resides in.
  * Utilizing the offset and limit allows us to continue to pull more DJs for the listener.
  * Example LIMIT 20, OFFSET 0 will pull the first 20 DJ's from the DJ table as organized by
  * number of listeners.  
  */

	$response = array();

	if(isset($_POST['zlocation']) && isset($_POST['xlocation']) && isset($_POST['userHash']) && isset($_POST['offset']) && isset($_POST['limit']))
	{
		$zlocation = $_POST['zlocation'];
		$xlocation = $_POST['xlocation'];
		$userHash = $_POST['userHash'];
		$limit = $_POST['limit'];
		$offset = $_POST['offset'];
		
		require_once __DIR__ . '/db_config.php';
		$db = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE, DB_PORT) or die(mysqli_error());
		
		$result = mysqli_query($db, "UPDATE listener SET xlocation = " + $xlocation + 
													  ", zlocation = " + $zlocation + 
													  " WHERE userHash = " + $userHash + ");" +
									"SELECT A.handle, A.numListeners, A.songHash, A.sessionID
										FROM dj AS A, listener AS B
										WHERE B.xlocation>A.xMin and B.xlocation<A.xMax and B.zlocation>A.zMin and B.zlocation<A.zMax and B.userHash = " + $userHash +
										"GROUP BY(A.sessionID)
										ORDER BY A.numListeners DESC
										LIMIT " + $limit +
										"OFFSET " + $offset + ";");
		if (!empty($result)) 
		{
			// check for empty result
			if (mysqli_num_rows($result) > 0) 
			{
				$response["DJs"] = array();
				while($result = mysqli_fetch_array($result))
				{
					$profile = array();
					$profile["handle"] = $result["handle"];
					$profile["numListeners"] = $result["numListeners"];
					$profile["songHash"] = $result["songHash"];
					$profile["sessionID"] = $result["sessionID"];
					
					array_push($response["DJs"], $profile);
				}
				
				echo json_encode($response);
			}
			
		}
		mysqli_close($db);
										
	}
?>