// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.repository.character

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.kompod.moonlike.data.database.dao.CharacterDao
import ru.kompod.moonlike.data.database.mapper.CharacterMapper
import ru.kompod.moonlike.domain.entity.base.Character
import ru.kompod.moonlike.domain.repository.character.ICharacterRepository
import ru.kompod.moonlike.utils.extensions.rxjava.toSingle
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val characterDao: CharacterDao,
    private val characterMapper: CharacterMapper
) : ICharacterRepository {
    override fun saveCharacter(model: Character): Single<Unit> =
        characterDao.insertCharacter(characterMapper.mapEntityToDbModel(model))
            .andThen(Unit.toSingle())
            .subscribeOn(Schedulers.io())

    override fun removeCharacterById(id: Short): Single<Unit> =
        characterDao.deleteCharacterById(id)
            .andThen(Unit.toSingle())
            .subscribeOn(Schedulers.io())

    override fun loadCharacters(): Single<List<Character>> =
        characterDao.getCharacters()
            .map { list -> list.map(characterMapper::mapDbModelToEntity) }
            .subscribeOn(Schedulers.io())

    override fun loadCharacterById(id: Short): Single<Character> =
        characterDao.getCharacterById(id)
            .map(characterMapper::mapDbModelToEntity)
            .subscribeOn(Schedulers.io())
}