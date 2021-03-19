<?php
$DBHOST = 'localhost';
$DBUSER = 'root';
$DBNAME = 'onlineexamination';
$DBPASSWORD = '';

$link = mysqli_connect($DBHOST, $DBUSER, $DBPASSWORD);

if (!$link) {
  die("Could not connect: " . mysqli_error($link));
}

$db_selected = mysqli_select_db($link, $DBNAME);

# Check if database exists. If not, create it and necessary tables.
if (!$db_selected) {

  $sql = "CREATE DATABASE $DBNAME";
  mysqli_query($link, $sql);
  mysqli_select_db($link, $DBNAME); # Select the database to create tables

  $instructor_table = "CREATE TABLE `Instructor` (
        `id` int NOT NULL AUTO_INCREMENT,
        `username` varchar(15) NOT NULL,
        `firstName` varchar(10),
        `lastName` varchar(10),
        `password` varchar(20) NOT NULL,
        `email` varchar(20) NOT NULL,
        `profile_image` varchar(255),
        `about` varchar(255),
        PRIMARY KEY (`id`)
      )";

  $student_table = "CREATE TABLE `Student` (
        `id` int NOT NULL AUTO_INCREMENT,
        `username` varchar(15) NOT NULL,
        `firstName` varchar(10),
        `lastName` varchar(10),
        `password` varchar(20) NOT NULL,
        `email` varchar(20) NOT NULL,
        `profile_image` varchar(255),
        `major` varchar(15),
        `about` varchar(255),
        PRIMARY KEY (`id`)
      )";


  $assessment_table = "CREATE TABLE `Assessment`(
        `Assessment_ID` INT NOT NULL AUTO_INCREMENT,
        `Assessment_Name` VARCHAR(255),
        `Assessment_date` DATE,
        `Assessment_starting_time` TIME,
        `Assessment_duration` INT,
        `Assessment_material` VARCHAR(255),
        `Assessment_rules` VARCHAR(255),
        `total_marks` INT,
        `num_of_questions` INT,
        `Instructor_ID` INT,
        PRIMARY KEY(`Assessment_ID`),
        FOREIGN KEY(`Instructor_ID`) REFERENCES `Instructor`(`id`)
    )";

  $question_table = "CREATE TABLE `Question` (
        `number` int NOT NULL AUTO_INCREMENT,
        `type` varchar(12),
        `description` varchar(100),
        `option1` varchar(30),
        `option2` varchar(30),
        `option3` varchar(30),
        `option4` varchar(30),
        `answer` int,
        `tf` BOOLEAN,
        `points` int,
        `assessment_ID` int,
        `test_ID` int,
        PRIMARY KEY (`number`),
        FOREIGN KEY (`assessment_ID`) REFERENCES Assessment(`Assessment_ID`)
        )";

  $practice_test_table = "CREATE TABLE `practice_test` (
        `test_ID` int NOT NULL AUTO_INCREMENT,
        `test_name` varchar(255),
        `num_of_questions` int,
        `instructor_ID` int,
        PRIMARY KEY (`test_ID`),
        FOREIGN KEY (`instructor_ID`) REFERENCES `Instructor`(`id`)
        )";

  $user_exam_association = "CREATE TABLE `user_exam_association` (
    `id` int NOT NULL AUTO_INCREMENT,
    `student_ID` int NOT NULL,
    `assessment_ID` int NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`student_ID`) REFERENCES `Student`(`id`),
    FOREIGN KEY (`assessment_ID`) REFERENCES `Assessment`(`Assessment_ID`)
    );";

  $user_exam_score = "CREATE TABLE `user_exam_score` (
    `id` int NOT NULL AUTO_INCREMENT,
    `student_ID` int NOT NULL,
    `assessment_ID` int NOT NULL,
    `score` int NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`student_ID`) REFERENCES `Student`(`id`),
    FOREIGN KEY (`assessment_ID`) REFERENCES `Assessment`(`Assessment_ID`)
  );";

  # Array to hold all table queries
  $tables = array(
    $instructor_table,
    $student_table,
    $assessment_table,
    $question_table,
    $practice_test_table,
    $user_exam_association,
    $user_exam_score
  );

  # Create all tables
  foreach ($tables as $sql) {
    $query = mysqli_query($link, $sql);

    echo (mysqli_error($link));
  }
}
