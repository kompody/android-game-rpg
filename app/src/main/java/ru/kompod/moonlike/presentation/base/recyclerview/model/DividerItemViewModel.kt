// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.base.recyclerview.model

import androidx.annotation.ColorRes
import ru.kompod.moonlike.R
import ru.kompod.moonlike.utils.extensions.kotlin.dp

class DividerItemViewModel(
    val left: Int = 16.dp,
    val top: Int = 0.dp,
    val right: Int = 16.dp,
    val bottom: Int = 0.dp,
    val height: Int = 1.dp,
    @ColorRes val colorResId: Int = R.color.dividerColor
) : IListItem