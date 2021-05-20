// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.usecase.characters

import io.reactivex.Single
import ru.kompod.moonlike.data.repository.character.CharacterRepository
import ru.kompod.moonlike.domain.entity.base.CharacterObject
import javax.inject.Inject

class SaveCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    fun execute(character: CharacterObject): Single<Unit> =
        characterRepository.saveCharacter(character)
}