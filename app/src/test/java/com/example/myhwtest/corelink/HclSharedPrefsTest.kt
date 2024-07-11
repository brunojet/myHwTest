package com.example.myhwtest.corelink

import android.content.Context
import android.content.SharedPreferences
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.mockito.ArgumentMatchers.anyString
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class HclSharedPrefsTest : KoinTest {
    private lateinit var sharedPrefs: HclSharedPrefs
    private val context = mock(Context::class.java)
    private val sharedPreferences = mock(SharedPreferences::class.java)
    private val editor = mock(SharedPreferences.Editor::class.java)

    @Before
    fun before() {
        sharedPrefs = HclSharedPrefs("TEST")
        `when`(context.getSharedPreferences(anyString(), eq(Context.MODE_PRIVATE))).thenReturn(
            sharedPreferences
        )
        `when`(sharedPreferences.edit()).thenReturn(editor)
        `when`(editor.putString(anyString(), anyString())).thenReturn(editor)
        `when`(editor.apply()).then { }

        startKoin {
            modules(module {
                single { context }
            })
        }
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `getString with default returns default value when key does not exist`() {
        `when`(sharedPreferences.getString("nonexistentKey", "defaultValue")).thenReturn("defaultValue")
        assertEquals("defaultValue", sharedPrefs.getString("nonexistentKey", "defaultValue"))
    }

    @Test
    fun `getString with default returns value when key exists`() {
        `when`(sharedPreferences.getString("existentKey", "defaultValue")).thenReturn("value")
        assertEquals("value", sharedPrefs.getString("existentKey", "defaultValue"))
    }

    @Test
    fun `getString without default returns null when key does not exist`() {
        `when`(sharedPreferences.getString("nonexistentKey", null)).thenReturn(null)
        assertNull(sharedPrefs.getString("nonexistentKey"))
    }

    @Test
    fun `getString without default returns value when key exists`() {
        `when`(sharedPreferences.getString("existentKey", null)).thenReturn("value")
        assertEquals("value", sharedPrefs.getString("existentKey"))
    }

    @Test
    fun `putString stores value correctly`() {
        `when`(editor.commit()).thenReturn(true)
        sharedPrefs.putString("key", "value")
        `when`(sharedPreferences.getString("key", null)).thenReturn("value")
        assertEquals("value", sharedPrefs.getString("key"))
    }
}