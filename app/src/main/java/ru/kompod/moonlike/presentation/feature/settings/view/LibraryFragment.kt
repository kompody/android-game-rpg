// Copyright (c) 2025 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.settings.view

import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.BaseFragment
import ru.kompod.moonlike.presentation.feature.settings.pm.LibraryPresentationModel
import ru.kompod.moonlike.utils.extensions.toothpick.getInstance

class LibraryFragment : BaseFragment<LibraryPresentationModel>(R.layout.fragment_library) {
    override val isFabRequired: Boolean = false

    override fun providePresentationModel(): LibraryPresentationModel = scope.getInstance()
}