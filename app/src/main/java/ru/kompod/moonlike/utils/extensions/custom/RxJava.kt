// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.custom

import io.reactivex.*
import ru.kompod.moonlike.domain.usecase.GetInternetStateUseCase
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

fun Completable.retryWhenInternetIsAvailable(internetState: GetInternetStateUseCase): Completable {
    return retryWhen { error ->
        error
            .switchMap { throwable ->
                when (throwable) {
                    is SSLHandshakeException,
                    is SocketTimeoutException,
                    is UnknownHostException -> {
                        internetState
                            .execute()
                            .filter { it }
                            .toFlowable(BackpressureStrategy.DROP)
                            .take(1)
                    }
                    else -> {
                        Flowable.error(throwable)
                    }
                }
            }
    }
}

fun<T: Any> Observable<T>.retryWhenInternetIsAvailable(internetState: GetInternetStateUseCase): Observable<T> {
    return retryWhen { error ->
        error
            .switchMap { throwable ->
                when (throwable) {
                    is UnknownHostException -> {
                        internetState
                            .execute()
                            .filter { it }
                            .take(1)
                    }
                    else -> {
                        Observable.error(throwable)
                    }
                }
            }
    }
}

fun<T: Any> Single<T>.retryWhenInternetIsAvailable(internetState: GetInternetStateUseCase): Single<T> {
    return retryWhen { error ->
        error
            .switchMap { throwable ->
                when (throwable) {
                    is UnknownHostException -> {
                        internetState
                            .execute()
                            .filter { it }
                            .toFlowable(BackpressureStrategy.DROP)
                            .take(1)
                    }
                    else -> {
                        Flowable.error(throwable)
                    }
                }
            }
    }
}

fun<T: Any> Maybe<T>.retryWhenInternetIsAvailable(internetState: GetInternetStateUseCase): Maybe<T> {
    return retryWhen { error ->
        error
            .switchMap { throwable ->
                when (throwable) {
                    is UnknownHostException -> {
                        internetState
                            .execute()
                            .filter { it }
                            .toFlowable(BackpressureStrategy.DROP)
                            .take(1)
                    }
                    else -> {
                        Flowable.error(throwable)
                    }
                }
            }
    }
}