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

		    	echo $location_id."%".$row['lat']."%".$row['lon']."%".$row['name']."%".$row['description'];

		    	$query2 = "SELECT * FROM tags WHERE location_id = '$location_id'";

		    	//echo $location_id.".........................";
		    	//echo "rows: ".mysql_num_rows($query2);

		    	$resultset2 = mysql_query($query2);
		    	while($row2 = mysql_fetch_array($resultset2)){
		    		echo "%".$row2['name'];
		    	}
		    	echo ";";
		    }

		endif;
	else:
	    echo "No valid Request Received";
	endif;


?>