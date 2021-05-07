// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.repository

import ru.kompod.moonlike.domain.entity.base.*
import ru.kompod.moonlike.domain.factory.Assets
import ru.kompod.moonlike.domain.repository.IAssetRepository
import javax.inject.Inject

class AssetRepository @Inject constructor(
    private val assets: Assets
) : IAssetRepository {
    override fun getCharacterRacesInfo(): List<RaceInfoObject> = listOf(
        assets.castleRaceInfo,
        assets.infernoRaceInfo,
        assets.necromancersRaceInfo,
        assets.rampartRaceInfo,
        assets.strongholdRaceInfo,
        assets.towerRaceInfo
    )

    override fun getCharacterRaces(): List<RaceObject> = listOf(
        assets.castleRace,
        assets.infernoRace,
        assets.necromancersRace,
        assets.rampartRace,
        assets.strongholdRace,
        assets.towerRace
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
        Assets.RACE_CASTLE -> assets.castleIconMale
        Assets.RACE_INFERNO -> assets.infernoIconMale
        Assets.RACE_NECROMANCERS -> assets.necromancersIconMale
        Assets.RACE_RAMPART -> assets.rampartIconMale
        Assets.RACE_STRONGHOLD -> assets.strongholdIconMale
        Assets.RACE_TOWER -> assets.towerIconMale
        else -> assets.castleIconMale
    }

    private fun getCharacterFemalePortraits(raceId: Short): List<PortraitObject> = when (raceId) {
        Assets.RACE_CASTLE -> assets.castleIconFemale
        Assets.RACE_INFERNO -> assets.infernoIconFemale
        Assets.RACE_NECROMANCERS -> assets.necromancersIconFemale
        Assets.RACE_RAMPART -> assets.rampartIconFemale
        Assets.RACE_STRONGHOLD -> assets.strongholdIconFemale
        Assets.RACE_TOWER -> assets.towerIconFemale
        else -> assets.castleIconFemale
    }

    override fun getCharacterRoles(): List<RoleObject> = listOf(
        assets.knight,
        assets.priest,
        assets.demonLord,
        assets.demonologist,
        assets.necromancer,
        assets.witch,
        assets.ranger,
        assets.druid,
        assets.barbarian,
        assets.beastMaster,
        assets.wizard,
        assets.sorcerer
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

    override fun getMapById(id: Short): MapObject = assets.testMap
}