// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.map.pm

import android.os.CountDownTimer
import io.reactivex.rxkotlin.subscribeBy
import me.dmdev.rxpm.action
import me.dmdev.rxpm.command
import me.dmdev.rxpm.state
import ru.kompod.moonlike.data.analytics.AnalyticsDelegate
import ru.kompod.moonlike.domain.AppScreen
import ru.kompod.moonlike.domain.entity.base.MapObject
import ru.kompod.moonlike.domain.entity.base.MonsterObject
import ru.kompod.moonlike.domain.factory.spawner.SpawnDelegate
import ru.kompod.moonlike.domain.usecase.map.GetMapUseCase
import ru.kompod.moonlike.presentation.base.BasePresentationModel
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.map.adapter.MonsterAdapterDelegate
import ru.kompod.moonlike.presentation.feature.map.adapter.TravelAdapterDelegate
import ru.kompod.moonlike.presentation.feature.map.mapper.MapViewModelMapper
import ru.kompod.moonlike.presentation.feature.map.model.MonsterItem
import ru.kompod.moonlike.presentation.feature.map.model.TravelItem
import ru.kompod.moonlike.utils.NO_ID
import ru.kompod.moonlike.utils.ResourceDelegate
import ru.kompod.moonlike.utils.extensions.rxjava.*
import ru.kompod.moonlike.utils.extensions.rxpm.accept
import ru.kompod.moonlike.utils.navigation.BottomTabRouter
import javax.inject.Inject

class MapPresentationModel @Inject constructor(
    override val router: BottomTabRouter,
    resources: ResourceDelegate,
    analytics: AnalyticsDelegate,
    private val mapper: MapViewModelMapper,
    private val getMapUseCase: GetMapUseCase,
    private val spawnDelegate: SpawnDelegate
) : BasePresentationModel(router, resources, analytics),
    TravelAdapterDelegate.TravelItemListener,
    MonsterAdapterDelegate.MonsterItemListener {
    override val screen = AppScreen.MAP

    override val onTravelClickObserver = action<TravelItem>()
    override val onMonsterClickObserver = action<MonsterItem>()

    val onPauseTimerAction = action<Unit>()
    val onStartTimerAction = action<Int>()

    val mapLabelState = state<String>()
    val mapDelayState = state<Int>()
    val progressState = state(0)
    val mapPathState = state<String>()
    val objectListState = state<List<IListItem>>()
    val canTravelState = state(false)

    val loadMapByIdCommand = command<Short>()

    var cacheMap: MapObject? = null
    lateinit var timer: CountDownTimer

    override fun onCreate() {
        super.onCreate()
        initViewActions()
        initCommands()
        initData()
    }

    override fun onResume() {
        super.onResume()

        cacheMap?.let { map ->
            onStartTimerAction.accept(map.delay - progressState.value)
        }
    }

    override fun onPause() {
        super.onPause()

        onPauseTimerAction.accept()
    }

    private fun initViewActions() {
        onTravelClickObserver
            .observable
            .map { it.travel.to }
            .filter { canTravelState.value }
            .doOnNext { loadMapByIdCommand.accept(it) }
            .subscribeBy()
            .untilDestroy()

        onMonsterClickObserver
            .observable
            .map { it.monster }
            .doOnNext {
                spawnDelegate.killMonster(cacheMap?.id ?: NO_ID, it)
            }
            .subscribeBy()
            .untilDestroy()

        onPauseTimerAction
            .observable
            .doOnNext {
                timer.cancel()
            }
            .retry()
            .subscribeBy()
            .untilDestroy()

        onStartTimerAction
            .observable
            .doOnNext { startTimer(it) }
            .retry()
            .subscribeBy()
            .untilDestroy()
    }

    private fun initCommands() {
        loadMapByIdCommand
            .observable
            .flatMapSingle { id -> getMapUseCase.execute(id) }
            .observeOn(ui())
            .doOnNext { map ->
                cacheMap = map
                mapLabelState.accept(map.label)
                mapDelayState.accept(map.delay)
                mapPathState.accept(map.path)
                canTravelState.accept(false)
                progressState.accept(0)
                restartTimer(map.delay)
            }
            .observeOn(io())
            .flatMap { map ->
                combineLatest(
                    map.toObservable(),
                    spawnDelegate.observe { it.id == map.id }
                        .filter { it.mapObject.id == map.id }
                )
            }
            .flatMapSingle { (map, spawn) ->
                mapper.mapEntityToViewModel(
                    map,
                    spawn.monsters.toList()
                ).toSingle()
            }
            .observeOn(ui())
            .doOnNext { objects ->
                objectListState.accept(objects)
            }
            .subscribeBy()
            .untilDestroy()
    }

    private fun initData() {
        loadMapByIdCommand.accept(6)
    }

    private fun startTimer(seconds: Int) {
        timer = object : CountDownTimer(seconds.toLong(), 1) {
            val startValue = progressState.value

            override fun onTick(millisUntilFinished: Long) {
                progressState.accept(startValue + seconds - millisUntilFinished.toInt() + 1)
            }

            override fun onFinish() {
                canTravelState.accept(true)
            }
        }.start()
    }

    private fun restartTimer(delay: Int) {
        onPauseTimerAction.accept()
        onStartTimerAction.accept(delay)
    }
}