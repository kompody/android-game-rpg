// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.di.provider.network

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import ru.kompod.moonlike.utils.network.buildOkHttpClient
import javax.inject.Inject
import javax.inject.Provider

class OkHttpForPicassoProvider @Inject constructor(
    private val context: Context
) : Provider<OkHttpClient> {
    override fun get(): OkHttpClient = buildOkHttpClient {
        cache(
            Cache(
                context.cacheDir,
                CACHE_SIZE
            )
        )
    }

    companion object {
        private const val CACHE_SIZE = 10 * 1024 * 1024L
    }
}