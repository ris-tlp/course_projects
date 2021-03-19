<?php

require_once "../config.php";

session_start();

// Get Student and Assessment ID
$student_Id = explode("-", $_SESSION['id'])[1];
$assessment_Id = $_POST["assessmentID"];

// Associate Student and Assessment
$sql = "INSERT INTO user_exam_association (
    student_ID,
    assessment_ID
) VALUES (
    $student_Id,
    $assessment_Id
)";

mysqli_query($link, $sql);
echo (mysqli_error($link));
