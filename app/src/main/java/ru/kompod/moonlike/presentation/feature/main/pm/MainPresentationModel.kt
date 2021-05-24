// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.main.pm

import io.reactivex.android.schedulers.AndroidSchedulers
import me.dmdev.rxpm.action
import me.dmdev.rxpm.command
import ru.kompod.moonlike.R
import ru.kompod.moonlike.Screens
import ru.kompod.moonlike.data.analytics.AnalyticsDelegate
import ru.kompod.moonlike.utils.factory.spawner.SpawnDelegate
import ru.kompod.moonlike.utils.factory.util.TimerDelegate
import ru.kompod.moonlike.presentation.BottomTabReselectionEventBus
import ru.kompod.moonlike.presentation.base.BasePresentationModel
import ru.kompod.moonlike.presentation.feature.BottomTabSelectionsEventBus
import ru.kompod.moonlike.utils.ResourceDelegate
import ru.kompod.moonlike.utils.eventbus.AppEventBus
import ru.kompod.moonlike.utils.eventbus.Event
import ru.kompod.moonlike.utils.extensions.kotlin.unsafeCastTo
import ru.kompod.moonlike.utils.extensions.rxjava.ui
import ru.kompod.moonlike.utils.factory.spawner.HealerDelegate
import ru.kompod.moonlike.utils.navigation.CustomRouter
import ru.terrakok.cicerone.android.support.SupportAppScreen
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainPresentationModel @Inject constructor(
    router: CustomRouter,
    resources: ResourceDelegate,
    analytics: AnalyticsDelegate,
    private val bottomTabSelectionsEventBus: BottomTabSelectionsEventBus,
    private val bottomTabReselectionEventBus: BottomTabReselectionEventBus,
    private val appEventBus: AppEventBus,
    private val timerDelegate: TimerDelegate,
    private val spawnDelegate: SpawnDelegate,
    private val healerDelegate: HealerDelegate
) : BasePresentationModel(router, resources, analytics) {

    private val SWITCH_TAB_DELAY = 300L

    val navigationItemSelections = action<Int>()

    val showScreenCommand = command<SupportAppScreen>()
    val selectBottomTab = command<Int>()

    var selectedTabId: Int = R.id.item_profile

    override fun onCreate() {
        super.onCreate()
        timerDelegate.subscribe(object : TimerDelegate.TickEmitter {
            override fun emmit(time: Long) {
                Timber.d("tick $time")
            }
        })
        timerDelegate.start()
        spawnDelegate.start()
        healerDelegate.start()

        bottomTabSelectionsEventBus
            .observable
            .doOnNext(selectBottomTab.consumer::accept)
            .subscribe()
            .untilDestroy()

        navigationItemSelections
            .observable
            .throttleLast(SWITCH_TAB_DELAY, TimeUnit.MILLISECONDS, ui())
            .doOnNext(::onNavigationItemSelected)
            .subscribe()
            .untilDestroy()

        appEventBus
            .registerForEvent(Event.SelectBottomTab::class)
            .map { event -> event.unsafeCastTo<Event.SelectBottomTab>() }
            .doOnNext(::dispatchTabSelectionsEvent)
            .subscribe()
            .untilDestroy()

        showScreen(Screens.ProfileContainer)
    }

    override fun onDestroy() {
        super.onDestroy()

        timerDelegate.stop()
        spawnDelegate.stop()
        healerDelegate.stop()
    }

    private fun dispatchTabSelectionsEvent(event: Event.SelectBottomTab) {
        when (event.screen) {
            is Screens.ProfileContainer -> {
                selectBottomTab.accept(R.id.item_profile)
            }
            is Screens.InventoryContainer -> {
                selectBottomTab.accept(R.id.item_inventory)
            }
            is Screens.MapContainer -> {
                selectBottomTab.accept(R.id.item_map)
            }
            is Screens.QuestsListContainer -> {
                selectBottomTab.accept(R.id.item_quest_list)
            }
            is Screens.LibraryContainer -> {
                selectBottomTab.accept(R.id.item_library)
            }
        }

        showScreen(event.screen)
    }

    private fun onNavigationItemSelected(menuItemId: Int) {
        if (selectedTabId == menuItemId) {
            bottomTabReselectionEventBus.run()
        } else {
            getScreenToShow(menuItemId)?.let(::showScreen)
        }
        selectedTabId = menuItemId
    }

    private fun getScreenToShow(menuItemId: Int): SupportAppScreen? {
        return when (menuItemId) {
            R.id.item_profile -> Screens.ProfileContainer
            R.id.item_inventory -> Screens.InventoryContainer
            R.id.item_map -> Screens.MapContainer
            R.id.item_quest_list -> Screens.QuestsListContainer
            R.id.item_library -> Screens.LibraryContainer
            else -> null
        }
    }

    private fun showScreen(screen: SupportAppScreen) = showScreenCommand.accept(screen)
}