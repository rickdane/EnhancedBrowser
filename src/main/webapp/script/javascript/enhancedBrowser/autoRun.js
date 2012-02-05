var autoRunIntervalSeconds = 15

autoRun = false

lastUrlModelId = ""

function startAutoRun() {

    autoRun = true

    setTimeout(autoRunNextPage, autoRunIntervalSeconds * 1000)

}

function stopAutoRun() {
    autoRun = false
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

    changeUrlStatus("BLOCKED")

}

