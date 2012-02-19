
function AjaxUtility() {

    this.registerSubmitListener = function (formId, path) {

        formId_paths[formId] = path;

        $('#' + formId).submit(function () {
            this.ajaxFormSubmit(formId_paths[formId], formId)

            //must return false in order to cancel the default action which is to refresh the page
            return false;
        });
    }

    this.ajaxGetNoCallback = function (endpoint) {

        $.get(
            endpoint,
            "{key:value}",
            function (data) {

            },
            "html"
        );
    }


    this.ajaxSetSessionAttribute = function (parameterName, callbackTargetDiv, value) {

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


    this.ajaxFormSubmit = function (endpoint, formId) {
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
}