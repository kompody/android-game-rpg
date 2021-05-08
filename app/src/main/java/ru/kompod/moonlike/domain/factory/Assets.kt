// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.factory

import ru.kompod.moonlike.R
import ru.kompod.moonlike.domain.entity.base.*
import ru.kompod.moonlike.domain.entity.base.MapObject
import ru.kompod.moonlike.utils.ResourceDelegate
import javax.inject.Inject

class Assets @Inject constructor(
    private val resources: ResourceDelegate
) {
    companion object {
        const val GENDER_MALE_ID: Short = 0
        const val GENDER_FEMALE_ID: Short = 1

        const val ROLE_KNIGHT: Short = 1
        const val ROLE_PRIEST: Short = 2
        const val ROLE_DEMON_LORD: Short = 3
        const val ROLE_DEMONOLOGIST: Short = 4
        const val ROLE_NECROMANCER: Short = 5
        const val ROLE_WITCH: Short = 6
        const val ROLE_RANGER: Short = 7
        const val ROLE_DRUID: Short = 8
        const val ROLE_BARBARIAN: Short = 9
        const val ROLE_BEAST_MASTER: Short = 10
        const val ROLE_WIZARD: Short = 11
        const val ROLE_SORCERER: Short = 12

        const val RACE_CASTLE: Short = 1
        const val RACE_INFERNO: Short = 2
        const val RACE_NECROMANCERS: Short = 3
        const val RACE_RAMPART: Short = 4
        const val RACE_STRONGHOLD: Short = 5
        const val RACE_TOWER: Short = 6

        const val MAP_01: Short = 1
        const val MAP_02: Short = 2
        const val MAP_03: Short = 3
        const val MAP_04: Short = 4
        const val MAP_05: Short = 5
        const val MAP_06: Short = 6
        const val MAP_07: Short = 7
        const val MAP_08: Short = 8
        const val MAP_09: Short = 9
        const val MAP_10: Short = 10
        const val MAP_11: Short = 11
        const val MAP_12: Short = 12
        const val MAP_13: Short = 13
        const val MAP_14: Short = 14
        const val MAP_15: Short = 15
        const val MAP_16: Short = 16
    }

    private enum class Paths(val path: String) {
        //portrait
        CASTLE_MALE("character/portrait/castle/male/castle_icon_male_%d.png"),
        CASTLE_FEMALE("character/portrait/castle/female/castle_icon_female_%d.png"),
        INFERNO_MALE("character/portrait/inferno/male/inferno_icon_male_%d.png"),
        INFERNO_FEMALE("character/portrait/inferno/female/inferno_icon_female_%d.png"),
        NECROMANCERS_MALE("character/portrait/necromancers/male/necromancers_icon_male_%d.png"),
        NECROMANCERS_FEMALE("character/portrait/necromancers/female/necromancers_icon_female_%d.png"),
        RAMPART_MALE("character/portrait/rampart/male/rampart_icon_male_%d.png"),
        RAMPART_FEMALE("character/portrait/rampart/female/rampart_icon_female_%d.png"),
        STRONGHOLD_MALE("character/portrait/stronghold/male/stronghold_icon_male_%d.png"),
        STRONGHOLD_FEMALE("character/portrait/stronghold/female/stronghold_icon_female_%d.png"),
        TOWER_MALE("character/portrait/tower/male/tower_icon_male_%d.png"),
        TOWER_FEMALE("character/portrait/tower/female/tower_icon_female_%d.png"),

        //maps
        MAP_01("map/map_01.png"),
        MAP_02("map/map_02.png"),
        MAP_03("map/map_03.png"),
        MAP_04("map/map_04.png"),
        MAP_05("map/map_05.png"),
        MAP_06("map/map_06.png"),
        MAP_07("map/map_07.png"),
        MAP_08("map/map_08.png"),
        MAP_09("map/map_09.png"),
        MAP_10("map/map_10.png"),
        MAP_11("map/map_11.png"),
        MAP_12("map/map_12.png"),
        MAP_13("map/map_13.png"),
        MAP_14("map/map_14.png"),
        MAP_15("map/map_15.png"),
        MAP_16("map/map_16.png")
    }

    //genders
    val male = GenderObject(GENDER_MALE_ID, resources.getString(R.string.asset_gender_male))
    val female = GenderObject(GENDER_FEMALE_ID, resources.getString(R.string.asset_gender_female))

    //portrait
    val castleIconMale = getIconsByPath(Paths.CASTLE_MALE)
    val castleIconFemale = getIconsByPath(Paths.CASTLE_FEMALE)
    val infernoIconMale = getIconsByPath(Paths.INFERNO_MALE)
    val infernoIconFemale = getIconsByPath(Paths.INFERNO_FEMALE)
    val necromancersIconMale = getIconsByPath(Paths.NECROMANCERS_MALE)
    val necromancersIconFemale = getIconsByPath(Paths.NECROMANCERS_FEMALE)
    val rampartIconMale = getIconsByPath(Paths.RAMPART_MALE)
    val rampartIconFemale = getIconsByPath(Paths.RAMPART_FEMALE)
    val strongholdIconMale = getIconsByPath(Paths.STRONGHOLD_MALE)
    val strongholdIconFemale = getIconsByPath(Paths.STRONGHOLD_FEMALE)
    val towerIconMale = getIconsByPath(Paths.TOWER_MALE)
    val towerIconFemale = getIconsByPath(Paths.TOWER_FEMALE)

    private fun getIconsByPath(path: Paths): List<PortraitObject> = (1..10).toList()
        .mapIndexed { index, it ->
            PortraitObject(index.toShort(), String.format(path.path, it))
        }

    val castleIcons = listOf(castleIconMale, castleIconFemale)
    val infernoIcons = listOf(infernoIconMale, infernoIconFemale)
    val necromancersIcons = listOf(necromancersIconMale, necromancersIconFemale)
    val rampartIcons = listOf(rampartIconMale, rampartIconFemale)
    val strongholdIcons = listOf(strongholdIconMale, strongholdIconFemale)
    val towerIcons = listOf(towerIconMale, towerIconFemale)

    //roles
    val knight = RoleObject(
        ROLE_KNIGHT,
        resources.getString(R.string.asset_role_knight),
        resources.getString(R.string.asset_role_knight_description),
        States.fromRaw(10, 10, 10)
    )
    val priest = RoleObject(
        ROLE_PRIEST,
        resources.getString(R.string.asset_role_priest),
        resources.getString(R.string.asset_role_priest_description),
        States.fromRaw(10, 10, 10)
    )
    val demonLord = RoleObject(
        ROLE_DEMON_LORD,
        resources.getString(R.string.asset_role_demon_lord),
        resources.getString(R.string.asset_role_demon_lord_description),
        States.fromRaw(10, 10, 10)
    )
    val demonologist = RoleObject(
        ROLE_DEMONOLOGIST,
        resources.getString(R.string.asset_role_demonologist),
        resources.getString(R.string.asset_role_demonologist_description),
        States.fromRaw(10, 10, 10)
    )
    val necromancer = RoleObject(
        ROLE_NECROMANCER,
        resources.getString(R.string.asset_role_necromancer),
        resources.getString(R.string.asset_role_necromancer_description),
        States.fromRaw(10, 10, 10)
    )
    val witch = RoleObject(
        ROLE_WITCH,
        resources.getString(R.string.asset_role_witch),
        resources.getString(R.string.asset_role_witch_description),
        States.fromRaw(10, 10, 10)
    )
    val ranger = RoleObject(
        ROLE_RANGER,
        resources.getString(R.string.asset_role_ranger),
        resources.getString(R.string.asset_role_ranger_description),
        States.fromRaw(10, 10, 10)
    )
    val druid = RoleObject(
        ROLE_DRUID,
        resources.getString(R.string.asset_role_druid),
        resources.getString(R.string.asset_role_druid_description),
        States.fromRaw(10, 10, 10)
    )
    val barbarian = RoleObject(
        ROLE_BARBARIAN,
        resources.getString(R.string.asset_role_barbarian),
        resources.getString(R.string.asset_role_barbarian_description),
        States.fromRaw(10, 10, 10)
    )
    val beastMaster = RoleObject(
        ROLE_BEAST_MASTER,
        resources.getString(R.string.asset_role_beastmaster),
        resources.getString(R.string.asset_role_beastmaster_description),
        States.fromRaw(10, 10, 10)
    )
    val wizard = RoleObject(
        ROLE_WIZARD,
        resources.getString(R.string.asset_role_wizard),
        resources.getString(R.string.asset_role_wizard_description),
        States.fromRaw(10, 10, 10)
    )
    val sorcerer = RoleObject(
        ROLE_SORCERER,
        resources.getString(R.string.asset_role_sorcerer),
        resources.getString(R.string.asset_role_sorcerer_description),
        States.fromRaw(10, 10, 10)
    )

    //races
    val castleRace = RaceObject(
        RACE_CASTLE,
        resources.getString(R.string.asset_race_castle),
        resources.getString(R.string.asset_race_castle_description)
    )
    val infernoRace = RaceObject(
        RACE_INFERNO,
        resources.getString(R.string.asset_race_inferno),
        resources.getString(R.string.asset_race_inferno_description)
    )
    val necromancersRace = RaceObject(
        RACE_NECROMANCERS,
        resources.getString(R.string.asset_race_necromancers),
        resources.getString(R.string.asset_race_necromancers_description)
    )
    val rampartRace = RaceObject(
        RACE_RAMPART,
        resources.getString(R.string.asset_race_rampart),
        resources.getString(R.string.asset_race_rampart_description)
    )
    val strongholdRace = RaceObject(
        RACE_STRONGHOLD,
        resources.getString(R.string.asset_race_stronghold),
        resources.getString(R.string.asset_race_stronghold_description)
    )
    val towerRace = RaceObject(
        RACE_TOWER,
        resources.getString(R.string.asset_race_tower),
        resources.getString(R.string.asset_race_tower_description)
    )

    //racesInfo
    val castleRaceInfo = RaceInfoObject(
        castleRace,
        listOf(male, female),
        castleIcons,
        listOf(knight, priest)
    )
    val infernoRaceInfo = RaceInfoObject(
        infernoRace,
        listOf(male, female),
        infernoIcons,
        listOf(demonLord, demonologist)
    )
    val necromancersRaceInfo = RaceInfoObject(
        necromancersRace,
        listOf(male, female),
        necromancersIcons,
        listOf(necromancer, witch)
    )
    val rampartRaceInfo = RaceInfoObject(
        rampartRace,
        listOf(male, female),
        rampartIcons,
        listOf(ranger, druid)
    )
    val strongholdRaceInfo = RaceInfoObject(
        strongholdRace,
        listOf(male, female),
        strongholdIcons,
        listOf(barbarian, beastMaster)
    )
    val towerRaceInfo = RaceInfoObject(
        towerRace,
        listOf(male, female),
        towerIcons,
        listOf(wizard, sorcerer)
    )

    //maps
    val map_01 = MapObject(
        MAP_01,
        resources.getString(R.string.asset_map_01_label),
        Paths.MAP_01.path,
        15000,
        listOf(
            TravelObject(0, "travel to MAP_05", MAP_05)
        )
    )
    val map_02 = MapObject(
        MAP_02,
        resources.getString(R.string.asset_map_02_label),
        Paths.MAP_02.path,
        15000,
        listOf(
        )
    )
    val map_03 = MapObject(
        MAP_03,
        resources.getString(R.string.asset_map_03_label),
        Paths.MAP_03.path,
        15000,
        listOf(
            TravelObject(0, "travel to MAP_04", MAP_04),
            TravelObject(1, "travel to MAP_07", MAP_07)
        )
    )
    val map_04 = MapObject(
        MAP_04,
        resources.getString(R.string.asset_map_04_label),
        Paths.MAP_04.path,
        15000,
        listOf(
            TravelObject(1, "travel to MAP_03", MAP_03)
        )
    )
    val map_05 = MapObject(
        MAP_05,
        resources.getString(R.string.asset_map_05_label),
        Paths.MAP_05.path,
        15000,
        listOf(
            TravelObject(0, "travel to MAP_01", MAP_01),
            TravelObject(1, "travel to MAP_06", MAP_06),
            TravelObject(2, "travel to MAP_09", MAP_09)
        )
    )
    val map_06 = MapObject(
        MAP_06,
        resources.getString(R.string.asset_map_06_label),
        Paths.MAP_06.path,
        15000,
        listOf(
            TravelObject(0, "travel to MAP_05", MAP_05),
            TravelObject(1, "travel to MAP_07", MAP_07),
            TravelObject(2, "travel to MAP_10", MAP_10)
        )
    )
    val map_07 = MapObject(
        MAP_07,
        resources.getString(R.string.asset_map_07_label),
        Paths.MAP_07.path,
        15000,
        listOf(
            TravelObject(0, "travel to MAP_03", MAP_03),
            TravelObject(1, "travel to MAP_06", MAP_06),
            TravelObject(2, "travel to MAP_08", MAP_08),
            TravelObject(3, "travel to MAP_11", MAP_11)
        )
    )
    val map_08 = MapObject(
        MAP_08,
        resources.getString(R.string.asset_map_08_label),
        Paths.MAP_08.path,
        15000,
        listOf(
            TravelObject(0, "travel to MAP_07", MAP_07)
        )
    )
    val map_09 = MapObject(
        MAP_09,
        resources.getString(R.string.asset_map_09_label),
        Paths.MAP_09.path,
        15000,
        listOf(
            TravelObject(0, "travel to MAP_05", MAP_05),
            TravelObject(1, "travel to MAP_13", MAP_13)
        )
    )
    val map_10 = MapObject(
        MAP_10,
        resources.getString(R.string.asset_map_10_label),
        Paths.MAP_10.path,
        15000,
        listOf(
            TravelObject(0, "travel to MAP_06", MAP_06),
            TravelObject(1, "travel to MAP_11", MAP_11)
        )
    )
    val map_11 = MapObject(
        MAP_11,
        resources.getString(R.string.asset_map_11_label),
        Paths.MAP_11.path,
        15000,
        listOf(
            TravelObject(0, "travel to MAP_07", MAP_07),
            TravelObject(1, "travel to MAP_10", MAP_10),
            TravelObject(2, "travel to MAP_15", MAP_15)
        )
    )
    val map_12 = MapObject(
        MAP_12,
        resources.getString(R.string.asset_map_12_label),
        Paths.MAP_12.path,
        15000,
        listOf(
            TravelObject(0, "travel to MAP_16", MAP_16)
        )
    )
    val map_13 = MapObject(
        MAP_13,
        resources.getString(R.string.asset_map_13_label),
        Paths.MAP_13.path,
        15000,
        listOf(
            TravelObject(0, "travel to MAP_09", MAP_09),
            TravelObject(1, "travel to MAP_14", MAP_14)
        )
    )
    val map_14 = MapObject(
        MAP_14,
        resources.getString(R.string.asset_map_14_label),
        Paths.MAP_14.path,
        15000,
        listOf(
            TravelObject(0, "travel to MAP_13", MAP_13),
            TravelObject(1, "travel to MAP_15", MAP_15)
        )
    )
    val map_15 = MapObject(
        MAP_15,
        resources.getString(R.string.asset_map_15_label),
        Paths.MAP_15.path,
        15000,
        listOf(
            TravelObject(0, "travel to MAP_11", MAP_11),
            TravelObject(1, "travel to MAP_14", MAP_14),
            TravelObject(2, "travel to MAP_16", MAP_16)
        )
    )
    val map_16 = MapObject(
        MAP_16,
        resources.getString(R.string.asset_map_16_label),
        Paths.MAP_02.path,
        15000,
        listOf(
            TravelObject(0, "travel to MAP_12", MAP_12),
            TravelObject(1, "travel to MAP_15", MAP_15)
        )
    )
}