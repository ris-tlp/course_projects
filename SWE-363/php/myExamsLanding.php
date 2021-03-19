<?php

session_start();

?>

<!DOCTYPE html>

<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>My Exams</title>
    <link rel="stylesheet" href="../assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
    <link rel="stylesheet" href="../assets/css/myExams.css">
    <link rel="stylesheet" href="../assets/css/practicePages.css">
</head>

<body>
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


        <section class="d-flex" id="main-area">
            <div id="exam-area">
                <h1>Upcoming Exams</h1>
                <button id="initEnroll" class="btn btn-primary">Enroll In Exam</button>
                <div id="inner-area">
                    <div class="table-responsive" id="exam-table" style="max-height: 350px;">
                        <table class="table" id="exam-table">
                            <!-- Populated by ajax -->
                        </table>
                    </div>
                </div>
            </div>
        </section>
    </section>


    <div id="myModal" class="modal">

        <!-- Modal content -->
        <div class="modal-content">
            <span class="close">&times;</span>
            <section class="d-flex">
                <section id="main-area">
                    <div id="question-area">
                        <select class="custom-select" style="width: 50%;">
                        </select>

                        <div class="table-responsive" id="info-table" style="max-height: 350px; width: 80%;">
                            <table class="table">
                                <!-- Populated by ajax -->
                            </table>
                        </div>

                        <button id="enroll" class="btn btn-primary">Enroll!</button>
                    </div>
                </section>
            </section>
        </div>

    </div>

    </div>
    <script src="../assets/js/jquery.min.js"></script>
    <script src="../assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="../assets/js/bs-init.js"></script>

    <!-- Handles enrolling of students -->
    <script src="../assets/js/enrollStudent.js"></script>

    <!-- Gets data to fill the exam table of the exams the students has -->
    <script type="text/javascript">
        $(document).ready(function() {
            $.ajax({
                type: "GET",
                url: "../php/myExams.php",
                dataType: "html",
                success: function(response) {
                    console.log(response);
                    $("#exam-table .table").html(response);
                }
            });
        });
    </script>
</body>

</html>