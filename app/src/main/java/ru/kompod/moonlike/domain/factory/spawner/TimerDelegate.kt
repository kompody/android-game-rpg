// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.factory.spawner

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import ru.kompod.moonlike.utils.extensions.rxjava.io
import ru.kompod.moonlike.utils.extensions.rxjava.ui
import timber.log.Timber
import java.util.concurrent.TimeUnit

class TimerDelegate {
    private val emitters: MutableList<TickEmitter> = mutableListOf()
    private var disposable: Disposable? = null

    fun start() {
        disposable?.dispose()
        disposable = Flowable.interval(1, 1, TimeUnit.SECONDS)
            .subscribe {
                emitters.forEach { it.emmit() }
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
        fun emmit()
    }
}