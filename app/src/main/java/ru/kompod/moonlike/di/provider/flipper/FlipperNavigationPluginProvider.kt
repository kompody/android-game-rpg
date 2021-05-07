// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.di.provider.flipper

import com.facebook.flipper.plugins.navigation.NavigationFlipperPlugin
import toothpick.InjectConstructor
import javax.inject.Provider

@InjectConstructor
class FlipperNavigationPluginProvider : Provider<NavigationFlipperPlugin> {
    override fun get(): NavigationFlipperPlugin {
        return NavigationFlipperPlugin.getInstance()
    }
}