package com.example.myhwtest.corelink

import android.content.Context
import android.content.SharedPreferences
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.telephony.TelephonyManager
import org.junit.Assert.*

import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.util.stream.Stream
import kotlin.test.assertFailsWith

class HclHardwareTest {
    private val context = mock(Context::class.java)
    private val sharedPreferences = mock(SharedPreferences::class.java)
    private val editor = mock(SharedPreferences.Editor::class.java)
    private val telephonyManager = mock(TelephonyManager::class.java)
    private val wifiManager = mock(WifiManager::class.java)
    private val wifiInfo = mock(WifiInfo::class.java)
    private val models = Stream.of("Smart", "GPOS700", "L400")
    private lateinit var hclHardware: HclHardware

    private fun before(modelName: String) {
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
        `when`(context.checkSelfPermission(android.Manifest.permission.ACCESS_WIFI_STATE)).thenReturn(
            android.content.pm.PackageManager.PERMISSION_GRANTED
        )
        `when`(context.getSystemService(Context.WIFI_SERVICE)).thenReturn(wifiManager)
        `when`(wifiManager.connectionInfo).thenReturn(wifiInfo)

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


    @Test
    fun getManufacturerName() {
        models.forEach { modelName ->
            before(modelName)
            hclHardware = HclHardware()
            val expectedManufacturer = when (modelName) {
                "GPOS700" -> "Gertec"
                "L400" -> "Positivo"
                else -> "Smart"
            }
            assertEquals(expectedManufacturer, hclHardware.getManufacturerName())
            after()
        }
    }

    @Test
    fun getManufacturer() {
        models.forEach { modelName ->
            before(modelName)
            hclHardware = HclHardware()
            val expectedManufacturer = when (modelName) {
                "GPOS700" -> HclHardware.Manufacturer.GERTEC
                "L400" -> HclHardware.Manufacturer.POSITIVO
                else -> HclHardware.Manufacturer.SMART
            }
            assertEquals(expectedManufacturer, hclHardware.getManufacturer())
            after()
        }
    }

    @Test
    fun getModelName() {
        models.forEach { modelName ->
            before(modelName)
            hclHardware = HclHardware()
            assertEquals(modelName, hclHardware.getModelName())
            after()
        }
    }

    private fun initializeMocks(imei: String?, macAddress: String?) {
        `when`(sharedPreferences.getString(eq("IMEI"), any())).thenReturn(imei)
        `when`(telephonyManager.getImei(0)).thenReturn(imei)
        `when`(telephonyManager.deviceId).thenReturn(imei)
        `when`(wifiInfo.macAddress).thenReturn(macAddress)
    }

    @Test
    fun `serialNumber returns macAddress when imei is not available`() {
        val macAddress = "00:11:22:33:44:55"
        models.forEach { modelName ->
            before(modelName)
            initializeMocks(null, macAddress)
            var hclHardware = HclHardware()
            assertEquals(macAddress, hclHardware.serialNumber())
            after()
        }
    }

    @Test
    fun `serialNumber throws exception when neither IMEI nor MAC address are available`() {
        models.forEach { modelName ->
            before(modelName)
            initializeMocks(null, null)
            var hclHardware = HclHardware()
            assertFailsWith<Exception> { hclHardware.serialNumber() }
            after()
        }
    }

    @Test
    fun `serialNumber returns imei `() {
        val imei = "123456789"
        val macAddress = "00:11:22:33:44:55"
        models.forEach { modelName ->
            before(modelName)
            initializeMocks(imei, macAddress)
            var hclHardware = HclHardware()
            assertEquals(imei, hclHardware.serialNumber())
            after()
        }
    }

    @Test
    fun `partNumber returns static value`() {
        models.forEach { modelName ->
            before(modelName)
            var hclHardware = HclHardware()
            assertEquals("fakePartNumber", hclHardware.partNumber())
            after()
        }
    }

    @Test
    fun `pciVersion returns static value`() {
        models.forEach { modelName ->
            before(modelName)
            var hclHardware = HclHardware()
            assertEquals("fakePciVersion", hclHardware.pciVersion())
            after()
        }
    }
}