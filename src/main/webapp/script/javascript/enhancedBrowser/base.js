divMain = "mainWidget"

appPrefix = ""

formId_paths = new Array()

curStatus = "ACTIVE"

properties = {};

properties["PROCESSED"] = "URL Marked as Processed";
properties["BLOCKED"] = "URL Blocked";
properties["autoRun_started"] = "Auto-run started";
properties["autoRun_paused"] = "Auto-run paused";


//encapsulator()
//
//function testCallBack() {
//    alert("testcallback")
//    function test() {
//        alert("something")
//
//    }
//}
//
//function independentFunc() {
//    alert("its def working")
//}
//
//function encapsulator() {
//
////    var callback = function () {
////
////         innerFunction = function () {
////
////            alert("hot damn fool")
////        }
////
////            innerFunction()
////
////    }
//
//    var callback = function () {
//        independentFunc()
//    }
//
//    thirdFunction(callback)
//
//}
//
//function thirdFunction(func) {
//    alert("before")
//    func()
//}

/**
 * Deprecated?
 * @param status
 * @param targetDivInpt
 */
function setCurrentStatus(status, targetDivInpt) {

    curStatus = status

    var endPoint = "/urls/findByStatus/CURUSER/" + status

    ajaxLoadWidget(endPoint, targetDivInpt)

    urlTableLoadCallback()

}

function registerSubmitListener(formId, path) {

    formId_paths[formId] = path;

    $('#' + formId).submit(function () {
        ajaxFormSubmit(formId_paths[formId], formId)

        //must return false in order to cancel the default action which is to refresh the page
        return false;
    });
}


function ajaxGetNoCallback(endpoint) {

    $.get(
        endpoint,
        "{key:value}",
        function (data) {

        },
        "html"
    );
}

function changeUrlStatus(status, callbackAfter) {

    var endpoint = '/urls/updateCurStatus/' + status

    $.get(
        endpoint,
        "{key:value}",
        function (data) {

            ajaxLoadWidget('/urls/findByStatus/CURUSER/' + curStatus, 'urlListHolder', null, callbackAfter)

            var displayVal = properties[status]
            temporaryTextDisplay("autoRun_text", displayVal, .5)
        },
        "html"
    );
}

function temporaryTextDisplay(elementId, displayString, lengthSeconds) {
    $("#" + elementId).css("display", "block")
    $("#" + elementId).html(displayString)
    setTimeout(function () {
        temporaryTextHide(elementId)
    }, lengthSeconds * 1000);

}

function temporaryTextHide(elementId) {
    $("#" + elementId).css("display", "none")
}

function changeUrlStatusCallback() {

    var url = $(".urlDisplayTable").first().html()

    var modelId = $(".urlDisplayTable").first().attr("modelid")

    selectUrlinSession(url, modelId)
}

function ajaxSetSessionAttribute(parameterName, callbackTargetDiv, value) {

    var sessionEndpoint = appPrefix + "/sessionvalues/" + parameterName + "/" + value
    $.get(
        sessionEndpoint,
        "{key:value}",
        function (data) {
            if (callbackTargetDiv != null) {
                $("#" + callbackTargetDiv).html(data)
            }
        },
        "html"
    );
}


function ajaxFormSubmit(endpoint, formId) {
    var $form = $("#" + formId),
        $inputs = $form.find("input, select, button, textarea"),
        serializedData = $form.serialize();
    $.post(endpoint,
        serializedData,
        function (data) {
            $("#" + divMain).html(data);
        },
        "html"
    );
}


function registerWithSideMenu(elementId) {
    $(elementId).mouseenter(
        function () {

        });
}

function registerWithSideMenuSelect(elementId) {
    $(elementId).mouseenter(
        function () {
            lastClickedIsSelectMenu = true;

            clickCampaignSelectList()
        });
}

$(document).ready(function () {

    $("#enhancedBrowser_sidebar").hide();

    $("div.enhancedBrowser_sidebarActivator").mouseenter(
        function () {
            $("#enhancedBrowser_sidebar").show();
        });

    registerWithSideMenu("div.enhancedBrowser_sidebar")

    ajaxLoadWidget("/campaigns/selectForSession", "campaignSelectAjax")

    lastClickedIsSelectMenu = false

    $("#enhancedBrowser_sidebar").mouseleave(
        function () {

            if (lastClickedIsSelectMenu) {
                lastClickedIsSelectMenu = false
                return false
            }

            $("#enhancedBrowser_sidebar").hide();

        });
});


function clickCampaignSelectList() {
    //TODO: need to generalize this, not hard-code it
    var path = appPrefix + "/campaigns/select/"

    var selectedValue = $("#selectform").val()

    ajaxGetNoCallback(path + selectedValue)
}


function urlTableLoadCallback() {

    $(".urlDisplayTable").click(
        function () {

            //clicking a url manually needs to always stop the autorun as its not worth the complexity to allow any other use case
            stopAutoRun()

            modelId = $(this).attr("modelId")

            url = $(this).html()

            selectUrlinSession(url, modelId)
        });
}

function selectUrlinSession(url, modelId) {

    //TODO: need to generalize this, not hard-code it
    var path = appPrefix + "/urls/select/"

    ajaxGetNoCallback(path + modelId)

    loadUrlIntoIframe(url)
}

function loadUrlIntoIframe(url) {

    $('#iframeMain').attr('src', url);
    window.frames["iframeMain"].location.reload();

}

function getCurrentIframeUrl() {
    var url = $("#iframeMain").attr('src')

}


function ajaxLoadWidget(endpoint, targetDivInpt, callbackFunctionBefore, callbackFunctionAfter) {

    endpoint = appPrefix + endpoint

    var targetDiv = divMain
    if (targetDivInpt != null) {
        targetDiv = targetDivInpt
    }

    $.get(
        endpoint,
        "{key:value}",
        function (data) {

            if (callbackFunctionBefore != null) {
                callbackFunctionBefore()
            }

            ajaxLoadWidgetDefaultCallback(targetDiv, data)

            if (callbackFunctionAfter != null) {
                callbackFunctionAfter()
            }
        },
        "html"
    );
}

function ajaxLoadWidgetDefaultCallback(targetDiv, data) {

    $('#' + targetDiv).html(data);

    urlTableLoadCallback()

    var curElement = $(".urlDisplayTable").first()

    if (curElement != null && curElement != undefined) {

        var curUrl = curElement.html()
        var curUrlModelId = curElement.attr("modelid")

        if (curUrl != undefined && curUrlModelId != undefined) {
            selectUrlinSession(curUrl, curUrlModelId)
        }
    }


//manually select from list (figure out how to genericize this)
    registerWithSideMenuSelect("#selectform")
}