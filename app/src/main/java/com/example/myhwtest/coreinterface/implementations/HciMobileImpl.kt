package com.example.myhwtest.coreinterface.implementations

import com.example.myhwtest.corelink.interfaces.HclMobileInterface
import com.example.myhwtest.corelink.HclSharedPrefs

abstract class HciMobileImpl : HclMobileInterface {
    private val sharedPrefs by lazy { HclSharedPrefs("MOBILE") }

    protected abstract fun platformImei(): String?

    override fun imei(): String? {
        var imei = sharedPrefs.getString("IMEI")

        if (imei.isNullOrEmpty()) {
            imei = platformImei()

            if (!imei.isNullOrEmpty()) {
                sharedPrefs.putString("IMEI", imei)
            }
        }

        return imei
    }
}
