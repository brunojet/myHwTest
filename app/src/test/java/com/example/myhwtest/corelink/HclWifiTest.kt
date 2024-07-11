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

class HclWifiTest : KoinTest {
    private lateinit var context: Context
    private lateinit var hclHardware: HclHardware
    private lateinit var wifiManager: WifiManager
    private lateinit var wifiInfo: WifiInfo


    @Before
    fun before() {
        wifiManager = mock(WifiManager::class.java)
        wifiInfo = mock(WifiInfo::class.java)
        context = mock(Context::class.java)
        hclHardware = mock(HclHardware::class.java)

        `when`(context.checkSelfPermission(android.Manifest.permission.ACCESS_WIFI_STATE)).thenReturn(
            android.content.pm.PackageManager.PERMISSION_GRANTED
        )

        startKoin {
            modules(module {
                single { context }
                single(named("modelName")) { "Smart" }
                single { hclHardware }
            })
        }
    }

    @After
    fun after() {
        stopKoin()
    }

    private fun initializeMocks(macAddress: String) {
        `when`(context.getSystemService(Context.WIFI_SERVICE)).thenReturn(wifiManager)
        `when`(wifiManager.connectionInfo).thenReturn(wifiInfo)
        `when`(wifiInfo.macAddress).thenReturn(macAddress)
    }


    @Test
    fun `macAddress returns expected value`() {
        initializeMocks("00:11:22:33:44:55")
        val hclWifi = HclWifi()
        assertEquals("00:11:22:33:44:55", hclWifi.macAddress())
    }
}
