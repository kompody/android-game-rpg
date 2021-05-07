// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import io.reactivex.Observable
import toothpick.InjectConstructor

@InjectConstructor
class AppLifecycleObservable {
    val observable: Observable<Lifecycle.State> = Observable.create<Lifecycle.State> { emitter ->
        val lifecycle = ProcessLifecycleOwner.get().lifecycle
        val lifecycleObserver = object : LifecycleObserver {
            @OnLifecycleEvent(value = Lifecycle.Event.ON_ANY)
            fun onEvent() {
                emitter.onNext(lifecycle.currentState)
            }
        }

        lifecycle.addObserver(lifecycleObserver)
        emitter.setCancellable { lifecycle.removeObserver(lifecycleObserver) }
    }
        .replay(1)
        .refCount(1)
}