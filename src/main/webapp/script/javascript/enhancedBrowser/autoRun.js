var autoRunIntervalSeconds = 7

autoRun = false

lastUrlModelId = ""

SKIP_STATUS = "BLOCKED"

function startAutoRun() {

    autoRun = true

    setTimeout(autoRunNextPage, autoRunIntervalSeconds * 1000)

    var displayVal = properties["autoRun_started"]
    temporaryTextDisplay("autoRun_text", displayVal, 2)

    $("#autoRun_icons").css("display", "block")


}

function stopAutoRun() {
    autoRun = false
    var displayVal = properties["autoRun_paused"]
    temporaryTextDisplay("autoRun_text", displayVal, 1)
    $("#autoRun_icons").css("display", "none")
}


function autoRunNextPage() {

    if (autoRun && curStatus != "BLOCKED") {

        var curElement = $(".urlDisplayTable").first()
        var curUrlModelId = curElement.attr("modelid")
        var curUrl = curElement.html()

        if (curUrlModelId == lastUrlModelId) {

            autoRunMarkSkipped(curUrl, curUrlModelId)

            curUrlModelId = $(".urlDisplayTable").first().attr("modelid")
        }

        lastUrlModelId = curUrlModelId

        setTimeout(autoRunNextPage, autoRunIntervalSeconds * 1000)
    }
}

function autoRunMarkSkipped() {

    //note: order these are called in is important


    var callback = function () {
        changeUrlStatusCallback()
    }

    changeUrlStatus(SKIP_STATUS, callback)

}

