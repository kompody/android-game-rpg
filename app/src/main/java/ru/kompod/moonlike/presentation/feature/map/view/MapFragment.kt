// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.map.view

import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.BaseFragment
import ru.kompod.moonlike.presentation.feature.map.pm.MapPresentationModel
import ru.kompod.moonlike.utils.extensions.toothpick.getInstance

class MapFragment : BaseFragment<MapPresentationModel>(R.layout.fragment_map) {
    override val isFabRequired: Boolean = false

    override fun providePresentationModel(): MapPresentationModel = scope.getInstance()
}