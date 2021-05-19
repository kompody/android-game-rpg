// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.core.view.forEach
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.view.visibility
import kotlinx.android.synthetic.main.progress_fullscreen.*
import me.dmdev.rxpm.bindTo
import me.dmdev.rxpm.widget.bindTo
import ru.kompod.moonlike.R
import ru.kompod.moonlike.di.DI
import ru.kompod.moonlike.utils.extensions.android.configureTopMargin
import ru.kompod.moonlike.utils.extensions.android.findTopFragment
import ru.kompod.moonlike.utils.extensions.android.setCallback
import ru.kompod.moonlike.utils.extensions.kotlin.castTo
import ru.kompod.moonlike.utils.extensions.kotlin.setScale
import ru.kompod.moonlike.utils.extensions.kotlin.unsafeCastTo
import ru.kompod.moonlike.utils.extensions.rxpm.accept
import toothpick.Scope
import toothpick.Toothpick
import toothpick.config.Module
import java.math.RoundingMode

abstract class BaseBottomSheetFragment<PM: BasePresentationModel>(
    @LayoutRes
    protected val layoutResource: Int,
    protected val isStatusBarSwitchRequired: Boolean
): PmBottomSheetFragment<PM>(), IScopeHolder, IBackDispatcher {
    protected open val activityOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    protected var toolbarView: View? = null
    protected var progressView: View? = null

    private var instanceStateSaved: Boolean = false

    protected var statusbarStub: View? = null

    protected open val parentScopeName: String by lazy {
        parentFragment
            .castTo<IScopeHolder>()
            ?.let { scopeHolder ->
                scopeHolder
                    .scope
                    .name
                    .toString()
            }
            ?: DI.UI_SCOPE
    }

    protected lateinit var scopeName: String

    override lateinit var scope: Scope

    override fun onCreate(savedInstanceState: Bundle?) {
        scopeName = savedInstanceState?.getString(SCOPE_NAME_KEY) ?: scopeName()
        scope = Toothpick.openScopes(parentScopeName, scopeName)
        installPresentationModelModules(scope)
        super.onCreate(savedInstanceState)
        installViewModules(scope)
        Toothpick.inject(this, scope)
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        dialog.setOnShowListener { dialog -> configureDialog(dialog.unsafeCastTo()) }
    }

    protected open fun setupSystemUiVisibility(isLightStatus: Boolean) {
        if (!isStatusBarSwitchRequired) return

        val systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        requireActivity().window?.decorView?.systemUiVisibility = systemUiVisibility
    }

    private fun configureDialog(bottomSheetDialog: BottomSheetDialog) {
        bottomSheetDialog
            .findViewById<View>(R.id.design_bottom_sheet)
            ?.let { container ->
                container
                    .parent
                    .castTo<ViewGroup>()
                    ?.let { coordinator ->
                        if (statusbarStub?.parent == null && isStatusBarSwitchRequired) {
                            coordinator.addView(statusbarStub)
                        }
                    }

                requireView().background = null
                container.background = null
                container.updateLayoutParams {
                    height = ViewGroup.LayoutParams.MATCH_PARENT
                }

                with(BottomSheetBehavior.from(container)) {
                    setCallback(
                        slide = { _, offset ->
                            if (!isStatusBarSwitchRequired && statusbarStub != null) return@setCallback

                            if (1f + offset >= 0.95f) {
                                val alpha = ((1.0 + offset) - 0.95) / 0.05

                                statusbarStub?.alpha = alpha.toFloat().setScale(2, RoundingMode.HALF_UP)

                                setupSystemUiVisibility(alpha > 0.5)
                            } else if (1f + offset < 0.95f && 1f + offset > 0) {
                                statusbarStub?.alpha = 0f
                            }
                        },
                        stateChanged = { _, state ->
                            setupSystemUiVisibility(state == BottomSheetBehavior.STATE_EXPANDED)

                            when (state) {
                                BottomSheetBehavior.STATE_EXPANDED -> statusbarStub?.alpha = 1f
                                BottomSheetBehavior.STATE_HIDDEN -> dispatchBackPressed()
                                else -> {}
                            }
                        }
                    )

                    state = BottomSheetBehavior.STATE_EXPANDED
                    peekHeight = bottomSheetDialog.window?.decorView?.layoutParams?.height ?: 0
                    skipCollapsed = true
                }
            }
    }

    protected open fun providePresentationModelModules(): Array<Module> = arrayOf()

    protected open fun provideViewModules(): Array<Module> = arrayOf()

    final override fun installPresentationModelModules(scope: Scope) {
        scope.installModules(*providePresentationModelModules())
    }

    final override fun installViewModules(scope: Scope) {
        scope.installModules(*provideViewModules())
    }

    final override fun onCreateView(inflater: LayoutInflater,
                                    container: ViewGroup?,
                                    savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(layoutResource, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object: BottomSheetDialog(requireContext(), theme) {
            override fun onBackPressed() {
                dispatchBackPressed()
            }

            override fun onCreate(savedInstanceState: Bundle?) {
                window?.let { window ->
                    with(window) {
                        addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                        setGravity(Gravity.TOP)

                        window.setLayout(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            requireActivity().window.decorView.height
                        )

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            attributes?.layoutInDisplayCutoutMode =
                                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        instanceStateSaved = false
        activity?.requestedOrientation = activityOrientation
        setupForInsets(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        statusbarStub = if (isStatusBarSwitchRequired) {
            View(requireContext()).apply {
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0)
                setBackgroundColor(Color.BLACK)
            }
        } else {
            null
        }

        toolbarView = view.findViewById(R.id.toolbar)
        progressView = view.findViewById(R.id.progress)
        onViewPreCreated()
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    protected open fun onViewPreCreated() {}

    override fun getTheme(): Int {
        return R.style.AppTheme_Dialog_BottomSheet_Fullscreen
    }

    @CallSuper
    protected open fun setupView() {
        setupProgressView()
        setupForInsets(false)
    }

    protected open fun setupForInsets(isForcedApply: Boolean) {
        if (!isForcedApply) {
            view?.setOnApplyWindowInsetsListener { _, windowInsets -> applyWindowInsets(windowInsets) }
        } else {
            activity?.let { activity -> activity.window.decorView.rootWindowInsets?.let(::applyWindowInsets) }
        }
    }

    private fun setupProgressView() {
        progressView?.findViewById<View>(R.id.progressBackButton)
            ?.clicks()
            ?.bindTo(presentationModel.backPressActions)
    }

    protected open fun applyWindowInsets(windowInsets: WindowInsets): WindowInsets {
        if (windowInsets.systemWindowInsetBottom != 0) {
            dialog?.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                requireActivity().window.decorView.height - windowInsets.systemWindowInsetBottom
            )
        }

        statusbarStub?.updateLayoutParams {
            if (windowInsets.systemWindowInsetTop != 0) {
                height = windowInsets.systemWindowInsetTop
            }
        }

        view?.configureTopMargin(windowInsets)

        view.castTo<ViewGroup>()?.forEach {
            it.dispatchApplyWindowInsets(windowInsets)
        }

        val isKeyboardVisible = windowInsets.systemWindowInsetBottom > 0
        presentationModel.keyboardVisibilityActions.consumer.accept(isKeyboardVisible)

        toolbarView?.configureTopMargin(windowInsets)
        progressView?.findViewById<View>(R.id.progressToolbar)?.configureTopMargin(windowInsets)
        progressView?.findViewById<View>(R.id.progressBar)?.configureTopMargin(windowInsets)

        return windowInsets
    }

    final override fun onBindPresentationModel(pm: PM) {
        pm.infoDialog
            .bindTo { data, dialogControl ->
                AlertDialog.Builder(requireContext(), R.style.AppTheme_Dialog_Info)
                    .setTitle(data.title)
                    .setMessage(data.message)
                    .setPositiveButton(data.positiveButtonText) { _, _ ->
                        dialogControl.sendResult(Unit)
                    }
                    .create()
            }

        progressView?.let { progressView ->
            pm.progressVisibility.bindTo(progressView.visibility())
            pm.progressBackButtonVisibility.bindTo(progressBackButton.visibility())
        }

        bindActions(pm)
        bindCommands(pm)
        bindStates(pm)
    }

    protected open fun bindActions(presentationModel: PM) {}

    protected open fun bindCommands(presentationModel: PM) {}

    protected open fun bindStates(presentationModel: PM) {}

    override fun dispatchBackPressed() {
        presentationModel.backPressActions.accept()
    }

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

    private fun isRealRemoving(): Boolean =
        (isRemoving && !instanceStateSaved) ||
                ((parentFragment as? BaseFragment<*>)?.isRealRemoving() ?: false)

    protected open fun scopeName() = "${javaClass.simpleName}_${hashCode()}"

    /**
     * Поиск верхнего фрагмента, который обработает onResume.
     * Может понадобиться в случае, если onResume должен вызваться не у самого верхнего
     * фрагмента в иерархии.
     * */
    open fun findTopFragment(): Fragment = childFragmentManager.findTopFragment() ?: this

    companion object {
        private const val SCOPE_NAME_KEY = "scope_name"
    }
}