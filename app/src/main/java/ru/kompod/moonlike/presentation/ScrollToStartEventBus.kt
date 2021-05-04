// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation

import io.reactivex.Observable
import io.reactivex.functions.Action
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class ScrollToStartEventBus @Inject constructor() : Action {
    private val publisher = PublishSubject.create<Unit>()

    val observable: Observable<Unit> = publisher.share()

    override fun run() {
        publisher.onNext(Unit)
    }
}