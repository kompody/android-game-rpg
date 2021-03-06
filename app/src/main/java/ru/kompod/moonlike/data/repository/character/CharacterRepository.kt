// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.repository.character

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import ru.kompod.moonlike.data.database.dao.CharacterDao
import ru.kompod.moonlike.data.database.mapper.CharacterMapper
import ru.kompod.moonlike.domain.entity.base.CharacterObject
import ru.kompod.moonlike.domain.entity.base.NewCharacterObject
import ru.kompod.moonlike.domain.repository.character.ICharacterRepository
import ru.kompod.moonlike.utils.extensions.rxjava.io
import ru.kompod.moonlike.utils.extensions.rxjava.toSingle
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val characterDao: CharacterDao,
    private val characterMapper: CharacterMapper
) : ICharacterRepository {
    override fun saveCharacter(model: NewCharacterObject): Single<Unit> =
        characterDao.insertCharacter(characterMapper.mapEntityToDbModel(model))
            .andThen(Unit.toSingle())
            .subscribeOn(io())

    override fun saveCharacter(model: CharacterObject): Single<Unit> =
        characterDao.updateCharacter(characterMapper.mapEntityToDbModel(model))
            .andThen(Unit.toSingle())
            .subscribeOn(io())

    override fun removeCharacterById(id: Int): Single<Unit> =
        characterDao.deleteCharacterById(id)
            .andThen(Unit.toSingle())
            .subscribeOn(io())

    override fun loadCharacters(): Single<List<CharacterObject>> =
        characterDao.getCharacters()
            .map { list -> list.map(characterMapper::mapDbModelToEntity) }
            .subscribeOn(io())

    override fun observeCharacters(): Observable<List<CharacterObject>> =
        characterDao.observeCharacters()
            .map { list -> list.map(characterMapper::mapDbModelToEntity) }
            .subscribeOn(io())

    override fun loadCharacterById(id: Int): Maybe<CharacterObject> =
        characterDao.getCharacterById(id)
            .map(characterMapper::mapDbModelToEntity)
            .subscribeOn(io())

    override fun observeCharacterById(id: Int): Observable<CharacterObject> =
        characterDao.observeCharacterById(id)
            .map(characterMapper::mapDbModelToEntity)
            .subscribeOn(io())
}