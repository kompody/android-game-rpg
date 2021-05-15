// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.database.mapper

import ru.kompod.moonlike.data.database.model.character.CharacterDbModel
import ru.kompod.moonlike.domain.entity.base.CharacterObject
import ru.kompod.moonlike.domain.entity.base.FractionObject
import ru.kompod.moonlike.domain.entity.base.GenderObject
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
            role = assetRepository.getCharacterRoleById(model.roleId)
        )

    fun mapEntityToDbModel(model: CharacterObject): CharacterDbModel =
        CharacterDbModel(
            id = model.id,
            label = model.label,
            description = model.description,
            fractionId = model.fraction.id,
            genderId = model.gender.id,
            portrait = model.portrait,
            roleId = model.role.id
        )
}