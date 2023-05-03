package com.example.myapplication.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import timber.log.Timber


object PrefManager {

    private lateinit var prefs: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private const val PREFS_NAME = "params"
    const val IS_LOGIN = "IS_LOGIN"
    const val ACCESS_TOKEN = "ACCESS_TOKEN"
    const val FIRE_BASE_TOKEN = "FIRE_BASE_TOKEN"




    @SuppressLint("CommitPrefEdits")
    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        editor = prefs.edit()
    }

    fun setString(key: String?, value: String?) {
        editor.putString(key, value)
        editor.commit()
    }

    fun setStringSet(key: String?, value: MutableSet<String>?) {
        editor.putStringSet(key, value)
        editor.commit()
    }

    fun getString(key: String?, default: String = ""): String {
        return prefs.getString(key, default) ?: ""
    }

    fun getStringSet(key: String?, default: MutableSet<String>): MutableSet<String> {
        return prefs.getStringSet(key, default) ?: default
    }

    fun setBoolean(key: String, value: Boolean) {
        editor.putBoolean(key.toString(), value)
        editor.commit()
    }

    fun getBoolean(key: String?, default: Boolean = false): Boolean {
        return prefs.getBoolean(key, default)
    }

    fun removeValue(key: String?) {
        with(editor) {
            remove(key)
            commit()
        }
    }

    fun saveHashMap(key: String?, obj: Any?) {
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(obj)
        editor.putString(key, json)
        editor.apply() // This line is IMPORTANT !!!
    }

    fun getHashMap(key: String?): HashMap<String?, String?>? {
        val gson = Gson()
        val json = prefs.getString(key, "")
        val type = object : TypeToken<HashMap<String?, String?>?>() {}.type
        return gson.fromJson(json, type)
    }

    fun clearAll() {
        with(editor) {
            clear()
            apply()
        }
    }

    fun clearExcept(keyList: List<String?>) {
        val keys: Map<String?, *> = prefs.all
        for ((key) in keys) {
            if (!keyList.contains(key)) {
                removeValue(key)
            }
        }
    }
    fun checkForFCM(context: Context?, callback: (oldToken: String, newToken: String) -> Unit) {
        // to get FCM token
        try {
            if (checkPlayServices(context as Activity)) {
                FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Timber.e("Fetching FCM registration token failed", task.exception)
                        return@OnCompleteListener
                    }
                    val token = task.result
                    Timber.d("fcm token===" + token.toString())
                    val oldToken = PrefManager.getString(PrefManager.FIRE_BASE_TOKEN)
                    if (oldToken != token) {
                        // token renewed
                        callback(oldToken, token)
                    } else {
                        callback(token, token)
                    }
                    PrefManager.setString(PrefManager.FIRE_BASE_TOKEN, token)
                })
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private const val PLAY_SERVICES_RESOLUTION_REQUEST = 9000
    private fun checkPlayServices(activity: Activity): Boolean {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val resultCode =
            GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(activity)
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(
                    activity,
                    resultCode,
                    PLAY_SERVICES_RESOLUTION_REQUEST
                )
                    ?.show()
            } else {
                Timber.d(
                    "==checkPlayServices==" +
                            "This device is not supported."
                )
                activity.finish()
            }
            return false
        }
        return true
    }






}