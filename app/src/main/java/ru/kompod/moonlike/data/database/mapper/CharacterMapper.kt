// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.database.mapper

import ru.kompod.moonlike.data.database.model.character.CharacterDbModel
import ru.kompod.moonlike.domain.entity.base.CharacterObject
import ru.kompod.moonlike.domain.repository.IAssetRepository
import javax.inject.Inject

class CharacterMapper @Inject constructor(
    private val assetRepository: IAssetRepository
) {
    fun mapDbModelToEntity(model: CharacterDbModel): CharacterObject =
        CharacterObject(
            id = model.id,
            label = model.label,
            race = assetRepository.getCharacterRaceById(model.raceId),
            gender = assetRepository.getCharacterGenderById(model.genderId),
            portrait = assetRepository.getCharacterPortraitById(
                model.raceId,
                model.genderId,
                model.portraitId
            ),
            role = assetRepository.getCharacterRoleById(model.roleId)
        )

    fun mapEntityToDbModel(model: CharacterObject): CharacterDbModel =
        CharacterDbModel(
            id = model.id,
            label = model.label,
            raceId = model.race.id,
            genderId = model.gender.id,
            portraitId = model.portrait.id,
            roleId = model.role.id
        )
}