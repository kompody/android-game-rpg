// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.usecase.characters

import io.reactivex.Completable
import io.reactivex.Single
import ru.kompod.moonlike.domain.entity.base.Character
import ru.kompod.moonlike.domain.repository.character.ICharacterRepository
import javax.inject.Inject

class RemoveCharactersUseCase @Inject constructor(
    private val characterRepository: ICharacterRepository
) {
    fun execute(id: Short): Single<Unit> = characterRepository.removeCharacterById(id)
}