// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.usecase.createcharacter

import io.reactivex.Single
import ru.kompod.moonlike.domain.entity.base.CharacterObject
import ru.kompod.moonlike.domain.repository.character.ICharacterRepository
import javax.inject.Inject

class CreateCharacterUseCase @Inject constructor(
    private val characterRepository: ICharacterRepository
) {
    fun execute(model: CharacterObject): Single<Unit> = characterRepository.saveCharacter(model)
}