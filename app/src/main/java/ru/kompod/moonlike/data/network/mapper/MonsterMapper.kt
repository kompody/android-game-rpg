// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.network.mapper

import android.content.Context
import ru.kompod.moonlike.data.network.model.MonsterApiModel
import ru.kompod.moonlike.domain.entity.base.MonsterObject
import javax.inject.Inject

class MonsterMapper @Inject constructor(
    private val context: Context,
    private val characterMapper: CharacterMapper
) {
    fun mapApiModelToEntity(model: MonsterApiModel): MonsterObject = MonsterObject(
        id = model.id,
        label = model.label,
        gender = characterMapper.mapGender(model.gender),
        portrait = model.portrait,
        level = model.level,
        exp = model.exp,
        baseHp = model.hp,
        hp = model.hp,
        sp = model.sp,
        fAtk = model.fAtk,
        fDef = model.fDef,
        mAtk = model.mAtk,
        mDef = model.mDef,
        spawnRate = model.spawnRate,
        delay = model.delay
    )
}