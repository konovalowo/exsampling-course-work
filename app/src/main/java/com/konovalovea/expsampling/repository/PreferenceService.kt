package com.konovalovea.expsampling.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.Gson
import com.konovalovea.expsampling.model.PreferenceStats

class PreferenceService(val context: Context) {

    private val gson by lazy { Gson() }
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private val sharedPreferences = EncryptedSharedPreferences.create(
        "PreferencesFilename",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveStats(stats: PreferenceStats) {
        editPreferences(context) {
            putString(STATS_KEY, gson.toJson(stats))
            apply()
        }
    }

    fun getStats(): PreferenceStats {
        val statsJsonString = sharedPreferences
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
        return sharedPreferences.getString(CODE_KEY, null)
    }

    fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

    private fun editPreferences(context: Context, editBlock: SharedPreferences.Editor.() -> Unit) {
        with(sharedPreferences.edit()) {
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