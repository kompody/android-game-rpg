// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.rxpm

import me.dmdev.rxpm.Action

fun <T: Any> Action<T>.accept(value: T) {
    this.consumer.accept(value)
}

fun Action<Unit>.accept() {
    this.consumer.accept(Unit)
}