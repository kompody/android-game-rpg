// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils

import android.content.Context
import android.view.Gravity
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.updateMargins
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.custom.coordinator.SlideFabBehavior
import ru.kompod.moonlike.utils.extensions.android.toContextThemeWrapper
import ru.kompod.moonlike.utils.extensions.kotlin.dp
import javax.inject.Inject

class FloatingActionButtonHelper @Inject constructor(
    private val context: Context
) {
    fun createFab(): View {
        return FloatingActionButton(context.toContextThemeWrapper(R.style.AppTheme_Widget_FloatingActionButton)).apply {
            layoutParams = CoordinatorLayout.LayoutParams(
                CoordinatorLayout.LayoutParams.WRAP_CONTENT,
                CoordinatorLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                updateMargins(right = 16.dp, bottom = 16.dp)
                gravity = Gravity.BOTTOM or Gravity.END
                behavior =
                    SlideFabBehavior(
                        context
                    )
            }
        }
    }
}