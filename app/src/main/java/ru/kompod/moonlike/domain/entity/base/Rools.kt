// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.entity.base

//character
class CharacterObject(
    val id: Short,
    val label: String,
    val description: String,
    val fraction: FractionObject,
    val gender: GenderObject,
    val portrait: String,
    val role: RoleObject
)

open class FractionObject(
    val id: Short,
    val label: String,
    val description: String
)

class FractionInfoObject(
    id: Short,
    label: String,
    description: String,
    val characters: List<CharacterInfoObject>
) : FractionObject(id, label, description)

class CharacterInfoObject(
    val id: Short,
    val label: String,
    val description: String,
    val gender: GenderObject,
    val portraits: List<String>,
    val roles: List<RoleObject>
)

class RaceObject(
    val id: Short,
    val label: String,
    val description: String
)

class GenderObject(
    val id: Short,
    val label: String
)

class RoleObject(
    val id: Short,
    val label: String,
    val description: String,
    val states: States
)

open class StateObject(val value: Short)

class PhysicalAttack(value: Short) : StateObject(value)
class PhysicalDefense(value: Short) : StateObject(value)
class MagicalAttack(value: Short) : StateObject(value)
class MagicalDefense(value: Short) : StateObject(value)

class States(
    val fAtk: PhysicalAttack,
    val fDef: PhysicalDefense,
    val mAtk: MagicalAttack,
    val mDef: MagicalDefense
) {
    companion object {
        fun fromRaw(
            fAtk: Short,
            fDef: Short,
            mAtk: Short,
            mDef: Short
        ) = States(
            PhysicalAttack(fAtk),
            PhysicalDefense(fDef),
            MagicalAttack(mAtk),
            MagicalDefense(mDef)
        )
    }
}

//loot item

//map
class MapObject(
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

class TravelObject(
    val id: Short,
    val label: String,
    val to: Short
)

//npc
open class NPCObject(
    open val id: Short,
    open val label: String,
    open val gender: GenderObject,
    open val portrait: String,
    open val role: RoleObject
)

class PeacefulObject(
    id: Short,
    label: String,
    gender: GenderObject,
    portrait: String,
    role: RoleObject
) : NPCObject(id, label, gender, portrait, role)

data class MonsterObject(
    override val id: Short,
    override val label: String,
    override val gender: GenderObject,
    override val portrait: String,
    override val role: RoleObject,
    val idOnPool: Short = id,
    val spawnRate: Float = 1f,
    val delay: Int = 20 * 1000,
    var isLife: Boolean = false,
    var timeDeath: Long = 0
) : NPCObject(id, label, gender, portrait, role)

//quest
