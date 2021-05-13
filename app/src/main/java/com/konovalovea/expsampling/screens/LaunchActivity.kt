package com.konovalovea.expsampling.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.konovalovea.expsampling.R
import com.konovalovea.expsampling.app.GlobalDependencies
import com.konovalovea.expsampling.screens.auth.AuthActivity
import com.konovalovea.expsampling.screens.main.MainActivity

class LaunchActivity : AppCompatActivity(R.layout.activity_launch) {

    override fun onStart() {
        super.onStart()

        val token = GlobalDependencies.INSTANCE.preferenceService.getToken()
        val activityToLaunch = if (token != null) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, AuthActivity::class.java)
        }

        startActivity(activityToLaunch)
        finish()
    }
}