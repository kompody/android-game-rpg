// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.analytics

import timber.log.Timber
import toothpick.InjectConstructor

@InjectConstructor
class LoggingAnalyticsDelegate : IAnalyticsService {
    override fun sendEvent(event: AnalyticsEvent) {
        Timber.d("Send event: $event")
    }
}