// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.usecase.characters

import io.reactivex.Observable
import io.reactivex.Single
import ru.kompod.moonlike.domain.entity.base.CharacterObject
import ru.kompod.moonlike.domain.repository.character.ICharacterRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val characterRepository: ICharacterRepository
) {
    fun execute(): Single<List<CharacterObject>> = characterRepository.loadCharacters()

    fun observe(): Observable<List<CharacterObject>> = characterRepository.observeCharacters()
}