package com.konovalovea.expsampling.repository

import android.content.Context
import com.konovalovea.expsampling.app.GlobalDependencies

class TokenService(val context: Context) {

    fun saveToken(token: String) {
        GlobalDependencies.INSTANCE.preferenceService.saveToken(token)
    }

    fun getToken(): String? {
        return GlobalDependencies.INSTANCE.preferenceService.getToken()
    }
}