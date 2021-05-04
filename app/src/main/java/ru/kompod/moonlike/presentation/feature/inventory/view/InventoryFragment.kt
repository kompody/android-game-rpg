// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.inventory.view

import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.BaseFragment
import ru.kompod.moonlike.presentation.feature.inventory.pm.InventoryPresentationModel
import ru.kompod.moonlike.utils.extensions.toothpick.getInstance

class InventoryFragment : BaseFragment<InventoryPresentationModel>(R.layout.fragment_inventory) {
    override val isFabRequired: Boolean = false

    override fun providePresentationModel(): InventoryPresentationModel = scope.getInstance()
}