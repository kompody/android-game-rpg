// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.picasso

import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import ru.kompod.moonlike.utils.tools.AssetProvider

fun Picasso.loadFromAsset(assetProvider: AssetProvider, path: String): RequestCreator =
    load(assetProvider.loadFileFromAsset(path))
