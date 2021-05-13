package com.konovalovea.expsampling.screens.main.model

import com.konovalovea.expsampling.screens.main.model.groups.Dashboard

sealed class MainScreenState {

    object Loading : MainScreenState()

    class Loaded(val dashboard: Dashboard) : MainScreenState()

    object Error : MainScreenState()
}