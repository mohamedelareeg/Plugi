package com.plugi.plugi.core.utilities

import android.util.Patterns
import android.widget.EditText
import com.plugi.plugi.R

object ValidationLayer {

    fun validateEmpty(editText: EditText): Boolean {
        return if (editText.text.toString().isEmpty()) {
            editText.error = editText.context.getString(R.string.required)
            false
        } else {
            true
        }
    }

    fun validateEmail(editText: EditText): Boolean {
        return if (editText.text.toString().isEmpty()) {
            editText.error = editText.context.getString(R.string.required)
            false
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(editText.text.toString()).matches()) {
                editText.error = editText.context.getString(R.string.error_mail)
                false
            } else {
                true

            }
        }
    }

    fun validateWebSite(editText: EditText): Boolean {
        return if (editText.text.toString().isEmpty()) {
            editText.error = editText.context.getString(R.string.required)
            false
        } else {
            if (!Patterns.WEB_URL.matcher(editText.text.toString()).matches()) {
                editText.error = editText.context.getString(R.string.error_web)
                false
            } else {
                true

            }
        }
    }

    fun validatePhone(editText: EditText): Boolean {
        return if (editText.text.toString().isEmpty()) {
            editText.error = editText.context.getString(R.string.required)
            false
        } else {
            if (!Patterns.PHONE.matcher(editText.text.toString()).matches()) {
                editText.error = editText.context.getString(R.string.error_phone)
                false
            } else {
                true

            }
        }
    }

    fun validateLength(editText: EditText): Boolean {
        return if (editText.text.toString().isEmpty()) {
            editText.error = editText.context.getString(R.string.required)
            false
        } else {
            if (editText.text.toString().length < 5) {
                editText.error = editText.context.getString(R.string.error_short)
                false
            } else {
                true

            }
        }
    }

    fun validateMatch(second: EditText, first: EditText): Boolean {
        return if (second.text.toString().isEmpty()) {
            second.error = second.context.getString(R.string.required)
            false
        } else {
            if (second.text.toString() != first.text.toString()) {
                second.error = second.context.getString(R.string.error_dismach)
                false
            } else {
                true
            }
        }
    }

}