package com.plugi.plugi.core.extentions

fun Int.isZero(): Boolean {
    return this==0
}

fun Int.isUndefine(): Boolean {
    return this==-1
}

fun Int.toBool(): Boolean {
    return this==1
}

