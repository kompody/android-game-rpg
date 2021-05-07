// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.picasso

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.squareup.picasso.Picasso

class PixelTargetAdapter(private val targetView: ImageView) : TargetAdapter() {
    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        targetView.setImageDrawable(placeHolderDrawable)
    }

    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
        targetView.setImageDrawable(errorDrawable)
    }

    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
        bitmap?.let {
            targetView.setImageBitmap(Bitmap.createScaledBitmap(it, 128, 128, false))
        }
    }
}