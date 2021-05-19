// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.picasso

import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import ru.kompod.moonlike.R
import ru.kompod.moonlike.utils.ResourceDelegate
import ru.kompod.moonlike.utils.extensions.picasso.loadFromAsset
import ru.kompod.moonlike.utils.tools.AssetProvider
import javax.inject.Inject

class PicassoUtil @Inject constructor(
    private val picasso: Picasso,
    private val assetProvider: AssetProvider,
    private val resources: ResourceDelegate
) {
    fun load(path: String): RequestCreator = if (path.isEmpty()) {
        picasso.load(R.color.placeholderColor)
    } else {
        picasso.loadFromAsset(assetProvider, path)
    }

    fun load(path: String, block: (RequestCreator) -> RequestCreator): RequestCreator =
        block.invoke(load(path))

    fun resize(rc: RequestCreator, width: Int, height: Int): RequestCreator {
        val widthPixels = resources.getDisplayMetrics().widthPixels

        val wk = widthPixels / resources.getDisplayMetrics().widthPixels

        return rc.resize(width * wk, height * wk)
    }
}