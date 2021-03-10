$.parseUrlParameters = function() {
    let queryString = window.location.search.slice(1);
    let retValue = {};

    if (queryString) {
        queryString = queryString.split('#')[0];
        const queryParts = queryString.split('&');

        for (let i = 0; i < queryParts.length; i++) {
            const queryPart = queryParts[i].split('=');

            // set parameter name and value (use 'true' if empty)
            let paramName = queryPart[0];
            let paramValue = typeof (queryPart[1]) === 'undefined' ? true : queryPart[1];

            paramName = paramName.toLowerCase();
            if (typeof paramValue === 'string') paramValue = paramValue.toLowerCase();

            if (!retValue[paramName]) {
                retValue[paramName] = paramValue;
            }
        }
    }

    return retValue;
}

$.getLanguageCode = function () {
    const urlParameters = $.parseUrlParameters();

    if (urlParameters) {
        const langCode = urlParameters['lang'];
        if (langCode) {
            return langCode;
        }
        else {
            return 'fi';
        }
    }
}

$.isMobileMode = function () {
    const urlParameters = $.parseUrlParameters();

    if (urlParameters) {
        const mode = urlParameters['mode'];
        if (mode) {
            return (mode === 'mobile');
        }
        else {
            return false;
        }
    }
}
