package com.plugi.plugi.core.extentions

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.plugi.domain.core.casesHandler.Results
import com.plugi.plugi.R
import com.plugi.plugi.core.presentationEnums.ViewEmums
import com.plugi.plugi.feature.splash.SplashActivity

////
fun Fragment.controlViews(status: ViewEmums = ViewEmums.VIEW_LOADING) {
    val rootView = this.view

    val viewContainer = rootView?.findViewById<View>(R.id.viewContainer)
    val viewLoading = rootView?.findViewById<View>(R.id.viewLoading)
    val viewStatus = rootView?.findViewById<View>(R.id.viewStatus)
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
            val ivStatusImage = rootView?.findViewById<ImageView>(R.id.ivStatusImage)
            val tvStatusText = rootView?.findViewById<TextView>(R.id.tvStatusText)
            val tvStatusMainText = rootView?.findViewById<TextView>(R.id.tvStatusMainText)

            when (status) {
                ViewEmums.VIEW_ERROR -> {
                    ivStatusImage?.setImageResource(R.drawable.ic_logo_splash)
                    tvStatusText?.text = getString(R.string.some_thing_wrong_info)
                    tvStatusMainText?.text = getString(R.string.some_thing_wrong)

                }
                ViewEmums.VIEW_EMPTY -> {
                    ivStatusImage?.setImageResource(R.drawable.ic_logo_splash)
                    tvStatusText?.text = getString(R.string.there_no_data_info)
                    tvStatusMainText?.text = getString(R.string.there_no_data)

                }
                ViewEmums.VIEW_OFFLINE -> {
                     ivStatusImage?.setImageResource(R.drawable.ic_logo_splash)
                    tvStatusText?.text = getString(R.string.offline_info)
                    tvStatusMainText?.text = getString(R.string.offlineMain)
                }
                else -> {
                }
            }
        }

    }
}

fun <T> Fragment.controlViewsResponse(result: Results<T>) {
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
                    context?.sStartActivityWithClear<SplashActivity>()
                }

            }
        }
    }
}

fun <T> Fragment.controlLoadingResponse(result: Results<T>) {
    when (result) {
        is Results.Success -> requireContext().toasting(result.message)
        is Results.Error -> {
            when (result.reason) {
                Results.FailureReason.UNAUTHORIZED -> {
                    requireContext().toasting(R.string.unAuthorize)
//                    AuthorizationUseCases().logOut()
//                    context?.sStartActivityWithClear<SplashActivity>()
                }
                Results.FailureReason.USER_SIDE -> {
                    requireContext().toasting(result.message)
                }
                Results.FailureReason.SERVER_SIDE -> {
                    requireContext().toasting(R.string.there_error)
                }
                Results.FailureReason.OFFLINE -> {
                    requireContext().toasting(R.string.offlineMain)
                }
                Results.FailureReason.UNKNOWN_REASON -> {
                    requireContext().toasting(R.string.there_error)
                }
            }
        }
    }
}