<?php
    $title = $_REQUEST['title'];
    $base=$_REQUEST['image'];
    $binary=base64_decode($base);
    header('Content-Type: bitmap; charset=utf-8');
    $file = fopen($title.'.jpg', 'wb');
    fwrite($file, $binary);
    fclose($file);
    echo 'Image upload complete!!, Please check your php file directory……';
?>