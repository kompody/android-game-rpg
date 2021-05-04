// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.di.provider.flipper

import com.facebook.flipper.plugins.navigation.NavigationFlipperPlugin
import javax.inject.Inject
import javax.inject.Provider

class FlipperNavigationPluginProvider @Inject constructor(): Provider<NavigationFlipperPlugin> {
    override fun get(): NavigationFlipperPlugin {
        return NavigationFlipperPlugin.getInstance()
    }
}