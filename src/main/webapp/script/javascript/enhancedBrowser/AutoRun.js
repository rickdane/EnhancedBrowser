function AutoRun(textDisplayManager, properties) {

    var autoRunIntervalSeconds = 7

    this.autoRunOn = false

    this.lastUrlModelId = ""

    this.SKIP_STATUS = "BLOCKED"

    this.startAutorun = function () {

        this.autoRunOn = true

        setTimeout(this.autoRunNextPage, autoRunIntervalSeconds * 1000)

        var displayVal = properties["autoRun_started"]
        textDisplayManager.temporaryTextDisplay("autoRun_text", displayVal, 2)

        $("#autoRun_icons").css("display", "block")

    }

    this.stopAutorun = function () {
        this.autoRunOn = false
        var displayVal = properties["autoRun_paused"]
        textDisplayManager.temporaryTextDisplay("autoRun_text", displayVal, 1)
        $("#autoRun_icons").css("display", "none")
    }


    this.autoRunNextPage = function () {

        if (this.autoRunOn && curStatus != "BLOCKED") {

            var curElement = $(".urlDisplayTable").first()
            var curUrlModelId = curElement.attr("modelid")
            var curUrl = curElement.html()

            if (curUrlModelId == this.lastUrlModelId) {

                autoRunMarkSkipped(curUrl, curUrlModelId)

                curUrlModelId = $(".urlDisplayTable").first().attr("modelid")
            }

            this.lastUrlModelId = curUrlModelId

            setTimeout(this.autoRunNextPage, autoRunIntervalSeconds * 1000)
        }
    }

    this.autoRunMarkSkipped = function () {

        //note: order these are called in is important


        var callback = function () {
            changeUrlStatusCallback()
        }

        changeUrlStatus(SKIP_STATUS, callback)

    }
}



