// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.kotlin

inline fun <reified T> Any?.castTo(): T? {
    return this as? T
}

inline fun <reified T> Any?.unsafeCastTo(): T {
    return this as T
}

inline fun <reified T> T?.isNull(): Boolean {
    return this == null
}

inline fun <reified T> T?.isNotNull(): Boolean {
    return this != null
}

inline fun <reified T> Any?.isTypeOf(): Boolean {
    return this is T
}

