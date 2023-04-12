package com.plugi.plugi.core.extentions

import java.text.SimpleDateFormat
import java.util.*

fun Date.toTimeAmPm(): String? {
    val systemLanguage = "en"
//    val systemLanguage = MutualUseCaseImpl().getSystemLanguage()
    val format = SimpleDateFormat("hh:mm a",Locale(systemLanguage))
    return format.format(this)
}