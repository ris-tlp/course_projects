<?php

require_once "../config.php";
session_start();

$q = $link->prepare("SELECT * FROM `question`;");

$q->execute();
$questions = $q->get_result();

?>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Add Practice Questions</title>
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
                    <li class="nav-item" role="presentation" id="nav-dropdown"><a class="nav-link" href="../php/createTestLanding.php">Create Assessment<br></a></li>
                    <li class="nav-item" role="presentation" id="nav-dropdown"><a class="nav-link" href="../php/addPracticeQuestions.php">Create Practice Test<br></a></li>
                    <li class="nav-item" role="presentation" id="nav-dropdown"><a class="nav-link" href="../php/instructorScheduleLanding.php">View Schedule</a></li>
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
                <div id="nav-items" style="width: inherit;height: 100vh;"><a href="createTestLanding.php">Create
                        Assessment<br></a><a href="../php/addPracticeQuestions.php">Create Practice Test<br></a><a href="../php/instructorScheduleLanding.php">View
                        Schedule<br></a></div>
            </section>
        </section>
        <section id="main-area">
            <div class="float-left" id="top-leftDiv" style="max-width: 50%;width: 50%;height: auto;"><label class="d-inline-block" style="margin-bottom: 7%;width: 100%;max-width: 100%;">Name of practice
                    test<input class="d-flex float-right" type="text" style="max-width: 60%;margin-right: 20px;width: 60%;min-width: 10%;height: auto;" id="practiceName"></label></div>
            <div class="d-block" id="bigAreaDiv" style="height: auto;max-width: 100%;"><label style="width: 100%;max-width: 100%;margin-top: 20px;">List of questions:</label>
                <div class="table-responsive" style="width: 80%;min-width: 40%;max-width: 80%;margin-left: 5px;">
                    <table class="table" id="questionTable">
                        <thead>
                            <tr>
                                <th>Number</th>
                                <th>Question</th>
                                <th>Type</th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="m-auto" id="bottomDiv" style="margin-right: 30%;color: rgb(0,0,0);width: 70%;min-width: 40%;max-width: 100%;height: auto;">
                <button class="btn btn-primary" id="button-link" type="button" style="min-width: auto;max-width: 30%;width: 30%;margin-right: 8%;">Add questions</button>
                <button class="btn btn-primary float-right" type="button" style="width: 30%;min-width: auto;max-width: 30%;margin-left: 10%;" id="createPracticeTest">Create practice test</button>
            </div>


        </section>


        <div id="myModal" class="modal">

            <!-- Modal content -->
            <div class="modal-content">
                <span class="close">&times;</span>
                <section class="d-flex">
                    <section id="main-area">
                        <div id="question-area">
                            <div class="dropdown" id="dropdown-menu">
                                <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-expanded="false" id="dropdown" type="button">Selection Question Type</button>
                                <div class="dropdown-menu" role="menu">
                                    <a class="dropdown-item" role="presentation" id="mcqOption" href="#" value="mcq">MCQ</a>
                                    <a class="dropdown-item" role="presentation" id="trueFalseOption" href="#" value="true-false">True / False</a>
                                </div>
                            </div>
                            <h1 id="tempText" style="margin-top: -20px;text-align: center;">Please select a question type.</h1>
                            <div id="borderMCQ">
                                <h1 style="font-size: 25px;margin-right: 2px;font-family: 'Raleway';"><strong>MCQ</strong></h1>

                                <label class="float-left" style="width: 100%;min-width: 50%;max-width: 100%;font-family: 'Raleway';">Points
                                    <input type="number" style="margin-left: 5px;width: 30%;max-width: 40px;" id="mcqPoints" required min="1">
                                </label>

                                <label style="font-family: 'Raleway';">Question:&nbsp;
                                    <input type="text" style="width: 70%;max-width: 70%;min-width: auto;" id="mcqQuestion" required>
                                </label>

                                <ol style="width: 100%;min-width: 20%;max-width: 768px;/*width: 500px;*//*list-style-type: none;*/">
                                    <li><input type="text" style="width: 50%;min-width: 20%;max-width: 250px;" id="ans1" required></li>
                                    <li><input type="text" style="width: 50%;min-width: 20%;max-width: 250px;" id="ans2" required></li>
                                    <li><input type="text" style="width: 50%;min-width: 20%;max-width: 250px;" id="ans3" required></li>
                                    <li><input type="text" style="width: 50%;min-width: 20%;max-width: 250px;" id="ans4" required></li>
                                </ol>

                                <label style="font-family: 'Raleway';">Answer:&nbsp;
                                    <select style="width: 50%;flex-basis: 100%;" id="mcqCorrectAnswer">
                                        <option value="1">1</option>
                                        <option value="2">2</option>
                                        <option value="3">3</option>
                                        <option value="4">4</option>
                                    </select>
                                </label>
                            </div>

                            <div id="borderTrueFalse">
                                <h1 style="font-size: 25px;margin-right: 2px;text-align: center;"><strong>True/False</strong></h1>
                                <label class="float-left" style="width: 100%;min-width: 50%;max-width: 100%;">Points
                                    <input type="number" style="width: 30%;max-width: 40px;margin: 5px;" id="tfPoints" min="1">
                                </label>
                                <label style="margin: 5px;">Question:&nbsp;
                                    <input type="text" style="width: 70%;max-width: 70%;min-width: auto;" id="tfQuestion">
                                </label>
                                <label style="width: 100%;margin-top: 5px;margin: 5px;">Answer:&nbsp;
                                    <select style="width: 20%;flex-basis: 100%;" id="tfAnswer">
                                        <option value="true">True</option>
                                        <option value="false">False</option>
                                    </select>
                                </label>
                            </div>
                            <button class="btn btn-primary" id="addQuestionBtn" type="button">Add</button>
                        </div>
                    </section>
                </section>
            </div>

        </div>
    </section>
    <script src="../assets/js/jquery.min.js"></script>
    <script src="../assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="../assets/js/bs-init.js"></script>
    <script src="../assets/js/dropdown.js"></script>
    <script src="../assets/js/practiceQuestionHandler.js"></script>

    <script>
        $("#createTest").click(function() {
            window.location.href = "instructorScheduleLanding.php";
        })
    </script>
</body>

</html>