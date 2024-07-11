package com.example.myhwtest.coreinterface

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Build
import com.example.myhwtest.coreinterface.interfaces.HciWifiInterface

open class HciWifiSmart(private val context: Context) : HciWifiInterface {
    private fun checkPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.checkSelfPermission(Manifest.permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED
        }

        return true
    }

    @Suppress("DEPRECATION")
    @SuppressLint("MissingPermission", "HardwareIds")
    override fun macAddress(): String? {
        var macAddress: String? = null

        if (checkPermission()) {
            val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager

            macAddress = wifiManager.connectionInfo.macAddress
        }

        return macAddress
    }
}
