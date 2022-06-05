package com.konovalovea.expsampling.repository
import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
//import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.konovalovea.expsampling.repository.PreferenceService
import com.konovalovea.expsampling.screens.LaunchActivity
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.jupiter.api.Test


import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(AndroidJUnit4::class)
class SharedPreferencesTest {
    private lateinit var profilePrefs: PreferenceService
    private lateinit var context: Context

    @Before
    fun setup() {
        context = getApplicationContext<LaunchActivity>()
        profilePrefs = PreferenceService(context)
    }


@Test
    fun useAppContext() {
    context = getApplicationContext<LaunchActivity>()
    profilePrefs = PreferenceService(context)
        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.example.basecomponents", appContext.packageName)
        profilePrefs.saveCode("12345")
        assertEquals("12345", profilePrefs.getCode())
    }

}