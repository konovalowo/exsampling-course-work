package com.konovalovea.expsampling.app

import com.konovalovea.expsampling.screens.LaunchActivity
import com.konovalovea.expsampling.screens.auth.AuthActivity
import com.konovalovea.expsampling.screens.main.MainFragment
import com.konovalovea.expsampling.screens.record.RecordFragment
import dagger.Component

@Component(
    modules = [
        AppModule::class,
        RepositoryModule::class,
        ApiModule::class
    ]
)
interface ApplicationComponent {

    fun inject(activity: LaunchActivity)

    fun inject(activity: AuthActivity)

    fun inject(fragment: RecordFragment)

    fun inject(fragment: MainFragment)
}