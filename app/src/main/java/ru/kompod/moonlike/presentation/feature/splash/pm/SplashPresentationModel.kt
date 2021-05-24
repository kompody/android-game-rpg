/* Copyright (c) 2021 Kompod. All rights reserved
 * Description: Презентационная модель загрузочного экрана
 * Author:
 * Project: Player
 */

package ru.kompod.moonlike.presentation.feature.splash.pm

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import me.dmdev.rxpm.action
import me.dmdev.rxpm.state
import ru.kompod.moonlike.Screens
import ru.kompod.moonlike.data.analytics.AnalyticsDelegate
import ru.kompod.moonlike.domain.AppScreen
import ru.kompod.moonlike.presentation.BottomTabReselectionEventBus
import ru.kompod.moonlike.presentation.base.BasePresentationModel
import ru.kompod.moonlike.utils.ResourceDelegate
import ru.kompod.moonlike.utils.eventbus.AppEventBus
import ru.kompod.moonlike.utils.extensions.rxjava.ui
import ru.kompod.moonlike.utils.navigation.CustomRouter
import javax.inject.Inject

class SplashPresentationModel @Inject constructor(
    router: CustomRouter,
    resources: ResourceDelegate,
    analytics: AnalyticsDelegate
) : BasePresentationModel(router, resources, analytics) {

    override val screen = AppScreen.SPLASH

    val onAnimationFinish = action<Boolean>()

    val splashState = state<Unit>()

    override fun onCreate() {
        super.onCreate()
        prepareViewActions()
    }

    override fun onResume() {
        prepareData()
    }

    private fun prepareData() {
        Observable.just(Unit)
            .observeOn(ui())
            .doOnNext(splashState.consumer::accept)
            .subscribe()
            .untilDestroy()
    }

    private fun prepareViewActions() {
        onAnimationFinish.observable
            .filter { isFinish -> isFinish }
            .doOnNext { showMainScreen() }
            .subscribe {}
            .untilDestroy()
    }

    private fun showMainScreen() {
        router.newRootScreen(Screens.MainScreen)
    }
}