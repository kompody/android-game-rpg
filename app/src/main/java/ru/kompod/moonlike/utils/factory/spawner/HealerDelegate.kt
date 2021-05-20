// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.factory.spawner

import io.reactivex.disposables.CompositeDisposable
import ru.kompod.moonlike.domain.usecase.characters.GetCharactersUseCase
import ru.kompod.moonlike.domain.usecase.characters.GetSelectedCharacterUseCase
import ru.kompod.moonlike.domain.usecase.characters.SaveCharacterUseCase
import ru.kompod.moonlike.utils.factory.util.Action
import ru.kompod.moonlike.utils.factory.util.Command
import ru.kompod.moonlike.utils.factory.util.EventBusDelegate
import ru.kompod.moonlike.utils.factory.util.TimerDelegate
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
            .doOnNext {
                when (it) {
                    is Command.RecoveryHealCharacterCommand -> handleRecoveryHealCharacterCommand()
                    is Command.RecoverySpellPointCharacterCommand -> handleRecoverySpellPointCharacterCommand()
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
        when {
            time % 4 == 0L -> eventBusDelegate.action(Action.RecoveryHealCharacterCommand)
            time % 6 == 0L -> eventBusDelegate.action(Action.RecoveryHealCharacterCommand)
        }
    }

    private fun handleRecoveryHealCharacterCommand() {
        getSelectedCharacterUseCase.getCharacterById()
            .map {
                it.copy(
                    hp = (it.hp + 1).coerceAtMost(it.baseHp)
                )
            }
            .flatMapSingle { saveCharacterUseCase.execute(it) }
            .subscribe()
            .also { disposable.add(it) }
    }

    private fun handleRecoverySpellPointCharacterCommand() {
//        getSelectedCharacterUseCase.getCharacterById()
//            .map {
//                it.copy(
//                    sp = (it.sp + 1).coerceAtMost(it.baseHp)
//                )
//            }
//            .flatMapSingle { saveCharacterUseCase.execute(it) }
//            .subscribe()
//            .also { disposable.add(it) }
    }
}