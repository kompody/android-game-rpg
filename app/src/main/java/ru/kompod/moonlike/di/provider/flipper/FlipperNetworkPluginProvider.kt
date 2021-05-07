// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.di.provider.flipper

import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import toothpick.InjectConstructor
import javax.inject.Provider

@InjectConstructor
class FlipperNetworkPluginProvider : Provider<NetworkFlipperPlugin> {
    override fun get(): NetworkFlipperPlugin {
        return NetworkFlipperPlugin()
    }
}