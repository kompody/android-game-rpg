// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.rxjava

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import ru.kompod.moonlike.utils.extensions.kotlin.isNull

fun<T: Any> T.toObservable(): Observable<T> {
    return Observable.just(this)
}

fun<T: Any> T.toSingle(): Single<T> {
    return Single.just(this)
}

inline fun<reified T> T.toMaybe(): Maybe<T> {
    return if (this.isNull()) {
        Maybe.empty()
    } else {
        Maybe.just(this)
    }
}