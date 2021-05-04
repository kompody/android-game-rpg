// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.createcharacter.view

import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.BaseFragment
import ru.kompod.moonlike.presentation.feature.createcharacter.pm.CreateCharacterPresentationModel
import ru.kompod.moonlike.utils.extensions.toothpick.getInstance

class CreateCharacterFragment : BaseFragment<CreateCharacterPresentationModel>(R.layout.fragment_create_character) {
    override val isFabRequired: Boolean = false

    override fun providePresentationModel(): CreateCharacterPresentationModel = scope.getInstance()

    override fun bindActions(presentationModel: CreateCharacterPresentationModel) {
    }
}