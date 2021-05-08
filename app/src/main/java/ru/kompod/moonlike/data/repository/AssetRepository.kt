// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.repository

import ru.kompod.moonlike.domain.entity.base.*
import ru.kompod.moonlike.domain.factory.Assets
import ru.kompod.moonlike.domain.repository.IAssetRepository
import ru.kompod.moonlike.utils.extensions.kotlin.mergeLists
import javax.inject.Inject

class AssetRepository @Inject constructor(
    private val assets: Assets
) : IAssetRepository {
    override fun getCharacterRacesInfo(): List<RaceInfoObject> = listOf(
        assets.humanRaceInfo
    )

    override fun getCharacterRaces(): List<RaceObject> = listOf(
        assets.humanRace
    )

    override fun getCharacterGenders(): List<GenderObject> = listOf(
        assets.male,
        assets.female
    )

    override fun getCharacterPortraits(raceId: Short, genderId: Short): List<PortraitObject> =
        when (genderId) {
            Assets.GENDER_MALE_ID -> getCharacterMalePortraits(raceId)
            Assets.GENDER_FEMALE_ID -> getCharacterFemalePortraits(raceId)
            else -> getCharacterMalePortraits(raceId)
        }

    private fun getCharacterMalePortraits(raceId: Short): List<PortraitObject> = when (raceId) {
        Assets.RACE_HUMAN -> mergeLists(assets.playerA, assets.playerC)
        else -> mergeLists(assets.playerA, assets.playerC)
    }

    private fun getCharacterFemalePortraits(raceId: Short): List<PortraitObject> = when (raceId) {
        Assets.RACE_HUMAN -> assets.playerB
        else -> assets.playerB
    }

    override fun getCharacterRoles(): List<RoleObject> = listOf(
        assets.knight,
        assets.ranger,
        assets.wizard
    )

    override fun getCharacterRaceById(id: Short): RaceObject =
        getCharacterRaces().first { it.id == id }

    override fun getCharacterGenderById(id: Short): GenderObject =
        getCharacterGenders().first { it.id == id }

    override fun getCharacterPortraitById(
        raceId: Short,
        genderId: Short,
        portraitId: Short
    ): PortraitObject =
        getCharacterPortraits(raceId, genderId).first { it.id == portraitId }

    override fun getCharacterRoleById(id: Short): RoleObject =
        getCharacterRoles().first { it.id == id }

    override fun getMaps(): List<MapObject> = listOf(
        assets.map_01,
        assets.map_02,
        assets.map_03,
        assets.map_04,
        assets.map_05,
        assets.map_06,
        assets.map_07,
        assets.map_08,
        assets.map_09,
        assets.map_10,
        assets.map_11,
        assets.map_12,
        assets.map_13,
        assets.map_14,
        assets.map_15,
        assets.map_16
    )

    override fun getMapById(id: Short): MapObject =
        getMaps().first { it.id == id }
}