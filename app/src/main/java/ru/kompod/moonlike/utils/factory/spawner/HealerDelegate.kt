// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.factory.spawner

import io.reactivex.disposables.CompositeDisposable
import ru.kompod.moonlike.domain.usecase.characters.GetCharactersUseCase
import ru.kompod.moonlike.domain.usecase.characters.GetSelectedCharacterUseCase
import ru.kompod.moonlike.domain.usecase.characters.SaveCharacterUseCase
import ru.kompod.moonlike.utils.RECOVERY_HEAL_POINT_INTERVAL
import ru.kompod.moonlike.utils.RECOVERY_SPELL_POINT_INTERVAL
import ru.kompod.moonlike.utils.extensions.rxjava.io
import ru.kompod.moonlike.utils.factory.util.Action
import ru.kompod.moonlike.utils.factory.util.Command
import ru.kompod.moonlike.utils.factory.util.EventBusDelegate
import ru.kompod.moonlike.utils.factory.util.TimerDelegate
import timber.log.Timber
import javax.inject.Inject

class HealerDelegate @Inject constructor(
    private val timerDelegate: TimerDelegate,
    private val eventBusDelegate: EventBusDelegate,
    private val getSelectedCharacterUseCase: GetSelectedCharacterUseCase,
    private val saveCharacterUseCase: SaveCharacterUseCase
) : TimerDelegate.TickEmitter {

    private var disposable = CompositeDisposable()

    init {
        eventBusDelegate.observeCommand()
            .observeOn(io())
            .doOnNext { command  ->
                when (command) {
                    is Command.RecoveryHealCharacterCommand ->
                        handleRecoveryHealCharacterCommand(command.hp, command.sp)
                }
            }
            .subscribe()
            .also { disposable.add(it) }
    }

    fun start() {
        timerDelegate.subscribe(this)
    }

    fun stop() {
        timerDelegate.unsubscribe(this)

        disposable.dispose()
    }

    override fun emmit(time: Long) {
        var healHp = 0
        var healSp = 0
        if (time % RECOVERY_HEAL_POINT_INTERVAL == 0L)
            healHp = 1
        if (time % RECOVERY_SPELL_POINT_INTERVAL == 0L)
            healSp = 1

        if (healHp == 1 || healSp == 1) {
            eventBusDelegate.action(Action.RecoveryHealCharacterAction(healHp, healSp))
        }
    }

    private fun handleRecoveryHealCharacterCommand(healHp: Int, healSp: Int) {
        getSelectedCharacterUseCase.getCharacterById()
            .map {
                it.copy(
                    hp = (it.hp + healHp).coerceAtMost(it.baseHp)
//                    sp = (it.sp + healSp).coerceAtMost(it.baseSp)
                )
            }
            .flatMapSingle { saveCharacterUseCase.execute(it) }
            .doOnNext { Timber.d("Recovery hp:$healHp sp:$healSp") }
            .subscribe()
            .also { disposable.add(it) }
    }
}