package com.plugi.plugi.core.extentions

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.gone(){
    this.visibility = View.GONE
}

fun View.visible(){
    this.visibility = View.VISIBLE
}
fun View.invisible(){
    this.visibility = View.INVISIBLE
}
//inline fun requiredLoginArea(view: View?, code: () -> Unit) {
//    val isVisitor = AuthorizationUseCases().isVisitor()
//    if (!isVisitor) {
//        code()
//    } else {
//        showLoginSnackBar(view)
//    }
//}
//fun showLoginSnackBar(view: View?) {
//    view?.let {
//        val snack = Snackbar.make(view, R.string.unAuthorize, Snackbar.LENGTH_LONG)
//        snack.setAction(R.string.login) {
//            view.context.sStartActivityWithClear<LoginActivity>()
//        }
//        snack.show()
//    }
//
//}
