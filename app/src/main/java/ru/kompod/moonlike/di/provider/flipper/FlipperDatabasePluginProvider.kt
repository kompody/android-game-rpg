// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.di.provider.flipper

import android.content.Context
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import javax.inject.Inject
import javax.inject.Provider

class FlipperDatabasePluginProvider @Inject constructor(
    val context: Context
): Provider<DatabasesFlipperPlugin> {
    override fun get(): DatabasesFlipperPlugin {
        return DatabasesFlipperPlugin(context)
    }
}