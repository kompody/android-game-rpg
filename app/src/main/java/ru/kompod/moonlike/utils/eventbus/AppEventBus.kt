// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.eventbus

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import toothpick.InjectConstructor
import kotlin.reflect.KClass

@InjectConstructor
class AppEventBus : Consumer<Event> {

    private val publisher = PublishSubject.create<Event>()

    override fun accept(event: Event) {
        publisher.onNext(event)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Event> registerForEvent(requiredEvent: KClass<out T>): Observable<out T> {
        return publisher
            .filter { event -> event::class == requiredEvent }
            .map { event -> event as T }
            .share()
    }
}