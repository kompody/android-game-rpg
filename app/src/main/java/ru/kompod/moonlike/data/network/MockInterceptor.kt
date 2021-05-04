// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.network

import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import ru.kompod.moonlike.BuildConfig
import ru.kompod.moonlike.data.network.mock.MockMapper
import ru.kompod.moonlike.utils.extensions.retrofit.toResponseBody
import timber.log.Timber

class MockInterceptor : Interceptor {
    companion object {
        const val SUCCESS_CODE = 200
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.DEBUG) {
            val uri = chain.request().url.toUri().toString()
            val responseString = MockMapper.parseUri(uri)

            Timber.d(uri)

            return chain.proceed(chain.request())
                .newBuilder()
                .code(SUCCESS_CODE)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(responseString.toResponseBody( "application/json"))
                .addHeader("content-type", "application/json")
                .build()
        } else {
            throw IllegalAccessError("MockInterceptor is only meant for Testing Purposes and " +
                    "bound to be used only with DEBUG mode")
        }
    }
}