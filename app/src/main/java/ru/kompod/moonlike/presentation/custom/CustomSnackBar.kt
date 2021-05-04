// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.custom

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.postDelayed
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import com.google.android.material.behavior.SwipeDismissBehavior
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.snack_bar.*
import ru.kompod.moonlike.R
import ru.kompod.moonlike.utils.extensions.android.inflate
import ru.kompod.moonlike.utils.extensions.android.setVisibleOrGone
import ru.kompod.moonlike.utils.extensions.kotlin.castTo
import ru.kompod.moonlike.utils.extensions.kotlin.empty
import java.util.*

class CustomSnackBar private constructor(
    private val rootView: ViewGroup,
    override val containerView: View
) : LayoutContainer {
    private val verticalTopMargin = containerView.resources.getDimensionPixelSize(R.dimen.snack_bar_margin_vertical_top)
    private val verticalBottomMargin = containerView.resources.getDimensionPixelSize(R.dimen.snack_bar_margin_vertical_bottom)

    private val translationY: Float
        get() = (rootView.measuredHeight - containerView.measuredHeight - verticalBottomMargin).toFloat()

    init {
        containerView.setOnApplyWindowInsetsListener { view, insets ->
            view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                updateMargins(top = 0)//verticalMargin + insets.systemWindowInsetTop)
            }
            return@setOnApplyWindowInsetsListener insets
        }

        containerView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(view: View) {
                view.requestApplyInsets()
            }

            override fun onViewDetachedFromWindow(view: View) {
            }
        })
    }

    fun setText(text: CharSequence): CustomSnackBar {
        titleTextView.text = text
        return this
    }

    fun setText(@StringRes text: Int): CustomSnackBar {
        titleTextView.setText(text)
        return this
    }

    fun setIconRes(@DrawableRes iconRes: Int): CustomSnackBar {
        iconImageView.setImageResource(iconRes)
        return this
    }

    fun show() {
        synchronized(queue) {
            val head = queue.peek()
            if (head !== this) {
                queue.poll()?.removeOldSnackBar()
            }
        }

        containerView.apply {
            layoutParams.castTo<CoordinatorLayout.LayoutParams>()?.let { layoutParams ->
                val swipeDismissBehavior = SwipeDismissBehavior<View>().apply {
                    setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END)
                    setListener(object : SwipeDismissBehavior.OnDismissListener {
                        override fun onDismiss(p0: View?) {
                            containerView.parent.castTo<ViewGroup>()?.removeView(containerView)
                        }

                        override fun onDragStateChanged(p0: Int) {}
                    })
                }
                layoutParams.behavior = swipeDismissBehavior
            }

            measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        }

        showView()
        scheduleHide()

        synchronized(queue) {
            queue.add(this)
        }
    }

    private fun showView() {
        containerView.apply {
            measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            translationY = rootView.measuredHeight.toFloat()
            alpha = 0f
            setVisibleOrGone(true)

            animate()
                .alpha(1f)
                .translationY(this@CustomSnackBar.translationY)
                .start()
        }
    }

    private val hideRunnable = {
        containerView.animate()
            .alpha(0f)
            .translationY(translationY)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    containerView.parent.castTo<ViewGroup>()?.removeView(containerView)
                    synchronized(queue) {
                        val head = queue.peek()
                        if (head === this@CustomSnackBar) {
                            queue.poll()
                        }
                    }
                }
            })
            .start()
    }

    private fun removeOldSnackBar() {
        containerView.handler.removeCallbacks(hideRunnable)
        containerView.parent.castTo<ViewGroup>()?.removeView(containerView)
    }

    private fun scheduleHide() {
        containerView.postDelayed(DURATION, hideRunnable)
    }

    companion object {
        private const val DURATION = 2000L

        private val queue: Queue<CustomSnackBar> = LinkedList<CustomSnackBar>()

        fun make(containerView: ViewGroup, text: CharSequence = String.empty): CustomSnackBar {
            val contentView = containerView.inflate(R.layout.snack_bar)

            return CustomSnackBar(containerView, contentView).apply {
                setText(text)
                findSuitableParent(containerView).addView(contentView)
            }
        }

        private fun findSuitableParent(possibleParent: ViewGroup): ViewGroup {
            return when (possibleParent.id) {
                R.id.rootContainer -> possibleParent
                else -> {
                    when (possibleParent.parent) {
                        is ViewGroup -> findSuitableParent(possibleParent.parent as ViewGroup)
                        else -> possibleParent
                    }
                }
            }
        }
    }
}