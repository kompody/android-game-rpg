// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.analytics

import javax.inject.Inject

class AnalyticsDelegate @Inject constructor(
    private val services: Set<IAnalyticsService>
) : IAnalyticsService {
    override fun sendEvent(event: AnalyticsEvent) {
        services.forEach { it.sendEvent(event) }
    }
}