package com.example.myhwtest.corelink

import android.content.Context
import com.example.myhwtest.coreinterface.HciMobileGertec
import com.example.myhwtest.coreinterface.HciMobilePositivo
import com.example.myhwtest.coreinterface.HciMobileSmart
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

import com.example.myhwtest.corelink.interfaces.HclMobileInterface

class HclMobile : HclMobileInterface, KoinComponent {
    private val context: Context by inject()
    private var hciMobile: HclMobileInterface

    init {
        val hclHardware = HclHardware()
        hciMobile = when (hclHardware.getManufacturer()) {
            HclHardware.Manufacturer.POSITIVO -> HciMobilePositivo(context)
            HclHardware.Manufacturer.GERTEC -> HciMobileGertec(context)
            else -> HciMobileSmart(context)
        }
    }

    override fun imei(): String? {
        return hciMobile.imei()
    }
}
