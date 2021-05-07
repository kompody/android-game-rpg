// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.profile.pm

import io.reactivex.rxkotlin.subscribeBy
import me.dmdev.rxpm.action
import ru.kompod.moonlike.Screens
import ru.kompod.moonlike.data.analytics.AnalyticsDelegate
import ru.kompod.moonlike.domain.AppScreen
import ru.kompod.moonlike.presentation.base.BasePresentationModel
import ru.kompod.moonlike.utils.ResourceDelegate
import ru.kompod.moonlike.utils.navigation.BottomTabRouter
import javax.inject.Inject

class ProfilePresentationModel @Inject constructor(
    override val router: BottomTabRouter,
    resources: ResourceDelegate,
    analytics: AnalyticsDelegate
) : BasePresentationModel(router, resources, analytics) {
    override val screen = AppScreen.PROFILE

    val onChangeCharacterClickObserver = action<Unit>()

    override fun onCreate() {
        super.onCreate()
        initCommand()
        initViewActions()
    }

    private fun initCommand() {

    }

    private fun initViewActions() {
        onChangeCharacterClickObserver
            .observable
            .doOnNext { router.navigateTo(Screens.CharactersList) }
            .subscribeBy()
            .untilDestroy()
    }
}