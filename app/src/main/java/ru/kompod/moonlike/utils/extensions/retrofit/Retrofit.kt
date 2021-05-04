// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.retrofit

import retrofit2.Retrofit

inline fun<reified T> Retrofit.create(): T {
    return create(T::class.java)
}