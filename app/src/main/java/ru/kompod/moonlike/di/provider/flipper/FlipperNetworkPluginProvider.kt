// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.di.provider.flipper

import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import javax.inject.Inject
import javax.inject.Provider

class FlipperNetworkPluginProvider @Inject constructor() : Provider<NetworkFlipperPlugin> {
    override fun get(): NetworkFlipperPlugin {
        return NetworkFlipperPlugin()
    }
}