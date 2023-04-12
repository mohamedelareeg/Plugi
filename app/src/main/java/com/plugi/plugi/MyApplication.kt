package com.plugi.plugi

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.plugi.domain.Domain


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Domain.integrateWith(this)
    }



}