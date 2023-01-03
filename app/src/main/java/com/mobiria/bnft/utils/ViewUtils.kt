package com.mobiria.bnft.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.Html
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun <A : Activity> Activity.startNextActivity(activity: Class<A>) {
    Intent(this, activity).also {
        startActivity(it)
    }
}

fun Context.myToast(message:String){
    Toast.makeText(this, message
        , Toast.LENGTH_SHORT).show()
}

fun Context.dialogMsgOk(message:String,title:String="Alert",buttonText:String="OK"){
    MaterialAlertDialogBuilder(this)
        .setTitle("$title")
        .setMessage(message)
        .setPositiveButton(
            "$buttonText"
        ) { dialogInterface, i ->
            dialogInterface.dismiss()
        }
        .show()
}

fun TextView.htmlToString(htmlString: String){
    val formatedString = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(htmlString, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(htmlString)
    }
    this.text = formatedString
}