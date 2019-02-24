<?php

$conn = new mysqli('localhost', 'root', '****', 'microlearn');
if($conn->connect_error)
{
    die("Error: Can't connect" . $conn->connect_error);
}
?>
