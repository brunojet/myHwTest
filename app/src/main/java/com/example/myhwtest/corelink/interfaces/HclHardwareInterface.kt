package com.example.myhwtest.corelink.interfaces

interface HclHardwareInterface {
    fun getManufacturerName(): String
    fun getModelName() : String
    fun serialNumber(): String
    fun partNumber(): String
    fun pciVersion(): String
}
