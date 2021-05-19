// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.mapcharacters.pm

import ru.kompod.moonlike.data.analytics.AnalyticsDelegate
import ru.kompod.moonlike.domain.AppScreen
import ru.kompod.moonlike.presentation.base.BasePresentationModel
import ru.kompod.moonlike.utils.ResourceDelegate
import ru.kompod.moonlike.utils.navigation.BottomTabRouter
import ru.kompod.moonlike.utils.navigation.CustomRouter
import javax.inject.Inject

class CharacterOnMapPresentationModel @Inject constructor(
    override val router: BottomTabRouter,
    resources: ResourceDelegate,
    analytics: AnalyticsDelegate
) : BasePresentationModel(router, resources, analytics) {
    override val screen = AppScreen.CHARACTERS_ON_MAP
}