// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation

import io.reactivex.Observable
import io.reactivex.functions.Action
import io.reactivex.subjects.PublishSubject
import toothpick.InjectConstructor

@InjectConstructor
class BottomTabReselectionEventBus : Action {
    private val publisher = PublishSubject.create<Unit>()

    val observable: Observable<Unit> = publisher.share()

    override fun run() {
        publisher.onNext(Unit)
    }
}