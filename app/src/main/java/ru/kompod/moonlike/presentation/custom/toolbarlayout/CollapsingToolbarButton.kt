// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.custom.toolbarlayout

import android.content.Context
import android.util.AttributeSet
import ru.kompod.moonlike.R

class CollapsingToolbarButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : DoubleStateButton(context, attrs, defStyle) {
    private val extraStates: IntArray

    var isCollapsed: Boolean = false
        set(value) {
            field = value
            refreshDrawableState()
        }

    init {
        extraStates = IntArray(1)
        extraStates.get(0)
        refreshDrawableState()
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val states = super.onCreateDrawableState(extraSpace + 1)
        val extraStates = if (isCollapsed)
            STATE_COLLAPSED
        else
            STATE_EXPANDED
        return mergeDrawableStates(states, extraStates)
    }

    companion object {
        private val STATE_COLLAPSED = intArrayOf(R.attr.state_toolbar_button_collapsed)
        private val STATE_EXPANDED = intArrayOf(R.attr.state_toolbar_button_expanded)
    }
}