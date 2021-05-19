// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.entity.base

//character
data class NewCharacterObject(
    val id: Short,
    val label: String,
    val description: String,
    val fraction: FractionObject,
    val gender: GenderObject,
    val portrait: String,
    val role: RoleInfoObject
)

data class CharacterObject(
    val id: Short,
    val label: String,
    val description: String,
    val fraction: FractionObject,
    val gender: GenderObject,
    val portrait: String,
    val role: RoleObject,
    val level: Short,
    val exp: Long,
    val hp: Short,
    val sp: Short,
    val baseFAtk: Short,
    val baseFDef: Short,
    val baseMAtk: Short,
    val baseMDef: Short
)

data class FractionObject(
    val id: Short,
    val label: String,
    val description: String
)

data class FractionInfoObject(
    val fraction: FractionObject,
    val characters: List<CharacterInfoObject>
)

data class CharacterInfoObject(
    val id: Short,
    val label: String,
    val description: String,
    val gender: GenderObject,
    val portraits: List<String>,
    val roles: List<RoleInfoObject>
)

data class RaceObject(
    val id: Short,
    val label: String,
    val description: String
)

data class GenderObject(
    val id: Short,
    val label: String
)

data class RoleInfoObject(
    val id: Short,
    val label: String,
    val description: String,
    val states: States
)

data class RoleObject(
    val id: Short,
    val label: String,
    val description: String
)

data class States(
    val hp: Short,
    val sp: Short,
    val fAtk: Short,
    val fDef: Short,
    val mAtk: Short,
    val mDef: Short
)

//loot item

//map
data class MapObject(
    val id: Short,
    val label: String,
    val path: String,
    val biome: Short,
    val monsterLimit: Int = 5,
    val bossesLimit: Int = 1,
    val delay: Int = 15 * 1000,
    val travels: List<TravelObject> = listOf(),
    val monsters: List<MonsterObject> = listOf(),
    val objects: List<Short> = listOf(),
    val actors: List<Short> = listOf()
)

data class TravelObject(
    val id: Short,
    val label: String,
    val to: Short
)

//npc
data class NPCObject(
    val id: Short,
    val label: String,
    val gender: GenderObject,
    val portrait: String,
    val role: RoleInfoObject
)

data class PeacefulObject(
    val id: Short,
    val label: String,
    val gender: GenderObject,
    val portrait: String,
    val role: RoleInfoObject
)

data class MonsterObject(
    val id: Short,
    val label: String,
    val gender: GenderObject,
    val portrait: String,
    val role: RoleInfoObject,
    val idOnPool: Short = id,
    val spawnRate: Float = 1f,
    val delay: Int = 20 * 1000,
    var isLife: Boolean = false,
    var timeDeath: Long = 0
)

//quest
