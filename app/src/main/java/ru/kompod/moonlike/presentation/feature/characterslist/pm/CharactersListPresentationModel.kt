// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.characterslist.pm

import io.reactivex.rxkotlin.subscribeBy
import me.dmdev.rxpm.action
import me.dmdev.rxpm.command
import me.dmdev.rxpm.state
import ru.kompod.moonlike.Screens
import ru.kompod.moonlike.data.analytics.AnalyticsDelegate
import ru.kompod.moonlike.domain.AppScreen
import ru.kompod.moonlike.domain.usecase.characters.GetCharactersUseCase
import ru.kompod.moonlike.domain.usecase.characters.GetSelectedCharacterUseCase
import ru.kompod.moonlike.domain.usecase.characters.RemoveCharactersUseCase
import ru.kompod.moonlike.domain.usecase.characters.SelectCharacterUseCase
import ru.kompod.moonlike.presentation.base.BasePresentationModel
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.characterslist.adapter.CharacterAdapterDelegate
import ru.kompod.moonlike.presentation.feature.characterslist.adapter.CreateCharacterAdapterDelegate
import ru.kompod.moonlike.presentation.feature.characterslist.model.CharacterItem
import ru.kompod.moonlike.presentation.feature.characterslist.model.CreateCharacterItem
import ru.kompod.moonlike.utils.CHARACTERS_LIMIT
import ru.kompod.moonlike.utils.ResourceDelegate
import ru.kompod.moonlike.utils.extensions.kotlin.mergeLists
import ru.kompod.moonlike.utils.extensions.rxjava.combineLatest
import ru.kompod.moonlike.utils.extensions.rxjava.ui
import ru.kompod.moonlike.utils.navigation.BottomTabRouter
import javax.inject.Inject

class CharactersListPresentationModel @Inject constructor(
    override val router: BottomTabRouter,
    resources: ResourceDelegate,
    analytics: AnalyticsDelegate,
    private val getCharactersUseCase: GetCharactersUseCase,
    private val selectedCharacterUseCase: SelectCharacterUseCase,
    private val getSelectedCharacterUseCase: GetSelectedCharacterUseCase,
    private val removeCharactersUseCase: RemoveCharactersUseCase
) : BasePresentationModel(router, resources, analytics),
    CharacterAdapterDelegate.CharacterItemListener,
    CreateCharacterAdapterDelegate.CreateCharacterItemListener {
    override val screen = AppScreen.CHARACTERS_LIST

    override val onCharacterItemClickObserver = action<CharacterItem>()
    override val onRemoveItemClickObserver = action<CharacterItem>()
    override val onCreateCharacterItemClickObserver = action<CreateCharacterItem>()

    val charactersListState = state<List<IListItem>>()
    val onRemoveCharacterAction = action<CharacterItem>()
    val onShowRemoveDialog = command<CharacterItem>()

    override fun onCreate() {
        super.onCreate()
        initViewActions()
    }

    override fun onResume() {
        super.onResume()
        initData()
    }

    private fun initViewActions() {
        onRemoveCharacterAction
            .observable
            .map { it.character.id }
            .flatMapSingle { removeCharactersUseCase.execute(it) }
            .subscribeBy()
            .untilDestroy()

        onCharacterItemClickObserver
            .observable
            .map { it.character.id }
            .flatMapSingle { selectedCharacterUseCase.execute(it) }
            .subscribeBy()
            .untilDestroy()

        onRemoveItemClickObserver
            .observable
            .doOnNext { onShowRemoveDialog.accept(it) }
            .subscribeBy()
            .untilDestroy()

        onCreateCharacterItemClickObserver
            .observable
            .doOnNext { router.navigateTo(Screens.CreateCharacter) }
            .subscribeBy()
            .untilDestroy()
    }

    private fun initData() {
        combineLatest(
            getCharactersUseCase.observe(),
            getSelectedCharacterUseCase.observeId()
        )
            .map { (list, selectedId) -> list.map { CharacterItem(it, it.id == selectedId) } }
            .map { characters ->
                if (characters.size >= CHARACTERS_LIMIT) {
                    characters
                } else {
                    mergeLists(characters, listOf(CreateCharacterItem))
                }
            }
            .observeOn(ui())
            .doOnNext { list ->
                charactersListState.accept(list)
            }
            .subscribeBy()
            .untilPause()

        charactersListState.accept(listOf(CreateCharacterItem))
    }
}