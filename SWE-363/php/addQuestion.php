<?php

    if($_SERVER["REQUEST_METHOD"] == "POST"){

        require_once "../config.php";

        $points = $_POST["points"];
        $type   = $_POST["type"];
        $desc   = $_POST["description"];
        
        $asID   = $_POST["assessment_ID"];
        $tsID   = $_POST["test_ID"];

        // In Case Question Type is "Short answer"
        if($type == "Short answer"){
            $answer = "-";
        } else {
            $answer = $_POST["answer"];
        }

        if($type == "MCQ") {
            $cho1 = $_POST["choice1"];
            $cho2 = $_POST["choice2"];
            $cho3 = $_POST["choice3"];
            $cho4 = $_POST["choice4"];
            $q = "INSERT INTO `question` (type,	description, option1, option2, option3, option4, answer, points, assessment_ID) 
                              VALUES ('$type', '$desc', '$cho1', '$cho2', '$cho3', '$cho4', '$answer', '$points','$asID');";
        } else {
            $q = "INSERT INTO `question` (type,	description, answer, points, assessment_ID) 
            VALUES ('$type', '$desc', '$answer', '$points', '$asID');";
        }


        if($link->query($q)){
            echo"Question Added Successfully.";
            header("Location: ./addPracticeQuestions.php");

        } else {
            echo"Faild To Add The Question.";
        }
        
    } else {
        echo "You Are Not Allowed Here.";
    }

    