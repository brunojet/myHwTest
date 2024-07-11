package com.example.myhwtest.corelink

import android.content.Context
import com.example.myhwtest.coreinterface.HciWifiGertec
import com.example.myhwtest.coreinterface.HciWifiPositivo
import com.example.myhwtest.coreinterface.HciWifiSmart
import com.example.myhwtest.coreinterface.interfaces.HciWifiInterface
import org.koin.core.component.inject
import org.koin.core.component.KoinComponent
import com.example.myhwtest.corelink.interfaces.HclWifiInterface

class HclWifi : HclWifiInterface, KoinComponent {
    private val context: Context by inject()
    private var hci: HciWifiInterface

    init {
        val hciHardware = HclHardware()
        hci = when (hciHardware.getManufacturer()) {
            HclHardware.Manufacturer.POSITIVO -> HciWifiPositivo(context)
            HclHardware.Manufacturer.GERTEC -> HciWifiGertec(context)
            else -> HciWifiSmart(context)
        }
    }

    override fun macAddress(): String? {
        return hci.macAddress()
    }
}
