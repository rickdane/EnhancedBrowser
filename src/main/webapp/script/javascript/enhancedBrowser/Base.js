divMain = "mainWidget"

appPrefix = ""

formId_paths = new Array()

curStatus = "ACTIVE"


function BaseMenu() {

    this.display = function () {

        var targetDiv = this.divId

        $.get(
            this.getUrl,
            "{key:value}",
            function (data) {

                $("#" + targetDiv).html(data)

            },
            "html"
        );

    }
}


//this probably isn't the optimal way to do this, mainly just testing class inheritance in JS
function MainMenu() {
    this.divId = "menuMain"
    this.getUrl = "/enhancedbrowser/loadMainMenu"
}

MainMenu.prototype = new BaseMenu();


function TextDisplayManager() {

    this.temporaryTextDisplay = function (elementId, displayString, lengthSeconds) {
        $("#" + elementId).css("display", "block")
        $("#" + elementId).html(displayString)
        setTimeout(function () {
            this.temporaryTextHide(elementId)
        }, lengthSeconds * 1000);

    }

    this.temporaryTextHide = function (elementId) {
        $("#" + elementId).css("display", "none")
    }
}


function IframeManager() {

    this.loadUrlIntoIframe = function (url) {

        $('#iframeMain').attr('src', url);
        window.frames["iframeMain"].location.reload();

    }

    this.getCurrentIframeUrl = function () {
        var url = $("#iframeMain").attr('src')

    }
}

$(document).ready(function () {

    var properties = {};

    properties["PROCESSED"] = "URL Marked as Processed";
    properties["BLOCKED"] = "URL Blocked";
    properties["autoRun_started"] = "Auto-run started";
    properties["autoRun_paused"] = "Auto-run paused";

    //instantiate utility classes
    var ajaxUtility = new AjaxUtility()
    var textDisplayManager = new TextDisplayManager()
    var iframeManager = new IframeManager()

    autoRun = new AutoRun(textDisplayManager, properties)

    mobj = new MainMenu()
    mobj.display()

    sideMenu = new SideMenu(ajaxUtility, textDisplayManager, iframeManager, properties, autoRun)


    // register listeners on DOM elements
    $("#sideMenu_DisplayNew").click(function () {
        sideMenu.setCurrentStatus('ACTIVE', 'urlListHolder')
    });
    $("#sideMenu_DisplayProcessed").click(function () {
        sideMenu.setCurrentStatus('PROCESSED', 'urlListHolder')
    });
    $("#sideMenu_DisplayBlocked").click(function () {
        sideMenu.setCurrentStatus('BLOCKED', 'urlListHolder')
    });

    $("#autorunStart").click(function () {
        autoRun.startAutorun()
    });
    $("#autorunStop").click(function () {
        autoRun.stopAutorun()
    });

    $(".urlStatus_MarkProcessed").click(function () {
        sideMenu.changeUrlStatus('PROCESSED')
    });
    $(".urlStatus_MarkBlocked").click(function () {
        sideMenu.changeUrlStatus('BLOCKED')
    });

});






