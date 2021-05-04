// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.profile.view

import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.BaseFragment
import ru.kompod.moonlike.presentation.feature.profile.pm.ProfilePresentationModel
import ru.kompod.moonlike.utils.extensions.toothpick.getInstance

class ProfileFragment : BaseFragment<ProfilePresentationModel>(R.layout.fragment_profile) {
    override val isFabRequired: Boolean = false

    override fun providePresentationModel(): ProfilePresentationModel = scope.getInstance()

    override fun bindActions(presentationModel: ProfilePresentationModel) {
    }
}