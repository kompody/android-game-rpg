// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.repository.character

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import ru.kompod.moonlike.domain.entity.base.CharacterObject
import ru.kompod.moonlike.domain.entity.base.NewCharacterObject

interface ICharacterRepository {
    fun saveCharacter(model: NewCharacterObject): Single<Unit>
    fun removeCharacterById(id: Short): Single<Unit>
    fun loadCharacters(): Single<List<CharacterObject>>
    fun observeCharacters(): Observable<List<CharacterObject>>
    fun loadCharacterById(id: Short): Maybe<CharacterObject>
}