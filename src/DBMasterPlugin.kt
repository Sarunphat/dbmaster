package com.mfec.dbmaster

import org.apache.cordova.*
import org.json.JSONArray
import org.json.JSONException
import org.jetbrains.anko.db.*

import com.facebook.stetho.Stetho
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import mobileapp.com.dbm.DBMaster
import mobileapp.com.dbm.DBMasterCallback

class DBMasterPlugin : CordovaPlugin() {
    lateinit var context: CallbackContext
        
    @Throws(JSONException::class)
    override fun execute(action: String, data: JSONArray, callbackContext: CallbackContext): Boolean {
        context = callbackContext
        var result = true
        try {
            Log.d("DBMaster", "DBMaster is called with action: " + action);
            if (action == "dbMaster") {
                DBMaster.getInstance().getMasterDB(webView.getContext(), object : DBMasterCallback() {
                    override fun OnSuccess() {
                        val database: MyDatabaseOpenHelper = MyDatabaseOpenHelper.getInstance(webView.getContext())
                        val rowParser = classParser<Occupation>()
                        val temp = database.use {
                            select("MdOccupations").parseList(rowParser)
                        }
                        val mapper = jacksonObjectMapper()
                        callbackContext.success(mapper.writeValueAsString(temp))
                    }
                    override fun OnError() {
                        result = false
                    }
                });
                Stetho.initializeWithDefaults(webView.getContext())
            } else if (action == "isLoading") {
                Log.d("DBMaster", "isLoading is called with result: " + DBMaster.getInstance().isGettingDBMaster())
                val isLoading: String = DBMaster.getInstance().isGettingDBMaster()
                callbackContext.success(isLoading)
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

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "MasterData.db", null, 1) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.getApplicationContext())
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable("MdOccupations", true,
                "rowid" to INTEGER + PRIMARY_KEY + UNIQUE,
                "Code" to TEXT,
                "Created" to TEXT,
                "CreatedBy" to TEXT,
                "GroupDescEn" to TEXT,
                "GroupDescTh" to TEXT,
                "HiRate" to REAL,
                "IsActive" to INTEGER,
                "NameEn" to TEXT,
                "NameTh" to TEXT,
                "OccuClass" to INTEGER,
                "PAOccuClass" to INTEGER,
                "Updated" to TEXT,
                "UpdatedBy" to TEXT
                )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }
}

data class Occupation (val Code: String, val Created: String?, val CreatedBy: String?, val GroupDescEn: String,
                  val GroupDescTh: String, val HiRate: Float, val IsActive: Boolean, val NameEn: String,
                  val NameTh: String, val OccuClass: Int, val PAOccuClass: Int, val Updated: String?, val UpdatedBy: String?)
