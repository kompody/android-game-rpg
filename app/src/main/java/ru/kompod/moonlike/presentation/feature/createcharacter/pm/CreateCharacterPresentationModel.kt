// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.createcharacter.pm

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import me.dmdev.rxpm.action
import me.dmdev.rxpm.state
import ru.kompod.moonlike.data.analytics.AnalyticsDelegate
import ru.kompod.moonlike.domain.AppScreen
import ru.kompod.moonlike.domain.entity.base.FractionInfoObject
import ru.kompod.moonlike.domain.usecase.createcharacter.CreateCharacterUseCase
import ru.kompod.moonlike.domain.usecase.createcharacter.GetCharacterRacesUseCase
import ru.kompod.moonlike.presentation.base.BasePresentationModel
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.createcharacter.adapter.CharacterAdapterDelegate
import ru.kompod.moonlike.presentation.feature.createcharacter.adapter.FractionAdapterDelegate
import ru.kompod.moonlike.presentation.feature.createcharacter.adapter.PortraitAdapterDelegate
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
    FractionAdapterDelegate.FractionItemListener,
    CharacterAdapterDelegate.CharacterItemListener,
    PortraitAdapterDelegate.PortraitItemListener,
    RoleAdapterDelegate.RoleItemListener {
    override val screen = AppScreen.CREATE_CHARACTER

    override val onChangeFractionClickObserver = action<Int>()
    override val onChangeCharacterClickObserver = action<Int>()
    override val onChangePortraitClickObserver = action<Int>()
    override val onChangeRoleClickObserver = action<Int>()

    val createButtonClickObservable = action<Unit>()

    val menuListState = state<List<IListItem>>()

    private lateinit var cachedViewModel: ViewModel

    override fun onCreate() {
        super.onCreate()
        initCommand()
        initViewActions()
        initData()
    }

    private fun initCommand() {
        createButtonClickObservable
            .observable
            .map { cachedViewModel.toEntity() }
            .flatMapSingle { model ->
                createCharacterUseCase.execute(model)
            }
            .observeOn(ui())
            .doOnNext { router.exit() }
            .subscribeBy()
            .untilDestroy()
    }

    private fun initViewActions() {
        onChangeFractionClickObserver
            .observable
            .doOnNext {
                handleChangeFractionIndex(it)
            }
            .subscribeBy()
            .untilDestroy()

        onChangeCharacterClickObserver
            .observable
            .doOnNext {
                handleChangeCharacterIndex(it)
            }
            .subscribeBy()
            .untilDestroy()

        onChangePortraitClickObserver
            .observable
            .doOnNext {
                handleChangePortraitIndex(it)
            }
            .subscribeBy()
            .untilDestroy()

        onChangeRoleClickObserver
            .observable
            .doOnNext {
                handleChangeRoleIndex(it)
            }
            .subscribeBy()
            .untilDestroy()
    }

    private fun initData() {
        getCharacterRacesUseCase.execute()
            .map { entity -> mapEntityToViewModel(entity) }
            .doOnSuccess { model -> cachedViewModel = model }
            .doOnSuccess { model -> handleChangeViewModel(model) }
            .subscribeBy()
            .untilDestroy()
    }

    private fun handleChangeFractionIndex(newIndex: Int) {
        val model = cachedViewModel
        val index = checkNewSelectedIndex(model.fractionItem.items.size, newIndex)

        if (model.fractionItem.selectedIndex == index) return

        with(model.fractionItem) {
            selectedIndex = index
        }
        with(model.characterItem) {
            items = model.fractionItem.items[index].characters
            selectedIndex = 0
        }
        with(model.portraitItem) {
            items = model.fractionItem.items[index].characters[0].portraits
            selectedIndex = 0
        }
        with(model.roleItem) {
            items = model.fractionItem.items[index].characters[0].roles
            selectedIndex = 0
        }
        with(model.aboutItem) {
            role = model.roleItem.items[0]
        }
        handleChangeViewModel(model)
    }

    private fun handleChangeCharacterIndex(newIndex: Int) {
        val model = cachedViewModel
        val index = checkNewSelectedIndex(model.characterItem.items.size, newIndex)

        if (model.characterItem.selectedIndex == index) return

        with(model.characterItem) {
            selectedIndex = index
        }
        with(model.portraitItem) {
            items =
                model.fractionItem.items[model.fractionItem.selectedIndex].characters[index].portraits
            selectedIndex = 0
        }
        with(model.roleItem) {
            items =
                model.fractionItem.items[model.fractionItem.selectedIndex].characters[index].roles
            selectedIndex = 0
        }
        with(model.aboutItem) {
            role = model.roleItem.items[0]
        }
        handleChangeViewModel(model)
    }

    private fun handleChangePortraitIndex(newIndex: Int) {
        val model = cachedViewModel
        val index = checkNewSelectedIndex(model.portraitItem.items.size, newIndex)

        if (model.portraitItem.selectedIndex == index) return

        with(model.portraitItem) {
            selectedIndex = index
        }
        handleChangeViewModel(model)
    }

    private fun handleChangeRoleIndex(newIndex: Int) {
        val model = cachedViewModel
        val index = checkNewSelectedIndex(model.roleItem.items.size, newIndex)

        if (model.roleItem.selectedIndex == index) return

        with(model.roleItem) {
            selectedIndex = index
        }
        with(model.aboutItem) {
            role = model.roleItem.items[index]
        }
        handleChangeViewModel(model)
    }

    private fun checkNewSelectedIndex(count: Int, selectedIndex: Int): Int = when {
        selectedIndex < 0 -> count - 1
        selectedIndex >= count -> 0
        else -> selectedIndex
    }

    private fun handleChangeViewModel(model: ViewModel) {
        listOf(
            model.fractionItem.copy(),
            model.characterItem.copy(),
            model.portraitItem.copy(),
            model.roleItem.copy(),
            model.aboutItem.copy()
        ).also { list -> menuListState.accept(list) }
    }

    private fun mapEntityToViewModel(model: List<FractionInfoObject>): ViewModel = ViewModel(
        fractionItem = FractionItem(model, 0),
        characterItem = CharacterItem(model[0].characters, 0),
        portraitItem = PortraitItem(model[0].characters[0].portraits, 0),
        roleItem = RoleItem(model[0].characters[0].roles, 0),
        aboutItem = AboutItem(model[0].characters[0].roles[0])
    )
}