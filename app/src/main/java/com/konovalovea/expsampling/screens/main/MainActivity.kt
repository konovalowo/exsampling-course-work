package com.konovalovea.expsampling.screens.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.konovalovea.expsampling.R
import com.konovalovea.expsampling.screens.record.RecordActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFragmentContainer, MainFragment.newInstance())
            .commit()
    }
}