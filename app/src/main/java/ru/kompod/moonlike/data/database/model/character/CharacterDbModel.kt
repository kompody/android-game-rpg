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
    val id: Int,
    val label: String,
    val description: String,
    @ColumnInfo(name = "fraction_id")
    val fractionId: Int,
    @ColumnInfo(name = "gender_id")
    val genderId: Int,
    @ColumnInfo(name = "portrait")
    val portrait: String,
    @ColumnInfo(name = "role_id")
    val roleId: Int,
    val level: Int,
    val exp: Long,
    @ColumnInfo(name = "base_hp")
    val baseHp: Int,
    val hp: Int,
    val sp: Int,
    @ColumnInfo(name = "base_f_atk")
    val baseFAtk: Int,
    @ColumnInfo(name = "base_f_def")
    val baseFDef: Int,
    @ColumnInfo(name = "base_m_atk")
    val baseMAtk: Int,
    @ColumnInfo(name = "base_m_def")
    val baseMDef: Int
)