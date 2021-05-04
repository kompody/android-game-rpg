// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.di.provider.flipper

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import javax.inject.Inject
import javax.inject.Provider

class FlipperInterceptorProvider @Inject constructor(
    private val flipperNetworkPlugin: NetworkFlipperPlugin
): Provider<FlipperOkhttpInterceptor> {
    override fun get(): FlipperOkhttpInterceptor {
        return FlipperOkhttpInterceptor(flipperNetworkPlugin)
    }
}