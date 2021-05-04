// Copyright (c) 2025 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.setting.view

import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.BaseFragment
import ru.kompod.moonlike.presentation.feature.setting.pm.SettingPresentationModel
import ru.kompod.moonlike.utils.extensions.toothpick.getInstance

class SettingFragment : BaseFragment<SettingPresentationModel>(R.layout.fragment_setting) {
    override val isFabRequired: Boolean = false

    override fun providePresentationModel(): SettingPresentationModel = scope.getInstance()
}