// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.di.provider

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import toothpick.InjectConstructor
import javax.inject.Provider

@InjectConstructor
class GsonProvider : Provider<Gson> {
    override fun get(): Gson {
        return GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .serializeNulls()
            .create()
    }
}