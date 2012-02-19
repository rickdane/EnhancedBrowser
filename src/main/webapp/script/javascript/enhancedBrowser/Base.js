divMain = "mainWidget"

appPrefix = ""

formId_paths = new Array()

curStatus = "ACTIVE"


function TextDisplayManager() {

    this.temporaryTextDisplay = function (elementId, displayString, lengthSeconds) {
        $("#" + elementId).css("display", "block")
        $("#" + elementId).html(displayString)

        var obj = this
        setTimeout(function () {
            obj.temporaryTextHide(elementId)
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

$.fn.resize = function (a) {
    var d = Math.ceil;
    if (a == null)a = 200;
    var e = a, f = a;
    $(this).each(function () {
        var b = $(this).height(), c = $(this).width();
        if (b > c)f = d(c / b * a); else e = d(b / c * a);
        $(this).css({height:e, width:f})
    })
};


function IconSizeManager(iconSetArr, baseSize, disableClick) {


    this.registerIconsForMouseover = function () {

        var obj = this
        $.each(this.iconSetArr, function (index, curElementId) {

            //set the image as its base size, this is so we don't have to set the size within CSS for each image
            obj.restoreIcon(curElementId, obj)

            $("#" + curElementId).mouseenter(function () {

                obj.enlargeIcon(curElementId, obj)
            })

            $("#" + curElementId).mouseleave(function () {
                obj.restoreIcon(curElementId, obj)
            })

            $("#" + curElementId).click(function () {
                obj.clickResize(curElementId, obj)
            })


        });

    }

    this.init = function () {

        this.baseSize = baseSize

        this.resizeSize = this.baseSize * .10 + this.baseSize

        this.resizeSmaller = this.baseSize * .05 + this.baseSize

        this.iconSetArr = iconSetArr

        this.registerIconsForMouseover()

        this.curClicked = false

        this.curClickedElement = ""

        this.disableClick = disableClick
    }

    this.resetIconsSize = function (obj) {

        obj.curClicked = false

        $.each(this.iconSetArr, function (index, curElementId) {

            //set the image as its base size, this is so we don't have to set the size within CSS for each image
            obj.restoreIcon(curElementId, obj)

        });

    }

    this.clickResize = function (elementId, obj) {

        if (disableClick) {
            return false
        }

        obj.curClickedElement = elementId

        obj.resetIconsSize(obj)

        obj.enlargeIcon(elementId, obj)

        obj.curClicked = true

    }


    this.setCurSelectedIcon = function (elementId) {

        this.enlargeIcon(elementId)
    }

    this.enlargeIcon = function (elementId, obj) {

        var resizeSizeLocal = obj.resizeSize

        if (obj.curClicked && obj.curClickedElement == elementId) {

            return false

        }
        else if (obj.curClicked && obj.curClickedElement != elementId) {
            resizeSizeLocal = obj.resizeSmaller
        }

        $("#" + elementId).resize(resizeSizeLocal);

    }

    this.restoreIcon = function (elementId, obj) {
        if (obj.curClicked && obj.curClickedElement == elementId) {
            return false
        }
        $("#" + elementId).resize(this.baseSize);
    }

    this.init()

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


    registerIconResizeManagers()

    autoRun = new AutoRun(textDisplayManager, properties)

    sideMenu = new SideMenu(ajaxUtility, textDisplayManager, iframeManager, properties, autoRun)

    var menuManager = new MenuManager(sideMenu)
    menuManager.showMenu("menuContent_welcome")


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


    function registerIconResizeManagers() {
        var iconArr1 = {}
        iconArr1[0] = "sideMenu_DisplayNew"
        iconArr1[1] = "sideMenu_DisplayProcessed"
        iconArr1[2] = "sideMenu_DisplayBlocked"
        iconSizeManager = new IconSizeManager(iconArr1, 31)


        var iconArr2 = {}
        var i = 0
        $(".urlStatusSelectIcon").each(function () {
            var elementId = $(this).attr("id")
            iconArr2[i] = elementId
            i++
        });

        iconSizeManager2 = new IconSizeManager(iconArr2, 33, true)


        var iconArr3 = {}
        iconArr3[0] = "autorunStart"
        iconArr3[1] = "autorunStop"
        iconSizeManager3 = new IconSizeManager(iconArr3, 33)
    }

    function MenuManager(sideMenuObj) {

        this.showMenu = function (elementContents) {
            var contents = $("#" + elementContents).html()

            $('#menuMain').html(contents)

            $('#menuMain').show()
            $("#overlayDim").show()

        }

        this.hideMenu = function () {
            $('#menuMain').hide()
            $("#overlayDim").hide()
            this.sideMenuObj.showSideMenu()
        }

        this.init = function () {

            this.sideMenuObj = sideMenuObj

            $("#menuLinkHolder").click(function () {
                menuManager.showMenu("menuContent_mainMenu")
            });

            $("#overlayDim").click(function () {
                menuManager.hideMenu()
            });
        }

        this.init()
    }

});






