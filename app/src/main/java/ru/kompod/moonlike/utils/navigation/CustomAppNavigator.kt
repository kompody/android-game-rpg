// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.navigation

import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import ru.kompod.moonlike.R
import ru.kompod.moonlike.utils.extensions.android.beginTransaction
import ru.kompod.moonlike.utils.extensions.android.findTopFragment
import ru.kompod.moonlike.utils.extensions.kotlin.castTo
import ru.kompod.moonlike.utils.extensions.kotlin.unsafeCastTo
import ru.kompod.moonlike.utils.navigation.commands.AddOverlayCommand
import ru.kompod.moonlike.utils.navigation.commands.ReplaceHideNavBarCommand
import ru.kompod.moonlike.utils.navigation.commands.ShowBottomSheetCommand
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.Command

class CustomAppNavigator(
    activity: FragmentActivity,
    fragmentManager: FragmentManager = activity.supportFragmentManager,
    containerId: Int
) : SupportAppNavigator(activity, fragmentManager, containerId) {
    override fun applyCommand(command: Command) {
        when (command) {
            is AddOverlayCommand -> addOverlayFragment(command)
            is ReplaceHideNavBarCommand -> hideNavBar(command)
            is ShowBottomSheetCommand -> showBottomSheetFragment(command)
            else -> super.applyCommand(command)
        }
    }

    private fun hideNavBar(command: ReplaceHideNavBarCommand) {
        activity.findViewById<View>(R.id.bottomNavigationView).visibility = View.GONE
        fragmentReplace(command)
    }

    private fun showBottomSheetFragment(command: ShowBottomSheetCommand) {
        val screen = command.screen
        val fragment = createFragment(screen)

        fragmentManager.beginTransaction {
            add(fragment!!, null)
            addToBackStack(screen.screenKey)
            commit()
        }

        localStackCopy.add(screen.screenKey)
    }

    private fun addOverlayFragment(command: AddOverlayCommand) {
        val screen: SupportAppScreen = command.screen.unsafeCastTo()
        val fragment = createFragment(screen)

        fragmentManager.beginTransaction {
            setupFragmentTransaction(
                command,
                fragmentManager.findFragmentById(containerId),
                fragment,
                this
            )

            add(containerId, fragment!!)
            addToBackStack(screen.screenKey)
            commit()
        }

        localStackCopy.add(screen.screenKey)
    }

    override fun fragmentBack() {
        if (localStackCopy.isNotEmpty()) {
            val fragment = fragmentManager.fragments.last()
            if (fragment is DialogFragment) {
                fragment.dismiss()
            }
            fragmentManager.popBackStackImmediate()
            localStackCopy.removeLast()
        } else {
            activityBack()
        }

        /**
         * Уведомляем предыдущий фрагмент. В случае, если текущий фрагмент добавлен через
         * addOverlayFragment, при его закрытии предыдущий фрагмент не уведомляется.
         */
        activity.castTo<FragmentActivity>()?.findTopFragment()?.onResume()
    }

    private fun FragmentActivity.findTopFragment(): Fragment? =
        this.supportFragmentManager.findTopFragment()
}