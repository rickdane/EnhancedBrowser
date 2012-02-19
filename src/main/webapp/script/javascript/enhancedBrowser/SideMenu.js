function SideMenu(ajaxUtility, textDisplayManager, iframeManager, properties, autoRun) {

    var sideBarDivId = "enhancedBrowser_sidebar"
    var urlDisplayTableClass = "urlDisplayTable"
    var selectFormClass = "selectform"
    var urlTableHolder = "urlListHolder"

    //leave as global for now, JS is messy with this sort of thing so its hard to find an elegant solution
    lastClickedIsSelectMenu = false

    this.registerWithSideMenu = function (elementId) {
        $(elementId).mouseenter(
            function () {

            });
    }

    this.registerWithSideMenuSelect = function (elementId) {

        var obj = this

        $(elementId).mouseenter(
            function () {
                lastClickedIsSelectMenu = true;

                obj.clickCampaignSelectList()
            });
    }

    this.selectUrlinSession = function (url, modelId) {

        //TODO: need to generalize this, not hard-code it
        var path = appPrefix + "/urls/select/"

        ajaxUtility.ajaxGetNoCallback(path + modelId)

        iframeManager.loadUrlIntoIframe(url)
    }

    this.clickCampaignSelectList = function () {

        //TODO: need to generalize this, not hard-code it
        var path = appPrefix + "/campaigns/select/"

        var selectedValue = $("#selectform").val()

        ajaxUtility.ajaxGetNoCallback(path + selectedValue)
    }

    this.changeUrlStatus = function (status, callbackAfter) {

        var endpoint = '/urls/updateCurStatus/' + status

        var obj = this

        $.get(
            endpoint,
            "{key:value}",
            function (data) {

                tmpFn = function (targetDiv, data, curObj) {
                    $("#" + urlTableHolder).html(data)
                    obj.setCurrentStatus('BLOCKED', 'urlListHolder')
                }

                obj.ajaxLoadWidget('/urls/findByStatus/CURUSER/' + curStatus, urlTableHolder, tmpFn, callbackAfter)

                var displayVal = properties[status]
                textDisplayManager.temporaryTextDisplay("autoRun_text", displayVal, .5)
            },
            "html"
        );
    }

    this.changeUrlStatusCallback = function () {

        var url = $("." + urlDisplayTableClass).first().html()

        var modelId = $("." + urlDisplayTableClass).first().attr("modelid")

        this.selectUrlinSession(url, modelId)
    }

    this.setCurrentStatus = function (status, targetDivInpt) {

        curStatus = status

        var endPoint = "/urls/findByStatus/CURUSER/" + status

        this.ajaxLoadWidget(endPoint, urlTableHolder, this.urlTableLoadCallback, this)

    }

    this.init = function () {

        $("#" + sideBarDivId).hide();

        $("div.enhancedBrowser_sidebarActivator").mouseenter(
            function () {
                $("#" + sideBarDivId).show();
            });

        this.registerWithSideMenu("div." + sideBarDivId)

        $("#" + sideBarDivId).mouseleave(
            function () {

                if (lastClickedIsSelectMenu) {
                    lastClickedIsSelectMenu = false
                    return false
                }

                $("#" + sideBarDivId).hide();

            });


        this.ajaxLoadWidget("/campaigns/selectForSession", "campaignSelectAjax", this.ajaxLoadWidgetDefaultCallback)


    }

    this.ajaxLoadWidget = function (endpoint, targetDiv, callBack, curObj) {

        endpoint = appPrefix + endpoint

        if (curObj == null) {
            var curObj = this
        }

        $.get(
            endpoint,
            "{key:value}",
            function (data) {

                callBack(targetDiv, data, curObj)

            },
            "html"
        );
    }

    this.urlTableLoadCallback = function (targetDiv, data, curObj) {

        if (curObj == null) {
            curObj = this
        }

        $("#" + targetDiv).html(data)

        $(".urlDisplayTable").click(
            function () {

                //clicking a url manually needs to always stop the autorun as its not worth the complexity to allow any other use case
                autoRun.stopAutorun()

                modelId = $(this).attr("modelId")

                url = $(this).html()

                curObj.selectUrlinSession(url, modelId)
            });
    }

    this.ajaxLoadWidgetDefaultCallback = function (targetDiv, data, obj) {

        $('#' + targetDiv).html(data)

        obj.urlTableLoadCallback()

        var curElement = $("." + urlDisplayTableClass).first()

        if (curElement != null && curElement != undefined) {

            var curUrl = curElement.html()
            var curUrlModelId = curElement.attr("modelid")

            if (curUrl != undefined && curUrlModelId != undefined) {
                obj.selectUrlinSession(curUrl, curUrlModelId)
            }
        }

        //manually select from list (figure out how to genericize this)
        obj.registerWithSideMenuSelect("#" + selectFormClass)
    }

    this.init()


}