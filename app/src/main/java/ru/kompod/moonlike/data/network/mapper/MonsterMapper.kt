// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.network.mapper

import android.content.Context
import ru.kompod.moonlike.data.network.model.MonsterApiModel
import ru.kompod.moonlike.domain.entity.base.MonsterObject
import ru.kompod.moonlike.domain.factory.Assets
import javax.inject.Inject

class MonsterMapper @Inject constructor(
    private val context: Context,
    private val assets: Assets,
    private val characterMapper: CharacterMapper
) {
    fun mapApiModelToEntity(model: MonsterApiModel): MonsterObject = MonsterObject(
        id = model.id,
        label = model.label,
        gender = characterMapper.mapGender(model.gender),
        portrait = model.portrait,
        role = characterMapper.mapRole(model.role),
        spawnRate = model.spawnRate,
        delay = model.delay
    )
}