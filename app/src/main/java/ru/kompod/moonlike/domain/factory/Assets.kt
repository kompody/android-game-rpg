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
        const val RACE_ANIMAL: Short = 2
        const val RACE_ENEMY: Short = 3
        const val RACE_VILLAGE: Short = 4

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

    private fun getIconsByPath(path: Paths, from: Short, to: Short): List<PortraitObject> =
        (from..to).toList()
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
    val enemyRace = RaceObject(
        RACE_ENEMY,
        resources.getString(R.string.asset_race_enemy),
        resources.getString(R.string.asset_race_enemy_description)
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
        id = MAP_01,
        label = resources.getString(R.string.asset_map_01_label),
        path = Paths.MAP_01.path,
        biome = BIOME_SNOW,
        travels = listOf(
            TravelObject(0, "travel to MAP_05", MAP_05)
        )
    )
    val map_02 = MapObject(
        id = MAP_02,
        label = resources.getString(R.string.asset_map_02_label),
        path = Paths.MAP_02.path,
        biome = BIOME_SNOW,
        travels = listOf(
        )
    )
    val map_03 = MapObject(
        id = MAP_03,
        label = resources.getString(R.string.asset_map_03_label),
        path = Paths.MAP_03.path,
        biome = BIOME_SNOW,
        travels = listOf(
            TravelObject(0, "travel to MAP_04", MAP_04),
            TravelObject(1, "travel to MAP_07", MAP_07)
        )
    )
    val map_04 = MapObject(
        id = MAP_04,
        label = resources.getString(R.string.asset_map_04_label),
        path = Paths.MAP_04.path,
        biome = BIOME_SNOW,
        travels = listOf(
            TravelObject(1, "travel to MAP_03", MAP_03)
        )
    )
    val map_05 = MapObject(
        id = MAP_05,
        label = resources.getString(R.string.asset_map_05_label),
        path = Paths.MAP_05.path,
        biome = BIOME_SWAMP,
        travels = listOf(
            TravelObject(0, "travel to MAP_01", MAP_01),
            TravelObject(1, "travel to MAP_06", MAP_06),
            TravelObject(2, "travel to MAP_09", MAP_09)
        )
    )
    val map_06 = MapObject(
        id = MAP_06,
        label = resources.getString(R.string.asset_map_06_label),
        path = Paths.MAP_06.path,
        biome = BIOME_FIELD,
        travels = listOf(
            TravelObject(0, "travel to MAP_05", MAP_05),
            TravelObject(1, "travel to MAP_07", MAP_07),
            TravelObject(2, "travel to MAP_10", MAP_10)
        )
    )
    val map_07 = MapObject(
        id = MAP_07,
        label = resources.getString(R.string.asset_map_07_label),
        path = Paths.MAP_07.path,
        biome = BIOME_FIELD,
        travels = listOf(
            TravelObject(0, "travel to MAP_03", MAP_03),
            TravelObject(1, "travel to MAP_06", MAP_06),
            TravelObject(2, "travel to MAP_08", MAP_08),
            TravelObject(3, "travel to MAP_11", MAP_11)
        )
    )
    val map_08 = MapObject(
        id = MAP_08,
        label = resources.getString(R.string.asset_map_08_label),
        path = Paths.MAP_08.path,
        biome = BIOME_RUIN,
        travels = listOf(
            TravelObject(0, "travel to MAP_07", MAP_07)
        )
    )
    val map_09 = MapObject(
        id = MAP_09,
        label = resources.getString(R.string.asset_map_09_label),
        path = Paths.MAP_09.path,
        biome = BIOME_DESERT,
        travels = listOf(
            TravelObject(0, "travel to MAP_05", MAP_05),
            TravelObject(1, "travel to MAP_13", MAP_13)
        )
    )
    val map_10 = MapObject(
        id = MAP_10,
        label = resources.getString(R.string.asset_map_10_label),
        path = Paths.MAP_10.path,
        biome = BIOME_FIELD,
        travels = listOf(
            TravelObject(0, "travel to MAP_06", MAP_06),
            TravelObject(1, "travel to MAP_11", MAP_11)
        )
    )
    val map_11 = MapObject(
        id = MAP_11,
        label = resources.getString(R.string.asset_map_11_label),
        path = Paths.MAP_11.path,
        biome = BIOME_FIELD,
        travels = listOf(
            TravelObject(0, "travel to MAP_07", MAP_07),
            TravelObject(1, "travel to MAP_10", MAP_10),
            TravelObject(2, "travel to MAP_15", MAP_15)
        )
    )
    val map_12 = MapObject(
        id = MAP_12,
        label = resources.getString(R.string.asset_map_12_label),
        path = Paths.MAP_12.path,
        biome = BIOME_MINE,
        travels = listOf(
            TravelObject(0, "travel to MAP_16", MAP_16)
        )
    )
    val map_13 = MapObject(
        id = MAP_13,
        label = resources.getString(R.string.asset_map_13_label),
        path = Paths.MAP_13.path,
        biome = BIOME_DESERT,
        travels = listOf(
            TravelObject(0, "travel to MAP_09", MAP_09),
            TravelObject(1, "travel to MAP_14", MAP_14)
        )
    )
    val map_14 = MapObject(
        id = MAP_14,
        label = resources.getString(R.string.asset_map_14_label),
        path = Paths.MAP_14.path,
        biome = BIOME_DESERT,
        travels = listOf(
            TravelObject(0, "travel to MAP_13", MAP_13),
            TravelObject(1, "travel to MAP_15", MAP_15)
        )
    )
    val map_15 = MapObject(
        id = MAP_15,
        label = resources.getString(R.string.asset_map_15_label),
        path = Paths.MAP_15.path,
        biome = BIOME_SWAMP,
        travels = listOf(
            TravelObject(0, "travel to MAP_11", MAP_11),
            TravelObject(1, "travel to MAP_14", MAP_14),
            TravelObject(2, "travel to MAP_16", MAP_16)
        )
    )
    val map_16 = MapObject(
        id = MAP_16,
        label = resources.getString(R.string.asset_map_16_label),
        path = Paths.MAP_16.path,
        biome = BIOME_MINE,
        travels = listOf(
            TravelObject(0, "travel to MAP_12", MAP_12),
            TravelObject(1, "travel to MAP_15", MAP_15)
        )
    )

    //monsters
    val fieldMonsters = listOf(
        MonsterObject(
            id = 1,
            label = "Slime",
            race = enemyRace,
            gender = male,
            portrait = field[0],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.3f,
            delay = 10 * 1000
        ),
        MonsterObject(
            id = 2,
            label = "Frog",
            race = enemyRace,
            gender = male,
            portrait = field[1],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.2f,
            delay = 10 * 1000
        ),
        MonsterObject(
            id = 3,
            label = "Wolf",
            race = enemyRace,
            gender = male,
            portrait = field[2],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.15f,
            delay = 10 * 1000
        ),
        MonsterObject(
            id = 4,
            label = "Hornet",
            race = enemyRace,
            gender = male,
            portrait = field[3],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.15f,
            delay = 10 * 1000
        ),
        MonsterObject(
            id = 5,
            label = "Goblin",
            race = enemyRace,
            gender = male,
            portrait = field[4],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.1f,
            delay = 10 * 1000
        ),
        MonsterObject(
            id = 6,
            label = "Goblin Archer",
            race = enemyRace,
            gender = male,
            portrait = field[5],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.1f,
            delay = 10 * 1000
        )
    )
    val swampMonsters = listOf(
        MonsterObject(
            id = 1,
            label = "monster 1",
            race = enemyRace,
            gender = male,
            portrait = swamp[0],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.3f,
            delay = 10 * 1000
        ),
        MonsterObject(
            id = 2,
            label = "monster 2",
            race = enemyRace,
            gender = male,
            portrait = swamp[1],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.2f,
            delay = 10 * 1000
        ),
        MonsterObject(
            id = 3,
            label = "monster 3",
            race = enemyRace,
            gender = male,
            portrait = swamp[2],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.15f,
            delay = 10 * 1000
        ),
        MonsterObject(
            id = 4,
            label = "monster 4",
            race = enemyRace,
            gender = male,
            portrait = swamp[3],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.15f,
            delay = 10 * 1000
        ),
        MonsterObject(
            id = 5,
            label = "monster 5",
            race = enemyRace,
            gender = male,
            portrait = swamp[4],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.1f,
            delay = 10 * 1000
        ),
        MonsterObject(
            id = 6,
            label = "monster 6",
            race = enemyRace,
            gender = male,
            portrait = swamp[5],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.1f,
            delay = 10 * 1000
        )
    )
    val mineMonsters = listOf(
        MonsterObject(
            id = 1,
            label = "monster 1",
            race = enemyRace,
            gender = male,
            portrait = mine[0],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.3f,
            delay = 10 * 1000
        ),
        MonsterObject(
            id = 2,
            label = "monster 2",
            race = enemyRace,
            gender = male,
            portrait = mine[1],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.2f,
            delay = 10 * 1000
        ),
        MonsterObject(
            id = 3,
            label = "monster 3",
            race = enemyRace,
            gender = male,
            portrait = mine[2],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.15f,
            delay = 10 * 1000
        ),
        MonsterObject(
            id = 4,
            label = "monster 4",
            race = enemyRace,
            gender = male,
            portrait = mine[3],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.15f,
            delay = 10 * 1000
        ),
        MonsterObject(
            id = 5,
            label = "monster 5",
            race = enemyRace,
            gender = male,
            portrait = mine[4],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.1f,
            delay = 10 * 1000
        ),
        MonsterObject(
            id = 6,
            label = "monster 6",
            race = enemyRace,
            gender = male,
            portrait = mine[5],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.1f,
            delay = 10 * 1000
        )
    )
    val ruinMonsters = listOf(
        MonsterObject(
            id = 1,
            label = "monster 1",
            race = enemyRace,
            gender = male,
            portrait = ruin[0],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.3f,
            delay = 10 * 1000
        ),
        MonsterObject(
            id = 2,
            label = "monster 2",
            race = enemyRace,
            gender = male,
            portrait = ruin[1],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.2f,
            delay = 10 * 1000
        ),
        MonsterObject(
            id = 3,
            label = "monster 3",
            race = enemyRace,
            gender = male,
            portrait = ruin[2],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.15f,
            delay = 10 * 1000
        ),
        MonsterObject(
            id = 4,
            label = "monster 4",
            race = enemyRace,
            gender = male,
            portrait = ruin[3],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.15f,
            delay = 10 * 1000
        ),
        MonsterObject(
            id = 5,
            label = "monster 5",
            race = enemyRace,
            gender = male,
            portrait = ruin[4],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.1f,
            delay = 10 * 1000
        ),
        MonsterObject(
            id = 6,
            label = "monster 6",
            race = enemyRace,
            gender = male,
            portrait = ruin[5],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.1f,
            delay = 10 * 1000
        )
    )
    val desertMonsters = listOf(
        MonsterObject(
            id = 1,
            label = "monster 1",
            race = enemyRace,
            gender = male,
            portrait = desert[0],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.3f,
            delay = 10 * 1000
        ),
        MonsterObject(
            id = 2,
            label = "monster 2",
            race = enemyRace,
            gender = male,
            portrait = desert[1],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.2f,
            delay = 10 * 1000
        ),
        MonsterObject(
            id = 3,
            label = "monster 3",
            race = enemyRace,
            gender = male,
            portrait = desert[2],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.15f,
            delay = 10 * 1000
        ),
        MonsterObject(
            id = 4,
            label = "monster 4",
            race = enemyRace,
            gender = male,
            portrait = desert[3],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.15f,
            delay = 10 * 1000
        ),
        MonsterObject(
            id = 5,
            label = "monster 5",
            race = enemyRace,
            gender = male,
            portrait = desert[4],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.1f,
            delay = 10 * 1000
        ),
        MonsterObject(
            id = 6,
            label = "monster 6",
            race = enemyRace,
            gender = male,
            portrait = desert[5],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.1f,
            delay = 10 * 1000
        )
    )
    val snowMonsters = listOf(
        MonsterObject(
            id = 1,
            label = "monster 1",
            race = enemyRace,
            gender = male,
            portrait = snow[0],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.3f,
            delay = 10 * 1000
        ),
        MonsterObject(
            id = 2,
            label = "monster 2",
            race = enemyRace,
            gender = male,
            portrait = snow[1],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.2f,
            delay = 10 * 1000
        ),
        MonsterObject(
            id = 3,
            label = "monster 3",
            race = enemyRace,
            gender = male,
            portrait = snow[2],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.15f,
            delay = 10 * 1000
        ),
        MonsterObject(
            id = 4,
            label = "monster 4",
            race = enemyRace,
            gender = male,
            portrait = snow[3],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.15f,
            delay = 10 * 1000
        ),
        MonsterObject(
            id = 5,
            label = "monster 5",
            race = enemyRace,
            gender = male,
            portrait = snow[4],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.1f,
            delay = 10 * 1000
        ),
        MonsterObject(
            id = 6,
            label = "monster 6",
            race = enemyRace,
            gender = male,
            portrait = snow[5],
            role = knight,
            isLife = false,
            timeDeath = 0L,
            changeSpawn = 0.1f,
            delay = 10 * 1000
        )
    )
}