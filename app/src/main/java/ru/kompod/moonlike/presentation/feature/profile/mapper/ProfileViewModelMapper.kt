// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.profile.mapper

import ru.kompod.moonlike.domain.entity.base.CharacterObject
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.profile.model.GeneralInfoItem
import ru.kompod.moonlike.presentation.feature.profile.model.TitleListItem
import ru.kompod.moonlike.utils.ResourceDelegate
import javax.inject.Inject

class ProfileViewModelMapper @Inject constructor(
    private val resources: ResourceDelegate
) {
    fun mapEntityToViewModel(character: CharacterObject): List<IListItem> = listOf(
        TitleListItem("общее"),
        GeneralInfoItem(character),
        TitleListItem("общее1"),
        TitleListItem("общее2"),
        TitleListItem("общее3"),
        TitleListItem("общее4")
    )
}