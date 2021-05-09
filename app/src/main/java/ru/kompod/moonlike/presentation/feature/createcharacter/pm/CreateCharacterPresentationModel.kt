// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.createcharacter.pm

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import me.dmdev.rxpm.action
import me.dmdev.rxpm.state
import ru.kompod.moonlike.data.analytics.AnalyticsDelegate
import ru.kompod.moonlike.domain.AppScreen
import ru.kompod.moonlike.domain.entity.base.RaceInfoObject
import ru.kompod.moonlike.domain.usecase.createcharacter.CreateCharacterUseCase
import ru.kompod.moonlike.domain.usecase.createcharacter.GetCharacterRacesUseCase
import ru.kompod.moonlike.presentation.base.BasePresentationModel
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.createcharacter.adapter.GenderAdapterDelegate
import ru.kompod.moonlike.presentation.feature.createcharacter.adapter.IconAdapterDelegate
import ru.kompod.moonlike.presentation.feature.createcharacter.adapter.RaceAdapterDelegate
import ru.kompod.moonlike.presentation.feature.createcharacter.adapter.RoleAdapterDelegate
import ru.kompod.moonlike.presentation.feature.createcharacter.mapper.toEntity
import ru.kompod.moonlike.presentation.feature.createcharacter.model.*
import ru.kompod.moonlike.utils.ResourceDelegate
import ru.kompod.moonlike.utils.extensions.rxjava.ui
import ru.kompod.moonlike.utils.navigation.BottomTabRouter
import javax.inject.Inject

class CreateCharacterPresentationModel @Inject constructor(
    override val router: BottomTabRouter,
    resources: ResourceDelegate,
    analytics: AnalyticsDelegate,
    private val getCharacterRacesUseCase: GetCharacterRacesUseCase,
    private val createCharacterUseCase: CreateCharacterUseCase
) : BasePresentationModel(router, resources, analytics),
    RaceAdapterDelegate.RaceItemListener,
    GenderAdapterDelegate.GenderItemListener,
    IconAdapterDelegate.IconItemListener,
    RoleAdapterDelegate.RoleItemListener {
    override val screen = AppScreen.CREATE_CHARACTER

    override val onChangeRaceClickObserver = action<Int>()
    override val onChangeGenderClickObserver = action<Int>()
    override val onChangeIconClickObserver = action<Int>()
    override val onChangeRoleClickObserver = action<Int>()

    val createButtonClickObservable = action<Unit>()

    val menuListState = state<List<IListItem>>()

    private val viewModelState = state<ViewModel>()

    override fun onCreate() {
        super.onCreate()
        initCommand()
        initViewActions()
        initData()
    }

    private fun initCommand() {
        createButtonClickObservable
            .observable
            .map { viewModelState.value.toEntity() }
            .flatMapSingle { model ->
                createCharacterUseCase.execute(model)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { router.exit() }
            .subscribeBy()
            .untilDestroy()
    }

    private fun initViewActions() {
        onChangeRaceClickObserver
            .observable
            .doOnNext { newIndex ->
                handleChangeRaceClick(newIndex)
            }
            .subscribeBy()
            .untilDestroy()

        onChangeGenderClickObserver
            .observable
            .doOnNext { newIndex ->
                handleChangeGenderClick(newIndex)
            }
            .subscribeBy()
            .untilDestroy()

        onChangeIconClickObserver
            .observable
            .doOnNext { newIndex ->
                handleChangeIconClick(newIndex)
            }
            .subscribeBy()
            .untilDestroy()

        onChangeRoleClickObserver
            .observable
            .doOnNext { newIndex ->
                handleChangeRoleClick(newIndex)
            }
            .subscribeBy()
            .untilDestroy()
    }

    private fun checkNewSelectedIndex(count: Int, selectedIndex: Int): Int = when {
        selectedIndex < 0 -> count - 1
        selectedIndex >= count -> 0
        else -> selectedIndex
    }

    private fun initData() {
        viewModelState
            .observable
            .doOnNext {
                handleViewModel(it)
            }
            .subscribeBy()
            .untilDestroy()

        getCharacterRacesUseCase.execute()
            .map { races -> racesToViewModel(races) }
            .observeOn(ui())
            .doOnSuccess { model ->
                viewModelState.accept(model)
            }
            .subscribeBy()
            .untilDestroy()
    }

    private fun handleChangeRaceClick(newIndex: Int) {
        val model = viewModelState.value
        val index = checkNewSelectedIndex(model.raceItem.races.size, newIndex)
        with(model.raceItem) {
            selectedIndex = index
        }
        with(model.genderItem) {
            genders = model.raceItem.races[index].genders
            selectedIndex = 0
        }
        with(model.portraitItem) {
            portraits = model.raceItem.races[index].portraits[0]
            selectedIndex = 0
        }
        with(model.roleItem) {
            roles = model.raceItem.races[index].roles
            selectedIndex = 0
        }
        handleViewModel(model)
    }

    private fun handleChangeGenderClick(newIndex: Int) {
        val model = viewModelState.value
        val index = checkNewSelectedIndex(model.genderItem.genders.size, newIndex)
        with(model.genderItem) {
            selectedIndex = index
        }
        with(model.portraitItem) {
            portraits = model.raceItem.races[model.raceItem.selectedIndex].portraits[index]
            selectedIndex = 0
        }
        handleViewModel(model)
    }

    private fun handleChangeIconClick(newIndex: Int) {
        val model = viewModelState.value
        val index = checkNewSelectedIndex(model.portraitItem.portraits.size, newIndex)
        with(model.portraitItem) {
            selectedIndex = index
        }
        handleViewModel(model)
    }

    private fun handleChangeRoleClick(newIndex: Int) {
        val model = viewModelState.value
        val index = checkNewSelectedIndex(model.roleItem.roles.size, newIndex)
        with(model.roleItem) {
            selectedIndex = index
        }
        handleViewModel(model)
    }

    private fun handleViewModel(model: ViewModel) {
        listOf(
            RaceItem(model.raceItem.races, model.raceItem.selectedIndex),
            GenderItem(model.genderItem.genders, model.genderItem.selectedIndex),
            PortraitItem(model.portraitItem.portraits, model.portraitItem.selectedIndex),
            RoleItem(model.roleItem.roles, model.roleItem.selectedIndex),
            CharacterAboutItem(
                model.raceItem.races[model.raceItem.selectedIndex].race,
                model.roleItem.roles[model.roleItem.selectedIndex]
            )
        ).also { menuListState.accept(it) }
    }

    private fun racesToViewModel(races: List<RaceInfoObject>): ViewModel = ViewModel(
        RaceItem(races, 0),
        GenderItem(races[0].genders, 0),
        PortraitItem(races[0].portraits[0], 0),
        RoleItem(races[0].roles, 0)
    )
}