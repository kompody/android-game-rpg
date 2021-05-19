// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike

import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.kompod.moonlike.presentation.feature.characterslist.view.CharactersListFragment
import ru.kompod.moonlike.presentation.feature.createcharacter.view.CreateCharacterFragment
import ru.kompod.moonlike.presentation.feature.inventory.view.InventoryFragment
import ru.kompod.moonlike.presentation.feature.main.view.*
import ru.kompod.moonlike.presentation.feature.map.view.MapFragment
import ru.kompod.moonlike.presentation.feature.mapcharacters.view.CharacterOnMapFragment
import ru.kompod.moonlike.presentation.feature.profile.view.ProfileFragment
import ru.kompod.moonlike.presentation.feature.questslist.view.QuestsListFragment
import ru.kompod.moonlike.presentation.feature.settings.view.LibraryFragment
import ru.kompod.moonlike.presentation.feature.splash.view.SplashFragment
import ru.kompod.moonlike.utils.navigation.BottomSheetSupportAppScreen
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {
    object SplashScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return SplashFragment()
        }
    }

    object MainScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return MainFragment()
        }
    }

    //PROFILE TAB
    object ProfileContainer : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return ProfileBottomTabContainerFragment()
        }
    }

    object Profile : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return ProfileFragment()
        }
    }

    object CharactersList : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return CharactersListFragment()
        }
    }

    object CreateCharacter : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return CreateCharacterFragment()
        }
    }

    //INVENTORY TAB
    object InventoryContainer : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return InventoryBottomTabContainerFragment()
        }
    }

    object Inventory : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return InventoryFragment()
        }
    }

    //MAP TAB
    object MapContainer : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return MapBottomTabContainerFragment()
        }
    }

    object Map : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return MapFragment()
        }
    }

    object CharactersOnMap : BottomSheetSupportAppScreen() {
        override fun getFragment(): BottomSheetDialogFragment {
            return CharacterOnMapFragment()
        }
    }

    //QUESTS TAB
    object QuestsListContainer : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return QuestsListBottomTabContainerFragment()
        }
    }

    object QuestsList : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return QuestsListFragment()
        }
    }

    //SETTINGS TAB
    object LibraryContainer : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return LibraryBottomTabContainerFragment()
        }
    }

    object Library : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return LibraryFragment()
        }
    }
}