// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.usecase.characters

import io.reactivex.Single
import ru.kompod.moonlike.data.repository.PreferencesRepository
import ru.kompod.moonlike.domain.repository.IPreferencesRepository
import ru.kompod.moonlike.domain.repository.character.ICharacterRepository
import ru.kompod.moonlike.utils.NO_ID
import javax.inject.Inject

class RemoveCharactersUseCase @Inject constructor(
    private val characterRepository: ICharacterRepository,
    private val preferencesRepository: IPreferencesRepository
) {
    fun execute(id: Int): Single<Unit> = characterRepository.removeCharacterById(id)
        .flatMap { preferencesRepository.putSelectedCharacter(NO_ID) }
}