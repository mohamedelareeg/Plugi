package com.plugi.domain.core

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


fun String.generateRequestBody(): RequestBody {
    return this.toRequestBody("text/plain".toMediaTypeOrNull())
}

fun File.generateRequestBodyForFiles(): RequestBody {
//    return RequestBody.create(MediaType.parse("*/*"), file)
    return this.asRequestBody("*/*".toMediaTypeOrNull())
}

fun File.generateRequestBodyForImage(): RequestBody {
//    return RequestBody.create(MediaType.parse("image/jpeg"), file)
    return this.asRequestBody("image/jpeg".toMediaTypeOrNull())
}
