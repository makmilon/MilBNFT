package com.mobiria.bnft.utils

import android.app.Activity
import android.content.Context
import android.util.Patterns
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.mobiria.bnft.R
import java.util.regex.Pattern

object Validation {

    private val specialChars = Pattern.compile("[$=\\\\|/<>^*%]")
    private val alphanumeric = Pattern.compile("[a-zA-Z0-9]*")
    private val namespecialChars = Pattern.compile("^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$")


    fun checkIsEmpty(vararg editTexts: TextView): Boolean {
        var isEmpty = false
        for (e in editTexts) {
            if (e.text.toString().trim { it <= ' ' }.isEmpty()) {
                isEmpty = true
                e.error = e.resources.getString(R.string.err_empty)
                e.requestFocus()
            } else {
                e.error = null
            }
        }
        return isEmpty
    }

    fun isAgree(activity: Activity, checkBox: CheckBox): Boolean {
        return if (checkBox.isChecked) {
            true
        } else {
            Toast.makeText(
                activity,
                activity.resources.getString(R.string.pleaseagree),
                Toast.LENGTH_SHORT
            ).show()
            false
        }
    }

    fun checkIsNull(e: String): Boolean {
        var isEmpty = false
        if (e.isEmpty() || e == "") {
            isEmpty = true
        }
        return isEmpty
    }

    fun checkIsAnEmail(editText: TextView): Boolean {
//        if(!checkIsEmpty(editText)) {
        if (Patterns.EMAIL_ADDRESS.matcher(editText.text.toString().trim { it <= ' ' }).matches()) {
            editText.error = null
            return true
        } else editText.error = editText.resources.getString(R.string.err_email_valid)
        //        }
        return false
    }

    fun checkIsAnEmail(email: String): Boolean {
        if (Patterns.EMAIL_ADDRESS.matcher(email.trim { it <= ' ' }).matches()) {
            return true
        }
        return false
    }

    fun CheckListEmpty(context: Context, images: List<String?>): Boolean {
        var isEmpty = false
        if (images.size == 0) {
            isEmpty = true
            Toast.makeText(
                context,
                context.resources.getString(R.string.uploadError),
                Toast.LENGTH_SHORT
            ).show()
        } else {
        }
        return isEmpty
    }


    fun isAValidMobile(editText: TextView): Boolean {
        if (Patterns.PHONE.matcher(editText.text.toString()).matches()) {
            editText.error = null
            return true
        }
        editText.error = editText.resources.getString(R.string.err_mobile_valid)
        return false
    }

    fun isAValidMobile(mobileNumber: String): Boolean {
        if (Patterns.PHONE.matcher(mobileNumber).matches()) {
            return true
        }
        return false
    }

    fun isAValidMobileLength(vararg editText: TextView): Boolean {
//        if (!checkIsHavingSpecialChars(editText)) {
        for (ed in editText) {
            val text = ed.text.toString()
            /* if (text.contains(" ") || text.contains("\t")) {
                    ed.setError(ed.getResources().getString(R.string.err_pass_space));
                    return false;
                }*/return if (text.length <= 5 || text.length > 12) {
                ed.error = ed.resources.getString(R.string.err_mobile_valid)
                false
            } else {
                true
            }
        }
        //            return true;
//        }
        return false
    }

    fun isAValidPassword(vararg editText: TextView): Boolean {
//        if (!checkIsHavingSpecialChars(editText)) {
        for (ed in editText) {
            val text = ed.text.toString()
            /* if (text.contains(" ") || text.contains("\t")) {
                    ed.setError(ed.getResources().getString(R.string.err_pass_space));
                    return false;
                }*/return if (text.length < 8 || text.length > 10) {
                ed.error = ed.resources.getString(R.string.error_password)
                false
            } else {
                true
            }
        }
        //            return true;
//        }
        return false
    }

    fun isAValidDescription(vararg editText: TextView): Boolean {

        for (ed in editText) {
            val text = ed.text.toString()
            return if (text.length < 10 || text.length > 10000) {
                ed.error = ed.resources.getString(R.string.err_description_length)
                false
            } else {
                true
            }
        }
        return false
    }

    fun checkIsHavingSpecialChars(vararg editTexts: TextView): Boolean {
        var contains = false
        for (e in editTexts) {
            if (specialChars.matcher(e.text.toString()).find()) {
                contains = true
                e.error = e.resources.getString(R.string.err_special_characters)
            }
        }
        return contains
    }

    fun isAvalidName(editText: TextView, context: Context?): Boolean {
//        boolean isValid=editText.getText().toString().matches("[a-zA-Z0-9 ]*");
        val isValid = namespecialChars.matcher(editText.text.toString()).find()
        if (!isValid) {
            // Utilities.showDismisAlert(editText.getContext(),context.getResources().getString(R.string.message),editText.getResources().getString(R.string.err_special_characters),context.getResources().getString(R.string.ok),null);
//            editText.setError(editText.getResources().getString(R.string.err_special_characters));
        } else {
            editText.error = null
        }
        return isValid
    }

    fun isMatchText(editText: EditText, context: Context?): Boolean {
//        boolean isValid=editText.getText().toString().matches("[a-zA-Z0-9 ]*");
        val isValid = alphanumeric.matcher(editText.text.toString()).find()
        if (!isValid) {
            //Utilities.showDismisAlert(editText.getContext(),context.getResources().getString(R.string.message),editText.getResources().getString(R.string.err_alpha_numeric),context.getResources().getString(R.string.ok),null);
//            editText.setError(editText.getResources().getString(R.string.err_special_characters));
        } else {
            editText.error = null
        }
        return isValid
    }


    fun isPasswordMatch(password: TextView, retype: TextView): Boolean {
        var check = true
        if (checkIsEmpty(password, retype)) {
            return false
        }
        if (password.text.toString() != retype.text.toString()) {
            retype.error = retype.resources.getString(R.string.err_pass_match)
            check = false
        } else {
            retype.error = null
        }
        return check
    }
}