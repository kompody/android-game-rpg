// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.navigation.NavigationFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import ru.kompod.moonlike.di.DI
import ru.kompod.moonlike.di.module.*
import ru.kompod.moonlike.utils.extensions.toothpick.lazyInjection
import timber.log.Timber
import toothpick.Scope
import toothpick.Toothpick
import toothpick.configuration.Configuration

class App : Application() {

    private val inspectorPlugin by lazyInjection<InspectorFlipperPlugin>()
    private val navigationPlugin by lazyInjection<NavigationFlipperPlugin>()
    private val crashPlugin by lazyInjection<CrashReporterPlugin>()
    private val networkPlugin by lazyInjection<NetworkFlipperPlugin>()
    private val sharedPrefsPlugin by lazyInjection<SharedPreferencesFlipperPlugin>()
    private val databasePlugin by lazyInjection<DatabasesFlipperPlugin>()

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initToothpick()
        initAppScope()
            .let { scope ->
                initFlipper(scope)
            }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initToothpick() {
        if (BuildConfig.DEBUG) {
            Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
        } else {
            Toothpick.setConfiguration(Configuration.forProduction())
        }
    }

    private fun initAppScope(): Scope =
        Toothpick.openScope(DI.APP_SCOPE)
            .installModules(
                AppModule(applicationContext),
                AnalyticsModule(applicationContext),
                DbModule(),
                NetworkModule()
            )

    private fun initFlipper(scope: Scope) {
        if (BuildConfig.DEBUG) {
            scope
                .installModules(DebugModule(applicationContext))
                .let { debugScope ->
                    Toothpick.inject(this, debugScope)
                }

            SoLoader.init(this, false)
            if (FlipperUtils.shouldEnableFlipper(this)) {
                with(AndroidFlipperClient.getInstance(this)) {
                    addPlugin(inspectorPlugin)
                    addPlugin(navigationPlugin)
                    addPlugin(crashPlugin)
                    addPlugin(networkPlugin)
                    addPlugin(sharedPrefsPlugin)
                    addPlugin(databasePlugin)
                    start()
                }
            }
        }
    }
}