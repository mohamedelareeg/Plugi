package com.plugi.domain.core.ext

import java.text.SimpleDateFormat
import java.util.*

fun Date.toTimeAmPm(): String {
    val format = SimpleDateFormat("hh:mm a", Locale.US)
    return format.format(this)
}