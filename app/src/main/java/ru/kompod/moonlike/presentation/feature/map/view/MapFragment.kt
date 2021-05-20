// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.map.view

import androidx.recyclerview.widget.DiffUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_characters_on_map.*
import kotlinx.android.synthetic.main.fragment_map.*
import me.dmdev.rxpm.bindTo
import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.BaseFragment
import ru.kompod.moonlike.presentation.base.recyclerview.adapter.BaseAdapter
import ru.kompod.moonlike.presentation.base.recyclerview.adapter.DefaultDiffCallback
import ru.kompod.moonlike.presentation.base.recyclerview.adapter.ListItemAdapter
import ru.kompod.moonlike.presentation.base.recyclerview.decorator.GridListDecorator
import ru.kompod.moonlike.presentation.base.recyclerview.decorator.VerticalListMarginDecorator
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.map.adapter.StateAdapterDelegate
import ru.kompod.moonlike.presentation.feature.map.adapter.TitleAdapterDelegate
import ru.kompod.moonlike.presentation.feature.map.model.MonsterItem
import ru.kompod.moonlike.presentation.feature.map.model.StateItem
import ru.kompod.moonlike.presentation.feature.map.model.TitleListItem
import ru.kompod.moonlike.presentation.feature.map.model.TravelItem
import ru.kompod.moonlike.presentation.feature.map.pm.MapPresentationModel
import ru.kompod.moonlike.utils.ResourceDelegate
import ru.kompod.moonlike.utils.extensions.android.setCallback
import ru.kompod.moonlike.utils.extensions.kotlin.dp
import ru.kompod.moonlike.utils.extensions.rxjava.ui
import ru.kompod.moonlike.utils.extensions.rxpm.accept
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
    lateinit var characterStatesAdapter: BaseAdapter

    @Inject
    lateinit var objectAdapter: ListItemAdapter

    @Inject
    lateinit var assetProvider: AssetProvider

    @Inject
    lateinit var picassoUtil: PicassoUtil

    @Inject
    lateinit var resourceDelegate: ResourceDelegate

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>

    override fun providePresentationModel(): MapPresentationModel = scope.getInstance()

    override fun provideViewModules(): Array<Module> = arrayOf(
        moduleOf {
            bind<DiffUtil.ItemCallback<IListItem>>().toInstance(object : DefaultDiffCallback() {
                override fun areItemsTheSame(oldItem: IListItem, newItem: IListItem): Boolean {
                    if (oldItem is TravelItem && newItem is TravelItem) {
                        return oldItem.travel.id == newItem.travel.id
                    }
                    if (oldItem is MonsterItem && newItem is MonsterItem) {
                        return oldItem.onMapObj == newItem.onMapObj
                    }
                    if (oldItem is StateItem && newItem is StateItem) {
                        return oldItem.label == newItem.label && oldItem.value == newItem.value
                    }
                    return true
                }
            })
            bind<BaseAdapter>().toInstance(
                BaseAdapter(
                    StateAdapterDelegate().get()
                )
            )
            bind<Set<AdapterDelegate<List<IListItem>>>>().toInstance(
                setOf(
                    TitleAdapterDelegate(
                        presentationModel,
                        presentationModel,
                        scope.getInstance()
                    ).get()
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
            .characterState
            .observable
            .map {
                it to listOf(
                    StateItem("level", it.level.toString()),
                    StateItem("exp", it.exp.toString()),
                    StateItem("hp", it.hp.toString()),
                    StateItem("sp", it.sp.toString()),
                    StateItem("fAtk", it.baseFAtk.toString()),
                    StateItem("fDef", it.baseFDef.toString()),
                    StateItem("mAtk", it.baseMAtk.toString()),
                    StateItem("mDef", it.baseMDef.toString())
                )
            }
            .observeOn(ui())
            .doOnNext { (character, states) ->
                with(character) {
                    picassoUtil.load(portrait)
                        .into(PixelTargetAdapter(iconImageView))

                    labelTextView.text = "${fraction.label} - ${role.label}"
                }

                characterStatesAdapter.items = states
            }
            .subscribe()
            .let(destroyViewDisposable::add)

        presentationModel
            .mapLabelState
            .observable
            .doOnNext {
//                titleTextView.text = it
            }
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
            .doOnNext { path ->
                picassoUtil.load(path) { rc ->
                    picassoUtil.resize(
                        rc,
                        resourceDelegate.getDisplayMetrics().widthPixels,
                        (resourceDelegate.getDisplayMetrics().widthPixels / 16f * 12).toInt()
                    )
                }
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
                    consumePendingUpdateOperationsMethod.invoke(
                        objectRecyclerView
                    )
                }
            }
            .subscribe()
            .let(destroyViewDisposable::add)
    }

    override fun onViewPreCreated() {
        super.onViewPreCreated()
        setupBottomSheet()
    }

    private fun setupBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(charactersOnMap).apply {
            setCallback(
                stateChanged = { _, state ->
                    setBottomSheetPeekHeight()

                    presentationModel.bottomSheetActions.accept(state)
                }
            )
        }
    }

    private fun setBottomSheetPeekHeight() {
        if (isRemoving.not()) {
            bottomSheetBehavior.peekHeight =
                root.measuredHeight -
                        characterInfoContainer.measuredHeight -
                        progressBar.measuredHeight -
                        mapImageView.measuredHeight -
                        32.dp
        }
    }

    override fun setupView() {
        super.setupView()
        with(statesRecyclerView) {
            adapter = characterStatesAdapter
            addItemDecoration(
                GridListDecorator(
                    applyFor = setOf(
                        StateItem::class
                    ),
                    columnCount = 4,
                    margin = 6.dp,
                    boundaryMargin = 8.dp
                )
            )
        }
        with(objectRecyclerView) {
            adapter = objectAdapter

            addItemDecoration(
                VerticalListMarginDecorator(
                    applyFor = setOf(
                        TitleListItem::class
                    ),
                    startMargin = 8.dp,
                    endMargin = 8.dp
                )
            )
        }
    }
}