// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.base.bottomtab.view

import android.os.Bundle
import ru.kompod.moonlike.R
import ru.kompod.moonlike.di.module.BottomTabModule
import ru.kompod.moonlike.presentation.base.BaseFragment
import ru.kompod.moonlike.presentation.base.IBackDispatcher
import ru.kompod.moonlike.presentation.base.bottomtab.pm.BottomTabContainerPresentationModel
import ru.kompod.moonlike.utils.extensions.kotlin.castTo
import ru.kompod.moonlike.utils.extensions.rxpm.accept
import ru.kompod.moonlike.utils.extensions.toothpick.getInstance
import ru.kompod.moonlike.utils.navigation.BottomTabRouter
import ru.kompod.moonlike.utils.navigation.CustomAppNavigator
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppScreen
import toothpick.Toothpick
import toothpick.config.Module
import javax.inject.Inject

abstract class BaseBottomTabContainerFragment : BaseFragment<BottomTabContainerPresentationModel>(R.layout.layout_container) {
    companion object {
        private const val CONTAINER_ID = R.id.container
    }

    override val isFabRequired: Boolean = false

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: BottomTabRouter

    private val navigator: Navigator by lazy {
        CustomAppNavigator(this.requireActivity(), childFragmentManager, CONTAINER_ID)
    }

    override fun providePresentationModel() : BottomTabContainerPresentationModel
            = scope.getInstance(BottomTabContainerPresentationModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, scope)

        if (childFragmentManager.fragments.isEmpty()) {
            router.newRootChain(*getTabInitialChain())
        }

        childFragmentManager.addOnBackStackChangedListener {
            presentationModel.backStackSizeChangeAction.accept(childFragmentManager.backStackEntryCount)
        }

        presentationModel.backStackSizeChangeAction.accept(childFragmentManager.backStackEntryCount)
    }

    override fun providePresentationModelModules(): Array<Module> {
        return arrayOf(
            BottomTabModule(scope.getInstance())
        )
    }

    abstract fun getTabRootScreen(): SupportAppScreen

    protected open fun getTabInitialChain(): Array<out SupportAppScreen> = arrayOf(getTabRootScreen())

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        presentationModel.hiddenChangeAction.accept(hidden)
        val currentFragment = childFragmentManager
            .findFragmentById(CONTAINER_ID)
            .castTo<BaseFragment<*>>()
        currentFragment?.onHiddenChanged(hidden)
    }

    override fun dispatchBackPressed() {
        childFragmentManager
            .findFragmentById(CONTAINER_ID)
            .castTo<IBackDispatcher>()
            ?.dispatchBackPressed() ?: super.dispatchBackPressed()
    }
}