<?php

if (isset($_POST["signup-submit"])) {

    # To access $link
    require_once "../config.php";

    $username = $_POST["username"];
    $password = $_POST["password"];
    $email = $_POST["email"];
    $accountType = empty($_POST["instructor"]) ? "Student" : "Instructor";

    if (!usernameExists($username, $link)) {
        if ($accountType == "Student") {
            $sql = "INSERT INTO $accountType (username, firstName, lastName, password, email, profile_image, major, about)
            VALUES ('$username', NULL, NULL, '$password', '$email', NULL, NULL, NULL)";
        } else {
            $sql = "INSERT INTO $accountType (username, firstName, lastName, password, email, profile_image, about)
            VALUES ('$username', NULL, NULL, '$password', '$email', NULL, NULL)";
        }

        mysqli_query($link, $sql);
        header("Location: ../signIn.html");
    } else {
        # TODO 

        $html = <<<EOD
        <div style="background-color: black;" width=50% height=50%>
            <h1 style="text-align:center; color:red;"> Account already exists! Please try again.</h1>
        </div>
        EOD;

        echo $html;
        header('Refresh:2; URL=../signUp.html');
    }
}

# Check if an account with the same username is already present
function usernameExists($username, $link)
{
    $queries = array("SELECT * FROM Student WHERE username='$username';", "SELECT * FROM Instructor WHERE username='$username';");
    $results = array();

    foreach ($queries as $sql) {
        $result = mysqli_query($link, $sql);
        array_push($results, $result);
    }

    return (mysqli_num_rows($results[0]) > 0) || (mysqli_num_rows($results[1]) > 0);
}
