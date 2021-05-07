// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.kompod.moonlike.data.database.Tables
import ru.kompod.moonlike.data.database.model.character.CharacterDbModel

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(vararg model: CharacterDbModel): Completable

    @Query("DELETE FROM ${Tables.CHARACTER_TABLE} WHERE id = :id")
    fun deleteCharacterById(id: Short): Completable

    @Query("SELECT * FROM ${Tables.CHARACTER_TABLE}")
    fun getCharacters(): Single<List<CharacterDbModel>>

    @Query("SELECT * FROM ${Tables.CHARACTER_TABLE} WHERE id = :id")
    fun getCharacterById(id: Short): Single<CharacterDbModel>
}