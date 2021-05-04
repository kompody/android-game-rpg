// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.usecase

import ru.kompod.moonlike.utils.InternetState
import javax.inject.Inject

class GetInternetStateUseCase @Inject constructor(
    private val internetState: InternetState
) {
    fun execute() = internetState.observable
}