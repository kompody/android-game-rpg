// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.model

import androidx.annotation.DrawableRes

class SnackBarViewModel(
    val text: String,
    @DrawableRes val iconRes: Int? = null
)