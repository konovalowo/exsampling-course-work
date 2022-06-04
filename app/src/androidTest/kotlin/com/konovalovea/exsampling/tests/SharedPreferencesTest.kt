package com.konovalovea.exsampling.tests


import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.konovalovea.expsampling.repository.PreferenceService
import com.konovalovea.expsampling.screens.main.MainActivity
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock






@RunWith(AndroidJUnit4::class)
class SharedPreferencesTest {
    @Rule
    var activityActivityTestRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java)

    @Mock
    lateinit var context: Context

    private lateinit var preferences: PreferenceService


    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        preferences = PreferenceService(context);
    }


    @Test
    fun prefTest() {
        preferences.saveCode("12345")
        assertEquals("12345", preferences.getCode())
    }

}