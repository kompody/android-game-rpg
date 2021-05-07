// Copyright (c) 2024 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.questslist.view

import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.BaseFragment
import ru.kompod.moonlike.presentation.feature.questslist.pm.QuestsListPresentationModel
import ru.kompod.moonlike.utils.extensions.toothpick.getInstance

class QuestsListFragment : BaseFragment<QuestsListPresentationModel>(R.layout.fragment_quests_list) {
    override val isFabRequired: Boolean = false

    override fun providePresentationModel(): QuestsListPresentationModel = scope.getInstance()
}