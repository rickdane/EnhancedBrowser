function SideMenu(ajaxUtility, textDisplayManager, iframeManager, properties, autoRun) {

    this.curStatus = "ACTIVE"

    var sideBarDivId = "enhancedBrowser_sidebar"
    var urlDisplayTableClass = "urlDisplayTable"
    var selectFormClass = "selectform"
    this.selectFormElement = "selectform"
    var urlTableHolder = "urlListHolder"
    this.urlListClickedColor = "#336699"
    this.urlListClickedFontColor = "#fff"

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

    this.selectUrlinSession = function (url, modelId, jqueryEle) {

        //for a case where its not being called form listener but instead called from within code where we just want to load the first item in the list
        if (jqueryEle == null) {
            jqueryEle = $(".urlDisplayTable").first()
        }

        //TODO: need to generalize this, not hard-code it
        var path = appPrefix + "/urls/select/"

        var endpoint = path + modelId

        var obj = this

        $.get(
            endpoint,
            "{key:value}",
            function (data) {
                obj.selectCurInUrlList(obj, jqueryEle)

                iframeManager.loadUrlIntoIframe(url)

            },
            "html"
        );

    }

    this.selectCurInUrlList = function (obj, jqueryEle) {

        var elements = $("." + urlDisplayTableClass)

        //reset the background color on any clicked elements in the list
        elements.each(function () {
            $(this).css('background-color', '');
            $(this).css('color', '');

        });

        $(jqueryEle).css("background-color", obj.urlListClickedColor)
        $(jqueryEle).css("color", obj.urlListClickedFontColor)

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

    this.changeUrlStatus = function (status, callbackAfter, suppressAutorunPause, obj) {

        var endpoint = '/urls/updateCurStatus/' + status

        if (obj == null) {
            var obj = this
        }

        $.get(
            endpoint,
            "{key:value}",
            function (data) {


                obj.setCurrentStatus(obj.curStatus, 'urlListHolder', suppressAutorunPause)

                obj.clickUrl(null, obj, suppressAutorunPause)

            },
            "html"
        );
    }


    this.changeUrlStatusCallback = function (obj) {

        var url = $("." + urlDisplayTableClass).first().html()

        var modelId = $("." + urlDisplayTableClass).first().attr("modelid")

        obj.selectUrlinSession(url, modelId)
    }

    this.setCurrentStatus = function (status, targetDivInpt, suppressAutorunPause) {

        var obj = this

        this.curStatus = status


        var endPoint = "/urls/findByStatus/CURUSER/" + status

        obj.ajaxLoadWidget(endPoint, urlTableHolder, obj.urlTableLoadCallback, obj, suppressAutorunPause)


    }

    this.showSideMenu = function () {
        $("#" + sideBarDivId).show();
    }

    this.init = function () {

        this.autoRun = autoRun

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

        this.registerListeners()

    }

    this.selectMenuRegisterCallback = function () {
        $("#" + this.selectFormElement).select(function () {

            obj.clickCampaignSelectList(null)
        });
    }


    this.ajaxLoadWidget = function (endpoint, targetDiv, callBack, curObj, suppressAutorunPause) {

        endpoint = appPrefix + endpoint

        if (curObj == null) {
            var curObj = this
        }

        $.get(
            endpoint,
            "{key:value}",
            function (data) {

                if (callBack != null) {
                    callBack(targetDiv, data, curObj, suppressAutorunPause)
                }

            },
            "html"
        );
    }

    this.urlTableLoadCallback = function (targetDiv, data, obj, suppressAutorunPause) {

        if (obj == null) {
            obj = this
        }

        $("#" + targetDiv).html(data)

        obj.clickUrl(null, obj, suppressAutorunPause)

        //prepend a select label in the select list
        var labelVal = "-- Select Campaign --"
        $("#" + this.selectFormElement).prepend("<option>" + labelVal + "</option>")
        $("#" + this.selectFormElement).val(labelVal)

        $("." + urlDisplayTableClass).click(
            function () {
                obj.clickUrl(this, obj)

            });
    }

    this.clickUrl = function (jqueryEle, obj, suppressAutorunPause) {

        //for a case where its not being called form listener but instead called from within code where we just want to load the first item in the list
        if (jqueryEle == null) {
            jqueryEle = $("." + urlDisplayTableClass).first()
        }

        //clicking a url manually needs to always stop the autorun as its not worth the complexity to allow any other use case
        if (!suppressAutorunPause) {
            this.autoRun.stopAutorun()
        }


        var modelId = $(jqueryEle).attr("modelId")

        var url = $(jqueryEle).attr("url")

        obj.selectUrlinSession(url, modelId, jqueryEle)

    }

    this.selectMenuClickCount = 0

    this.ajaxLoadWidgetDefaultCallback = function (targetDiv, data, obj) {

        $('#' + targetDiv).html(data)

        obj.urlTableLoadCallback(targetDiv, data, obj)

        //The weirdness here is to deal with an issue that occurs with the sidebar  hide / show mouseover functionality, its basically a fundamental bug in
        // Jquery, or something deeper, and this should later be replaced with a menu that does not use HTML select functioanlity (a custom menu made from DIV's, etc)
        $("#" + selectFormClass).click(
            function () {

                var campaignSelectCallback = function () {

                    obj.setCurrentStatus('ACTIVE', 'urlListHolder')
                }

                obj.clickCampaignSelectList(campaignSelectCallback)


                if (obj.selectMenuClickCount >= 1) {


                    $("#" + sideBarDivId).click()


                    lastClickedIsSelectMenu = false

                    obj.selectMenuClickCount = 0
                    return
                }

                obj.selectMenuClickCount++

            }
        );


        obj.registerWithSideMenuSelect("#" + selectFormClass)

    }

    this.registerListeners = function () {

        var obj = this

        // register listeners on DOM elements
        $("#sideMenu_DisplayNew").click(function () {
            obj.setCurrentStatus('ACTIVE', 'urlListHolder')
        });
        $("#sideMenu_DisplayProcessed").click(function () {
            obj.setCurrentStatus('PROCESSED', 'urlListHolder')
        });
        $("#sideMenu_DisplayBlocked").click(function () {
            obj.setCurrentStatus('BLOCKED', 'urlListHolder')
        });

        $(".urlStatus_MarkProcessed").click(function () {
            obj.changeUrlStatus('PROCESSED', obj.urlTableLoadCallback)
        });
        $(".urlStatus_MarkBlocked").click(function () {
            obj.changeUrlStatus('BLOCKED', obj.urlTableLoadCallback)
        });

        this.autoRun.registerListeners(this)


    }

    this.init()


}