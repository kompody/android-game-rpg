// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.kompod.moonlike.BuildConfig
import ru.kompod.moonlike.utils.CONNECTION_READ_WRITE_TIMEOUT
import ru.kompod.moonlike.utils.CONNECTION_TIMEOUT
import java.util.concurrent.TimeUnit

fun buildOkHttpClient(
    builderAction: OkHttpClient.Builder.() -> Unit = {}
): OkHttpClient
        = with(OkHttpClient.Builder()) {
    connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
    readTimeout(CONNECTION_READ_WRITE_TIMEOUT, TimeUnit.SECONDS)
    writeTimeout(CONNECTION_READ_WRITE_TIMEOUT, TimeUnit.SECONDS)

    if (BuildConfig.DEBUG) {
        addNetworkInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
    }

    builderAction()
    build()
}