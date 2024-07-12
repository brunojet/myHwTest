package com.example.myhwtest.coreinterface

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiManager
import com.example.myhwtest.coreinterface.apidependent.PermissionUtils
import com.example.myhwtest.coreinterface.interfaces.HciWifiInterface

open class HciWifiSmart(private val context: Context) : HciWifiInterface {
    @Suppress("DEPRECATION")
    @SuppressLint("MissingPermission", "HardwareIds")
    override fun macAddress(): String? {
        var macAddress: String? = null

        if (PermissionUtils.hasWifiStatePermission(context)) {
            val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager

            macAddress = wifiManager.connectionInfo.macAddress
        }

        return macAddress
    }
}
