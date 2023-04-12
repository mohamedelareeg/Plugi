package com.plugi.plugi.core.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.kaopiz.kprogresshud.KProgressHUD
import com.plugi.plugi.core.extentions.getLoadingDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job


abstract class BaseFragment : Fragment() {

    private var loadingDialog: KProgressHUD? = null
    private val job = Job()
    val scopeMain= CoroutineScope(job + Dispatchers.Main)
    val scopeIO = CoroutineScope(job + Dispatchers.IO)
    @LayoutRes
    protected abstract fun layoutRes(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(layoutRes(), container, false)
        return rootView
    }

    fun attachContextToLoadingDialog(context: Context?) {
        context?.let {
            loadingDialog = getLoadingDialog(it)

        }
    }
    override fun onDestroy() {
        super.onDestroy()
        loadingDialog = null

    }
    fun showLoadingDialog() {

        loadingDialog?.show()

    }

    fun dismissLoadingDialog() {
        loadingDialog?.dismiss()

    }
}
