<?php

require_once '../config.php';

session_start();
$student_Id = explode("-", $_SESSION['id'])[1];
echo($student_Id);

echo <<<EOD
    <thead>
        <tr>
            <th> Major Name </th>
            <th> Assessment Material </th>
            <th> Instructor Name </th>
            <th> Scheduled Time </th>
        </tr>
    </thead>
EOD;

$sql = "SELECT * FROM Assessment, Student, user_exam_association, Instructor
WHERE Assessment.Assessment_ID = user_exam_association.assessment_ID 
AND Student.id = user_exam_association.student_ID
AND $student_Id = user_exam_association.student_ID
AND Assessment.Instructor_ID = Instructor.id";

$result = mysqli_query($link, $sql);
echo(mysqli_error($link));
echo(mysqli_num_rows($result));

while ($data = mysqli_fetch_row($result)) {
    
    echo <<<EOD
        <tr>
            <td> $data[1] </td>
            <td> $data[5] </td>
            <td> $data[23] </td>
            <td> $data[2] | $data[3] </td>
        </tr>
    EOD;
}




echo "</tbody>";
