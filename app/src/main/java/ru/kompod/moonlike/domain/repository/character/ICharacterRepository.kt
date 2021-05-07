// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.repository.character

import io.reactivex.Completable
import io.reactivex.Single
import ru.kompod.moonlike.domain.entity.base.Character

interface ICharacterRepository {
    fun saveCharacter(model: Character): Single<Unit>
    fun removeCharacterById(id: Short): Single<Unit>
    fun loadCharacters(): Single<List<Character>>
    fun loadCharacterById(id: Short): Single<Character>
}