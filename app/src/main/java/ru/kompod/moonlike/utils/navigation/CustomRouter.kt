// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.navigation

import ru.kompod.moonlike.utils.navigation.commands.AddOverlayCommand
import ru.kompod.moonlike.utils.navigation.commands.ReplaceHideNavBarCommand
import ru.kompod.moonlike.utils.navigation.commands.ShowBottomSheetCommand
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen

open class CustomRouter : Router() {
    fun navigateTo(screen: Screen, asOverlay: Boolean) {
        if (asOverlay) {
            executeCommands(
                AddOverlayCommand(screen)
            )
        } else {
            super.navigateTo(screen)
        }
    }

    fun showBottomSheet(screen: BottomSheetSupportAppScreen) {
        executeCommands(
            ShowBottomSheetCommand(screen)
        )
    }

    fun replaceScreen(screen: Screen, hideNavBar: Boolean) {
        if (hideNavBar) {
            executeCommands(
                ReplaceHideNavBarCommand(screen)
            )
        } else {
            super.replaceScreen(screen)
        }
    }
}