<?php

session_start();
require_once("../config.php");

// var_dump($_POST["questionsData"]);
// var_dump($_POST["formData"]);
// var_dump($_POST["questionsData"]);


# Split time data into respective fields
$num_of_questions = 0;

# Calculate total marks and total number of questions
foreach ($_POST["questionsData"] as $question) {
    $num_of_questions++;
}

# Preparing query data
$assessmentName = $_POST['formData']['assessmentName'];

$instructorId = explode("-", $_SESSION['id'])[1];

$sql = "INSERT INTO practice_test (
    test_name,
    num_of_questions,
    instructor_ID
) VALUES (
    '$assessmentName',
    $num_of_questions,
    $instructorId
)";

# Add assessment to table
mysqli_query($link, $sql);
echo(mysqli_error($link));

# Get ID of the assessment inserted to add as foreign key in questions
$last_id = mysqli_insert_id($link);

# Formatting query according to question type
foreach ($_POST["questionsData"] as $question) {
    $type = $question["type"];
    $description = $question["question"];
    $points = $question["points"];

    # Setting column data according to question type
    if ($type == "MCQ") {
        $option1 = $question["option1"];
        $option2 = $question["option2"];
        $option3 = $question["option3"];
        $option4 = $question["option4"];
        $answer = $question["answer"];

        echo gettype($option1);
        echo gettype($description);
        $sql = "INSERT INTO Question (
            type,
            description,
            option1,
            option2,
            option3,
            option4,
            answer,
            points,
            test_ID
        ) VALUES (
            '$type',
            '$description',
            '$option1',
            '$option2',
            '$option3',
            '$option4',
            '$answer',
            $points,
            $last_id
        )";
    }

    else if ($type == "Short") {
        $sql = "INSERT INTO Question (
            type,
            description,
            points,
            test_ID
        ) VALUES (
            '$type',
            '$description',
            $points,
            $last_id
        )";
    }

    else {
        $tf = $question["answer"];
        $tf = $tf == "True" ? 1 : 0;

        echo($tf);

        $sql = "INSERT INTO Question (
            type,
            description,
            tf,
            points,
            test_ID
        ) VALUES (
            '$type',
            '$description',
            '$tf',
            $points,
            $last_id
        )";
    }

    mysqli_query($link, $sql);
    echo(mysqli_error($link));
}
