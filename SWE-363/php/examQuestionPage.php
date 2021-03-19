<?php

session_start();
# Talk about bad practice galore amirite
require_once "../config.php";
$assessment_id = $_GET["id"];

$sql = "SELECT Assessment_duration FROM Assessment WHERE assessment_ID=$assessment_id";
$result = mysqli_query($link, $sql);

while ($data = mysqli_fetch_row($result)) {
    $duration = $data[0];
    $duration *= 60; # Convert to seconds for timer
}

echo (mysqli_error($link));

$sql = "SELECT * FROM Question WHERE assessment_ID=$assessment_id";
$result = mysqli_query($link, $sql);
echo (mysqli_error($link));

$mcq = array();
$tf = array();

while ($data = mysqli_fetch_row($result)) {
    if ($data[1] === "MCQ") {
        $temp = array();
        $temp["question"] = $data[2];
        $temp["option1"] = $data[3];
        $temp["option2"] = $data[4];
        $temp["option3"] = $data[5];
        $temp["option4"] = $data[6];
        $temp["answer"] = $data[7];
        $temp["id"] = $data[0];
        $temp["points"] = $data[9];

        array_push($mcq, $temp);
    } else if ($data[1] === "True/False") {
        $temp = array();
        $temp["id"] = $data[0];
        $temp["question"] = $data[2];
        $temp["answer"] = $data[8];
        $temp["points"] = $data[9];

        array_push($tf, $temp);
    }
}

echo (mysqli_error($link));
?>


<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Exam</title>
    <link rel="stylesheet" href="../assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
    <link rel="stylesheet" href="../assets/css/examQuestion.css">

    <style>
        ul>li {
            margin: 15px;
        }
    </style>
</head>

<body style="background-color:lightgray">
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
            <!-- <div id="exam-area"> -->
            <div id="progress-bar">
                <div></div>
            </div>
            <?php

            $counter = 0;

            for ($i = 0; $i < count($mcq); $i++) {
                echo '
                   <div id="question-box" class="questionMCQ' . ($mcq[$i]["id"]) . '" data-correct-answer="' . $mcq[$i]["answer"] . '" data-question-points="' . $mcq[$i]["points"] . '">
                        <p id="question"><strong>Question '  . (++$counter) . '</strong>: ' .  $mcq[$i]["question"] . '</p>
                        <form class="myForm">
                            <ul style="list-style: none;" id="mcqOptions">
                                <li> <input type="radio" id="option1" name="mcqAnswer" value="1">' . $mcq[$i]["option1"] . '   </li>
                                <li> <input type="radio" id="option2" name="mcqAnswer" value="2">' . $mcq[$i]["option2"] . '  </li> 
                                <li> <input type="radio" id="option3" name="mcqAnswer" value="3">' . $mcq[$i]["option3"] . '  </li> 
                                <li> <input type="radio" id="option4" name="mcqAnswer" value="4">' . $mcq[$i]["option4"] . '  </li> 
                            </ul>
                        </form>
                    </div>
                   ';
            }

            for ($i = 0; $i < count($tf); $i++) {
                echo '
                   <div id="question-box" class="questionTF' . ($tf[$i]["id"]) . '" data-correct-answer="' . $tf[$i]["answer"] . '" data-question-points="' . $tf[$i]["points"] . '">
                        <p id="question"><strong>Question ' . (++$counter) . '</strong>: ' .  $tf[$i]["question"] . '</p>
                        <form class="myForm">
                            <ul style="list-style: none;" id="tfOptions">
                                <li> <input type="radio" id="true" name="tfAnswer" value="true"> True   </li>
                                <li> <input type="radio" id="false" name="tfAnswer" value="false"> False  </li> 
                            </ul>
                        </form>
                    </div>
                   ';
            }

            ?>

            </div><button class="btn btn-primary" id="next-btn" type="button" onclick="exit()"><strong>Submit</strong></button>
            <!-- </div> -->
        </section>
    </section>
    <script src="../assets/js/jquery.min.js"></script>
    <script src="../assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="../assets/js/bs-init.js"></script>
    <!-- <script src="../assets/js/countdown-bar.js"></script> -->

    <script>
        function exit() {
            // Array containing all questions
            var questions = {};

            var mcqQuestions = jQuery("[class*=questionMCQ]");
            var tfQuestions = jQuery("[class*=questionTF]");

            // Create question object pertaining to question id for each question
            for (var i = 0; i < mcqQuestions.length; i++) {
                let enteredAnswer = $('input[name=mcqAnswer]:checked', '.' + mcqQuestions[i].className + ' .myForm').val();
                let correctAnswer = $("." + mcqQuestions[i].className).attr('data-correct-answer');
                let questionPoints = $("." + mcqQuestions[i].className).attr('data-question-points');
                let questionID = mcqQuestions[i].className.substring("mcqQuestion".length);

                questions[questionID] = {
                    id: questionID,
                    correctAnswer: correctAnswer,
                    questionPoints: questionPoints,
                    enteredAnswer: enteredAnswer
                }
            }

            // Create question object pertaining to question id for each question
            for (var i = 0; i < tfQuestions.length; i++) {
                let enteredAnswer = $('input[name=tfAnswer]:checked', '.' + tfQuestions[i].className + ' .myForm').val();
                let correctAnswer = $("." + tfQuestions[i].className).attr('data-correct-answer');
                let questionPoints = $("." + tfQuestions[i].className).attr('data-question-points');
                let questionID = tfQuestions[i].className.substring("tfQuestion".length);

                enteredAnswer = enteredAnswer === "true" ? "1" : "0";

                questions[questionID] = {
                    id: questionID,
                    correctAnswer: correctAnswer,
                    questionPoints: questionPoints,
                    enteredAnswer: enteredAnswer
                }

                console.log(questions[questionID]);
            }

            $.ajax({
                url: "../php/gradeTest.php",
                data: {
                    questionsData: questions,
                    assessmentID: <?php echo $assessment_id ?>
                },
                type: "POST",
                success: function(data) {
                    console.log(data);
                }
            })

            window.location.href = "../php/studentScheduleLanding.php";

        }
    </script>

    <script>
        function progress(timeleft, timetotal, $element) {
            var progressBarWidth = (timeleft * $element.width()) / timetotal
            $element
                .find('div')
                .animate({
                    width: progressBarWidth
                }, 500)
                .html(
                    'Time remaining: ' + Math.floor(timeleft / 60) + ':' + (timeleft % 60)
                )
            if (timeleft > 0) {
                setTimeout(function() {
                    progress(timeleft - 1, timetotal, $element)
                }, 1000)
            }

            if (timeleft == 0) {
                exit();
            }
        }

        progress(<?php echo $duration ?>, <?php echo $duration ?>, $('#progress-bar'))
    </script>
</body>

</html>