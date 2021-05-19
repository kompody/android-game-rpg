// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.base.recyclerview.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.dsl.AdapterDelegateLayoutContainerViewHolder
import ru.kompod.moonlike.presentation.base.recyclerview.adapter.IItemsHolder
import ru.kompod.moonlike.utils.extensions.kotlin.castTo
import kotlin.reflect.KClass

class VerticalListMarginDecorator(
    private val topMargin: Int = 0,
    private val startMargin: Int = 0,
    private val endMargin: Int = 0,
    private val bottomMargin: Int = 0,
    private val margin: Int = 0,
    private val applyFor: Set<KClass<*>>
) : RecyclerView.ItemDecoration() {

    private val applyForAll = applyFor.isEmpty()

    constructor(boundaryMargin: Int = 0, margin: Int = 0, applyFor: Set<KClass<*>>) : this(
        applyFor = applyFor,
        margin = margin,
        topMargin = boundaryMargin,
        startMargin = boundaryMargin,
        endMargin = boundaryMargin,
        bottomMargin = boundaryMargin
    )

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val layoutManager = parent.layoutManager
            ?: throw IllegalStateException("RecyclerView has no LayoutManager")

        val holder = parent.adapter?.castTo<IItemsHolder<*>>()
        holder ?: return

        val position = parent.getChildLayoutPosition(view)

        if (position == RecyclerView.NO_POSITION) return

        parent
            .getChildViewHolder(view)
            .castTo<AdapterDelegateLayoutContainerViewHolder<*>>()
            ?.let { holder ->
                if (!applyForAll && applyFor.isNotEmpty() && holder.item != null && holder.item!!::class !in applyFor) {
                    return
                }
            }

        val itemsCount = state.itemCount

        if (margin > 0) {
            outRect.top = if (position == 0) topMargin else margin / 2
            outRect.bottom =
                if (position == itemsCount - 1) bottomMargin else margin / 2
        } else {
            outRect.top = topMargin
            outRect.bottom = bottomMargin
        }
        outRect.left = startMargin
        outRect.right = endMargin
    }
}