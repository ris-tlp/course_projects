<?php

if (isset($_POST['signin-submit'])) {
    require_once '../config.php';
    session_start();

    $username = $_POST["username"];
    $password = $_POST["password"];

    $queries = array("SELECT * FROM Student WHERE username='$username';", "SELECT * FROM Instructor WHERE username='$username';");
    $results = array();

    # Query both Student and Instructor tables to find username
    foreach ($queries as $sql) {
        $result = mysqli_query($link, $sql);
        array_push($results, $result);
    }

    # If the username doesn't exist, redirect the user back to the sign in page (temp)
    if (mysqli_num_rows($results[0]) == 0 && mysqli_num_rows($results[1]) == 0) {

        # TODO 
        echo printErrorMessage("Account does not exist. Please try again.");
        header('Refresh:2; URL=../signIn.html');
        exit();
    } else if (mysqli_num_rows($results[0]) > 0) {
        $tableName = "Student";
    } else if (mysqli_num_rows($results[1]) > 0) {
        $tableName = "Instructor";
    }

    # Once confirmed that username exists, validate login credentials
    $sql =
        <<<EOD
        SELECT * FROM $tableName WHERE username='$username' AND password='$password';
        EOD;

    $result = mysqli_query($link, $sql);

    # Set session vars if login credentials are valid
    if (mysqli_num_rows($result) > 0) {
        $attributes = mysqli_fetch_assoc($result);
        $id_prefix = $tableName == "Student" ? "S-" : "I-";

        # Sets id in the form of S-1 or I-1 according to account type
        $_SESSION["id"] = $id_prefix . $attributes["id"];
        $_SESSION["username"] = $attributes["username"];
        
        # Redirect to instructor/student schedule page
        header('Location: ../php/' . strtolower($tableName) . 'ScheduleLanding.php');
    } else {
        # TODO 
        echo printErrorMessage("Your username or password is incorrect. Please try again.");
        header('Refresh:2; URL=../signIn.html');
    }
}

# Print error messages
function printErrorMessage($message)
{
    $html =
        <<<EOD
            <div style="background-color: black;" width=50% height=50%>
                <h1 style="text-align:center; color:red;"> $message </h1>
            </div>
            EOD;

    return $html;
}
