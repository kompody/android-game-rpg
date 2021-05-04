// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.kotlin

fun ByteArray.toHexString(): String = joinToString("") { "%02x".format(it) }