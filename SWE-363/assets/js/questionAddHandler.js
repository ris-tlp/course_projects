var questions = []
    // Get the modal
var modal = document.getElementById('myModal')

// Get the button that opens the modal
var btn = document.getElementById('button-link')
var addbtn = document.getElementById('addQuestionBtn')

// Get the <span> element that closes the modal
var span = document.getElementsByClassName('close')[0]

// When the user clicks the button, open the modal
btn.onclick = function() {
    modal.style.display = 'block'
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = 'none'
}

addbtn.onclick = function() {
    createArray()
    modal.style.display = 'none'
    addQuestionsToTable()
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = 'none'
    }
}

function addQuestionsToTable() {
    $('#questionTable > tbody').empty()
    for (var i = 0; i < questions.length; i++) {
        $('#questionTable tbody').append(
            '<tr><td> ' +
            (i + 1) +
            '</td> <td> ' +
            questions[i]['question'] +
            '</td> <td> ' +
            questions[i]['type'] +
            '</td> <td>' +
            questions[i]['points'] +
            '</td> </td>' +
            '<td><input type="button" value="Remove" class="btnDelete"></td></tr>'
        )
    }
}



function createArray() {
    var questionType = $('#dropdown').text()
    var question

    if (questionType.split(' ')[0] === 'MCQ') {
        // Create question object
        question = {
            type: 'MCQ',
            question: $('#mcqQuestion').val(),
            points: $('#mcqPoints').val(),
            option1: $('#ans1').val(),
            option2: $('#ans2').val(),
            option3: $('#ans3').val(),
            option4: $('#ans4').val(),
            answer: $('#mcqCorrectAnswer :selected').text()
        }

        // Add question to global question array
        questions.push(question)
    } else if (questionType.split(' ')[0] === 'Short') {
        // Create question object
        question = {
            type: 'Short',
            question: $('#shortQuestion').val(),
            points: $('#shortPoints').val()
        }

        // Add question to global question array
        questions.push(question)
    } else {
        // Create question object
        question = {
            type: 'True/False',
            question: $('#tfQuestion').val(),
            points: $('#tfPoints').val(),
            answer: $('#tfAnswer :selected').text()
        }

        // Add question to global question array
        questions.push(question)
    }

    console.log(questions)
}

$('#createTest').click(function() {
    form = {
        assessmentName: $('#assessmentName').val(),
        assessmentDate: $('#assessmentDate').val(),
        assessmentDuration: $('#assessmentDuration').val(),
        assessmentSyllabus: $('#assessmentSyllabus').val(),
        assessmentRules: $('#assessmentRules').val(),
        isRandomize: $('#formCheck-1').is(':checked') ? 'true' : 'false'
    }

    console.log(form)

    $.ajax({
        url: '../php/createTest.php',
        data: {
            questionsData: questions,
            formData: form
        },
        type: 'POST',
        success: function(data) {
            console.log(data)
        }
    })
})


$("#questionTable").on('click', '.btnDelete', function() {
    let index = $(this).closest('td').parent()[0].sectionRowIndex; // get the index of the row
    questions.splice(index, 1); // remove the question from the questions array
    $(this).closest('tr').remove(); // remove the row itself
    addQuestionsToTable() //reprint the table
});