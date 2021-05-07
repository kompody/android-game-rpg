// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.usecase.characters

import io.reactivex.Single
import ru.kompod.moonlike.domain.entity.base.Character
import ru.kompod.moonlike.domain.repository.character.ICharacterRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val characterRepository: ICharacterRepository
) {
    fun execute(): Single<List<Character>> = characterRepository.loadCharacters()
}