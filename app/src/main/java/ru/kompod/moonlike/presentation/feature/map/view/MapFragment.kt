// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.map.view

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_map.*
import me.dmdev.rxpm.bindTo
import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.BaseFragment
import ru.kompod.moonlike.presentation.base.recyclerview.adapter.DefaultDiffCallback
import ru.kompod.moonlike.presentation.base.recyclerview.adapter.ListItemAdapter
import ru.kompod.moonlike.presentation.base.recyclerview.decorator.VerticalListMarginDecorator
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.map.adapter.TitleAdapterDelegate
import ru.kompod.moonlike.presentation.feature.map.adapter.TravelAdapterDelegate
import ru.kompod.moonlike.presentation.feature.map.model.TitleListItem
import ru.kompod.moonlike.presentation.feature.map.model.TravelItem
import ru.kompod.moonlike.presentation.feature.map.pm.MapPresentationModel
import ru.kompod.moonlike.utils.extensions.kotlin.dp
import ru.kompod.moonlike.utils.extensions.toothpick.bind
import ru.kompod.moonlike.utils.extensions.toothpick.getInstance
import ru.kompod.moonlike.utils.extensions.toothpick.moduleOf
import ru.kompod.moonlike.utils.picasso.PicassoUtil
import ru.kompod.moonlike.utils.picasso.PixelTargetAdapter
import ru.kompod.moonlike.utils.tools.AssetProvider
import toothpick.config.Module
import javax.inject.Inject

class MapFragment : BaseFragment<MapPresentationModel>(R.layout.fragment_map) {
    override val isFabRequired: Boolean = false

    @Inject
    lateinit var destroyViewDisposable: CompositeDisposable

    @Inject
    lateinit var objectAdapter: ListItemAdapter

    @Inject
    lateinit var assetProvider: AssetProvider

    @Inject
    lateinit var picasso: PicassoUtil

    override fun providePresentationModel(): MapPresentationModel = scope.getInstance()

    override fun provideViewModules(): Array<Module> = arrayOf(
        moduleOf {
            bind<DiffUtil.ItemCallback<IListItem>>().toInstance(
                DefaultDiffCallback()
            )
            bind<Set<AdapterDelegate<List<IListItem>>>>().toInstance(
                setOf(
                    TitleAdapterDelegate().get(),
                    TravelAdapterDelegate(presentationModel).get()
                )
            )
        }
    )

    override fun bindActions(presentationModel: MapPresentationModel) {

    }

    override fun bindCommands(presentationModel: MapPresentationModel) {

    }

    override fun bindStates(presentationModel: MapPresentationModel) {
        presentationModel
            .mapLabelState
            .observable
            .doOnNext { titleTextView.text = it }
            .subscribe()
            .let(destroyViewDisposable::add)

        presentationModel
            .mapDelayState
            .observable
            .doOnNext { progressBar.max = it }
            .subscribe()
            .let(destroyViewDisposable::add)

        presentationModel.progressState.bindTo(progressBar::setProgress)

        presentationModel
            .mapPathState
            .observable
            .doOnNext {path ->
                picasso.load(path) { rc -> picasso.resize(rc, 540, 540)}
                    .into(PixelTargetAdapter(mapImageView, false))
            }
            .subscribe()
            .let(destroyViewDisposable::add)

        presentationModel
            .objectListState
            .observable
            .doOnNext(objectAdapter::setItems)
            .doOnNext {
                objectRecyclerView?.let {
                    consumePendingUpdateOperationsMethod?.invoke(
                        objectRecyclerView
                    )
                }
            }
            .subscribe()
            .let(destroyViewDisposable::add)
    }

    override fun setupView() {
        super.setupView()
        with(objectRecyclerView) {
            adapter = objectAdapter
            addItemDecoration(
                VerticalListMarginDecorator(
                    applyFor = setOf(
                        TitleListItem::class,
                        TravelItem::class
                    ),
                    boundaryItemMargin = 8.dp,
                    circleMargin = 16.dp
                )
            )
        }
    }
}