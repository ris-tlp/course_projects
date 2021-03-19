$(document).ready(function () {
  $('.dropdown-item').click(function () {
    if (this.id == 'mcqOption') {
      $('#borderMCQ').show()
      $('#borderShortAnswer').hide()
      $('#borderTrueFalse').hide()

      $('#dropdown').text('MCQ Question')
    } else if (this.id == 'shortAnswerOption') {
      $('#borderMCQ').hide()
      $('#borderShortAnswer').show()
      $('#borderTrueFalse').hide()

      $('#dropdown').text('Short Answer Question')
    } else {
      $('#borderMCQ').hide()
      $('#borderShortAnswer').hide()
      $('#borderTrueFalse').show()
      $('#dropdown').text('True/False Question')
    }

    $('#addQuestionBtn').show()
    $('#tempText').hide()
  })
})
