package com.plugi.domain.core.casesHandler

sealed class Results<out T>{
    data class Success<out T>(val value: T,val message: String?=null) : Results<T>()
    data class Error(val reason: FailureReason, val message: String?=null) : Results<Nothing>()
    enum class FailureReason {
        UNAUTHORIZED,
        USER_SIDE,
        SERVER_SIDE,
        OFFLINE,
        UNKNOWN_REASON
    }
}