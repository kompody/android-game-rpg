// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.entity.error

sealed class KompodApiException: RuntimeException() {
    object WrongLoginData: KompodApiException()

    sealed class Api: KompodApiException() {
        data class NotSuccess(override val message: String): Api()
    }

    sealed class User: KompodApiException() {
        object NotFound: User()
        object Exist: User()
    }

    sealed class Code: KompodApiException() {
        object Expired: Code()
        object NotFound: Code()
    }

    sealed class Reservation: KompodApiException() {
        object FlatReserved: KompodApiException()
    }

    sealed class Validation: KompodApiException() {
        object NotValidPassport: Validation()
    }
}