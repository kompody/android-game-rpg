// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.repository.character

import io.reactivex.Observable
import io.reactivex.Single
import ru.kompod.moonlike.domain.entity.base.CharacterObject

interface ICharacterRepository {
    fun saveCharacter(model: CharacterObject): Single<Unit>
    fun removeCharacterById(id: Short): Single<Unit>
    fun loadCharacters(): Single<List<CharacterObject>>
    fun observeCharacters(): Observable<List<CharacterObject>>
    fun loadCharacterById(id: Short): Single<CharacterObject>
}