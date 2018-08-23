var dbMaster = {
    loadDatabase: (successCallback, errorCallback) => {
        cordova.exec(
            successCallback,
            errorCallback,
            "DBMaster",
            "dbMaster"
        );
    }
}

module.exports = dbMaster;