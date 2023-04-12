package com.plugi.domain.core.ext

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


fun String.toLongDate(): Long {
    return try {
        val formatter = "yyy-MM-dd hh:mm:ss"
        val df = SimpleDateFormat(formatter, Locale.US)
        val date = df.parse(this)
        date?.let {
            return (it.time / 1000L)
        } ?: return 0
    } catch (e: Exception) {
        0
    }
}

fun getCurrentDateInYYY_MM_DD(): String {
    val c = Calendar.getInstance().time
    val df = SimpleDateFormat("yyy-MM-dd HH:mm:ss", Locale.US)
    return df.format(c)
}