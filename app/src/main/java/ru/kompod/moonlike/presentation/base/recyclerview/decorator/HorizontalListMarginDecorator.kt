// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.base.recyclerview.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalListMarginDecorator(
    private val startMargin: Int = 0,
    private val endMargin: Int = 0,
    private val boundaryItemMargin: Int = 0
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val itemsCount = parent.adapter?.itemCount ?: 0
        val position = parent.getChildAdapterPosition(view)
        outRect.left = if (position != 0) startMargin else boundaryItemMargin
        outRect.right = if (position != itemsCount - 1) endMargin else boundaryItemMargin
    }
}