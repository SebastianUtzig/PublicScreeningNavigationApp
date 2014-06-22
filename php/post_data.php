<?php 

	mysql_connect("localhost","root","");
	mysql_select_db("public_screening_db");

	$lat =doubleval($_POST['lat']);
	$lon =doubleval($_POST['lon']);
	$name = $_POST['name'];
	$description = $_POST['description'];

	$insert = mysql_query("INSERT INTO locations (lat, lon, name, description) VALUES ('$lat', '$lon','$name','$description')");

	$insert_id = mysql_insert_id();

	$tags = explode(" ", $_POST['tags']);

	foreach ($tags as $tag) {
		$insert2 = mysql_query("INSERT INTO tags (name, location_id) VALUES ('$tag','$insert_id')");
	}

	//app uses this id to store new location locally:
	echo $insert_id;


?>