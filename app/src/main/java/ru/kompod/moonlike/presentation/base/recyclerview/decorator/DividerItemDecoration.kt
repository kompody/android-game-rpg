// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.base.recyclerview.decorator

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DividerItemDecoration(
    private val mDivider: Drawable?,
    private val isDrawLast: Boolean = false
) : RecyclerView.ItemDecoration() {
    private var mOrientation = 0

    override fun onDraw(
        canvas: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            drawHorizontalDividers(canvas, parent)
        } else if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawVerticalDividers(canvas, parent)
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) == 0) {
            return
        }
        mOrientation = (parent.layoutManager as LinearLayoutManager?)!!.orientation
        if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            outRect.left = mDivider?.intrinsicWidth ?: 0
        } else if (mOrientation == LinearLayoutManager.VERTICAL) {
            outRect.bottom = mDivider?.intrinsicHeight ?: 0
        }
    }

    private fun getLastIndex(parent: RecyclerView): Int =
        parent.childCount - if (!isDrawLast) 1 else 0

    private fun drawHorizontalDividers(
        canvas: Canvas,
        parent: RecyclerView
    ) {
        val parentTop = parent.paddingTop
        val parentBottom = parent.height - parent.paddingBottom
        val childCount = getLastIndex(parent)
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params =
                child.layoutParams as RecyclerView.LayoutParams
            val parentLeft = child.right + params.rightMargin
            val parentRight = parentLeft + (mDivider?.intrinsicWidth ?: 0)
            mDivider?.setBounds(parentLeft, parentTop, parentRight, parentBottom)
            mDivider?.draw(canvas)
        }
    }

    private fun drawVerticalDividers(
        canvas: Canvas,
        parent: RecyclerView
    ) {
        val parentLeft = parent.paddingLeft
        val parentRight = parent.width - parent.paddingRight
        val childCount = getLastIndex(parent)
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params =
                child.layoutParams as RecyclerView.LayoutParams
            val parentTop = child.bottom + params.bottomMargin
            val parentBottom = parentTop + (mDivider?.intrinsicHeight ?: 0)
            mDivider?.setBounds(parentLeft, parentTop, parentRight, parentBottom)
            mDivider?.draw(canvas)
        }
    }
}