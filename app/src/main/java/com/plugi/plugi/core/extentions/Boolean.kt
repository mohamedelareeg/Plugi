package com.plugi.plugi.core.extentions

fun Boolean.toInt(): Int {
    return if (this) 1 else 0
}
fun Boolean.toIsAvailable(): String {
    return if (this) "available" else "not_available"
}