// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.usecase.map

import io.reactivex.Single
import ru.kompod.moonlike.domain.entity.base.MapObject
import ru.kompod.moonlike.domain.repository.IAssetRepository
import ru.kompod.moonlike.utils.extensions.rxjava.io
import ru.kompod.moonlike.utils.extensions.rxjava.toSingle
import javax.inject.Inject

class GetMapUseCase @Inject constructor(
    private val assetRepository: IAssetRepository
) {
    fun execute(): Single<MapObject> = assetRepository.getMapById(6).toSingle()
        .subscribeOn(io())

    fun execute(id: Int): Single<MapObject> = assetRepository.getMapById(id).toSingle()
        .subscribeOn(io())
}