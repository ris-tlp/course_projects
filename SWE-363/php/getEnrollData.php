<?php

require_once("../config.php");

$sql = "SELECT * FROM Assessment";
$results = mysqli_query($link, $sql);

$names = array();

while ($row = mysqli_fetch_array($results)) {
    array_push($names, array(
        $row["Assessment_Name"],
        $row["Assessment_date"],
        $row["Assessment_starting_time"],
        $row["total_marks"],
        $row["Assessment_material"],
        $row["Assessment_ID"]
    ));
}

// var_dump($names);
echo json_encode($names);