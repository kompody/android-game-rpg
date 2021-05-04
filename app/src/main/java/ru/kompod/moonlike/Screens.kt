// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike

import androidx.fragment.app.Fragment
import ru.kompod.moonlike.presentation.feature.main.view.*
import ru.kompod.moonlike.presentation.feature.splash.view.SplashFragment
import ru.kompod.moonlike.presentation.feature.profile.view.ProfileFragment
import ru.kompod.moonlike.presentation.feature.inventory.view.InventoryFragment
import ru.kompod.moonlike.presentation.feature.map.view.MapFragment
import ru.kompod.moonlike.presentation.feature.questlist.view.QuestListFragment
import ru.kompod.moonlike.presentation.feature.setting.view.SettingFragment
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

    object QuestListContainer : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            return QuestListBottomTabContainerFragment()
        }
    }

    object QuestList : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            return QuestListFragment()
        }
    }

    object SettingContainer : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            return SettingBottomTabContainerFragment()
        }
    }

    object Setting : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            return SettingFragment()
        }
    }

    object Test : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            return InventoryFragment()
        }
    }
}