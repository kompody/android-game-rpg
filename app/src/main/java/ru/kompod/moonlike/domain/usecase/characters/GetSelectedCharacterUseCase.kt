// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.usecase.characters

import io.reactivex.Observable
import ru.kompod.moonlike.domain.entity.base.CharacterObject
import ru.kompod.moonlike.domain.entity.base.emptyCharacterObject
import ru.kompod.moonlike.domain.repository.IPreferencesRepository
import ru.kompod.moonlike.domain.repository.character.ICharacterRepository
import ru.kompod.moonlike.utils.extensions.rxjava.toObservable
import javax.inject.Inject

class GetSelectedCharacterUseCase @Inject constructor(
    private val characterRepository: ICharacterRepository,
    private val preferencesRepository: IPreferencesRepository
) {
    fun observe(): Observable<CharacterObject> = preferencesRepository.getSelectedCharacter()
        .flatMapSingle { characterRepository.loadCharacterById(it) }
        .onErrorReturn { emptyCharacterObject }
        .retry()
}