package com.konovalovea.expsampling.screens.record

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.konovalovea.expsampling.R


class RecordActivity : AppCompatActivity(R.layout.activity_record) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras
        val isTutorial = bundle?.getBoolean(IS_TUTORIAL_KEY) ?: false
        val notificationId = bundle?.getInt(NOTIFICATION_ID_KEY) ?: -1
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.recordFragmentContainer,
                RecordFragment.newInstance(isTutorial, notificationId)
            )
            .commit()
    }

    companion object {

        private const val IS_TUTORIAL_KEY = "is_tutorial"
        private const val NOTIFICATION_ID_KEY = "notification_id"

        fun getStartIntent(context: Context, isTutorial: Boolean, notificationId: Int): Intent {
            return Intent(context, RecordActivity::class.java)
                .putExtra(IS_TUTORIAL_KEY, isTutorial)
                .putExtra(NOTIFICATION_ID_KEY, notificationId)
        }
    }
}