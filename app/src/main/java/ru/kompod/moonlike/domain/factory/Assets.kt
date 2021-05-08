// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.factory

import ru.kompod.moonlike.R
import ru.kompod.moonlike.domain.entity.base.*
import ru.kompod.moonlike.domain.entity.base.MapObject
import ru.kompod.moonlike.utils.ResourceDelegate
import ru.kompod.moonlike.utils.extensions.kotlin.mergeLists
import javax.inject.Inject

class Assets @Inject constructor(
    private val resources: ResourceDelegate
) {
    companion object {
        const val GENDER_MALE_ID: Short = 0
        const val GENDER_FEMALE_ID: Short = 1

        const val ROLE_KNIGHT: Short = 1
        const val ROLE_RANGER: Short = 2
        const val ROLE_WIZARD: Short = 3

        const val RACE_HUMAN: Short = 1

        const val BIOME_FIELD: Short = 1
        const val BIOME_SWAMP: Short = 2
        const val BIOME_MINE: Short = 3
        const val BIOME_RUIN: Short = 4
        const val BIOME_DESERT: Short = 5
        const val BIOME_SNOW: Short = 6

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
        PLAYER_A("character/player/player_a_%d.png"),
        PLAYER_B("character/player/player_b_%d.png"),
        PLAYER_C("character/player/player_c_%d.png"),
        VILLAGER("character/villager/villager_%d.png"),
        ANIMAL("character/animal/animal_%d.png"),
        MOUNT("character/mount/mount_%d.png"),
        DESERT("character/desert/enemy_%d.png"),
        DESERT_BOSS("character/desert/boss_%d.png"),
        FIELD("character/field/enemy_%d.png"),
        FIELD_BOSS("character/field/boss_%d.png"),
        MINE("character/mine/enemy_%d.png"),
        MINE_BOSS("character/mine/boss_%d.png"),
        RUIN("character/ruin/enemy_%d.png"),
        RUIN_BOSS("character/ruin/boss_%d.png"),
        SNOW("character/ruin/enemy_%d.png"),
        SNOW_BOSS("character/ruin/boss_%d.png"),
        SWAMP("character/swamp/enemy_%d.png"),
        SWAMP_BOSS("character/swamp/boss_%d.png"),

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
    //portrait player
    val playerA = getIconsByPath(Paths.PLAYER_A, 1, 2)
    val playerB = getIconsByPath(Paths.PLAYER_B, 1, 2)
    val playerC = getIconsByPath(Paths.PLAYER_C, 1, 2)
    //portrait npc
    val villager = getIconsByPath(Paths.VILLAGER, 1, 6)
    val animal = getIconsByPath(Paths.ANIMAL, 1, 8)
    val mount = getIconsByPath(Paths.MOUNT, 1, 6)
    //portrait enemy
    val desert = getIconsByPath(Paths.DESERT, 1, 6)
    val desertBoss = getIconsByPath(Paths.DESERT_BOSS, 1, 3)
    val field = getIconsByPath(Paths.FIELD, 1, 6)
    val fieldBoss = getIconsByPath(Paths.FIELD_BOSS, 1, 3)
    val mine = getIconsByPath(Paths.MINE, 1, 6)
    val mineBoss = getIconsByPath(Paths.MINE_BOSS, 1, 3)
    val ruin = getIconsByPath(Paths.RUIN, 1, 6)
    val ruinBoss = getIconsByPath(Paths.RUIN_BOSS, 1, 3)
    val snow = getIconsByPath(Paths.SNOW, 1, 6)
    val snowBoss = getIconsByPath(Paths.SNOW_BOSS, 1, 3)
    val swamp = getIconsByPath(Paths.SWAMP, 1, 6)
    val swampBoss = getIconsByPath(Paths.SWAMP_BOSS, 1, 3)

    private fun getIconsByPath(path: Paths, from: Short, to: Short): List<PortraitObject> = (from..to).toList()
        .mapIndexed { index, it ->
            PortraitObject(index.toShort(), String.format(path.path, it))
        }

    val humanIcons = listOf(mergeLists(playerA, playerC), playerB)

        //roles
    val knight = RoleObject(
        ROLE_KNIGHT,
        resources.getString(R.string.asset_role_knight),
        resources.getString(R.string.asset_role_knight_description),
        States.fromRaw(10, 10, 10)
    )
    val ranger = RoleObject(
        ROLE_RANGER,
        resources.getString(R.string.asset_role_ranger),
        resources.getString(R.string.asset_role_ranger_description),
        States.fromRaw(10, 10, 10)
    )
    val wizard = RoleObject(
        ROLE_WIZARD,
        resources.getString(R.string.asset_role_wizard),
        resources.getString(R.string.asset_role_wizard_description),
        States.fromRaw(10, 10, 10)
    )

    //races
    val humanRace = RaceObject(
        RACE_HUMAN,
        resources.getString(R.string.asset_race_human),
        resources.getString(R.string.asset_race_human_description)
    )

    //racesInfo
    val humanRaceInfo = RaceInfoObject(
        humanRace,
        listOf(male, female),
        humanIcons,
        listOf(knight, ranger, wizard)
    )

    //maps
    val map_01 = MapObject(
        MAP_01,
        resources.getString(R.string.asset_map_01_label),
        Paths.MAP_01.path,
        BIOME_SNOW,
        15000,
        listOf(
            TravelObject(0, "travel to MAP_05", MAP_05)
        )
    )
    val map_02 = MapObject(
        MAP_02,
        resources.getString(R.string.asset_map_02_label),
        Paths.MAP_02.path,
        BIOME_SNOW,
        15000,
        listOf(
        )
    )
    val map_03 = MapObject(
        MAP_03,
        resources.getString(R.string.asset_map_03_label),
        Paths.MAP_03.path,
        BIOME_SNOW,
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
        BIOME_SNOW,
        15000,
        listOf(
            TravelObject(1, "travel to MAP_03", MAP_03)
        )
    )
    val map_05 = MapObject(
        MAP_05,
        resources.getString(R.string.asset_map_05_label),
        Paths.MAP_05.path,
        BIOME_SWAMP,
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
        BIOME_FIELD,
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
        BIOME_FIELD,
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
        BIOME_RUIN,
        15000,
        listOf(
            TravelObject(0, "travel to MAP_07", MAP_07)
        )
    )
    val map_09 = MapObject(
        MAP_09,
        resources.getString(R.string.asset_map_09_label),
        Paths.MAP_09.path,
        BIOME_DESERT,
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
        BIOME_FIELD,
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
        BIOME_FIELD,
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
        BIOME_MINE,
        15000,
        listOf(
            TravelObject(0, "travel to MAP_16", MAP_16)
        )
    )
    val map_13 = MapObject(
        MAP_13,
        resources.getString(R.string.asset_map_13_label),
        Paths.MAP_13.path,
        BIOME_DESERT,
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
        BIOME_DESERT,
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
        BIOME_SWAMP,
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
        BIOME_MINE,
        15000,
        listOf(
            TravelObject(0, "travel to MAP_12", MAP_12),
            TravelObject(1, "travel to MAP_15", MAP_15)
        )
    )
}