<?php
session_start();
require_once("../config.php");

$student_id = explode("-", $_SESSION['id'])[1];

$sql = "SELECT * FROM user_exam_score WHERE student_ID=$student_id";
$result = mysqli_query($link, $sql);

echo (mysqli_error($link));
$assessments = array();

while ($data = mysqli_fetch_row($result)) {
    $sql = "SELECT Assessment_Name, total_marks FROM Assessment WHERE Assessment_ID=$data[2]";
    $res = mysqli_query($link, $sql);
    $assessment_data = mysqli_fetch_row($res);

    $temp = array();
    $temp["assessment_name"] = $assessment_data[0];
    $temp["total_marks"] = $assessment_data[1];
    $temp["id"] = $data[2];
    $temp["score"] = $data[3];

    # Get Average
    $sql = "SELECT AVG(score) FROM user_exam_score WHERE assessment_ID=$data[2]";
    $res = mysqli_query($link, $sql);
    $avg = mysqli_fetch_row($res);

    $temp["avg"] = $avg[0];

    # add all assessment data to array
    array_push($assessments, $temp);
}


?>


<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>My Scores</title>
    <link rel="stylesheet" href="../assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
    <link rel="stylesheet" href="../assets/css/practicePages.css">
</head>

<body style="background-color: #d8dbe2;">
    <nav class="navbar navbar-light navbar-expand-md d-flex flex-grow-0 navigation-clean">
        <div class="container"><button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
            <picture><img src="../assets/img/logo.jpg" style="width: 75px;height: 50px;border-radius: 50px;" alt="Logo">
            </picture><a class="navbar-brand" href="#" style="padding: 5px 25px;color: rgb(216,219,226);font-family: Raleway, sans-serif;text-transform: uppercase;font-size: 27px;">Online
                Examination System</a>
            <div class="collapse navbar-collapse" id="navcol-1">
                <ul class="nav navbar-nav ml-auto" id="navbar-toggle">
                    <li class="nav-item" role="presentation" id="nav-dropdown"><a class="nav-link" href="../php/studentScores.php">My Scores<br></a></li>
                    <li class="nav-item" role="presentation" id="nav-dropdown"><a class="nav-link" href="../php/myExamsLanding.php">My Exams</a></li>
                    <li class="nav-item" role="presentation" id="nav-dropdown"><a class="nav-link" href="../php/studentPractice.php">Practice</a></li>
                    <li class="nav-item" role="presentation" id="nav-dropdown"><a class="nav-link" href="studentSchedule.html">View Schedule</a></li>
                    <li class="nav-item" role="presentation" id="nav-dropdown"><a class="nav-link" href="../signIn.html" style="color: red;">Log Out</a></li>
                </ul>
            </div><button class="btn btn-primary text-center" data-toggle="tooltip" data-bs-tooltip="" id="logout-btn" type="submit" title="Log Out" onclick="window.location.href='../signIn.html'" style="background-color: rgb(216,219,226);color: rgb(0,0,0);width: 127px;">Log Out</button>
        </div>
    </nav>
    <section class="d-flex">
        <section id="nav-area" style="/*margin-right: 10px;*/width: 240px;max-width: 240px;/*min-width: 150px;*/height: 100vh;min-height: 50%;">
            <section id="profile-section" style="width: 240px;height: 200px;background-image: linear-gradient(to top, rgb(169,188,208), rgb(135, 178, 195));text-align: center;padding-top: 15px;min-height: 200px;">
                <p style="padding: 20px;font-family: Raleway, sans-serif;margin: auto;"><strong>Username: <?php echo $_SESSION["username"] ?></strong></p>
                <p style="padding: 20px;font-family: Raleway, sans-serif;margin: auto;"><strong>ID: <?php echo $_SESSION["id"] ?></strong></p>
            </section>
            <section style="height: 100vh;background-image: linear-gradient(to bottom, rgb(27, 27, 30), rgb(55, 63, 81));min-height: 100vh;">
                <div id="nav-items" style="width: inherit;height: 100vh;"><a href="../php/studentScores.php">My Scores<br></a><a href="../php/myExamsLanding.php">My Exams<br></a><a href="../php/studentPractice.php">Practice<br></a><a href="../php/studentScheduleLanding.php">View Schedule<br></a></div>
            </section>
        </section>
        <section id="main-area">
            <div class="table-responsive border-dark">
                <table class="table">
                    <thead class="border-dark">
                        <tr>
                            <th>Exam</th>
                            <th>Score</th>
                            <th>Average</th>
                        </tr>
                    </thead>
                    <tbody>

                        <?php
                        foreach ($assessments as $a) {
                            $str = '
                       <tr>
                            <td>' . $a["assessment_name"] . '</td>
                            <td>
                                ' . $a["score"] . ' / ' . $a["total_marks"] . ' 
                            </td>

                            <td> ' . $a["avg"] . ' </td>
                        </tr>';

                            echo $str;
                        }
                        ?>
                    </tbody>
                </table>
            </div>
        </section>
    </section>
    <script src="../assets/js/jquery.min.js"></script>
    <script src="../assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="../assets/js/bs-init.js"></script>
    <script src="../assets/js/dropdown.js"></script>
</body>

</html>