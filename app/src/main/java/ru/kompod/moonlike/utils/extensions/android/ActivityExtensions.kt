// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.android

import android.util.DisplayMetrics
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.getWindowWidth() =
    DisplayMetrics().apply{
        windowManager.defaultDisplay.getMetrics(this)
    }
        .widthPixels