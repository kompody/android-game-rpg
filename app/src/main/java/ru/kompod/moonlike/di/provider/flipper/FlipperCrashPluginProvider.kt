// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.di.provider.flipper

import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import toothpick.InjectConstructor
import javax.inject.Provider

@InjectConstructor
class FlipperCrashPluginProvider : Provider<CrashReporterPlugin> {
    override fun get(): CrashReporterPlugin {
        return CrashReporterPlugin.getInstance()
    }
}