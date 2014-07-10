<?php

	mysql_connect("localhost","root","");
	mysql_select_db("public_screening_db");
	if(isset($_POST['name'])) :

		$table_name = $_POST['name'];

		if($table_name == "locations"):

		    $query = "SELECT * FROM locations";
		    $resultset = mysql_query($query);
		    while($row = mysql_fetch_array($resultset)){

		    	$location_id = $row['id'];

		    	echo $location_id."%".$row['lat']."%".$row['lon']."%".$row['name']."%".utf8_encode($row['description']);

		    	$query2 = "SELECT * FROM tags WHERE location_id = '$location_id'";

		    	$resultset2 = mysql_query($query2);
		    	while($row2 = mysql_fetch_array($resultset2)){
		    		echo "%".$row2['name'];
		    	}
		    	echo ";";
		    }
		endif;
		if($table_name == "images"):

			$location_id = $_POST['location_id'];

			$query = "SELECT * FROM images WHERE location_id = '$location_id'";
		    $resultset = mysql_query($query);
		    while($row = mysql_fetch_array($resultset)){
		    	echo $row['path'].";";
		    }
		endif;
	else:
	    echo "No valid Request Received";
	endif;


?>