// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.rxjava

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.functions.*
import io.reactivex.disposables.Disposable
import me.dmdev.rxpm.Action
import java.util.concurrent.TimeUnit

fun<T> Observable<T>.subscribe(subscriber: Action<T>): Disposable {
    return subscribe(subscriber.consumer)
}

fun<T> Observable<T>.takeOne(): Observable<T> {
    return take(1)
}

fun <T> Observable<T>.onNextSwitchMapCompletable(mapper: (T) -> Completable): Observable<T> =
    switchMap {
        mapper(it)
            .andThen(Observable.just(it))
    }

fun <T> Observable<T>.throttle(): Observable<T> = throttleFirst(500L, TimeUnit.MILLISECONDS)

data class Multi5<T1, T2, T3, T4, T5>(val t1: T1, val t2: T2, val t3: T3, val t4: T4, val t5: T5)

fun <T1, T2, T3, T4, T5> combineLatest(
        observable1: Observable<T1>,
        observable2: Observable<T2>,
        observable3: Observable<T3>,
        observable4: Observable<T4>,
        observable5: Observable<T5>
): Observable<Multi5<T1, T2, T3, T4, T5>> =
        Observable.combineLatest(
                observable1,
                observable2,
                observable3,
                observable4,
                observable5,
                Function5 { t1, t2, t3, t4, t5 -> Multi5(t1, t2, t3, t4, t5) })
