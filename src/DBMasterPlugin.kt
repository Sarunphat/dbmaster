package com.mfec.dbmaster;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import android.util.Log;
import com.facebook.stetho.Stetho;

import mobileapp.com.dbm.DBMaster;

class DBMasterPlugin : CordovaPlugin() {
    lateinit var context: CallbackContext
        
    @Throws(JSONException::class)
    override fun execute(action: String, data: JSONArray, callbackContext: CallbackContext): Boolean {
        context = callbackContext
        var result = true
        try {
            if (action == "dbMaster") {
                DBMaster.getInstance().getMasterDB(webView.getContext());
                // Stetho.initializeWithDefaults(webView.getContext());
                callbackContext.success();
            } else {
                handleError("Invalid action")
                result = false
            }
        } catch (e: Exception) {
            handleException(e)
            result = false
        }
        return result
    }
    
    /**
     * Handles an error while executing a plugin API method.
     * Calls the registered Javascript plugin error handler callback.
     *
     * @param errorMsg Error message to pass to the JS error handler
     */
    private fun handleError(errorMsg: String) {
        try {
            Log.e(TAG, errorMsg)
            context.error(errorMsg)
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }

    }

    private fun handleException(exception: Exception) {
        handleError(exception.toString())
    }

    companion object {

        protected val TAG = "DBMasterPlugin"
    }
}
