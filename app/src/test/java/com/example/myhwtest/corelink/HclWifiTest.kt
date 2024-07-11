package com.example.myhwtest.corelink

import android.content.Context
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.util.stream.Stream

class HclWifiTest : KoinTest {
    private val context = mock(Context::class.java)
    private val hclHardware = mock(HclHardware::class.java)
    private val wifiManager = mock(WifiManager::class.java)
    private val wifiInfo = mock(WifiInfo::class.java)
    private val models = Stream.of("Smart", "GPOS700", "L400")

    private fun begin(model: String) {

        `when`(context.checkSelfPermission(android.Manifest.permission.ACCESS_WIFI_STATE)).thenReturn(
            android.content.pm.PackageManager.PERMISSION_GRANTED
        )

        startKoin {
            modules(module {
                single { context }
                single(named("modelName")) { model }
                single { hclHardware }
            })
        }
    }

    private fun end() {
        stopKoin()
    }

    private fun initializeMocks(macAddress: String) {
        `when`(context.getSystemService(Context.WIFI_SERVICE)).thenReturn(wifiManager)
        `when`(wifiManager.connectionInfo).thenReturn(wifiInfo)
        `when`(wifiInfo.macAddress).thenReturn(macAddress)
    }


    @Test
    fun `macAddress returns expected value`() {
        models.forEach { model ->
            begin(model)
            val hclWifi = HclWifi()
            initializeMocks("00:11:22:33:44:55")
            assertEquals("00:11:22:33:44:55", hclWifi.macAddress())
            end()
        }
    }
}
