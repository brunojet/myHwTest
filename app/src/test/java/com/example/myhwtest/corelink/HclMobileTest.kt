package com.example.myhwtest.corelink

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.TelephonyManager
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.stream.Stream
import kotlin.test.assertEquals

class HclMobileTest : KoinTest {
    private val context = mock(Context::class.java)
    private val sharedPreferences = mock(SharedPreferences::class.java)
    private val editor = mock(SharedPreferences.Editor::class.java)
    private val telephonyManager = mock(TelephonyManager::class.java)
    private val models = Stream.of("Smart", "GPOS700", "L400")

    private fun before(modelName: String) {
        `when`(context.getSharedPreferences(anyString(), eq(Context.MODE_PRIVATE))).thenReturn(
            sharedPreferences
        )
        `when`(sharedPreferences.edit()).thenReturn(editor)
        `when`(editor.putString(anyString(), anyString())).thenReturn(editor)
        `when`(editor.apply()).then { }
        `when`(context.getSystemService(Context.TELEPHONY_SERVICE)).thenReturn(telephonyManager)
        `when`(context.checkSelfPermission(anyString())).thenReturn(
            PackageManager.PERMISSION_GRANTED
        )

        startKoin {
            modules(module {
                single { context }
                single(named("modelName")) { modelName }
            })
        }
    }

    private fun after() {
        stopKoin()
    }

    @Suppress("DEPRECATION")
    private fun initializeMocks(imei: String) {
        `when`(sharedPreferences.getString(eq("IMEI"), any())).thenReturn(null)
        `when`(telephonyManager.getImei(0)).thenReturn(imei)
        `when`(telephonyManager.deviceId).thenReturn(imei)
    }

    @Test
    fun `imei returns expected value`() {
        models.forEach { model ->
            before(model)
            val hclMobile = HclMobile()
            initializeMocks("123456789")
            assertEquals("123456789", hclMobile.imei())
            after()
        }
    }
}