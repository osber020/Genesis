get_top_dj.php
<?php
 /*
  * This function is used to fetch the list of top DJ's currently broadcasting.
  * The list is generated based on the highest overall score of all DJs that
  * are currently broadcasting.
  */
  
	$response = array();
	
	if(isset($_POST['xlocation']) && isset($_POST['zlocation']) && isset($_POST['userHash']))
	{
		$zlocation = $_POST['zlocation'];
		$xlocation = $_POST['xlocation'];
		$userHash = $_POST['userHash'];
		
		require_once __DIR__ . '/db_config.php';
		$db = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE, DB_PORT) or die(mysqli_error());
		
		$result = mysqli_query($db, "UPDATE listener SET xlocation = " + $xlocation + 
													  ", zlocation = " + $zlocation + 
													  " WHERE userHash = " + $userHash + ";" +
									"SELECT A.handle, C.allTimeScore, A.sessionID
										FROM dj AS A, listener AS B, profiles as C
										WHERE A.userHash = C.userHash and B.xlocation>A.xMin and B.xlocation<A.xMax and B.zlocation>A.zMin and B.zlocation<A.zMax and B.userHash = " + $userHash +
									  " ORDER BY C.allTimeScore desc
										LIMIT 30;");

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
					$profile["allTimeScore"] = $result["allTimeScore"];
					$profile["sessionID"] = $result["sessionID"];
					
					array_push($response["DJs"], $profile);
				}
				
				echo json_encode($response);
			}
			
		}
		mysqli_close($db);
	}
?>