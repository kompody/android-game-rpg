// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.di.provider.network

import ru.kompod.moonlike.utils.factory.spawner.SpawnDelegate
import ru.kompod.moonlike.utils.factory.util.TimerDelegate
import ru.kompod.moonlike.domain.repository.IAssetRepository
import ru.kompod.moonlike.utils.factory.util.EventBusDelegate
import javax.inject.Inject
import javax.inject.Provider

class SpawnDelegateProvider @Inject constructor(
    private val timerDelegate: TimerDelegate,
    private val assetRepository: IAssetRepository,
    private val eventBusDelegate: EventBusDelegate
) : Provider<SpawnDelegate> {
    override fun get(): SpawnDelegate = SpawnDelegate(timerDelegate, assetRepository, eventBusDelegate)
}