package com.example.myhwtest.corelink

import android.content.Context
import android.content.SharedPreferences
import android.telephony.TelephonyManager
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class HclMobileTest : KoinTest {
    private lateinit var context: Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var telephonyManager: TelephonyManager

    @Before
    fun before() {
        context = mock(Context::class.java)
        sharedPreferences = mock(SharedPreferences::class.java)
        editor = mock(SharedPreferences.Editor::class.java)
        telephonyManager = mock(TelephonyManager::class.java)

        `when`(context.getSharedPreferences(anyString(), eq(Context.MODE_PRIVATE))).thenReturn(
            sharedPreferences
        )
        `when`(sharedPreferences.edit()).thenReturn(editor)
        `when`(editor.putString(anyString(), anyString())).thenReturn(editor)
        `when`(editor.apply()).then { }
        `when`(context.getSystemService(Context.TELEPHONY_SERVICE)).thenReturn(telephonyManager)
        `when`(context.checkSelfPermission(android.Manifest.permission.READ_PHONE_STATE)).thenReturn(
            android.content.pm.PackageManager.PERMISSION_GRANTED
        )

        startKoin {
            modules(module {
                single { context }
                single(named("modelName")) { "Smart" }
            })
        }
    }

    @After
    fun after() {
        stopKoin()
    }

    private fun initializeMocks(imei: String) {
        `when`(sharedPreferences.getString(eq("IMEI"), any())).thenReturn(imei)
        `when`(telephonyManager.getImei(0)).thenReturn(imei)
        `when`(telephonyManager.deviceId).thenReturn(imei)
    }


    @Test
    fun `imei returns expected value`() {
        val imei = "123456789";
        val hclMobile = HclMobile()
        initializeMocks(imei);
        assertEquals(imei, hclMobile.imei())
    }
}