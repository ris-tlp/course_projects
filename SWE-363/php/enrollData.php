<?php

require_once("../config.php");

$sql = "SELECT * FROM Assessment";
$results = mysqli_query($link, $sql);

while ($row = mysqli_fetch_array($results)) {
    echo($row["Assessment_name"]);
}