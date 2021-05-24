// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.library.delegate

import io.reactivex.Single
import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.library.model.TitleListItem
import ru.kompod.moonlike.presentation.feature.library.model.TypeItem
import ru.kompod.moonlike.utils.ResourceDelegate
import ru.kompod.moonlike.utils.extensions.rxjava.toSingle
import javax.inject.Inject

class MakeLibraryMenuDelegate @Inject constructor(
    private val resources: ResourceDelegate
) {

    fun getDefault(): Single<List<IListItem>> = listOf(
        TitleListItem(TypeItem.CHARACTERS, resources.getString(R.string.library_main_menu_characters)),
        TitleListItem(TypeItem.ITEMS, resources.getString(R.string.library_main_menu_items)),
        TitleListItem(TypeItem.LOCATIONS, resources.getString(R.string.library_main_menu_locations)),
        TitleListItem(TypeItem.MONSTERS, resources.getString(R.string.library_main_menu_monsters))
    ).toSingle()
}