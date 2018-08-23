package com.mfec.dbmaster;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

public class DBMaster extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if(action.equals("dbMaster")) {
            this.loadDatabase(callbackContext);
            return true;
        }
        return false;
    }

    private boolean load() {
        return true;
    }

    private void loadDatabase(CallbackContext callbackContext) {
        if(load()) {
            callbackContext.success();
        } else {
            callbackContext.error("");
        }
    }
}

