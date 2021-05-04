// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.picasso

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.PixelFormat
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import timber.log.Timber
import kotlin.math.min

class PicassoImageGetter(
    private val maxWidth: Int,
    private val resources: Resources,
    private val picasso: Picasso,
    private val imageLoaded: () -> Unit
) : Html.ImageGetter {

    private val target = BitmapTarget()

    override fun getDrawable(source: String): Drawable? {
        picasso
            .load(source)
            .into(target)

        return target
    }

    inner class BitmapTarget : Drawable(), Target {

        private var drawable: Drawable? = null
            set(value) {
                field = value
                field?.let(this::checkBounds)
            }

        override fun draw(canvas: Canvas) {
            drawable?.draw(canvas)
        }

        override fun setAlpha(alpha: Int) {
            drawable?.alpha = alpha
        }

        override fun getOpacity(): Int {
            return drawable?.opacity ?: PixelFormat.UNKNOWN
        }

        override fun setColorFilter(filter: ColorFilter?) {
            drawable?.colorFilter = filter
        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            placeHolderDrawable?.let(this::drawable::set)
        }

        override fun onBitmapFailed(e: Exception, errorDrawable: Drawable?) {
            Timber.e(e)
            errorDrawable?.let(this::drawable::set)
        }

        override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom?) {
            drawable = BitmapDrawable(resources, bitmap)
        }

        private fun checkBounds(drawable: Drawable) {
            val defaultProportion =
                drawable.intrinsicWidth.toFloat() / drawable.intrinsicHeight.toFloat()
            val width = min(maxWidth, drawable.intrinsicWidth)
            val height = (width.toFloat() / defaultProportion).toInt()

            if (bounds.right != maxWidth || bounds.bottom != height) {
                setBounds(0, 0, maxWidth, height)
                val halfOfPlaceHolderWidth = (bounds.right.toFloat() / 2f).toInt()
                val halfOfImageWidth = (width.toFloat() / 2f).toInt()
                drawable.setBounds(
                    halfOfPlaceHolderWidth - halfOfImageWidth,
                    0,
                    halfOfPlaceHolderWidth + halfOfImageWidth,
                    height
                )
            }

            imageLoaded()
        }
    }
}