// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.rxjava

import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import io.reactivex.functions.Function4
import io.reactivex.functions.Function6

fun <T1, T2> combineSingle(single1: Single<T1>, single2: Single<T2>) =
    Single.zip(
        single1,
        single2,
        BiFunction<T1, T2, Pair<T1, T2>> { t1, t2 -> t1 to t2 })

fun <T1, T2, T3> combineSingle(
    single1: Single<T1>,
    single2: Single<T2>,
    single3: Single<T3>
): Single<Triple<T1, T2, T3>> =
    Single.zip(
        single1,
        single2,
        single3,
        Function3 { t1, t2, t3 -> Triple(t1, t2, t3) })

fun <T1, T2, T3, T4> combineSingle(
    single1: Single<T1>,
    single2: Single<T2>,
    single3: Single<T3>,
    single4: Single<T4>
): Single<Quad<T1, T2, T3, T4>> =
    Single.zip(
        single1,
        single2,
        single3,
        single4,
        Function4 { t1, t2, t3, t4 -> Quad(t1, t2, t3, t4) })

fun <T1, T2, T3, T4, T5, T6> combineSingle(
    single1: Single<T1>,
    single2: Single<T2>,
    single3: Single<T3>,
    single4: Single<T4>,
    single5: Single<T5>,
    single6: Single<T6>
): Single<Multi6<T1, T2, T3, T4, T5, T6>> =
    Single.zip(
        single1,
        single2,
        single3,
        single4,
        single5,
        single6,
        Function6 { t1, t2, t3, t4, t5, t6 -> Multi6(t1, t2, t3, t4, t5, t6) })