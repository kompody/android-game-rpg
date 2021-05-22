// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.database.mapper

import ru.kompod.moonlike.data.database.model.character.CharacterDbModel
import ru.kompod.moonlike.domain.entity.base.CharacterObject
import ru.kompod.moonlike.domain.entity.base.NewCharacterObject
import ru.kompod.moonlike.domain.entity.mapper.toRoleObject
import ru.kompod.moonlike.domain.repository.IAssetRepository
import javax.inject.Inject

class CharacterMapper @Inject constructor(
    private val assetRepository: IAssetRepository
) {
    fun mapDbModelToEntity(model: CharacterDbModel): CharacterObject =
        CharacterObject(
            id = model.id,
            label = model.label,
            description = model.description,
            fraction = assetRepository.getCharacterFractionById(model.fractionId),
            gender = assetRepository.getCharacterGenderById(model.genderId),
            portrait = model.portrait,
            role = assetRepository.getCharacterRoleById(model.roleId).toRoleObject(),
            level = model.level,
            exp = model.exp,
            baseHp = model.baseHp,
            baseSp = model.baseSp,
            hp = model.hp,
            sp = model.sp,
            baseFAtk = model.baseFAtk,
            baseFDef = model.baseFDef,
            baseMAtk = model.baseMAtk,
            baseMDef = model.baseMDef
        )

    fun mapEntityToDbModel(model: NewCharacterObject): CharacterDbModel =
        CharacterDbModel(
            id = model.id,
            label = model.label,
            description = model.description,
            fractionId = model.fraction.id,
            genderId = model.gender.id,
            portrait = model.portrait,
            roleId = model.role.id,
            level = 1,
            exp = 0,
            baseHp = model.role.levelStates[0].states.hp,
            baseSp = model.role.levelStates[0].states.sp,
            hp = model.role.levelStates[0].states.hp,
            sp = model.role.levelStates[0].states.sp,
            baseFAtk = model.role.levelStates[0].states.fAtk,
            baseFDef = model.role.levelStates[0].states.fDef,
            baseMAtk = model.role.levelStates[0].states.mAtk,
            baseMDef = model.role.levelStates[0].states.mDef
        )

    fun mapEntityToDbModel(model: CharacterObject): CharacterDbModel =
        CharacterDbModel(
            id = model.id,
            label = model.label,
            description = model.description,
            fractionId = model.fraction.id,
            genderId = model.gender.id,
            portrait = model.portrait,
            roleId = model.role.id,
            level = model.level,
            exp = model.exp,
            baseHp = model.baseHp,
            baseSp = model.baseSp,
            hp = model.hp,
            sp = model.sp,
            baseFAtk = model.baseFAtk,
            baseFDef = model.baseFDef,
            baseMAtk = model.baseMAtk,
            baseMDef = model.baseMDef
        )
}