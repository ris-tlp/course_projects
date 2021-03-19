<?php

include "../config.php";
session_start();
if (isset($_GET["id"]) && is_numeric($_GET["id"])) {
    $test_id = $_GET["id"];

    if (isset($_GET["num"]) && is_numeric($_GET["num"])) {
        $limit = $_GET["num"];
    } else {
        // Default Number Of Questions IF Not Set
        $limit = 5;
    }
    $q = $link->prepare("SELECT * FROM `question` WHERE test_ID = $test_id LIMIT $limit; ");
    $q->execute();
    $questions = $q->get_result();
}
?>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Practice</title>
    <link rel="stylesheet" href="../assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
    <link rel="stylesheet" href="../assets/css/practicePages.css">
    <style>
        .button {
            border-radius: 25px;
            background-color: #D8DBE2;
            color: black;
            width: 60%;
            height: 30%;
            margin: 5% 10%;
            word-wrap: break-word;
        }

        .textarea {
            width: 60%;
            height: 50%;
            margin: 5% 10%;
            text-align: center;
        }
    </style>
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
    
    <section id="main-area"><a href="../php/studentPractice.php" style="font-size: 20px;margin-left: 5px;margin-top: 5px;">Back</a>
        <div class="table-responsive border-dark">
            <table class="table">
                <thead class="border-dark">
                    <tr>
                        <th>Question</th>
                        <th class="text-center">Solution key</th>
                    </tr>
                </thead>
                <tbody>
                    <?php
                    $num = 1;
                    foreach ($questions as $question) {
                        $q = "Q" . $num;
                    ?>
                        <tr>
                            <td><a class="d-block" href="#"><?php echo $q . ": " . $question["description"] . "?"; ?></a>
                                <?php
                                // Check Question Type
                                if ($question["type"] == "Short answer") {
                                    echo '<input class="d-block"
                                    type="text"
                                    style="margin-bottom: 0px;min-width: 10%;max-width: 50%;height: 40%;width: 50%;min-height: 35%;margin-top: 5px;">';
                                } elseif ($question["type"] == "True/False") {
                                    echo '<form class="d-block"
                                    style="width: 50%;min-width: 10%;max-width: 70%;height: 40%;min-height: 35%;margin-top: 5px;">
                                    <input type="radio" id="true" name="truefalse" value="True">
                                    <label for="male">True</label><br>
                                    <input type="radio" id="false" name="truefalse" value="False">
                                    <label for="female">False</label></form>';
                                } ?>
                            </td>
                            <td class="align-content-center" style="margin-bottom: 0px;">
                                <section id="<?php echo $q; ?>" style="display:none">

                                    <?php
                                    if ($question["type"] != "Short answer") { ?>

                                        <textarea class="textarea" style="resize: none;" readonly><?php if ($question["answer"] == 0) {
                                                                        echo "False";
                                                                    } elseif ($question["answer"] == 1) {
                                                                        echo "True";
                                                                    } else {
                                                                        echo $question["answer"];
                                                                    }; ?></textarea>

                                        <button class="button" type="button" onclick="document.getElementById('<?php echo $q; ?>').style.display='none'">Close</button>
                                </section>

                                <button class="button" type="button" onclick="document.getElementById('<?php echo $q; ?>').style.display='block'">Solution key </button>

                            <?php } ?>
                            </td>
                        </tr>
                    <?php $num++;
                    } ?>
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