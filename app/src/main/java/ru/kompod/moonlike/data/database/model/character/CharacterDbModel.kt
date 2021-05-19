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
    val description: String,
    @ColumnInfo(name = "fraction_id")
    val fractionId: Short,
    @ColumnInfo(name = "gender_id")
    val genderId: Short,
    @ColumnInfo(name = "portrait")
    val portrait: String,
    @ColumnInfo(name = "role_id")
    val roleId: Short,
    val level: Short,
    val exp: Long,
    val hp: Short,
    val sp: Short,
    @ColumnInfo(name = "base_f_atk")
    val baseFAtk: Short,
    @ColumnInfo(name = "base_f_def")
    val baseFDef: Short,
    @ColumnInfo(name = "base_m_atk")
    val baseMAtk: Short,
    @ColumnInfo(name = "base_m_def")
    val baseMDef: Short
)