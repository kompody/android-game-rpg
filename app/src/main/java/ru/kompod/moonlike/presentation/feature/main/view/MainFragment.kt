// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.main.view

import android.view.MenuItem
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import com.jakewharton.rxbinding3.material.itemSelections
import kotlinx.android.synthetic.main.fragment_main.*
import me.dmdev.rxpm.bindTo
import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.BaseFragment
import ru.kompod.moonlike.presentation.feature.BottomTabSelectionsEventBus
import ru.kompod.moonlike.presentation.feature.main.pm.MainPresentationModel
import ru.kompod.moonlike.utils.extensions.android.beginTransaction
import ru.kompod.moonlike.utils.extensions.kotlin.castTo
import ru.kompod.moonlike.utils.extensions.toothpick.bind
import ru.kompod.moonlike.utils.extensions.toothpick.getInstance
import ru.kompod.moonlike.utils.extensions.toothpick.moduleOf
import ru.terrakok.cicerone.android.support.SupportAppScreen
import toothpick.config.Module

class MainFragment : BaseFragment<MainPresentationModel>(R.layout.fragment_main) {

    override val isFabRequired: Boolean = false

    override fun providePresentationModelModules(): Array<Module> {
        return arrayOf(
            moduleOf {
                bind<BottomTabSelectionsEventBus>().singleton()
            }
        )
    }

    override fun providePresentationModel(): MainPresentationModel = scope.getInstance()

    override fun bindActions(presentationModel: MainPresentationModel) {
        bottomNavigationView
            .itemSelections()
            .skip(1)
            .map(MenuItem::getItemId)
            .bindTo(presentationModel.navigationItemSelections)
    }

    override fun bindCommands(presentationModel: MainPresentationModel) {
        presentationModel.showScreenCommand.bindTo(::selectTab)
        presentationModel.selectBottomTab.bindTo(bottomNavigationView::setSelectedItemId)
    }

    private fun selectTab(screen: SupportAppScreen) {
        val currentFragment = childFragmentManager
            .fragments
            .firstOrNull(Fragment::isVisible)

        val newFragment = childFragmentManager.findFragmentByTag(screen.screenKey)

        if (currentFragment != null && newFragment != null && currentFragment == newFragment) return

        childFragmentManager.beginTransaction {
            if (newFragment == null) {
                add(R.id.container, screen.fragment!!, screen.screenKey)
            } else {
                with(newFragment.childFragmentManager) {
                    beginTransaction {
                        fragments.forEach { fragment -> show(fragment) }
                        commit()
                    }
                }

                show(newFragment)
            }

            currentFragment?.let { currentFragment ->
                with(currentFragment.childFragmentManager) {
                    beginTransaction {
                        fragments.forEach { fragment -> hide(fragment) }
                        commit()
                    }
                }

                hide(currentFragment)
            }

            commit()
        }
    }

    override fun dispatchBackPressed() {
        childFragmentManager
            .fragments
            .firstOrNull(Fragment::isVisible)
            .castTo<BaseFragment<*>>()
            ?.dispatchBackPressed() ?: super.dispatchBackPressed()
    }

    override fun setupForInsets() {
        super.setupForInsets()
        container.setOnApplyWindowInsetsListener { view, windowInsets ->
            container.forEach { it.dispatchApplyWindowInsets(windowInsets) }
            windowInsets
        }
    }
}