// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import toothpick.InjectConstructor

@InjectConstructor
class BottomTabSelectionsEventBus {
    private val publisher = PublishSubject.create<Int>()

    val observable: Observable<Int> = publisher.share()

    fun selectBottomTab(tabId: Int) {
        publisher.onNext(tabId)
    }
}