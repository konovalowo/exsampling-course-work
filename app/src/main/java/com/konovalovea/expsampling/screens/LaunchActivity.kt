package com.konovalovea.expsampling.screens

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.konovalovea.expsampling.R
import com.konovalovea.expsampling.app.appComponent
import com.konovalovea.expsampling.repository.PreferenceService
import com.konovalovea.expsampling.screens.auth.AuthActivity
import com.konovalovea.expsampling.screens.main.MainActivity
import javax.inject.Inject

class LaunchActivity : AppCompatActivity(R.layout.activity_launch) {

    @Inject
    lateinit var preferenceService: PreferenceService

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        val token = preferenceService.getToken()
        val activityToLaunch = if (token != null) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, AuthActivity::class.java)
        }

        startActivity(activityToLaunch)
        finish()
    }
}