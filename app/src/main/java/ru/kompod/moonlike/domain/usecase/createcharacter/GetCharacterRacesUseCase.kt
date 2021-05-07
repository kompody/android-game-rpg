// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.usecase.createcharacter

import io.reactivex.Single
import ru.kompod.moonlike.domain.entity.base.RaceInfo
import ru.kompod.moonlike.domain.repository.IAssetRepository
import ru.kompod.moonlike.utils.extensions.rxjava.toSingle
import javax.inject.Inject

class GetCharacterRacesUseCase @Inject constructor(
    private val assetRepository: IAssetRepository
) {
    private var cacheRaceInfoList: List<RaceInfo> = listOf()

    fun getRaces(): Single<List<RaceInfo>> = assetRepository.getCharacterRacesInfo().toSingle()
        .doOnSuccess { list -> cacheRaceInfoList = list }

    fun getRaceByIndex(index: Int): Single<RaceInfo> =
        if (cacheRaceInfoList.isEmpty()) {
            getRaces().map { it[index] }
        } else {
            cacheRaceInfoList[index].toSingle()
        }
}