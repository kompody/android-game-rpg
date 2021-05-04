// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.rxjava

import io.reactivex.functions.Consumer
import io.reactivex.subjects.Subject

fun<T: Any> Subject<T>.asConsumer(): Consumer<T> {
    return Consumer(this::onNext)
}