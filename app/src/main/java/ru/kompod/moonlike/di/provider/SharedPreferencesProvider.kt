// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.di.provider

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Provider

class SharedPreferencesProvider @Inject constructor(
    private val context: Context
): Provider<SharedPreferences> {
    override fun get(): SharedPreferences {
        val name = context.packageName + "_preferences"
        return context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }
}