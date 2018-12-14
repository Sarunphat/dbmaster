var dbMaster = {
    loadDatabase: (successCallback, errorCallback) => {
        cordova.exec(
            successCallback,
            errorCallback,
            "DBMasterPlugin",
            "dbMaster"
        );
    },
    isLoading: (successCallback, errorCallback) => {
        cordova.exec(
            successCallback,
            errorCallback,
            "DBMasterPlugin",
            "isLoading"
        );
    }
}

module.exports = dbMaster;
