package com.example.myapplication.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import java.util.*

object Utils {

    fun snackBar(view: View?, context: Context?, message: String?) {
        if (message != null && view != null) {
            val imm =
                context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)

            val snackbar =
                Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            val snackbarView = snackbar.view
            val snackTextView =
                snackbarView.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView

            snackTextView.maxLines = 4
            snackbar.show()
        }
    }

    fun logout(context: Context) {
        try {

            PrefManager.clearAll()
            val intent : Intent?=
                context.packageManager.getLaunchIntentForPackage(context.packageName)
            intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            ContextCompat.startActivity(context,intent!!,null)

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Timber.e(e.localizedMessage)
        }
    }


    fun timeAgo(
        date: Long
    ): String {
        try {
            val currentTime = Date().time
            val delta = currentTime - date
            val elapsedHour = delta / (1000 * 60 * 60)

            Timber.e("$elapsedHour === ${date} === $currentTime")
            return "$elapsedHour h ago"

        } catch (e: Exception){
            e.printStackTrace()
            return ""
        }
    }
    fun shareText(text: String, context: Context){
        Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }.also {
            context.startActivity(
                Intent.createChooser(it, "Share using")
            )
        }
    }


}