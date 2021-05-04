// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.toothpick

import toothpick.config.Binding
import toothpick.config.Module

inline fun<reified T> Module.bind(): Binding<T>.CanBeNamed {
    return bind(T::class.java)
}

inline fun moduleOf(block: Module.() -> Unit): Module {
    return object: Module(){}.apply(block)
}