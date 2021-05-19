// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.characterslist.view

import android.content.DialogInterface
import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_characters_list.*
import me.dmdev.rxpm.bindTo
import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.BaseFragment
import ru.kompod.moonlike.presentation.base.recyclerview.adapter.DefaultDiffCallback
import ru.kompod.moonlike.presentation.base.recyclerview.adapter.ListItemAdapter
import ru.kompod.moonlike.presentation.base.recyclerview.decorator.VerticalListMarginDecorator
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.characterslist.adapter.CharacterAdapterDelegate
import ru.kompod.moonlike.presentation.feature.characterslist.adapter.CreateCharacterAdapterDelegate
import ru.kompod.moonlike.presentation.feature.characterslist.model.CharacterItem
import ru.kompod.moonlike.presentation.feature.characterslist.model.CreateCharacterItem
import ru.kompod.moonlike.presentation.feature.characterslist.pm.CharactersListPresentationModel
import ru.kompod.moonlike.utils.extensions.android.buildSupportAlertDialog
import ru.kompod.moonlike.utils.extensions.kotlin.dp
import ru.kompod.moonlike.utils.extensions.rxpm.accept
import ru.kompod.moonlike.utils.extensions.toothpick.bind
import ru.kompod.moonlike.utils.extensions.toothpick.getInstance
import ru.kompod.moonlike.utils.extensions.toothpick.moduleOf
import toothpick.config.Module
import javax.inject.Inject

class CharactersListFragment :
    BaseFragment<CharactersListPresentationModel>(R.layout.fragment_characters_list) {
    override val isFabRequired: Boolean = false

    @Inject
    lateinit var destroyViewDisposable: CompositeDisposable

    @Inject
    lateinit var charactersAdapter: ListItemAdapter

    override fun providePresentationModel(): CharactersListPresentationModel = scope.getInstance()

    override fun provideViewModules(): Array<Module> = arrayOf(
        moduleOf {
            bind<DiffUtil.ItemCallback<IListItem>>().toInstance(DefaultDiffCallback())
            bind<Set<AdapterDelegate<List<IListItem>>>>().toInstance(
                setOf(
                    CharacterAdapterDelegate(
                        presentationModel,
                        scope.getInstance()
                    ).get(),
                    CreateCharacterAdapterDelegate(
                        presentationModel
                    ).get()
                )
            )
        }
    )

    override fun bindActions(presentationModel: CharactersListPresentationModel) {

    }

    override fun bindCommands(presentationModel: CharactersListPresentationModel) {
        presentationModel.onShowRemoveDialog.bindTo {
            buildSupportAlertDialog(requireContext(), R.style.AppTheme_Dialog_Info) {
                setTitle(R.string.screen_characters_list_remove_character_dialog_title)
                setMessage(R.string.screen_characters_list_remove_character_dialog_message)

                setNegativeButton(R.string.button_no) { dialog, _ ->
                    dialog.dismiss()
                }
                setPositiveButton(R.string.button_yes) { dialog, _ ->
                    dialog.dismiss()
                    presentationModel.onRemoveCharacterAction.accept(it)
                }
                setOnCancelListener {
                    it.dismiss()
                }

                show().apply {
                    getButton(DialogInterface.BUTTON_POSITIVE)?.setTextColor(resources.getColor(R.color.textError))
                }
            }
        }
    }

    override fun bindStates(presentationModel: CharactersListPresentationModel) {
        presentationModel
            .charactersListState
            .observable
            .doOnNext(charactersAdapter::setItems)
            .observeOn(AndroidSchedulers.mainThread())
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

    override fun onDestroyView() {
        super.onDestroyView()

        destroyViewDisposable.clear()
    }

    override fun setupView() {
        super.setupView()
        with(charactersRecyclerView) {
            adapter = charactersAdapter
            addItemDecoration(
                VerticalListMarginDecorator(
                    applyFor = setOf(CharacterItem::class, CreateCharacterItem::class),
                    margin = 8.dp,
                    boundaryMargin = 16.dp
                )
            )
        }
    }
}