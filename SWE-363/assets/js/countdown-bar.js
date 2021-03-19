function progress(timeleft, timetotal, $element) {
    var progressBarWidth = (timeleft * $element.width()) / timetotal
    $element
        .find('div')
        .animate({ width: progressBarWidth }, 500)
        .html(
            'Time remaining: ' + Math.floor(timeleft / 60) + ':' + (timeleft % 60)
        )
    if (timeleft > 0) {
        setTimeout(function() {
            progress(timeleft - 1, timetotal, $element)
        }, 1000)
    }
}

progress(500, 500, $('#progress-bar'))