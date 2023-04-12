package com.plugi.plugi.feature.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import com.plugi.plugi.R
import com.plugi.plugi.core.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), TabLayout.OnTabSelectedListener {
    override fun layoutResource(): Int =R.layout.activity_login
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tlSelection.addOnTabSelectedListener(this)
        changeFragment(RegisterFragment(), false)
        ivBack.setOnClickListener {
            onBackPressed()

        }

    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        when(tab?.position){
            0->{
                changeFragment(RegisterFragment(), false)
            }
            1->{
                changeFragment(LoginFragment(), false)
            }
        }
    }

    private fun changeFragment(fragment: Fragment?, addToStack: Boolean) {
        if (fragment != null) {
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            ft.setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_right,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            //    ft.addToBackStack(tag);
            ft.replace(R.id.viewContent, fragment, fragment::class.java.name)
            //   ft.commit();
            if (addToStack) {
                ft.addToBackStack(null)
            } else {
                val fm: FragmentManager = supportFragmentManager
                for (i in 0 until fm.getBackStackEntryCount()) {
                    fm.popBackStack()
                }
            }
            ft.commitAllowingStateLoss()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

}