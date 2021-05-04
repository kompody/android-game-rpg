// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.analytics

import timber.log.Timber
import javax.inject.Inject

class LoggingAnalyticsDelegate @Inject constructor() : IAnalyticsService {
    override fun sendEvent(event: AnalyticsEvent) {
        Timber.d("Send event: $event")
    }
}