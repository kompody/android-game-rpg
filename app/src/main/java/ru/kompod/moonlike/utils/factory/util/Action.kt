// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.factory.util

import ru.kompod.moonlike.domain.entity.base.OnMapObject

sealed class Action {
    class RecoveryHealCharacterCommand(val hp: Int, val sp: Int) : Action()

    class RefreshMapCommand(val mapId: Int) : Action()
    class KillMonsterOnMapCommand(val mapId: Int, val monster: OnMapObject) : Action()
}