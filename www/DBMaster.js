var dbMaster = {
    loadDatabase: (successCallback, errorCallback) => {
        cordova.exec(
            successCallback,
            errorCallback,
            "DBMasterPlugin",
            "dbMaster"
        );
    }
}

module.exports = dbMaster;