var dbMaster = {
    loadDatabase: (successCallback, errorCallback) => {
        console.log("dbMaster starts loading database.");
        cordova.exec(
            successCallback,
            errorCallback,
            "DBMasterPlugin",
            "dbMaster"
        );
    }
}

module.exports = dbMaster;
