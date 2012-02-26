function AutoRun(textDisplayManager, properties) {

    this.autoRunIntervalSeconds = 7

    this.autoRunOn = false

    this.lastUrlModelId = ""

    this.SKIP_STATUS = "BLOCKED"

    autoRun_skipNextAutoIter = false


    this.startAutorun = function () {

        this.curElementIndex = 1

        this.autoRunOn = true

        var obj = this

        setTimeout(function () {
            obj.autoRunNextPage(obj)
        }, obj.autoRunIntervalSeconds * 1000)

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


    this.autoRunNextPage = function (obj) {

        if (obj.autoRunOn && obj.sideMenu.curStatus != "BLOCKED") {

            if (!autoRun_skipNextAutoIter) {
                var elements = $(".urlDisplayTable")

                var curElement = elements[obj.curElementIndex]

                if (curElement == undefined) {
                    obj.curElementIndex = 0
                }
                else {
                    obj.curElementIndex++
                }

                this.sideMenu.clickUrl(curElement, this.sideMenu, true)

            }

            autoRun_skipNextAutoIter = false


            setTimeout(function () {
                obj.autoRunNextPage(obj)
            }, obj.autoRunIntervalSeconds * 1000)

        }
    }


    this.registerListeners = function (sideMenu) {

        this.sideMenu = sideMenu

        $(".urlStatus_MarkProcessedAutoRun").click(function () {
            autoRun_skipNextAutoIter = true
            sideMenu.changeUrlStatus('PROCESSED', sideMenu.urlTableLoadCallback, true, sideMenu)
        });
        $(".urlStatus_MarkBlockedAutoRun").click(function () {
            autoRun_skipNextAutoIter = true
            sideMenu.changeUrlStatus('BLOCKED', sideMenu.urlTableLoadCallback, true, sideMenu)
        });

    }

}



