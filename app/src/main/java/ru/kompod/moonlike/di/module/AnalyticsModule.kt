// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.di.module

import android.content.Context
import ru.kompod.moonlike.BuildConfig
import ru.kompod.moonlike.data.analytics.AnalyticsDelegate
import ru.kompod.moonlike.data.analytics.IAnalyticsService
import ru.kompod.moonlike.data.analytics.LoggingAnalyticsDelegate
import ru.kompod.moonlike.utils.extensions.toothpick.bind
import toothpick.config.Module

class AnalyticsModule(context: Context) : Module() {
    init {
        val analyticsServices = if (BuildConfig.DEBUG) {
            setOf(LoggingAnalyticsDelegate())
        } else {
            setOf()
        }

        bind<Set<IAnalyticsService>>().toInstance(analyticsServices)
        bind<AnalyticsDelegate>().singleton()
    }
}