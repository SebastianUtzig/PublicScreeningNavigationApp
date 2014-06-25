<?php
    $title = $_REQUEST['title'];
    $location_id = $_REQUEST['location_id'];
    $base=$_REQUEST['image'];
    $binary=base64_decode($base);
    header('Content-Type: bitmap; charset=utf-8');

    $filepath = '..\\img\\'.$title.'.jpg';
    $file = fopen($filepath, 'wb');

    $filepath2 = "../img/".$title.".jpg";
    echo "filepath: ".$filepath;
    echo "location id: ".$location_id;
    fwrite($file, $binary);
    fclose($file);
    echo 'Image upload complete!!, Please check your php file directory……';

    mysql_connect("localhost","root","");
	mysql_select_db("public_screening_db");

	$insert = mysql_query("INSERT INTO images (path, location_id) VALUES ('$filepath2','$location_id')");
?>