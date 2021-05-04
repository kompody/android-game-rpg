// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.custom.toolbarlayout

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import io.reactivex.subjects.PublishSubject
import kotlinx.android.parcel.Parcelize
import ru.kompod.moonlike.R
import ru.kompod.moonlike.utils.extensions.kotlin.castTo
import ru.kompod.moonlike.utils.extensions.kotlin.unsafeCastTo
import java.lang.reflect.Field

class CustomToolbarLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : CollapsingToolbarLayout(context, attrs, defStyle) {
    private val extraStates = IntArray(1)
    var isExpanded = false
        private set

    private var onOffsetChangedListener: AppBarLayout.OnOffsetChangedListener

    val collapsedStatePublisher = PublishSubject.create<Boolean>()

    private var currentOffsetField: Field = CollapsingToolbarLayout::class.java
        .getDeclaredField("currentOffset")
        .apply { isAccessible = true }

    private var currentOffset: Int
        set(value) {
            currentOffsetField.set(this, value)
        }
        get() = currentOffsetField.get(this).unsafeCastTo()

    init {
        setScrimsShown(true, false)
        onOffsetChangedListener = AppBarLayout.OnOffsetChangedListener { _, offset ->
            val maxScrollOffset = height
            isExpanded = maxScrollOffset + offset > scrimVisibleHeightTrigger
            collapsedStatePublisher.onNext(!isExpanded)
            refreshDrawableState()
        }

        // По дефолту CollapsingToolbarLayout потребляет инсеты
        setOnApplyWindowInsetsListener { v, insets -> insets }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        parent.castTo<AppBarLayout>()?.addOnOffsetChangedListener(onOffsetChangedListener)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        parent.castTo<AppBarLayout>()?.removeOnOffsetChangedListener(onOffsetChangedListener)
    }


    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        return SavedState(currentOffset, superState)
    }

    /*
    * Восстанавливаем значение currentOffset вручную, иначе при пересоздании content  scrim
    * показывается с анимацией
    * */
    override fun onRestoreInstanceState(state: Parcelable?) {
        state.castTo<SavedState>()?.let { savedState ->
            super.onRestoreInstanceState(savedState.superState)
            currentOffset = savedState.offset
        }
            ?: super.onRestoreInstanceState(null)
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val states = super.onCreateDrawableState(extraSpace + extraStates.size)
        extraStates[0] = if (isExpanded)
            R.attr.state_toolbar_layout_expanded
        else
            R.attr.state_toolbar_layout_collapsed
        return mergeDrawableStates(states, extraStates)
    }

    @Parcelize
    private data class SavedState(
        val offset: Int,
        val superState: Parcelable?
    ): Parcelable
}