package com.plugi.plugi.core.extentions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Parcelable
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import com.plugi.domain.core.casesHandler.Results
import com.plugi.plugi.R
import com.plugi.plugi.core.presentationEnums.ViewEmums

fun Activity.hideStatusBar() {
    if (Build.VERSION.SDK_INT >= 21) {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
    }
}

@SuppressLint("RestrictedApi")
//fun Activity.setToolbar(title: String? = "") {
//    val toolbar = findViewById<Toolbar>(R.id.viewToolBar)
//    if (this is AppCompatActivity) {
//        this.setSupportActionBar(toolbar)
//        this.supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
//        this.supportActionBar?.title = " "
//        toolbar.setNavigationOnClickListener { onBackPressed() }
//        val tvToolBarTitle = findViewById<TextView>(R.id.tvToolBarTitle)
//        tvToolBarTitle?.let {
//            it.text = title
//        }
//
//    }
//}

//fun Activity.changeToolbarTitle(title: String? = "") {
//    val tvToolBarTitle = findViewById<TextView>(R.id.tvToolBarTitle)
//    tvToolBarTitle?.let {
//        it.text = title
//    }
//}

inline fun <reified T : Activity> Activity.sStartActivityForResult(
    code: Int = 5,
    vararg params: Pair<String, Any>
) {
    val intent = Intent(this, T::class.java)
    params.forEach {
        when (it.second) {
            is String -> intent.putExtra(it.first, it.second as String)
            is Int -> intent.putExtra(it.first, it.second as Int)
            is Parcelable -> intent.putExtra(it.first, it.second as Parcelable)
        }
    }
    startActivityForResult(intent, code)
}

fun Activity.controlViews(status: ViewEmums = ViewEmums.VIEW_LOADING) {
    val viewContainer = this.findViewById<View>(R.id.viewContainer)
    val viewLoading = this.findViewById<View>(R.id.viewLoading)
    val viewStatus = this.findViewById<View>(R.id.viewStatus)
    when (status) {
        ViewEmums.VIEW_LOADING -> {
            viewContainer?.let { view -> view.visibility = View.GONE }
            viewLoading?.let { view -> view.visibility = View.VISIBLE }
            viewStatus?.let { view -> view.visibility = View.GONE }
        }
        ViewEmums.VIEW_SHOWING -> {
            viewContainer?.let { view -> view.visibility = View.VISIBLE }
            viewLoading?.let { view -> view.visibility = View.GONE }
            viewStatus?.let { view -> view.visibility = View.GONE }
        }
        else -> {
            viewContainer?.let { view -> view.visibility = View.GONE }
            viewLoading?.let { view -> view.visibility = View.GONE }
            viewStatus?.let { view -> view.visibility = View.VISIBLE }
            val ivStatusImage = this.findViewById<ImageView>(R.id.ivStatusImage)
            val tvStatusText = this.findViewById<TextView>(R.id.tvStatusText)
            val tvStatusMainText = this.findViewById<TextView>(R.id.tvStatusMainText)

            when (status) {
                ViewEmums.VIEW_ERROR -> {
                    ivStatusImage.setImageResource(R.drawable.ic_logo_splash)
                    tvStatusText.text = getString(R.string.some_thing_wrong_info)
                    tvStatusMainText.text = getString(R.string.some_thing_wrong)
                }
                ViewEmums.VIEW_EMPTY -> {
                    ivStatusImage.setImageResource(R.drawable.ic_logo_splash)
                    tvStatusText.text = getString(R.string.there_no_data_info)
                    tvStatusMainText.text = getString(R.string.there_no_data)

                }
                ViewEmums.VIEW_OFFLINE -> {
//                    btnTryAgain.visible()
                    ivStatusImage.setImageResource(R.drawable.ic_logo_splash)
                    tvStatusText.text = getString(R.string.offline_info)
                    tvStatusMainText.text = getString(R.string.offlineMain)

                }
                else -> {
                }
            }
        }

    }
}
//
fun <T> Activity.controlViewsResponse(result: Results<T>) {
    when (result) {
        is Results.Success -> {
            result.value?.let { returnedValue ->
                controlViews(ViewEmums.VIEW_SHOWING)
            }
        }
        is Results.Error -> {
            when (result.reason) {
                Results.FailureReason.USER_SIDE -> controlViews(ViewEmums.VIEW_ERROR)
                Results.FailureReason.SERVER_SIDE -> controlViews(ViewEmums.VIEW_ERROR)
                Results.FailureReason.OFFLINE -> controlViews(ViewEmums.VIEW_OFFLINE)
                Results.FailureReason.UNKNOWN_REASON -> controlViews(ViewEmums.VIEW_ERROR)
                Results.FailureReason.UNAUTHORIZED -> {
                    controlViews(ViewEmums.VIEW_ERROR)
//                    AuthorizationUseCases().logOut()
                    // TODO: 03/09/2020 change Authorization
                }

            }
        }
    }
}


fun Activity.openKeyboard(view: View) {
    view.post {
        view.requestFocus()
        val igor: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        igor.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

}
//fun <T> Activity.controlLoadingResponse(result: Results<T>) {
//    when (result) {
//        is Results.Success -> toasting(result.message)
//        is Results.Error -> {
//            when (result.reason) {
//                Results.FailureReason.UNAUTHORIZED -> {
//                    toasting(R.string.unAuthorize)
////                    AuthorizationUseCases().logOut()
//                    sStartActivityWithClear<SplashActivity>()
//                }
//                Results.FailureReason.USER_SIDE -> {
//                    toasting(result.message)
//                }
//                Results.FailureReason.SERVER_SIDE -> {
//                    toasting(R.string.there_error)
//                }
//                Results.FailureReason.OFFLINE -> {
//                    toasting(R.string.offline)
//                }
//                Results.FailureReason.UNKNOWN_REASON -> {
//                    toasting(R.string.there_error)
//                }
//            }
//        }
//    }
//}

