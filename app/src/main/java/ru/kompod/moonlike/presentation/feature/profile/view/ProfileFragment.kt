// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.profile.view

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_profile.*
import me.dmdev.rxpm.bindTo
import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.BaseFragment
import ru.kompod.moonlike.presentation.base.recyclerview.adapter.DefaultDiffCallback
import ru.kompod.moonlike.presentation.base.recyclerview.adapter.ListItemAdapter
import ru.kompod.moonlike.presentation.base.recyclerview.decorator.VerticalListMarginDecorator
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.profile.adapter.GeneralInfoAdapterDelegate
import ru.kompod.moonlike.presentation.feature.profile.adapter.TitleAdapterDelegate
import ru.kompod.moonlike.presentation.feature.profile.model.GeneralInfoItem
import ru.kompod.moonlike.presentation.feature.profile.pm.ProfilePresentationModel
import ru.kompod.moonlike.utils.extensions.kotlin.dp
import ru.kompod.moonlike.utils.extensions.toothpick.bind
import ru.kompod.moonlike.utils.extensions.toothpick.getInstance
import ru.kompod.moonlike.utils.extensions.toothpick.moduleOf
import toothpick.config.Module
import javax.inject.Inject

class ProfileFragment : BaseFragment<ProfilePresentationModel>(R.layout.fragment_profile) {
    override val isFabRequired: Boolean = false

    @Inject
    lateinit var destroyViewDisposable: CompositeDisposable

    @Inject
    lateinit var characterInfoAdapter: ListItemAdapter

    override fun providePresentationModel(): ProfilePresentationModel = scope.getInstance()

    override fun provideViewModules(): Array<Module> = arrayOf(
        moduleOf {
            bind<DiffUtil.ItemCallback<IListItem>>().toInstance(DefaultDiffCallback())
            bind<Set<AdapterDelegate<List<IListItem>>>>().toInstance(
                setOf(
                    TitleAdapterDelegate().get(),
                    GeneralInfoAdapterDelegate(scope.getInstance()).get()
                )
            )
        }
    )

    override fun bindActions(presentationModel: ProfilePresentationModel) {
        changeCharacterImageView.clicks().bindTo(presentationModel.onChangeCharacterClickObserver)
    }

    override fun bindCommands(presentationModel: ProfilePresentationModel) {
        super.bindCommands(presentationModel)
    }

    override fun bindStates(presentationModel: ProfilePresentationModel) {
        presentationModel
            .characterState
            .observable
            .doOnNext(characterInfoAdapter::setItems)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                characterInfoRecyclerView?.let {
                    consumePendingUpdateOperationsMethod.invoke(
                        characterInfoRecyclerView
                    )
                }
            }
            .subscribe()
            .let(destroyViewDisposable::add)
    }

    override fun setupView() {
        super.setupView()
        with(characterInfoRecyclerView) {
            adapter = characterInfoAdapter
            addItemDecoration(
                VerticalListMarginDecorator(
                    applyFor = setOf(GeneralInfoItem::class),
                    margin = 8.dp,
                    boundaryMargin = 16.dp
                )
            )
        }
    }
}