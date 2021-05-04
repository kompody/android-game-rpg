// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.base.bottomtab.pm

import io.reactivex.rxkotlin.withLatestFrom
import me.dmdev.rxpm.action
import ru.kompod.moonlike.data.analytics.AnalyticsDelegate
import ru.kompod.moonlike.presentation.BottomTabReselectionEventBus
import ru.kompod.moonlike.presentation.ScrollToStartEventBus
import ru.kompod.moonlike.presentation.base.BasePresentationModel
import ru.kompod.moonlike.utils.ResourceDelegate
import ru.kompod.moonlike.utils.extensions.rxpm.accept
import ru.kompod.moonlike.utils.navigation.BottomTabRouter
import javax.inject.Inject

class BottomTabContainerPresentationModel @Inject constructor(
    resources: ResourceDelegate,
    analytics: AnalyticsDelegate,
    override val router: BottomTabRouter,
    private val bottomTabReselectionEventBus: BottomTabReselectionEventBus,
    private val scrollToStartEventBus: ScrollToStartEventBus
) : BasePresentationModel(router, resources, analytics) {
    val backStackSizeChangeAction = action<Int>()
    val hiddenChangeAction = action<Boolean>()

    override fun onCreate() {
        super.onCreate()
        bottomTabReselectionEventBus.observable
            .withLatestFrom(hiddenChangeAction.observable)
            .filter { (_, isHidden) -> !isHidden }
            .withLatestFrom(backStackSizeChangeAction.observable)
            .doOnNext { (_, backStackSize) ->
                if (backStackSize > 0) {
                    router.backTo(null)
                } else {
                    scrollToStartEventBus.run()
                }
            }
            .subscribe()
            .untilDestroy()

        // onHiddenChanged вызывается только при переходе между элементами нижней навигации
        hiddenChangeAction.accept(false)
    }
}