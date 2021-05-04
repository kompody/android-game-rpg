// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.base

import toothpick.Scope

interface IScopeHolder {
    var scope: Scope

    fun installPresentationModelModules(scope: Scope)

    fun installViewModules(scope: Scope)
}