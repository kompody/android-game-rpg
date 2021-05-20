// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.profile.pm

import io.reactivex.rxkotlin.subscribeBy
import me.dmdev.rxpm.action
import me.dmdev.rxpm.state
import ru.kompod.moonlike.Screens
import ru.kompod.moonlike.data.analytics.AnalyticsDelegate
import ru.kompod.moonlike.domain.AppScreen
import ru.kompod.moonlike.domain.usecase.characters.GetSelectedCharacterUseCase
import ru.kompod.moonlike.presentation.base.BasePresentationModel
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.profile.mapper.ProfileViewModelMapper
import ru.kompod.moonlike.utils.ResourceDelegate
import ru.kompod.moonlike.utils.extensions.rxjava.toObservable
import ru.kompod.moonlike.utils.navigation.BottomTabRouter
import javax.inject.Inject

class ProfilePresentationModel @Inject constructor(
    override val router: BottomTabRouter,
    resources: ResourceDelegate,
    analytics: AnalyticsDelegate,
    private val mapper: ProfileViewModelMapper,
    private val getSelectedCharacterUseCase: GetSelectedCharacterUseCase
) : BasePresentationModel(router, resources, analytics) {
    override val screen = AppScreen.PROFILE

    val onChangeCharacterClickObserver = action<Unit>()

    val characterState = state<List<IListItem>>()

    override fun onCreate() {
        super.onCreate()
        initCommand()
        initViewActions()
    }

    override fun onResume() {
        super.onResume()
        initData()
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

    private fun initData() {
        getSelectedCharacterUseCase.observeHasSelected()
            .flatMap { hasSelected ->
                return@flatMap if (hasSelected) {
                    getSelectedCharacterUseCase.observeCharacterById()
                        .map { mapper.mapEntityToViewModel(it) }
                } else {
                    listOf<IListItem>().toObservable()
                }
            }
            .doOnNext {
                characterState.accept(it)
            }
            .subscribeBy()
            .untilPause()
    }
}