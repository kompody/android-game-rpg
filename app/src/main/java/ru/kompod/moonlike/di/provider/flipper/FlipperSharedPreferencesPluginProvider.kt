// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.di.provider.flipper

import android.content.Context
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import javax.inject.Inject
import javax.inject.Provider

class FlipperSharedPreferencesPluginProvider @Inject constructor(
    private val context: Context
): Provider<SharedPreferencesFlipperPlugin> {
    override fun get(): SharedPreferencesFlipperPlugin {
        return SharedPreferencesFlipperPlugin(context)
    }
}