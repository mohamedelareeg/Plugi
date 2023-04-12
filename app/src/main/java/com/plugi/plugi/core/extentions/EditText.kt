package com.plugi.plugi.core.extentions

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText

fun EditText.value(): String {
    return this.text.toString()
}
fun sequenceEditText(n1: EditText, n2: EditText, n3: EditText, n4: EditText, btnContinue: View) {
    n1.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            if (charSequence.length == 1) {
                n2.requestFocus()
            }
        }

        override fun afterTextChanged(editable: Editable) {

        }
    })
    n2.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            if (charSequence.length == 1) {
                n3.requestFocus()
            } else {
                n1.requestFocus()
            }
        }

        override fun afterTextChanged(editable: Editable) {

        }
    })
    n3.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            if (charSequence.length == 1) {
                n4.requestFocus()
            } else {
                n2.requestFocus()
            }
        }

        override fun afterTextChanged(editable: Editable) {

        }
    })
    n4.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            if (charSequence.length == 1) {
                btnContinue.requestFocus()
            } else {
                n3.requestFocus()
            }
        }

        override fun afterTextChanged(editable: Editable) {

        }
    })
}
