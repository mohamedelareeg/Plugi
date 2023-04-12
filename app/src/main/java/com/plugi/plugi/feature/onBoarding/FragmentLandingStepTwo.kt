package com.plugi.plugi.feature.onBoarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.plugi.plugi.R
import com.plugi.plugi.core.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_landing_one.*

class FragmentLandingStepTwo : BaseFragment() {
    override fun layoutRes(): Int = R.layout.fragment_landing_two
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

    }

    private fun init() {

        (activity as OnBoardingActivity).model?.let {
            rvLandingOne.layoutManager = LinearLayoutManager(requireContext())
            val adapter = LandingTwoAdapter(it.step2)
            rvLandingOne.adapter = adapter
        }
    }

}
