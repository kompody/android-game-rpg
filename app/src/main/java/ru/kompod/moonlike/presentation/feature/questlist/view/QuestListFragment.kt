// Copyright (c) 2024 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.questlist.view

import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.BaseFragment
import ru.kompod.moonlike.presentation.feature.questlist.pm.QuestListPresentationModel
import ru.kompod.moonlike.utils.extensions.toothpick.getInstance

class QuestListFragment : BaseFragment<QuestListPresentationModel>(R.layout.fragment_quest_list) {
    override val isFabRequired: Boolean = false

    override fun providePresentationModel(): QuestListPresentationModel = scope.getInstance()
}