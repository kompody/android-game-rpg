// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.kompod.moonlike.data.database.dao.CharacterDao
import ru.kompod.moonlike.data.database.model.character.CharacterDbModel

@Database(
    entities = [
        CharacterDbModel::class
    ],
    version = 2,
    exportSchema = false
)
abstract class KompodDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}