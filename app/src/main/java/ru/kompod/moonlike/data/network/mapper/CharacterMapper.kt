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
        fraction = FractionObject(
            id = model.id,
            label = model.label.getResource(context),
            description = model.description.getResource(context)
        ),
        characters = model.characters.map { mapCharacter(it) }
    )

    fun mapCharacter(model: CharacterApiModel): CharacterInfoObject = CharacterInfoObject(
        id = model.id,
        label = model.label.getResource(context),
        description = model.description.getResource(context),
        gender = mapGender(model.gender),
        portraits = model.portraits,
        roles = model.roleObjects
    )

    fun mapGender(model: GenderApiModel): GenderObject = GenderObject(
        id = model.id,
        label = model.label.getResource(context)
    )

    fun mapRole(model: RoleApiModel): RoleInfoObject = RoleInfoObject(
        id = model.id,
        label = model.label.getResource(context),
        description = model.description.getResource(context),
        states = States(
            hp = model.states.hp,
            sp = model.states.sp,
            fAtk = model.states.fAtk,
            fDef = model.states.fDef,
            mAtk = model.states.mAtk,
            mDef = model.states.mDef
        )
    )
}