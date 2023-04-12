package com.plugi.plugi.core.extentions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.widget.Toast
import com.kaopiz.kprogresshud.KProgressHUD
import android.net.Uri
import com.plugi.plugi.R
import java.io.Serializable


inline fun <reified T : Activity> Context.sStartActivity(
    vararg params: Pair<String, Any?>
) {
    val intent = Intent(this, T::class.java)
    params.forEach {
        when (it.second) {
            is String -> intent.putExtra(it.first, it.second as String)
            is Int -> intent.putExtra(it.first, it.second as Int)
            is Parcelable -> intent.putExtra(it.first, it.second as Parcelable)
        }
    }
    startActivity(intent)
}

inline fun <reified T : Activity> Context.sStartActivityWithClear(
    vararg params: Pair<String, Any?>
) {
    val intent = Intent(this, T::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    params.forEach {
        if (it.second!=null)
        when (it.second) {
            is String -> intent.putExtra(it.first, it.second as String)
            is Int -> intent.putExtra(it.first, it.second as Int)
            is Parcelable -> intent.putExtra(it.first, it.second as Parcelable)
            is Serializable -> intent.putExtra(it.first, it.second as Serializable)
        }
    }
    startActivity(intent)
}
fun Context.toasting(value: String?) {
    value?.let {
        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
    }
}
fun Context.toasting(value: Int) {
    Toast.makeText(this, value, Toast.LENGTH_SHORT).show()
}
fun Context.goToUrl(url:String){
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(browserIntent)
}

fun getLoadingDialog(context: Context): KProgressHUD {
    return KProgressHUD.create(context)
        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
        .setLabel(context.getText(R.string.please_wait).toString())
        .setCancellable(false)
        .setAnimationSpeed(2)
        .setDimAmount(0.5f)

}
