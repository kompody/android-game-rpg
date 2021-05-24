// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.base

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.view.visibility
import kotlinx.android.synthetic.main.progress_fullscreen.*
import me.dmdev.rxpm.base.PmFragment
import me.dmdev.rxpm.bindTo
import me.dmdev.rxpm.widget.bindTo
import ru.kompod.moonlike.BuildConfig
import ru.kompod.moonlike.R
import ru.kompod.moonlike.di.DI
import ru.kompod.moonlike.presentation.custom.CustomSnackBar
import ru.kompod.moonlike.presentation.custom.coordinator.SlideFabBehavior
import ru.kompod.moonlike.utils.FloatingActionButtonHelper
import ru.kompod.moonlike.utils.extensions.android.*
import ru.kompod.moonlike.utils.extensions.kotlin.castTo
import ru.kompod.moonlike.utils.extensions.kotlin.unsafeCastTo
import ru.kompod.moonlike.utils.extensions.rxpm.accept
import toothpick.Scope
import toothpick.Toothpick
import toothpick.config.Module
import java.lang.reflect.Method
import javax.inject.Inject

abstract class BaseFragment<PresentationModel : BasePresentationModel>(
    @LayoutRes
    protected val layoutResource: Int
) : PmFragment<PresentationModel>(), IScopeHolder, IBackDispatcher {
    protected open val activityOrientation =
//        if (BuildConfig.DEBUG)
//            ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
//        else
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    protected var toolbarView: View? = null
    protected var backButton: View? = null
    protected var progressView: View? = null
    protected var floatingActionButton: View? = null
    protected var placeholderNoInternet: View? = null

    private var instanceStateSaved: Boolean = false

    protected open val parentScopeName: String by lazy {
        parentFragment.castTo<IScopeHolder>()?.scope?.name?.toString() ?: DI.UI_SCOPE
    }

    protected open val consumePendingUpdateOperationsMethod: Method by lazy {
        RecyclerView::class.java.getDeclaredMethod("consumePendingUpdateOperations").apply {
            isAccessible = true
        }
    }

    protected lateinit var scopeName: String

    override lateinit var scope: Scope

    protected open val isLightStatus
        get() = true

    abstract val isFabRequired: Boolean

    @Inject
    lateinit var fabHelper: FloatingActionButtonHelper

    protected open var fabSlidingBehavior: SlideFabBehavior? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        scopeName = savedInstanceState?.getString(SCOPE_NAME_KEY) ?: scopeName()
        scope = Toothpick.openScopes(parentScopeName, scopeName)
        installPresentationModelModules(scope)
        super.onCreate(savedInstanceState)
        installViewModules(scope)
        Toothpick.inject(this, scope)
    }

    protected open fun providePresentationModelModules(): Array<Module> = arrayOf()

    protected open fun provideViewModules(): Array<Module> = arrayOf()

    final override fun installPresentationModelModules(scope: Scope) {
        scope.installModules(*providePresentationModelModules())
    }

    final override fun installViewModules(scope: Scope) {
        scope.installModules(*provideViewModules())
    }

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutResource, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        floatingActionButton = null
    }

    override fun onResume() {
        super.onResume()
        instanceStateSaved = false
        activity?.requestedOrientation = activityOrientation
        changeScreenParameters(isHidden)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        presentationModel.hiddenConsumer(hidden)
        changeScreenParameters(hidden)
    }

    protected fun changeScreenParameters(hidden: Boolean) {
        if (hidden) return

        setupSystemUiVisibility()
        view?.requestApplyInsets()
    }

    protected open fun setupSystemUiVisibility() {
        var systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        if (isLightStatus)
            systemUiVisibility += View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        activity?.window?.decorView?.systemUiVisibility = systemUiVisibility
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onViewPreCreated()
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    @CallSuper
    protected open fun onViewPreCreated() {
        toolbarView = view?.findViewById(R.id.toolbar)
        toolbarView?.isVisible = presentationModel.toolbarVisibility.valueOrNull ?: true
        backButton = view?.findViewById(R.id.backButton)
        progressView = view?.findViewById(R.id.progress)
//        placeholderNoInternet = view?.findViewById(R.id.placeholderNoInternet)
//        placeholderNoInternet?.isVisible = presentationModel.placeholderNoInternetVisibility.valueOrNull ?: false
        initializeFAB()
    }

    @CallSuper
    protected open fun setupView() {
        setupForInsets()
        setBackButtonView()
        setupProgressView()
    }

    protected open fun setupForInsets() {
        view?.setOnApplyWindowInsetsListener { _, windowInsets -> applyWindowInsets(windowInsets) }
        activity?.let { activity -> activity.window.decorView.rootWindowInsets?.let(::applyWindowInsets) }
    }

    private fun setupProgressView() {
        progressView?.findViewById<View>(R.id.progressBackButton)
            ?.clicks()
            ?.bindTo(presentationModel.backPressActions)
    }

    private fun setBackButtonView() {
        backButton?.clicks()?.bindTo(presentationModel.backPressActions)
    }

    @CallSuper
    protected open fun applyWindowInsets(windowInsets: WindowInsets): WindowInsets {
        view.castTo<ViewGroup>()?.forEach {
            it.dispatchApplyWindowInsets(windowInsets)
        }

        val isKeyboardVisible =
            windowInsets.systemWindowInsetBottom.toFloat() / resources.displayMetrics.heightPixels.toFloat() > 0.1f
        presentationModel.keyboardVisibilityActions.consumer.accept(isKeyboardVisible)

        toolbarView?.configureTopMargin(windowInsets)
        progressView?.findViewById<View>(R.id.progressToolbar)?.configureTopMargin(windowInsets)
        progressView?.findViewById<View>(R.id.progressBar)?.configureTopMargin(windowInsets)

        return windowInsets
    }

    final override fun onBindPresentationModel(pm: PresentationModel) {
        pm.showPlaceholder.bindTo {
            requireView().castTo<ViewGroup>()?.addView(
                requireContext().layoutInflater.inflate(
                    R.layout.item_placeholder,
                    requireView().castTo<ViewGroup>(),
                    false
                )
            )
        }

        presentationModel.showFab.bindTo {
            fabSlidingBehavior?.slideUp(floatingActionButton.unsafeCastTo())
        }

        pm.infoDialog
            .bindTo { data, dialogControl ->
                AlertDialog.Builder(requireContext(), R.style.AppTheme_Dialog_Info)
                    .setTitle(data.title)
                    .setMessage(data.message)
                    .setPositiveButton(data.positiveButtonText) { _, _ ->
                        dialogControl.sendResult(Unit)
                    }
                    .setOnCancelListener {
                        dialogControl.sendResult(Unit)
                    }
                    .create()
            }

        presentationModel.snackBarCommand
            .bindTo { model ->
                CustomSnackBar.make(requireView().unsafeCastTo()).apply {
                    setText(model.text)
                    model.iconRes?.let(::setIconRes)
                    show()
                }
            }

        progressView?.let { progressView ->
            pm.progressVisibility.bindTo(progressView.visibility())

            progressBackButton?.let { progressBackButton ->
                pm.progressBackButtonVisibility.bindTo(progressBackButton.visibility())
            }
        }

        toolbarView?.let { toolbarView ->
            presentationModel.toolbarVisibility.bindTo(toolbarView.visibility())
        }

        presentationModel.showKeyboard.bindTo { show ->
            if (show) {
                requireView().showKeyboard()
            } else {
                requireView().hideKeyboard()
            }
        }

        placeholderNoInternet?.let { placeholderNoInternet ->
            presentationModel.placeholderNoInternetVisibility.bindTo(placeholderNoInternet::setVisibleOrGone)
        }

        bindActions(pm)
        bindCommands(pm)
        bindStates(pm)
    }

    protected open fun bindActions(presentationModel: PresentationModel) {}

    protected open fun bindCommands(presentationModel: PresentationModel) {}

    protected open fun bindStates(presentationModel: PresentationModel) {}

    override fun dispatchBackPressed() {
        presentationModel.backPressActions.accept()
    }

    @CallSuper
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SCOPE_NAME_KEY, scopeName)
        instanceStateSaved = true
    }

    override fun onDestroy() {
        super.onDestroy()
        if (needCloseScope())
            Toothpick.closeScope(scope.name)
    }

    private fun needCloseScope(): Boolean =
        when {
            activity?.isChangingConfigurations == true -> false
            activity?.isFinishing == true -> true
            else -> isRealRemoving()
        }

    internal fun isRealRemoving(): Boolean =
        (isRemoving && !instanceStateSaved) ||
                ((parentFragment as? BaseFragment<*>)?.isRealRemoving() ?: false)

    protected open fun scopeName() = "${javaClass.simpleName}_${hashCode()}"

    /**
     * Поиск верхнего фрагмента, который обработает onResume.
     * Может понадобиться в случае, если onResume должен вызваться не у самого верхнего
     * фрагмента в иерархии.
     * */
    open fun findTopFragment(): Fragment = childFragmentManager.findTopFragment() ?: this

    protected fun setArguments(vararg pairs: Pair<String, Any>) {
        arguments = bundleOf(*pairs)
    }

    protected open fun initializeFAB() {
        if (isFabRequired) {
            floatingActionButton = fabHelper.createFab()
            view.castTo<ViewGroup>()?.addView(floatingActionButton)
            fabSlidingBehavior = SlideFabBehavior.from(floatingActionButton!!)
        }
    }

    companion object {
        private const val SCOPE_NAME_KEY = "scope_name"
    }
}