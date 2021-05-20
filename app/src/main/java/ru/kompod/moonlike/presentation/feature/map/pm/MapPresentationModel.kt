// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.map.pm

import android.os.CountDownTimer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import io.reactivex.rxkotlin.subscribeBy
import me.dmdev.rxpm.action
import me.dmdev.rxpm.command
import me.dmdev.rxpm.state
import ru.kompod.moonlike.data.analytics.AnalyticsDelegate
import ru.kompod.moonlike.domain.AppScreen
import ru.kompod.moonlike.domain.entity.base.CharacterObject
import ru.kompod.moonlike.domain.entity.base.MapObject
import ru.kompod.moonlike.utils.factory.spawner.SpawnDelegate
import ru.kompod.moonlike.domain.usecase.characters.GetSelectedCharacterUseCase
import ru.kompod.moonlike.domain.usecase.game.CalculateFightUseCase
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
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

class MapPresentationModel @Inject constructor(
    override val router: BottomTabRouter,
    resources: ResourceDelegate,
    analytics: AnalyticsDelegate,
    private val getSelectedCharacterUseCase: GetSelectedCharacterUseCase,
    private val calculateFightUseCase: CalculateFightUseCase,
    private val mapper: MapViewModelMapper,
    private val getMapUseCase: GetMapUseCase,
    private val spawnDelegate: SpawnDelegate
) : BasePresentationModel(router, resources, analytics),
    TravelAdapterDelegate.TravelItemListener,
    MonsterAdapterDelegate.MonsterItemListener {
    override val screen = AppScreen.MAP

    override val onTravelClickObserver = action<TravelItem>()
    override val onMonsterClickObserver = action<MonsterItem>()

    val bottomSheetActions = action<Int>()
    val onPauseTimerAction = action<Unit>()
    val onStartTimerAction = action<Int>()

    val characterState = state<CharacterObject>()
    val mapLabelState = state<String>()
    val mapDelayState = state<Int>()
    val progressState = state(0)
    val mapPathState = state<String>()
    val objectListState = state<List<IListItem>>()
    val canTravelState = state(false)
    val isBottomSheetHideable = state(true)

    val loadMapByIdCommand = command<Int>()

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

        getSelectedCharacterUseCase.observeHasSelected()
            .flatMap { hasSelected ->
                if (hasSelected) {
                    getSelectedCharacterUseCase.observeCharacterById()
                        .map { Provider<CharacterObject?> { it } }
                } else {
                    Provider<CharacterObject?> { null }
                        .toObservable()
                }
            }
            .doOnNext {
                if (it.get() != null)
                    characterState.accept(it.get()!!)
            }
            .retry()
            .subscribeBy()
            .untilPause()
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
            .map { it.onMapObj }
            .flatMap { calculateFightUseCase.execute(cacheMap?.id ?: NO_ID, it) }
            .doOnNext { (character, monster) ->
//                router.navigateTo(Screens.CharactersOnMap)
//                if (monster.monster.hp <= 0) {
//                    spawnDelegate.killMonster(cacheMap?.id ?: NO_ID, monster)
//                }
            }
            .retry()
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

        bottomSheetActions
            .observable
            .doOnNext { state ->
                when (state) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        isBottomSheetHideable.accept(true)
                    }
                }
            }
            .doOnError(Timber::e)
            .subscribe()
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
        loadMapByIdCommand.accept(1)
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