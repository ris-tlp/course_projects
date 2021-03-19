<?php 

require_once "../config.php";

session_start();

$student_id = explode("-", $_SESSION['id'])[1];
$assessment_id = $_REQUEST["assessmentID"];
$questionsData = $_REQUEST["questionsData"];

$totalPoints = 0;

// Calculating total points scored by the student
foreach ($questionsData as $question){   
    var_dump ($question);
    if ($question["correctAnswer"] === $question["enteredAnswer"])
        $totalPoints += $question["questionPoints"];
}

$sql = "INSERT INTO user_exam_score (
    student_ID,
    assessment_ID,
    score
) VALUES (
    $student_id,
    $assessment_id,
    $totalPoints
)";

$result = mysqli_query($link, $sql);

echo (mysqli_error($link));
