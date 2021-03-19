//Function to validate all fields of the Sign Up Form.
function signUpvalidation() {
    if (document.form.username.value == '') {
        $('#usernameErrorText').text('Please enter your username.')
        $('#usernameErrorText').show();
        event.preventDefault();
    }

    if (document.form.username.value != '') {
        $('#usernameErrorText').hide();
    }

    if (!validateEmail()) {
        $('#emailErrorText').text('Please enter a valid email.')
        $('#emailErrorText').show();
        event.preventDefault();
    } else {
        $('#emailErrorText').hide()
    }

    if (document.form.password.value == '') {
        $('#passwordErrorText').text('Please enter a password.')
        $('#passwordErrorText').show();
        event.preventDefault();
    }

    if (document.form.password.value != '') {
        $('#passwordErrorText').hide();
    }

    if (document.form.reenter.value == '') {
        $('#repasswordErrorText').text('Please re-enter your password.')
        $('#repasswordErrorText').show();
        event.preventDefault();
    }

    if (document.form.reenter.value != '') {
        $('#repasswordErrorText').hide();
    }

    if (
        document.form.reenter.value != '' &&
        document.form.password.value != '' &&
        !checkPasswordMatch()
    ) {
        $('#repasswordErrorText').text("Password doesn't match.");
        $('#repasswordErrorText').show();
        event.preventDefault();
    }

    if (
        document.form.reenter.value != '' &&
        document.form.password.value != '' &&
        checkPasswordMatch()
    ) {
        $('#repasswordErrorText').hide();
    }

    if (!validateCheckbox()) {
        $('#checkboxErrorText').text('Select only 1')
        $('#checkboxErrorText').show();
        event.preventDefault();
    } else {
        $('#checkboxErrorText').hide();
    }
}

// Function to validate all fields of the Sign In Form.
function signInValidation() {
    if (document.form.username.value == '') {
        $('#usernameErrorText').text('Please enter your username.')
        $('#usernameErrorText').show()
        event.preventDefault();
    } else {
        $('#usernameErrorText').hide()
    }

    if (document.form.password.value == '') {
        $('#passwordErrorText').text('Please enter your password.')
        $('#passwordErrorText').show()
        event.preventDefault();
    } else {
        $('#passwordErrorText').hide()
    }

    // if (document.form.username.value != '' && document.form.password.value != '') {
    //     window.location.href = 'php/signIn.php';
    // }
}

// HELPER METHODS

// Function to validate the structure of the email entered
function validateEmail() {
    var emailID = document.form.email.value
    at = emailID.indexOf('@')
    dot = emailID.lastIndexOf('.')

    if (at < 1 || dot - at < 2) {
        return false
    }
    return true
}

// Function to validate whether the password enter in the re-enter field is the same as the initial password entered.
function checkPasswordMatch() {
    var password = document.form.password.value
    var reenter = document.form.reenter.value

    if (password != reenter) return false
    else return true
}

// Function to validate whether exactly 1 checkbox is selected.
function validateCheckbox() {
    var studentCheckbox = document.getElementById('instructor')
    var instructorCheckbox = document.getElementById('student')

    if (
        (studentCheckbox.checked == true && instructorCheckbox.checked != true) ||
        (studentCheckbox.checked != true && instructorCheckbox.checked == true)
    )
        return true
    else return false
}