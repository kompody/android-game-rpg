// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.di.provider.network

import ru.kompod.moonlike.utils.factory.util.EventBusDelegate
import ru.kompod.moonlike.utils.factory.util.TimerDelegate
import toothpick.InjectConstructor
import javax.inject.Provider

@InjectConstructor
class EventBusDelegateProvider : Provider<EventBusDelegate> {
    override fun get(): EventBusDelegate = EventBusDelegate()
}