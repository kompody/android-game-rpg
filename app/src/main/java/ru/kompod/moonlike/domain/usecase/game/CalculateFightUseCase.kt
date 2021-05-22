// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.usecase.game

import io.reactivex.Observable
import ru.kompod.moonlike.domain.entity.base.*
import ru.kompod.moonlike.domain.repository.IAssetRepository
import ru.kompod.moonlike.domain.usecase.characters.GetSelectedCharacterUseCase
import ru.kompod.moonlike.domain.usecase.characters.SaveCharacterUseCase
import ru.kompod.moonlike.utils.extensions.rxjava.combineSingle
import ru.kompod.moonlike.utils.extensions.rxjava.toSingle
import ru.kompod.moonlike.utils.factory.util.Action
import ru.kompod.moonlike.utils.factory.util.EventBusDelegate
import javax.inject.Inject
import kotlin.math.max

class CalculateFightUseCase @Inject constructor(
    private val getCharactersUseCase: GetSelectedCharacterUseCase,
    private val saveCharacterUseCase: SaveCharacterUseCase,
    private val eventBusDelegate: EventBusDelegate,
    private val assetRepository: IAssetRepository
) {
    fun execute(mapId: Int, monster: OnMapObject): Observable<Pair<CharacterObject, OnMapObject>> {
        return getCharactersUseCase.getCharacterById()
            .flatMapSingle { character ->
                val result = calculateFight(mapId, character, monster)

                combineSingle(
                    saveCharacterUseCase.execute(result.first),
                    result.toSingle()
                )
                    .map { it.second }
            }
    }

    private fun calculateFight(
        mapId: Int,
        character: CharacterObject,
        monsterOnMap: OnMapObject
    ): Pair<CharacterObject, OnMapObject> {
        hitMonster(mapId, character, monsterOnMap, monsterOnMap.monster).also { (character, monster) ->
            return character.copy(
                level = character.level,
                exp = character.exp,
                hp = character.hp,
                sp = character.sp
            ) to monsterOnMap.copy(
                monster = monster
            )
        }
    }

    private fun hitMonster(
        mapId: Int,
        character: CharacterObject,
        monsterOnMap: OnMapObject,
        monster: MonsterObject
    ): Pair<CharacterObject, MonsterObject> {
        if (character.hp <= 0) return character to monster

        if (monster.hp > 0) {
            val postHitMonster =
                monster.copy(hp = monster.hp - ((character.atk() - monster.fDef).takeIf { it > 0 } ?: 1))
            val posHitCharacter =
                character.copy(hp = max(character.hp - ((monster.fAtk - character.def()).takeIf { it > 0 } ?: 1), 0))
            return hitMonster(mapId, posHitCharacter, monsterOnMap, postHitMonster)
        }

        eventBusDelegate.action(Action.KillMonsterOnMapAction(mapId, monsterOnMap))

        val tempExp = character.exp + monster.exp
        val tempLevel = calculateLevel(character.level, tempExp)

        val isNeedLevelUp = character.level != tempLevel

        if (isNeedLevelUp) {
            val role = assetRepository.getCharacterRoleById(character.id)
            val newStates = role.levelStates[tempLevel - 1].states
            return character.copy(
                level = tempLevel,
                exp = 0,
                baseHp = newStates.hp,
                hp =  newStates.hp,
                baseSp = newStates.sp,
                sp = newStates.sp,
                baseFAtk = newStates.fAtk,
                baseFDef = newStates.fDef,
                baseMAtk = newStates.mAtk,
                baseMDef = newStates.mDef
            ) to monster
        }

        return character.copy(
            hp = character.hp,
            exp = tempExp
        ) to monster
    }

    private fun calculateLevel(level: Int, exp: Long): Int =
        when (level) {
            1 -> if (exp >= 10) 2 else 1
            2 -> if (exp >= 24) 3 else 2
            3 -> if (exp >= 58) 4 else 3
            4 -> if (exp >= 138) 5 else 4
            5 -> if (exp >= 332) 6 else 5
            6 -> if (exp >= 796) 7 else 6
            7 -> if (exp >= 1912) 8 else 7
            8 -> if (exp >= 4586) 9 else 8
            9 -> if (exp >= 11008) 10 else 9
            else -> level
        }
}