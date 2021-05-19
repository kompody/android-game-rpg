// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.entity.mapper

import ru.kompod.moonlike.domain.entity.base.RoleInfoObject
import ru.kompod.moonlike.domain.entity.base.RoleObject

fun RoleInfoObject.toRoleObject () = RoleObject(
    id = id,
    label = label,
    description = description
)