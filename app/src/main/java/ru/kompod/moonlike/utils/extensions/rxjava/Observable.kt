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

fun <T> Observable<T>.takeOne(): Observable<T> {
    return take(1)
}

fun <T> Observable<T>.onNextSwitchMapCompletable(mapper: (T) -> Completable): Observable<T> =
    switchMap {
        mapper(it)
            .andThen(Observable.just(it))
    }

fun <T> Observable<T>.throttle(): Observable<T> = throttleFirst(500L, TimeUnit.MILLISECONDS)

fun <T1, T2> zip(observable1: Observable<T1>, observable2: Observable<T2>) =
    Observable.zip(
        observable1,
        observable2,
        BiFunction<T1, T2, Pair<T1, T2>> { t1, t2 -> t1 to t2 })

fun <T1, T2> combineLatest(observable1: Observable<T1>, observable2: Observable<T2>) =
    Observable.combineLatest(
        observable1,
        observable2,
        BiFunction<T1, T2, Pair<T1, T2>> { t1, t2 -> t1 to t2 })

fun <T1, T2, T3> combineLatest(
    observable1: Observable<T1>,
    observable2: Observable<T2>,
    observable3: Observable<T3>
): Observable<Triple<T1, T2, T3>> =
    Observable.combineLatest(
        observable1,
        observable2,
        observable3,
        Function3 { t1, t2, t3 -> Triple(t1, t2, t3) })

data class Quad<T1, T2, T3, T4>(val t1: T1, val t2: T2, val t3: T3, val t4: T4)

fun <T1, T2, T3, T4> combineLatest(
    observable1: Observable<T1>,
    observable2: Observable<T2>,
    observable3: Observable<T3>,
    observable4: Observable<T4>
): Observable<Quad<T1, T2, T3, T4>> =
    Observable.combineLatest(
        observable1,
        observable2,
        observable3,
        observable4,
        Function4 { t1, t2, t3, t4 -> Quad(t1, t2, t3, t4) })


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

data class Multi6<T1, T2, T3, T4, T5, T6>(val t1: T1, val t2: T2, val t3: T3, val t4: T4, val t5: T5, val t6: T6)

fun <T1, T2, T3, T4, T5, T6> combineLatest(
    observable1: Observable<T1>,
    observable2: Observable<T2>,
    observable3: Observable<T3>,
    observable4: Observable<T4>,
    observable5: Observable<T5>,
    observable6: Observable<T6>
): Observable<Multi6<T1, T2, T3, T4, T5, T6>> =
    Observable.combineLatest(
        observable1,
        observable2,
        observable3,
        observable4,
        observable5,
        observable6,
        Function6 { t1, t2, t3, t4, t5, t6 -> Multi6(t1, t2, t3, t4, t5, t6) })

data class Multi7<T1, T2, T3, T4, T5, T6, T7>(val t1: T1, val t2: T2, val t3: T3, val t4: T4, val t5: T5, val t6: T6, val t7: T7)

fun <T1, T2, T3, T4, T5, T6, T7> combineLatest(
    observable1: Observable<T1>,
    observable2: Observable<T2>,
    observable3: Observable<T3>,
    observable4: Observable<T4>,
    observable5: Observable<T5>,
    observable6: Observable<T6>,
    observable7: Observable<T7>
): Observable<Multi7<T1, T2, T3, T4, T5, T6, T7>> =
    Observable.combineLatest(
        observable1,
        observable2,
        observable3,
        observable4,
        observable5,
        observable6,
        observable7,
        Function7 { t1, t2, t3, t4, t5, t6, t7 -> Multi7(t1, t2, t3, t4, t5, t6, t7) })