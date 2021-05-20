// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.di.module

import ru.kompod.moonlike.di.provider.network.EventBusDelegateProvider
import ru.kompod.moonlike.di.provider.network.SpawnDelegateProvider
import ru.kompod.moonlike.di.provider.network.TimerDelegateProvider
import ru.kompod.moonlike.presentation.BottomTabReselectionEventBus
import ru.kompod.moonlike.utils.InternetState
import ru.kompod.moonlike.utils.extensions.toothpick.bind
import ru.kompod.moonlike.utils.factory.spawner.SpawnDelegate
import ru.kompod.moonlike.utils.factory.util.EventBusDelegate
import ru.kompod.moonlike.utils.factory.util.TimerDelegate
import ru.kompod.moonlike.utils.navigation.CustomRouter
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import toothpick.config.Module

class UiModule : Module() {
    init {
        val cicerone = Cicerone.create(CustomRouter())
        bind<CustomRouter>().toInstance(cicerone.router)
        bind<NavigatorHolder>().toInstance(cicerone.navigatorHolder)

        bind<BottomTabReselectionEventBus>().singleton()
        bind<InternetState>().singleton()

        bind<TimerDelegate>().toProvider(TimerDelegateProvider::class.java).providesSingleton()
        bind<EventBusDelegate>().toProvider(EventBusDelegateProvider::class.java).providesSingleton()
        bind<SpawnDelegate>().toProvider(SpawnDelegateProvider::class.java).providesSingleton()
    }
}