package com.example.myhwtest.coreinterface.apidependent

import android.annotation.SuppressLint
import android.os.Build
import android.telephony.TelephonyManager

object MobileInfo {
    @Suppress("DEPRECATION")
    @SuppressLint("MissingPermission", "HardwareIds")
    fun imei(telephonyManager: TelephonyManager): String? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            telephonyManager.getImei(0)
        } else {
            telephonyManager.deviceId
        }
    }
}