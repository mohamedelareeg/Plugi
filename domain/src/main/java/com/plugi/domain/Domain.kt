package com.plugi.domain

import android.app.Application

object Domain {
    internal lateinit var application: Application private set
    fun integrateWith(application: Application) {
        this.application = application
    }
}