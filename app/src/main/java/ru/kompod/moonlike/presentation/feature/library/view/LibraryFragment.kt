// Copyright (c) 2025 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.library.view

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_characters_list.*
import kotlinx.android.synthetic.main.fragment_library.*
import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.BaseFragment
import ru.kompod.moonlike.presentation.base.recyclerview.adapter.DefaultDiffCallback
import ru.kompod.moonlike.presentation.base.recyclerview.adapter.ListItemAdapter
import ru.kompod.moonlike.presentation.base.recyclerview.decorator.VerticalListMarginDecorator
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.library.adapter.TitleAdapterDelegate
import ru.kompod.moonlike.presentation.feature.library.model.TitleListItem
import ru.kompod.moonlike.presentation.feature.library.pm.LibraryPresentationModel
import ru.kompod.moonlike.utils.extensions.kotlin.dp
import ru.kompod.moonlike.utils.extensions.rxjava.ui
import ru.kompod.moonlike.utils.extensions.toothpick.bind
import ru.kompod.moonlike.utils.extensions.toothpick.getInstance
import ru.kompod.moonlike.utils.extensions.toothpick.moduleOf
import toothpick.config.Module
import javax.inject.Inject

class LibraryFragment : BaseFragment<LibraryPresentationModel>(R.layout.fragment_library) {
    override val isFabRequired: Boolean = false

    @Inject
    lateinit var destroyViewDisposable: CompositeDisposable

    @Inject
    lateinit var menuAdapter: ListItemAdapter

    override fun providePresentationModel(): LibraryPresentationModel = scope.getInstance()

    override fun provideViewModules(): Array<Module> = arrayOf(
        moduleOf {
            bind<DiffUtil.ItemCallback<IListItem>>().toInstance(DefaultDiffCallback())
            bind<Set<AdapterDelegate<List<IListItem>>>>().toInstance(
                setOf(
                    TitleAdapterDelegate(presentationModel).get()
                )
            )
        }
    )

    override fun bindActions(presentationModel: LibraryPresentationModel) {
        super.bindActions(presentationModel)
    }

    override fun bindCommands(presentationModel: LibraryPresentationModel) {
        super.bindCommands(presentationModel)
    }

    override fun bindStates(presentationModel: LibraryPresentationModel) {
        presentationModel
            .menuListState
            .observable
            .doOnNext(menuAdapter::setItems)
            .observeOn(ui())
            .doOnNext {
                charactersRecyclerView?.let {
                    consumePendingUpdateOperationsMethod.invoke(
                        charactersRecyclerView
                    )
                }
            }
            .subscribe()
            .let(destroyViewDisposable::add)
    }

    override fun onDestroy() {
        super.onDestroy()

        destroyViewDisposable.clear()
    }

    override fun setupView() {
        super.setupView()
        with(menuRecyclerView) {
            adapter = menuAdapter
            addItemDecoration(
                VerticalListMarginDecorator(
                    applyFor = setOf(
                        TitleListItem::class
                    ),
                    margin = 8.dp,
                    boundaryMargin = 16.dp
                )
            )
        }
    }
}