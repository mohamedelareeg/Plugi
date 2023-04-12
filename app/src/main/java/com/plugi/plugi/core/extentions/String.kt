package com.plugi.plugi.core.extentions

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.isYes(): Boolean {
    return this == "yes"
}

fun String.getDayWeekName(): String {
    val formatter = "yyy-MM-dd"
    val df = SimpleDateFormat(formatter, Locale.US)
    val date = df.parse(this)
    val outFormat = SimpleDateFormat("EE", Locale.US)
    val goal = outFormat.format(date)
    return goal
}

fun String.convertNormalToDate(): Date? {
    val formatter = "yyy-MM-dd"
    val df = SimpleDateFormat(formatter, Locale.US)
    return df.parse(this)
}

fun String.timeToDate(): Date {
    val sdf = SimpleDateFormat("HH:mm:ss", Locale.US)
    var date: Date? = null
    try {
        date = sdf.parse(this)
    } catch (e: ParseException) {
    }

    return date ?: Date()

}

fun String.fullPathToPathForService(): String {

    return this.replace(
        "https://therose-app.com/uploads/services/",
        ""
    )
}

fun String.fullPathToPathForUser(): String {

    return this.replace(
        "https://therose-app.com/uploads/users/files/",
        ""
    )
}