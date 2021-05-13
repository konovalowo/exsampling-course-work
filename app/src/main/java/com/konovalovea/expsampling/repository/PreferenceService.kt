package com.konovalovea.expsampling.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.konovalovea.expsampling.model.PreferenceStats

class PreferenceService(val context: Context) {

    private val gson by lazy { Gson() }

    fun saveStats(stats: PreferenceStats) {
        editPreferences(context) {
            putString(STATS_KEY, gson.toJson(stats))
            apply()
        }
    }

    fun getStats(): PreferenceStats {
        val statsJsonString = PreferenceManager.getDefaultSharedPreferences(context)
            .getString(STATS_KEY, null)
        return if (statsJsonString == null) {
            PreferenceStats(0, -1)
        } else {
            gson.fromJson(statsJsonString, PreferenceStats::class.java)
        }
    }

    fun saveCode(code: String) {
        editPreferences(context) {
            putString(CODE_KEY, code)
            apply()
        }
    }

    fun saveToken(token: String) {
        editPreferences(context) {
            putString(TOKEN_KEY, token)
            apply()
        }
    }

    fun getCode(): String? {
        return PreferenceManager.getDefaultSharedPreferences(context)
            .getString(CODE_KEY, null)
    }

    fun getToken(): String? {
        return PreferenceManager.getDefaultSharedPreferences(context)
            .getString(TOKEN_KEY, null)
    }

    private fun editPreferences(context: Context, editBlock: SharedPreferences.Editor.() -> Unit) {
        with(PreferenceManager.getDefaultSharedPreferences(context).edit()) {
            editBlock()
            apply()
        }
    }

    companion object {
        private const val CODE_KEY = "code"
        private const val TOKEN_KEY = "token"
        private const val STATS_KEY = "stats"
    }
}