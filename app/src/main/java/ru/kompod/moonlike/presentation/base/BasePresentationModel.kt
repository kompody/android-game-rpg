// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.base

import androidx.annotation.CallSuper
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import me.dmdev.rxpm.*
import me.dmdev.rxpm.widget.DialogControl
import me.dmdev.rxpm.widget.InputControl
import me.dmdev.rxpm.widget.dialogControl
import ru.kompod.moonlike.R
import ru.kompod.moonlike.data.analytics.AnalyticsDelegate
import ru.kompod.moonlike.data.analytics.AnalyticsEvent
import ru.kompod.moonlike.domain.AppScreen
import ru.kompod.moonlike.presentation.base.model.InfoDialogViewModel
import ru.kompod.moonlike.presentation.model.SnackBarViewModel
import ru.kompod.moonlike.utils.ResourceDelegate
import ru.kompod.moonlike.utils.extensions.kotlin.empty
import ru.kompod.moonlike.utils.extensions.rxpm.CustomInputControl
import ru.kompod.moonlike.utils.navigation.CustomRouter
import ru.terrakok.cicerone.Screen
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

abstract class BasePresentationModel(
    protected open val router: CustomRouter,
    protected val resources: ResourceDelegate,
    protected val analytics: AnalyticsDelegate
) : PresentationModel() {

    protected open val screen: AppScreen? = null

    val keyboardVisibilityActions = action<Boolean>()
    val backPressActions = action<Unit>()

    val showKeyboard = command<Boolean>()
    val showFab = command<Unit>()
    val showPlaceholder = command<Unit>()

    val infoDialog = dialogControl<InfoDialogViewModel, Unit>()
    val snackBarCommand = command<SnackBarViewModel>()

    val progressVisibility = state<Boolean>()
    val progressBackButtonVisibility = state<Boolean>()
    open val toolbarVisibility = state<Boolean>()
    val placeholderNoInternetVisibility = state<Boolean>()

    protected val isHidden = state(false)
    val hiddenConsumer = isHidden.consumer::accept

    @CallSuper
    override fun onCreate() {
        super.onCreate()

        backPressActions
            .observable
            .doOnNext { dispatchBackAction() }
            .doOnError(Timber::e)
            .subscribe()
            .untilDestroy()
    }

    protected open fun dispatchBackAction() {
        router.exit()
    }

    private fun navigateTo(screen: Screen): Completable {
        return Completable.fromCallable { router.navigateTo(screen) }
    }

    protected fun <T> State<T>.accept(value: T) {
        this.consumer.accept(value)
    }

    protected fun Command<Unit>.accept() {
        this.consumer.accept(Unit)
    }

    protected fun <T> Command<T>.accept(value: T) {
        this.consumer.accept(value)
    }

    protected infix fun <T> T.passTo(command: Command<T>) {
        command.consumer.accept(this)
    }

    protected infix fun <T> T.passTo(state: State<T>) {
        state.consumer.accept(this)
    }

    protected fun <T> Observable<T>.mergeWith(action: me.dmdev.rxpm.Action<T>): Observable<T> {
        return mergeWith(action.observable)
    }

    protected fun <T> Observable<T>.subscribe(subscriber: State<T>): Disposable {
        return subscribe(subscriber.consumer)
    }

    protected fun <T> Single<T>.subscribe(subscriber: State<T>): Disposable {
        return subscribe(subscriber.consumer)
    }

    protected fun <T> Observable<T>.subscribe(subscriber: Command<T>): Disposable {
        return subscribe(subscriber.consumer)
    }

    protected fun InputControl.resetError() {
        error.accept(String.empty)
    }

    protected fun <T> Observable<T>.bindProgress(progressConsumer: Consumer<Boolean>): Observable<T> {
        return this
            .doOnSubscribe { progressConsumer.accept(true) }
            .doFinally { progressConsumer.accept(false) }
    }

    protected fun <T> Observable<T>.handleCommonErrors(
        onNetworkErrorCompletable: Completable = showNetworkErrorSnackBar(resources),
        onUnknownErrorCompletable: Completable = showUnknownErrorSnackBar(resources)
    ): Observable<T> {
        return retryWhen { errors ->
            errors
                .switchMapSingle { throwable ->
                    Timber.e(throwable)
                    when (throwable) {
                        is SSLHandshakeException,
                        is SocketTimeoutException,
                        is UnknownHostException -> {
                            onNetworkErrorCompletable
                                .andThen(Single.just(Unit))
                        }
                        else -> {
                            showErrorSnackBar(throwable.message ?: "")
                                .andThen(Single.just(Unit))
                        }
                    }
                }
        }
    }

    private fun showNetworkErrorSnackBar(resourceDelegate: ResourceDelegate): Completable =
        showErrorMessage(resourceDelegate.getString(R.string.no_internet))

    private fun showUnknownErrorSnackBar(resourceDelegate: ResourceDelegate): Completable =
        showErrorMessage(resourceDelegate.getString(R.string.something_went_wrong))

    private fun showErrorSnackBar(message: String): Completable =
        showErrorMessage(message)

    protected fun showErrorMessage(errorMessage: String): Completable = Completable.fromAction {
        snackBarCommand.accept(SnackBarViewModel(errorMessage))
    }

    protected fun CustomInputControl.reset() {
        error.accept(String.empty)
        text.accept(String.empty)
    }

    protected fun <R> DialogControl<Unit, R>.show() {
        this.show(Unit)
    }

    protected fun <R> DialogControl<Unit, R>.showForResult(): Maybe<R> {
        return showForResult(Unit)
    }
}