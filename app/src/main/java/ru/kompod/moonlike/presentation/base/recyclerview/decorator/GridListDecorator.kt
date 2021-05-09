// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.base.recyclerview.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.dsl.AdapterDelegateLayoutContainerViewHolder
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.utils.extensions.kotlin.castTo
import kotlin.reflect.KClass

class GridListDecorator(
    private val columnCount: Int,
    private val margin: Int = 0,
    private val boundaryMargin: Int = margin,
    private val applyFor: Set<KClass<out IListItem>> = emptySet()
) : RecyclerView.ItemDecoration() {
    var offset: Int = 0

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildLayoutPosition(view)

        if (position == RecyclerView.NO_POSITION) return

        parent
            .getChildViewHolder(view)
            .castTo<AdapterDelegateLayoutContainerViewHolder<*>>()
            ?.let { holder ->
                if (applyFor.isNotEmpty() && holder.item != null && holder.item!!::class !in applyFor ) {
                    return
                }
            }

        val itemsCount = parent.adapter?.itemCount ?: 0
        val rowsCount = itemsCount / columnCount + if (itemsCount % columnCount == 0) 0 else 1

        val gridPosition = position - offset
        outRect.left = if (gridPosition % columnCount == 0) boundaryMargin else (gridPosition % columnCount) * margin / columnCount
        outRect.right = if (gridPosition % columnCount == columnCount - 1) boundaryMargin else margin / 2
        outRect.top = if (gridPosition / columnCount == 0) boundaryMargin else margin / 2
        outRect.bottom = if (gridPosition / columnCount == rowsCount - 1) boundaryMargin else margin / 2
    }
}