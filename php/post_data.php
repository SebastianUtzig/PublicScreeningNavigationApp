<?php 

	mysql_connect("localhost","root","");
	mysql_select_db("public_screening_db");

	$lat =doubleval($_POST['lat']);
	$lon =doubleval($_POST['lon']);
	$name = $_POST['name'];

	$insert = mysql_query("INSERT INTO locations (lat, lon, name) VALUES ('$lat', '$lon','$name')");



?>