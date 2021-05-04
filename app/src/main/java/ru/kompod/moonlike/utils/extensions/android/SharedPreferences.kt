// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.android

import android.content.SharedPreferences

fun SharedPreferences.getStringOrNull(key: String): String? {
    return getString(key, null)
}