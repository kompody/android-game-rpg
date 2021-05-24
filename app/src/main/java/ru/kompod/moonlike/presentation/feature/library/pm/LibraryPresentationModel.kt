// Copyright (c) 2025 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.library.pm

import io.reactivex.rxkotlin.subscribeBy
import me.dmdev.rxpm.action
import me.dmdev.rxpm.state
import ru.kompod.moonlike.data.analytics.AnalyticsDelegate
import ru.kompod.moonlike.domain.AppScreen
import ru.kompod.moonlike.presentation.base.BasePresentationModel
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.library.adapter.TitleAdapterDelegate
import ru.kompod.moonlike.presentation.feature.library.delegate.MakeLibraryMenuDelegate
import ru.kompod.moonlike.presentation.feature.library.model.TitleListItem
import ru.kompod.moonlike.utils.ResourceDelegate
import ru.kompod.moonlike.utils.navigation.BottomTabRouter
import javax.inject.Inject

class LibraryPresentationModel @Inject constructor(
    override val router: BottomTabRouter,
    resources: ResourceDelegate,
    analytics: AnalyticsDelegate,
    private val makeLibraryMenuDelegate: MakeLibraryMenuDelegate
) : BasePresentationModel(router, resources, analytics),
    TitleAdapterDelegate.LibraryMenuListener {
    override val screen = AppScreen.LIBRARY

    override val onLibraryMenuItemClickObserver = action<TitleListItem>()

    val menuListState = state<List<IListItem>>()

    override fun onCreate() {
        super.onCreate()
        initViewActions()
    }

    override fun onResume() {
        initData()
    }

    private fun initViewActions() {
        onLibraryMenuItemClickObserver
            .observable
            .doOnNext {
//                makeLibraryMenuDelegate.
            }
            .subscribeBy()
            .untilDestroy()
    }

    private fun initData() {
        makeLibraryMenuDelegate.getDefault()
            .doOnSuccess { menuListState.accept(it) }
            .subscribeBy()
            .untilPause()
    }
}