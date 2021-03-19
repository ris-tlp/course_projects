<?php

require_once '../config.php';

session_start();
$student_Id = explode("-", $_SESSION['id'])[1];
echo ($student_Id);

echo <<<EOD
    <thead>
        <tr>
            <th> Major Name </th>
            <th> Assessment Material </th>
            <th> Scheduled Time </th>
        </tr>
    </thead>
EOD;

$sql = "SELECT * FROM Assessment, Student, user_exam_association
WHERE Assessment.Assessment_ID = user_exam_association.assessment_ID 
AND Student.id = user_exam_association.student_ID
AND $student_Id = user_exam_association.student_ID";

$result = mysqli_query($link, $sql);
echo (mysqli_error($link));
echo (mysqli_num_rows($result));

while ($data = mysqli_fetch_row($result)) {
    // Check to see if the student has already taken the exam
    $sql = "SELECT * FROM user_exam_score WHERE student_ID=$student_Id AND assessment_ID=$data[0]";
    $res = mysqli_query($link, $sql);

    // Only display if the student hasn't taken the exam
    if (mysqli_num_rows($res) == 0) {
        echo <<<EOD
        <tr>
            <td> <a href="../php/examStart.php?id=$data[0]"> $data[1] </a> </td>
            <td> $data[5] </td>
            <td> $data[2] | $data[3] </td>
        </tr>
    EOD;
    }
}
