// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.retrofit

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody

fun Any.toRequestBody(mediaType: String = "text/plain"): RequestBody {
    return this.toString().toRequestBody(mediaType.toMediaTypeOrNull())
}

fun Any.toResponseBody(mediaType: String = "text/plain"): ResponseBody {
    return this.toString().toResponseBody(mediaType.toMediaTypeOrNull())
}
