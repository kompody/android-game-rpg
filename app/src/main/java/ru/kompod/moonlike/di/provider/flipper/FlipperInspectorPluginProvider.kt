// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.di.provider.flipper

import android.content.Context
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import javax.inject.Inject
import javax.inject.Provider

class FlipperInspectorPluginProvider @Inject constructor(
    private val context: Context
): Provider<InspectorFlipperPlugin> {
    override fun get(): InspectorFlipperPlugin {
        return InspectorFlipperPlugin(context, DescriptorMapping.withDefaults())
    }
}