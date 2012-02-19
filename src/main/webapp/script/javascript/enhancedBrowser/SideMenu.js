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

            });
    }

    this.selectUrlinSession = function (url, modelId) {

        //TODO: need to generalize this, not hard-code it
        var path = appPrefix + "/urls/select/"

        ajaxUtility.ajaxGetNoCallback(path + modelId)

        iframeManager.loadUrlIntoIframe(url)
    }

    this.clickCampaignSelectList = function (callback) {

        //TODO: need to generalize this, not hard-code it
        var path = appPrefix + "/campaigns/select/"

        var selectedValue = $("#selectform").val()

        $.get(
            path + selectedValue,
            "{key:value}",
            function (data) {
                callback()
            },
            "html"
        );

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

        var obj = this

        var callback = function () {
            curStatus = status

            var endPoint = "/urls/findByStatus/CURUSER/" + status

            obj.ajaxLoadWidget(endPoint, urlTableHolder, obj.urlTableLoadCallback, obj)
        }


        this.clickCampaignSelectList(callback)


    }

    this.showSideMenu = function () {
        $("#" + sideBarDivId).show();
    }

    this.init = function () {

        $("#" + sideBarDivId).hide();

        $("div.enhancedBrowser_sidebarActivator").mouseenter(
            function () {
                $("#" + sideBarDivId).show();
            });

        this.registerWithSideMenu("div." + sideBarDivId)

        var obj = this

        $("#" + sideBarDivId).mouseleave(
            function () {

                if (lastClickedIsSelectMenu) {
                    lastClickedIsSelectMenu = false
                    return false
                }

                $("#" + sideBarDivId).hide();

            }
        );

        $("#" + sideBarDivId).hover(
            function () {
                lastClickedIsSelectMenu = false

            }
        );


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

                url = $(this).attr("url")

                curObj.selectUrlinSession(url, modelId)
            });
    }

    this.selectMenuClickCount = 0

    this.ajaxLoadWidgetDefaultCallback = function (targetDiv, data, obj) {

        $('#' + targetDiv).html(data)

        obj.urlTableLoadCallback(targetDiv, data, obj)


        $("#" + selectFormClass).mouseleave(
            function () {


            }
        );

        $("#" + selectFormClass).click(
            function () {

                if (obj.selectMenuClickCount >= 1) {


                    $("#" + sideBarDivId).click()


                    lastClickedIsSelectMenu = false

                    obj.selectMenuClickCount = 0
                    return
                }

                obj.selectMenuClickCount++

            }
        );

        var curElement = $("." + urlDisplayTableClass).first()

        if (curElement != null && curElement != undefined) {

            var curUrl = curElement.html()
            var curUrlModelId = curElement.attr("modelid")

            if (curUrl != undefined && curUrlModelId != undefined) {
                obj.selectUrlinSession(curUrl, curUrlModelId)
            }
        }

        obj.registerWithSideMenuSelect("#" + selectFormClass)
    }

    this.init()


}