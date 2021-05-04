// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.plugins.RxJavaPlugins
import ru.kompod.moonlike.R
import ru.kompod.moonlike.Screens
import ru.kompod.moonlike.di.DI
import ru.kompod.moonlike.di.module.UiModule
import ru.kompod.moonlike.presentation.base.IBackDispatcher
import ru.kompod.moonlike.utils.extensions.android.buildSupportAlertDialog
import ru.kompod.moonlike.utils.extensions.kotlin.castTo
import ru.kompod.moonlike.utils.navigation.CustomAppNavigator
import ru.kompod.moonlike.utils.navigation.CustomRouter
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import timber.log.Timber
import toothpick.Toothpick
import javax.inject.Inject

class AppActivity : AppCompatActivity() {
    companion object {
        private const val CONTAINER_ID = R.id.rootContainer

        const val PAYLOAD_KEY = "payload"
    }

    @Inject
    lateinit var disposable: CompositeDisposable

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: CustomRouter

    private val navigator: Navigator by lazy {
        CustomAppNavigator(this, supportFragmentManager, CONTAINER_ID)
    }

    private lateinit var errorDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        errorDialog = buildSupportAlertDialog(this, R.style.AppTheme_Dialog_Info) {
            setMessage(R.string.unexpected_error)

            setOnCancelListener { dialog ->
                dialog.dismiss()
            }

            setPositiveButton(R.string.button_ok) { dialog, _ ->
                dialog.dismiss()
            }
        }

        RxJavaPlugins.setErrorHandler { error ->
            Timber.e(error)

            if (!errorDialog.isShowing) {
                window.decorView.post {
                    errorDialog.show()
                }
            }
        }

        Toothpick.openScopes(DI.APP_SCOPE, DI.UI_SCOPE)
            .installModules(UiModule())
            .let { scope ->
                Toothpick.inject(this, scope)
            }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity)

        router.newRootScreen(Screens.SplashScreen)
    }

    override fun onDestroy() {
        disposable.clear()
        Toothpick.closeScope(DI.UI_SCOPE)
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        supportFragmentManager
            .findFragmentById(CONTAINER_ID)
            ?.castTo<IBackDispatcher>()
            ?.dispatchBackPressed() ?: super.onBackPressed()
    }
}