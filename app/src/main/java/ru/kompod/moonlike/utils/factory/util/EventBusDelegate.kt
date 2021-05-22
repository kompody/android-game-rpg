// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.factory.util

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class EventBusDelegate {

    private val commandPublisher = PublishSubject.create<Command>()

    fun observeCommand(): Observable<Command> = commandPublisher
        .delay(100, TimeUnit.MICROSECONDS)
        .retry()

    fun action(action: Action) {
        when (action) {
            is Action.RecoveryHealCharacterCommand ->
                Command.RecoveryHealCharacterCommand(action.hp, action.sp)
            is Action.RefreshMapCommand ->
                Command.RefreshMapCommand(action.mapId)
            is Action.KillMonsterOnMapCommand ->
                Command.KillMonsterOnMapCommand(action.mapId, action.monster)
            else -> null
        }?.also { commandPublisher.onNext(it) }
    }
}