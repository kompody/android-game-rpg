// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.factory.spawner

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import ru.kompod.moonlike.domain.entity.base.MapObject
import ru.kompod.moonlike.domain.entity.base.MonsterObject
import ru.kompod.moonlike.domain.repository.IAssetRepository
import ru.kompod.moonlike.utils.NO_ID
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.min
import kotlin.math.roundToInt

class SpawnDelegate @Inject constructor(
    private val timerDelegate: TimerDelegate,
    private val assetRepository: IAssetRepository
) : TimerDelegate.TickEmitter {

    class Spawner(
        val mapObject: MapObject,
        val monsters: MutableList<MonsterObject>,
        val monstersPool: MutableList<MonsterObject>
    )

    sealed class Command {
        class RefreshCommand(val mapId: Short) : Command()
        class KillMonsterCommand(val mapId: Short, val monster: MonsterObject) : Command()
    }

    private var filter: (MapObject) -> Boolean = { false }

    private val publisher = BehaviorSubject.create<Spawner>()
    private val commandPublisher = PublishSubject.create<Command>()

    private var disposable = CompositeDisposable()

    private var spawners: List<Spawner> = listOf()

    init {
        spawners = assetRepository.getMaps()
            .map { map ->
                val monsterPool: MutableList<MonsterObject> = generateMonsterPool(
                    map.monsterLimit * 2,
                    map.monsters
                )
                monsterPool.shuffle()

                val monsters: MutableList<MonsterObject> =
                    monsterPool.subList(0, min(map.monsters.size, map.monsterLimit))
                        .sortedBy { it.id }
                        .map {
                            it.apply { isLife = true }
                        }
                        .toMutableList()
                monsterPool.removeAll(monsters)

                Spawner(
                    map,
                    monsters,
                    monsterPool
                )
            }

        commandPublisher
            .delay(100, TimeUnit.MICROSECONDS)
            .doOnNext {
                when (it) {
                    is Command.RefreshCommand -> handleRefreshCommand(it)
                    is Command.KillMonsterCommand -> handleKillMonsterCommand(it)
                }
            }
            .retry()
            .subscribe()
            .also { disposable.add(it) }
    }

    private fun handleRefreshCommand(command: Command.RefreshCommand) {
        with(command) {
            val spawner = spawners.first { it.mapObject.id == mapId }
            with(spawner) {
                if (monsters.size <= mapObject.monsterLimit) {
                    val limit = mapObject.monsterLimit - monsters.size

                    val endIndex = if (monstersPool.size < limit) monstersPool.size else limit

                    val pool = monstersPool.subList(0, endIndex).filter {
                        it.timeDeath < Calendar.getInstance().timeInMillis - it.delay
                    }
                    if (pool.isNotEmpty()) {
                        monstersPool.removeAll(pool)

                        pool.forEach {
                            it.isLife = true

                            Timber.d("${it.label} is alive")
                        }

                        monsters.addAll(pool)
                        monsters.sortBy { it.id }

                        if (filter.invoke(mapObject)) {
                            publisher.onNext(this)
                        }
                    }
                }
            }
        }
    }

    private fun handleKillMonsterCommand(command: Command.KillMonsterCommand) {
        with(command) {
            spawners.firstOrNull { it.mapObject.id == mapId }?.apply {
                val index = monsters.indexOf(monster)

                if (index >= 0) {
                    val oldMonster = monsters.removeAt(index)
                    oldMonster.isLife = false

                    monstersPool.add(oldMonster)

                    val limit = mapObject.monsterLimit - monsters.size

                    val endIndex = if (monstersPool.size < limit) monstersPool.size else limit

                    monstersPool.subList(0, endIndex).forEach {
                        val isNeedRefreshTimeDeath =
                            it.timeDeath < Calendar.getInstance().timeInMillis - it.delay
                        if (isNeedRefreshTimeDeath) {
                            it.timeDeath = Calendar.getInstance().timeInMillis
                        }
                    }

                    if (filter.invoke(mapObject)) {
                        publisher.onNext(this)
                    }
                }
            }
        }
    }

    private fun generateMonsterPool(
        poolSize: Int,
        original: List<MonsterObject>
    ): MutableList<MonsterObject> {
        val monstersPool = mutableListOf<MonsterObject>()

        original.forEach { obj ->
            monstersPool.addAll(
                arrayOf(
                    *Array((poolSize * obj.spawnRate).roundToInt()) {
                        obj.copy(idOnPool = it.toShort())
                    })
            )
        }

        return monstersPool
    }

    fun start() {
        timerDelegate.subscribe(this)
    }

    fun stop() {
        timerDelegate.unsubscribe(this)

        disposable.dispose()
    }

    fun observe(filter: (MapObject) -> Boolean): Observable<Spawner> {
        this.filter = filter
        publisher.onNext(spawners.first { filter.invoke(it.mapObject) })
        return publisher
    }

    fun killMonster(mapId: Short, monster: MonsterObject) {
        commandPublisher.onNext(Command.KillMonsterCommand(mapId, monster))
    }

    override fun emmit() {
        spawners.forEach { spawner ->
            with(spawner) {
                if (monsters.size >= mapObject.monsterLimit) return@with

                commandPublisher.onNext(Command.RefreshCommand(mapObject.id))
            }
        }
    }
}