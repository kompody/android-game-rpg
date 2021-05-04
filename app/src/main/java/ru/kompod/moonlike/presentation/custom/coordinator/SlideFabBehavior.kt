// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.custom.coordinator

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.forEach
import androidx.core.view.isNotEmpty
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.kompod.moonlike.utils.extensions.android.content
import ru.kompod.moonlike.utils.extensions.kotlin.castTo
import ru.kompod.moonlike.utils.extensions.kotlin.unsafeCastTo

class SlideFabBehavior @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FloatingActionButton.Behavior(context, attrs) {

    private enum class State {
        VISIBLE,
        HIDDEN
    }

    companion object {
        private const val DEFAULT_TRANSLATION = 0f

        fun from(fab: View): SlideFabBehavior? {
            return if (fab !is FloatingActionButton) {
                null
            } else {
                fab.layoutParams.castTo<CoordinatorLayout.LayoutParams>()?.behavior.castTo()
            }
        }
    }

    private var isScrollDispatched = false
    private var animator: ViewPropertyAnimator? = null
    private var currentState =
        State.VISIBLE
    private var hideTranslationY = 0f

    private val animationListener = object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            animator = null
        }
    }

    fun slideUp(fab: FloatingActionButton) {
        animateChild(fab, DEFAULT_TRANSLATION)
        currentState = State.VISIBLE
    }

    fun slideDown(fab: FloatingActionButton) {
        animateChild(fab, hideTranslationY)
        currentState = State.HIDDEN
    }

    override fun onLayoutChild(
        parent: CoordinatorLayout,
        child: FloatingActionButton,
        layoutDirection: Int
    ): Boolean {
        val layoutParams = child.layoutParams.unsafeCastTo<CoordinatorLayout.LayoutParams>()
        val fabBottomMargin = layoutParams.bottomMargin

        hideTranslationY = (child.measuredHeight + fabBottomMargin).toFloat()

        return super.onLayoutChild(parent, child, layoutDirection)
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        target: View,
        type: Int
    ) {
        super.onStopNestedScroll(coordinatorLayout, child, target, type)
        isScrollDispatched = false
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        if (canScroll(coordinatorLayout)) {
            scroll(child, dy)
            isScrollDispatched = true
        }
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        if (!isScrollDispatched) {
            scroll(child, dyConsumed)
        }
    }


    private fun scroll(child: View, dy: Int) {
        if (dy > 0 && currentState != State.HIDDEN) {
            currentState =
                State.HIDDEN

            animator?.cancel()
            child.clearAnimation()

            animateChild(child, hideTranslationY)
        } else if (dy < 0 && currentState != State.VISIBLE) {
            currentState =
                State.VISIBLE

            animator?.cancel()
            child.clearAnimation()

            animateChild(
                child,
                DEFAULT_TRANSLATION
            )
        }
    }

    private fun animateChild(child: View, value: Float) {
        animator = child.animate().translationY(value).setListener(animationListener)
    }

    private fun canScroll(coordinatorLayout: CoordinatorLayout): Boolean {
        var result = false
        coordinatorLayout.forEach { child ->
            when {
                child is NestedScrollView && child.isNotEmpty() -> {
                    val childSize = child
                        .content
                        .layoutParams
                        .unsafeCastTo<ViewGroup.MarginLayoutParams>()
                        .let { params -> child.content.height + params.topMargin + params.bottomMargin }

                    val parentSpace: Int =
                        child.getHeight() - child.getPaddingTop() - child.getPaddingBottom()

                    result = childSize > parentSpace
                }

                child is AppBarLayout -> {
                    child.forEach { nestedChild ->
                        val layoutParams = nestedChild.layoutParams as AppBarLayout.LayoutParams
                        if (layoutParams.scrollFlags and 1 == 1 && layoutParams.scrollFlags and 10 != 0) {
                            result = true
                        }
                    }
                }

                child is RecyclerView -> {
                    result = child.canScrollVertically(-1) || child.canScrollVertically(1)
                }
            }
        }

        return result
    }
}