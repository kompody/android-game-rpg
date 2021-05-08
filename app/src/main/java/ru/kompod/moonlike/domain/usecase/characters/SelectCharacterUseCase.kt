// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.usecase.characters

import io.reactivex.Single
import ru.kompod.moonlike.domain.repository.IPreferencesRepository
import javax.inject.Inject

class SelectCharacterUseCase @Inject constructor(
    private val preferencesRepository: IPreferencesRepository
) {
    fun execute(id: Short): Single<Unit> = preferencesRepository.putSelectedCharacter(id)
}