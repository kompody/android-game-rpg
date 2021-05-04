// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.navigation

import ru.terrakok.cicerone.Screen

class BottomTabRouter(
    private val globalRouter: CustomRouter
) : CustomRouter() {
    fun newRootFlow(screen: Screen, asOverlay: Boolean = false) {
        globalRouter.navigateTo(screen, asOverlay)
    }

    fun newRootBottomSheet(screen: BottomSheetSupportAppScreen) {
        globalRouter.showBottomSheet(screen)
    }
}