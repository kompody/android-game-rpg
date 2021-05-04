// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.navigation

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

abstract class BottomSheetSupportAppScreen: SupportAppScreen() {
    override fun getFragment(): BottomSheetDialogFragment? {
        return null
    }
}