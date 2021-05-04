// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.network

import okhttp3.Interceptor
import okhttp3.Response
import okio.GzipSource
import ru.kompod.moonlike.domain.entity.error.KompodApiException
import java.nio.charset.Charset
import javax.inject.Inject

class HttpErrorMappingInterceptor @Inject constructor(
    private val errorMapper: HttpErrorMapper
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (response.code in 200..500) {
            responseBody(response)?.also { responseBody ->
                errorMapper.map(responseBody)?.also { errorBody ->
                    when (errorBody.success) {
                        true -> {

                        }
                        false -> {
                            throw KompodApiException.Api.NotSuccess(errorBody.message)
                        }
                    }
                }
            }
        }
        return response
    }

    private fun responseBody(response: Response): String? {
        val responseBody = response.body ?: return null
        val contentLength = responseBody.contentLength()

        if (contentLength == 0L) {
            return null
        }

        val source = responseBody.source()
        source.request(Long.MAX_VALUE) // Buffer the entire body.
        val buffer = source.buffer
        val headers = response.headers

        if ("gzip".equals(headers.get("Content-Encoding"), ignoreCase = true)) {
            GzipSource(buffer.clone()).use { gzippedResponseBody ->
                okio.Buffer().writeAll(gzippedResponseBody)
            }
        }

        val charset: Charset = responseBody.contentType()?.charset(UTF8) ?: UTF8
        return buffer.clone().readString(charset)
    }

    private companion object {
        val UTF8: Charset = Charset.forName("UTF-8")
    }
}