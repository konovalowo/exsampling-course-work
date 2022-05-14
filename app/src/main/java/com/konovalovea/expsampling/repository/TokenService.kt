package com.konovalovea.expsampling.repository

import android.content.Context
import javax.inject.Inject

class TokenService @Inject constructor(
    private val context: Context,
    private val preferenceService: PreferenceService
) {

    fun saveToken(token: String) {
        preferenceService.saveToken(token)
    }

    fun getToken(): String? {
        return preferenceService.getToken()
    }
}