// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.factory.util

import ru.kompod.moonlike.domain.entity.base.OnMapObject

sealed class Command {
    class RecoveryHealCharacterCommand(val hp: Int, val sp: Int) : Command()

    class RefreshMapCommand(val mapId: Int) : Command()
    class KillMonsterOnMapCommand(val mapId: Int, val monster: OnMapObject) : Command()
}