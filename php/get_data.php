<?php

	mysql_connect("localhost","root","");
	mysql_select_db("public_screening_db");
	if(isset($_POST['name'])) :

		$table_name = $_POST['name'];

		if($table_name == "locations"):

		    $query = "SELECT * FROM locations";
		    $resultset = mysql_query($query);
		    while($row = mysql_fetch_array($resultset))
		    	echo $row['id']."%".$row['lat']."%".$row['lon']."%".$row['name']."%".$row['description'].";";

		endif;
	else:
	    echo "No valid Request Received";
	endif;


?>