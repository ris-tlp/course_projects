var assessmentsGlobal;
// Get the modal
var modal = document.getElementById("myModal");

// Get the button that opens the modal
var btn = document.getElementById("initEnroll");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// To set global var thanks to async ajax
function setGlobal(next_load) {
    assessmentsGlobal = next_load;
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

// Set the options of the dropdown box with the names of each assessment so the student can choose them
$(btn).click(function() {
    modal.style.display = "block";
    $.ajax({
        async: 'false',
        type: "GET",
        url: "../php/getEnrollData.php",
        success: function(response) {
            assessments = JSON.parse(response);
            var select = document.getElementsByClassName("custom-select");

            for (var i in assessments) {
                $(select).append('<option value=' + assessments[i][0] + '>' + assessments[i][0] + '</option>');
            }
            $(select).val(assessments[1]);
            setGlobal(assessments);
        }
    })
})

// Global var to get position of exam selected in the array
var currentExamLocation;

// Get exam from list
$(".custom-select").change(function() {
    var val = $(".custom-select option:selected").text();
    console.log(val);
    for (currentExamLocation in assessmentsGlobal) {
        if (assessmentsGlobal[currentExamLocation][0] === val) {
            break;
        }
    }

    // Add current information to table
    $("#info-table .table").html(
        "<tr> <td> <b> Name </b> </td> <td> " + assessmentsGlobal[currentExamLocation][0] + " </td> </tr>" + "<tr> <td> <b> Time </b> </td> <td> " + assessmentsGlobal[currentExamLocation][1] + " | " + assessmentsGlobal[currentExamLocation][2] + "</td> </td>" + "<tr> <td> <b> Syllabus </b> </td> <td> " + assessmentsGlobal[currentExamLocation][4] + "</td> </td>" + "<tr> <td> <b> Total Marks </b> </td> <td> " + assessmentsGlobal[currentExamLocation][3]
    );
})

// Send assessment ID to enroll student
$("#enroll").click(function() {
    $.ajax({
        url: "../php/enrollStudent.php",
        data: {
            assessmentID: assessmentsGlobal[currentExamLocation][5]
        },
        type: "POST",
        success: function(data) {
            console.log(data);
        }
    })
    modal.style.display = "none";
    location.reload(true);
})