// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.di.module

import okhttp3.OkHttpClient
import ru.kompod.moonlike.di.OkHttpForPicasso
import ru.kompod.moonlike.di.provider.network.OkHttpForPicassoProvider
import toothpick.config.Module
import ru.kompod.moonlike.utils.extensions.toothpick.bind

class NetworkModule : Module() {
    init {
        bind<OkHttpClient>()
            .withName(OkHttpForPicasso::class.java)
            .toProvider(OkHttpForPicassoProvider::class.java)
            .providesSingleton()
    }
}