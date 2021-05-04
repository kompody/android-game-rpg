// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.di.module

import android.content.Context
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.navigation.NavigationFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import ru.kompod.moonlike.di.provider.flipper.*
import ru.kompod.moonlike.utils.extensions.toothpick.bind
import toothpick.config.Module

class DebugModule(appContext: Context) : Module() {
    init {
        bind<Context>().toInstance(appContext)

        bind<InspectorFlipperPlugin>()
            .toProvider(FlipperInspectorPluginProvider::class.java)
            .providesSingleton()
        bind<NavigationFlipperPlugin>()
            .toProvider(FlipperNavigationPluginProvider::class.java)
            .providesSingleton()
        bind<CrashReporterPlugin>()
            .toProvider(FlipperCrashPluginProvider::class.java)
            .providesSingleton()
        bind<NetworkFlipperPlugin>()
            .toProvider(FlipperNetworkPluginProvider::class.java)
            .providesSingleton()
        bind<SharedPreferencesFlipperPlugin>()
            .toProvider(FlipperSharedPreferencesPluginProvider::class.java)
            .providesSingleton()
        bind<DatabasesFlipperPlugin>()
            .toProvider(FlipperDatabasePluginProvider::class.java)
            .providesSingleton()
        bind<FlipperOkhttpInterceptor>()
            .toProvider(FlipperInterceptorProvider::class.java)
            .providesSingleton()
    }
}