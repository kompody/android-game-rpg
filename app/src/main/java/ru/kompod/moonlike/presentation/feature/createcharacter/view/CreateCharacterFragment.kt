// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.createcharacter.view

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_create_character.*
import me.dmdev.rxpm.bindTo
import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.BaseFragment
import ru.kompod.moonlike.presentation.base.recyclerview.adapter.DefaultDiffCallback
import ru.kompod.moonlike.presentation.base.recyclerview.adapter.ListItemAdapter
import ru.kompod.moonlike.presentation.base.recyclerview.decorator.VerticalListMarginDecorator
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.createcharacter.adapter.*
import ru.kompod.moonlike.presentation.feature.createcharacter.model.*
import ru.kompod.moonlike.presentation.feature.createcharacter.pm.CreateRacePresentationModel
import ru.kompod.moonlike.utils.extensions.kotlin.dp
import ru.kompod.moonlike.utils.extensions.toothpick.bind
import ru.kompod.moonlike.utils.extensions.toothpick.getInstance
import ru.kompod.moonlike.utils.extensions.toothpick.moduleOf
import toothpick.config.Module
import javax.inject.Inject

class CreateCharacterFragment :
    BaseFragment<CreateRacePresentationModel>(R.layout.fragment_create_character) {
    override val isFabRequired: Boolean = false

    @Inject
    lateinit var destroyViewDisposable: CompositeDisposable

    @Inject
    lateinit var creatorMenuAdapter: ListItemAdapter

    override fun providePresentationModel(): CreateRacePresentationModel = scope.getInstance()

    override fun provideViewModules(): Array<Module> = arrayOf(
        moduleOf {
            bind<DiffUtil.ItemCallback<IListItem>>().toInstance(
                DefaultDiffCallback()
            )
            bind<Set<AdapterDelegate<List<IListItem>>>>().toInstance(
                setOf(
                    RaceAdapterDelegate(presentationModel).get(),
                    GenderAdapterDelegate(presentationModel).get(),
                    IconAdapterDelegate(
                        presentationModel,
                        scope.getInstance(),
                        scope.getInstance()
                    ).get(),
                    RoleAdapterDelegate(presentationModel).get(),
                    CharacterAboutAdapterDelegate().get()
                )
            )
        }
    )

    override fun bindActions(presentationModel: CreateRacePresentationModel) {
        createButton.clicks().bindTo(presentationModel.createButtonClickObservable)
    }

    override fun bindStates(presentationModel: CreateRacePresentationModel) {
        val consumePendingUpdateOperationsMethod =
            RecyclerView::class.java.getDeclaredMethod("consumePendingUpdateOperations").apply {
                isAccessible = true
            }

        presentationModel
            .menuListState
            .observable
            .doOnNext(creatorMenuAdapter::setItems)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                creatorMenuRecyclerView?.let {
                    consumePendingUpdateOperationsMethod.invoke(
                        creatorMenuRecyclerView
                    )
                }
            }
            .subscribe()
            .let(destroyViewDisposable::add)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        destroyViewDisposable.clear()
    }

    override fun setupView() {
        super.setupView()
        with(creatorMenuRecyclerView) {
            adapter = creatorMenuAdapter
            addItemDecoration(
                VerticalListMarginDecorator(
                    applyFor = setOf(
                        RaceItem::class,
                        GenderItem::class,
                        PortraitItem::class,
                        RoleItem::class,
                        CharacterAboutItem::class
                    ),
                    boundaryItemMargin = 8.dp,
                    circleMargin = 16.dp
                )
            )
        }
    }
}