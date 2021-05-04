// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.analytics

sealed class AnalyticsEvent {
    enum class Category {
        MAIN
    }

    abstract val category: Category
    abstract val name: String
    abstract val params: Map<String, Any>

    override fun toString(): String {
        return "AnalyticsEvent(category=$category, name='$name', params=$params)"
    }
}