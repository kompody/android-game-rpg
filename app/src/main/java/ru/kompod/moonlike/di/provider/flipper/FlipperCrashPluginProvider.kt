// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.di.provider.flipper

import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import javax.inject.Inject
import javax.inject.Provider

class FlipperCrashPluginProvider @Inject constructor() : Provider<CrashReporterPlugin> {
    override fun get(): CrashReporterPlugin {
        return CrashReporterPlugin.getInstance()
    }
}