// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.kotlin

import java.lang.RuntimeException
import java.text.SimpleDateFormat
import java.util.*

fun calendarOf(date: Date): Calendar {
    return Calendar.getInstance().apply {
        time = date
    }
}

fun calendarOf(millis: Long): Calendar {
    return Calendar.getInstance().apply {
        time = Date(millis)
    }
}

fun Date.toString(pattern: String): String {
    return SimpleDateFormat(pattern, Locale.getDefault()).format(this)
}

fun String.toDate(pattern: String): Date {
    return SimpleDateFormat(pattern, Locale.getDefault()).parse(this) ?: throw RuntimeException("Can't convert $this to Date")
}