// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.di.module

import ru.kompod.moonlike.presentation.ScrollToStartEventBus
import ru.kompod.moonlike.utils.extensions.toothpick.bind
import ru.kompod.moonlike.utils.navigation.BottomTabRouter
import ru.kompod.moonlike.utils.navigation.CustomRouter
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import toothpick.config.Module

class BottomTabModule(globalRouter: CustomRouter) : Module() {
    init {
        val cicerone = Cicerone.create(BottomTabRouter(globalRouter))
        bind<BottomTabRouter>().toInstance(cicerone.router)
        bind<NavigatorHolder>().toInstance(cicerone.navigatorHolder)
        bind<ScrollToStartEventBus>().singleton()
    }
}