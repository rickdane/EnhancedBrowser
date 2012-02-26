function AutoRun(textDisplayManager, properties) {

    this.autoRunIntervalSeconds = 7

    this.autoRunOn = false

    this.lastUrlModelId = ""

    this.SKIP_STATUS = "BLOCKED"


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

            var elements = $(".urlDisplayTable")

            var curElement = elements[obj.curElementIndex]

            if (curElement == undefined) {
                obj.curElementIndex = 0
            }
            else {
                obj.curElementIndex++
            }

            this.sideMenu.clickUrl(curElement, this.sideMenu,true)


            setTimeout(function () {
                obj.autoRunNextPage(obj)
            }, obj.autoRunIntervalSeconds * 1000)

        }
    }


    this.registerListeners = function (sideMenu) {

        this.sideMenu = sideMenu

        $(".urlStatus_MarkProcessedAutoRun").click(function () {
            sideMenu.changeUrlStatus('PROCESSED', sideMenu.urlTableLoadCallback, true, sideMenu)
        });
        $(".urlStatus_MarkProcessedAutoRun").click(function () {
            sideMenu.changeUrlStatus('BLOCKED', sideMenu.urlTableLoadCallback, true, sideMenu)
        });

    }

}



