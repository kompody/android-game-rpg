// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.mapcharacters.view

import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.BaseBottomSheetFragment
import ru.kompod.moonlike.presentation.feature.mapcharacters.pm.CharacterOnMapPresentationModel
import ru.kompod.moonlike.utils.extensions.toothpick.getInstance

class CharacterOnMapFragment : BaseBottomSheetFragment<CharacterOnMapPresentationModel>(
    R.layout.fragment_characters_on_map,
    true
) {
    override fun providePresentationModel(): CharacterOnMapPresentationModel = scope.getInstance()
}