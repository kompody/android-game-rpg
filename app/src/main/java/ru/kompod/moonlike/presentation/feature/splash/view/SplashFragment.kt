/* Copyright (c) 2021 Kompod. All rights reserved
 * Description: Фрагмент загрузочного экрана
 * Author:
 * Project: Player
 */

package ru.kompod.moonlike.presentation.feature.splash.view

import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.BaseFragment
import ru.kompod.moonlike.presentation.feature.splash.pm.SplashPresentationModel
import ru.kompod.moonlike.utils.ResourceDelegate
import ru.kompod.moonlike.utils.extensions.rxpm.accept
import ru.kompod.moonlike.utils.extensions.toothpick.getInstance
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashFragment : BaseFragment<SplashPresentationModel>(R.layout.fragment_splash) {

    override val isFabRequired: Boolean = false

    @Inject
    lateinit var destroyViewDisposable: CompositeDisposable

    @Inject
    lateinit var picasso: Picasso

    @Inject
    lateinit var resourceDelegate: ResourceDelegate

    override fun providePresentationModel(): SplashPresentationModel = scope.getInstance()

    override fun bindCommands(presentationModel: SplashPresentationModel) {
    }

    override fun bindStates(presentationModel: SplashPresentationModel) {
        presentationModel
            .splashState
            .observable
            .delay(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                presentationModel.onAnimationFinish.accept(true)
            }
            .subscribe()
            .let(destroyViewDisposable::add)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        destroyViewDisposable.clear()
    }

    override fun dispatchBackPressed() {}
}