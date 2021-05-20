// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.factory.util

import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class TimerDelegate {
    private val emitters: MutableList<TickEmitter> = mutableListOf()
    private var disposable: Disposable? = null

    fun start() {
        disposable?.dispose()
        disposable = Flowable.interval(1, 1, TimeUnit.SECONDS)
            .subscribe { time ->
                emitters.forEach { it.emmit(time) }
            }
    }

    fun stop() {
        emitters.clear()
        disposable?.dispose()
    }

    fun subscribe(emitter: TickEmitter) {
        emitters.remove(emitter)
        emitters.add(emitter)
    }

    fun unsubscribe(emitter: TickEmitter) {
        emitters.remove(emitter)
    }

    interface TickEmitter {
        fun emmit(time: Long)
    }
}