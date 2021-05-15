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
    fun observeId(): Observable<Short> = preferencesRepository.getSelectedCharacter()
        .retry()

    fun observeHasSelected(): Observable<Boolean> = preferencesRepository.getSelectedCharacter()
        .map { id -> id != NO_ID }

    fun observe(): Observable<CharacterObject> = preferencesRepository.getSelectedCharacter()
        .flatMapMaybe { characterRepository.loadCharacterById(it) }
}