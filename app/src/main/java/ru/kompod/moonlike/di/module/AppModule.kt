// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.di.module

import android.content.Context
import android.content.res.Resources
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.reactivex.disposables.CompositeDisposable
import ru.kompod.moonlike.data.repository.PreferencesRepository
import ru.kompod.moonlike.di.provider.CompositeDisposableProvider
import ru.kompod.moonlike.di.provider.GsonProvider
import ru.kompod.moonlike.di.provider.PicassoProvider
import ru.kompod.moonlike.domain.repository.IPreferencesRepository
import ru.kompod.moonlike.utils.ResourceDelegate
import ru.kompod.moonlike.utils.eventbus.AppEventBus
import ru.kompod.moonlike.utils.extensions.toothpick.bind
import toothpick.config.Module

class AppModule(appContext: Context) : Module() {
    init {
        bind<Context>().toInstance(appContext)
        bind<Resources>().toInstance(appContext.resources)
        bind<ResourceDelegate>().singleton()
        bind<AppEventBus>().singleton()
        bind<CompositeDisposable>().toProvider(CompositeDisposableProvider::class.java)
        bind<Gson>().toProvider(GsonProvider::class.java)
            .providesSingleton()
        bind<Picasso>().toProvider(PicassoProvider::class.java)
            .providesSingleton()

        bind<IPreferencesRepository>().to(PreferencesRepository::class.java)
            .singleton()
    }
}