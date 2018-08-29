package com.mfec.dbmaster;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import mobileapp.com.dbm.DBMaster;

public class DBMasterPlugin extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if(action.equals("dbMaster")) {
            this.loadDatabase(callbackContext);
            return true;
        }
        return false;
    }

    private boolean load() {
        //DBMaster.Companion.getInstance().getMasterDB(this.cordova.getActivity().getApplicationContext());
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

