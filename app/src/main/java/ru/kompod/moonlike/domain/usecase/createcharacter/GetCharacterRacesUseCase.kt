// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.usecase.createcharacter

import io.reactivex.Single
import ru.kompod.moonlike.domain.entity.base.RaceInfoObject
import ru.kompod.moonlike.domain.repository.IAssetRepository
import ru.kompod.moonlike.utils.extensions.rxjava.io
import ru.kompod.moonlike.utils.extensions.rxjava.toSingle
import javax.inject.Inject

class GetCharacterRacesUseCase @Inject constructor(
    private val assetRepository: IAssetRepository
) {
    fun execute(): Single<List<RaceInfoObject>> =
        assetRepository.getCharacterRacesInfo().toSingle()
            .subscribeOn(io())
}