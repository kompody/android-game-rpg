// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.toothpick

import ru.kompod.moonlike.utils.extensions.kotlin.isNull
import toothpick.Scope

@JvmOverloads
inline fun<reified T> Scope.getInstance(name: String? = null): T {
    return if (name.isNull()) {
        getInstance(T::class.java)
    } else {
        getInstance(T::class.java, name)
    }
}