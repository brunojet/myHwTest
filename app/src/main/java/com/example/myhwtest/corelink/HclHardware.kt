package com.example.myhwtest.corelink

import com.example.myhwtest.corelink.interfaces.HclHardwareInterface
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class HclHardware : KoinComponent, HclHardwareInterface {
    private val _modelName: String by inject(named("modelName"))
    private var manufacturer: Manufacturer
    private val mobile by lazy { HclMobile() }
    private val wifi by lazy { HclWifi() }

    enum class Manufacturer(val displayName: String) {
        SMART("Smart"), POSITIVO("Positivo"), GERTEC("Gertec")
    }

    init {
        manufacturer = Manufacturer.SMART

        if (_modelName.startsWith("L400")) {
            manufacturer = Manufacturer.POSITIVO
        } else if (_modelName.startsWith("GPOS")) {
            manufacturer = Manufacturer.GERTEC
        }
    }

    override fun getManufacturerName(): String {
        return manufacturer.displayName
    }

    fun getManufacturer(): Manufacturer {
        return manufacturer
    }

    override fun getModelName(): String {
        return _modelName
    }

    override fun serialNumber(): String {
        var serialNumber = mobile.imei()

        if (serialNumber.isNullOrEmpty()) {
            serialNumber = wifi.macAddress()
        }

        if (serialNumber.isNullOrEmpty()) {
            throw Exception("Cannot get serial number")
        }

        return serialNumber
    }

    override fun partNumber(): String {
        return "fakePartNumber"
    }

    override fun pciVersion(): String {
        return "fakePciVersion"
    }
}
