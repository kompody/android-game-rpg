// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.eventbus

import ru.terrakok.cicerone.android.support.SupportAppScreen

sealed class Event {
    class SelectBottomTab(val screen: SupportAppScreen) : Event()
}