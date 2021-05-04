// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.android

import android.animation.Animator
import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Rect
import android.os.Build
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.getSystemService
import androidx.core.text.PrecomputedTextCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.core.widget.NestedScrollView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import io.reactivex.functions.Consumer
import ru.kompod.moonlike.utils.extensions.kotlin.isNotNull
import java.util.concurrent.Executor

fun View.setVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

fun View.setVisibleOrGone(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun ImageView.imageResource(): Consumer<Int> {
    return Consumer { imageRes -> setImageResource(imageRes) }
}

fun AppCompatTextView.setText(text: CharSequence, textParams: PrecomputedTextCompat.Params, executor: Executor? = null) {
    setTextFuture(
        PrecomputedTextCompat.getTextFuture(
            text,
            textParams,
            executor
        )
    )
}

fun TextView.clearCompoundDrawables() {
    setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
}

fun TextView.setDrawables(
    @DrawableRes drawableLeft: Int = 0,
    @DrawableRes drawableTop: Int = 0,
    @DrawableRes drawableRight: Int = 0,
    @DrawableRes drawableBottom: Int = 0
) {
    setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom)
}

fun TextView.setDrawableColor(@ColorRes color: Int) {
    compoundDrawables.filterNotNull().forEach {
        it.colorFilter = PorterDuffColorFilter(getColor(context, color), PorterDuff.Mode.SRC_IN)
    }
}

val NestedScrollView.content: View
    get() = getChildAt(0) ?: throw Exception("ViewGroup does not have any child")

fun TextView.refreshText() {
    this.text = this.text
}

fun View.showKeyboard() {
    post {
        requestFocus()
        val inputMethodManager = context.getSystemService<InputMethodManager>()
        inputMethodManager?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun View.getWindowVisibleDisplayFrame(): Rect {
    val rect = Rect()

    getWindowVisibleDisplayFrame(rect)

    return rect
}

fun View.hideKeyboard() {
    context
        .getSystemService<InputMethodManager>()
        ?.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

@SuppressLint("NewApi")
fun View.configureTopPadding(windowInsets: WindowInsets) {
    updatePadding(
        top = windowInsets.getDisplayCutoutOrNull()?.safeInsetTop ?: let {
            if (windowInsets.systemWindowInsetTop != 0) {
                windowInsets.systemWindowInsetTop
            } else {
                paddingTop
            }
        }
    )
}

@SuppressLint("NewApi")
fun View.configureTopMargin(windowInsets: WindowInsets) {
    updateLayoutParams<ViewGroup.MarginLayoutParams> {
        windowInsets.getDisplayCutoutOrNull()?.let { displayCutout ->
            topMargin = displayCutout.safeInsetTop
        } ?: let {
            if (windowInsets.systemWindowInsetTop != 0) {
                topMargin = windowInsets.systemWindowInsetTop
            }
        }
    }
}

fun WindowInsets.getDisplayCutoutOrNull(): DisplayCutout? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
        displayCutout
    else
        null


fun<V: View> BottomSheetBehavior<V>.setCallback(
    slide: ((view: View, offset: Float) -> Unit)? = null,
    stateChanged: ((view: View, state: Int) -> Unit)? = null
) {
    if (slide.isNotNull() || stateChanged.isNotNull()) {
        setBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(view: View, offset: Float) {
                slide?.invoke(view, offset)
            }

            override fun onStateChanged(view: View, state: Int) {
                stateChanged?.invoke(view, state)
            }
        })
    }
}

fun ViewPropertyAnimator.removeListener(): ViewPropertyAnimator {
    return setListener(null)
}

fun ViewPropertyAnimator.setCallbacks(
    onRepeat: (Animator?) -> Unit = {},
    onEnd: (Animator?) -> Unit = {},
    onCancel: (Animator?) -> Unit = {},
    onStart: (Animator?) -> Unit = {}
): ViewPropertyAnimator {
    return setListener(object: Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {
            onRepeat(animation)
        }

        override fun onAnimationEnd(animation: Animator?) {
            onEnd(animation)
        }

        override fun onAnimationCancel(animation: Animator?) {
            onCancel(animation)
        }

        override fun onAnimationStart(animation: Animator?) {
            onStart(animation)
        }
    })
}
