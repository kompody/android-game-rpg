// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.database.model.character

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.kompod.moonlike.data.database.Tables

@Entity(
    tableName = Tables.CHARACTER_TABLE
)
data class CharacterDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Short,
    val label: String,
    @ColumnInfo(name = "race_id")
    val raceId: Short,
    @ColumnInfo(name = "gender_id")
    val genderId: Short,
    @ColumnInfo(name = "portrait_id")
    val portraitId: Short,
    @ColumnInfo(name = "role_id")
    val roleId: Short
)