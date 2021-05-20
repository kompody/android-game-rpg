// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.usecase.characters

import io.reactivex.Maybe
import io.reactivex.Observable
import ru.kompod.moonlike.domain.entity.base.CharacterObject
import ru.kompod.moonlike.domain.repository.IPreferencesRepository
import ru.kompod.moonlike.domain.repository.character.ICharacterRepository
import ru.kompod.moonlike.utils.NO_ID
import javax.inject.Inject

class GetSelectedCharacterUseCase @Inject constructor(
    private val characterRepository: ICharacterRepository,
    private val preferencesRepository: IPreferencesRepository
) {
    fun observeId(): Observable<Int> = preferencesRepository.getSelectedCharacter()
        .retry()

    fun observeHasSelected(): Observable<Boolean> = observeId()
        .map { id -> id != NO_ID }

    fun getCharacterById(): Observable<CharacterObject> = observeId()
        .flatMapMaybe { characterRepository.loadCharacterById(it) }

    fun observeCharacterById(): Observable<CharacterObject> = observeId()
            .flatMap { characterRepository.observeCharacterById(it) }
}