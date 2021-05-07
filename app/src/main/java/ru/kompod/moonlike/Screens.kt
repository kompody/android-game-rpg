// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike

import androidx.fragment.app.Fragment
import ru.kompod.moonlike.presentation.feature.characterslist.view.CharactersListFragment
import ru.kompod.moonlike.presentation.feature.createcharacter.view.CreateCharacterFragment
import ru.kompod.moonlike.presentation.feature.main.view.*
import ru.kompod.moonlike.presentation.feature.splash.view.SplashFragment
import ru.kompod.moonlike.presentation.feature.profile.view.ProfileFragment
import ru.kompod.moonlike.presentation.feature.inventory.view.InventoryFragment
import ru.kompod.moonlike.presentation.feature.map.view.MapFragment
import ru.kompod.moonlike.presentation.feature.questslist.view.QuestsListFragment
import ru.kompod.moonlike.presentation.feature.settings.view.SettingsFragment
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
        override fun getFragment(): Fragment? {
            return ProfileBottomTabContainerFragment()
        }
    }

    object Profile : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            return ProfileFragment()
        }
    }

    object CharactersList : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            return CharactersListFragment()
        }
    }

    object CreateCharacter : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            return CreateCharacterFragment()
        }
    }

    //INVENTORY TAB
    object InventoryContainer : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            return InventoryBottomTabContainerFragment()
        }
    }

    object Inventory : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            return InventoryFragment()
        }
    }

    //MAP TAB
    object MapContainer : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            return MapBottomTabContainerFragment()
        }
    }

    object Map : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            return MapFragment()
        }
    }

    //QUESTS TAB
    object QuestsListContainer : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            return QuestsListBottomTabContainerFragment()
        }
    }

    object QuestsList : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            return QuestsListFragment()
        }
    }

    //SETTINGS TAB
    object SettingsContainer : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            return SettingsBottomTabContainerFragment()
        }
    }

    object Settings : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            return SettingsFragment()
        }
    }
}