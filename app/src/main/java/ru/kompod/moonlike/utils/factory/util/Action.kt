// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.factory.util

import ru.kompod.moonlike.domain.entity.base.CharacterObject
import ru.kompod.moonlike.domain.entity.base.OnMapObject

sealed class Action {
    class RecoveryHealCharacterAction(val hp: Int, val sp: Int) : Action()

    class LevelUpAction(val character: CharacterObject, val newLevel: Int) : Action()

    class RefreshMapAction(val mapId: Int) : Action()
    class KillMonsterOnMapAction(val mapId: Int, val monster: OnMapObject) : Action()
}