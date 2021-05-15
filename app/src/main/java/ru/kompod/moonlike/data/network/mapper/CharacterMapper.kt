// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.network.mapper

import android.content.Context
import ru.kompod.moonlike.data.network.model.*
import ru.kompod.moonlike.domain.entity.base.*
import ru.kompod.moonlike.domain.entity.base.States
import ru.kompod.moonlike.utils.extensions.kotlin.getResource
import javax.inject.Inject

class CharacterMapper @Inject constructor(
    private val context: Context
) {
    fun mapApiModelToEntity(model: FractionApiModel): FractionObject = FractionObject(
        id = model.id,
        label = model.label.getResource(context),
        description = model.description.getResource(context)
    )

    fun mapApiModelToEntity(model: FractionInfoApiModel): FractionInfoObject = FractionInfoObject(
        id = model.id,
        label = model.label.getResource(context),
        description = model.description.getResource(context),
        characters = model.characters.map { mapCharacter(it) }
    )

    fun mapCharacter(model: CharacterApiModel): CharacterInfoObject = CharacterInfoObject(
        id = model.id,
        label = model.label.getResource(context),
        description = model.description.getResource(context),
        gender = mapGender(model.gender),
        portraits = model.portraits,
        roles = model.roles.map { mapRole(it) }
    )

    fun mapGender(model: GenderApiModel): GenderObject = GenderObject(
        id = model.id,
        label = model.label.getResource(context)
    )

    fun mapRole(model: RoleApiModel): RoleObject = RoleObject(
        id = model.id,
        label = model.label.getResource(context),
        description = model.description.getResource(context),
        states = States.fromRaw(
            fAtk = model.states.fAtk,
            fDef = model.states.fDef,
            mAtk = model.states.mAtk,
            mDef = model.states.mDef
        )
    )
}