package com.konovalovea.expsampling.screens.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.konovalovea.expsampling.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFragmentContainer, MainFragment.newInstance())
            .commit()
    }
}