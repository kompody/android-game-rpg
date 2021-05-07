// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.di.provider

import android.content.Context
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import ru.kompod.moonlike.BuildConfig
import ru.kompod.moonlike.di.OkHttpForPicasso
import javax.inject.Inject
import javax.inject.Provider

class PicassoProvider @Inject constructor(
    private val context: Context,
    @OkHttpForPicasso private val okHttpClient: OkHttpClient
) : Provider<Picasso> {
    override fun get(): Picasso = Picasso.Builder(context)
//        .downloader(OkHttp3Downloader(okHttpClient))
        .indicatorsEnabled(BuildConfig.DEBUG)
        .build()
}