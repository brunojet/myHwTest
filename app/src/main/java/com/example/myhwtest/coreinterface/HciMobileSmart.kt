package com.example.myhwtest.coreinterface

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.TelephonyManager
import com.example.myhwtest.coreinterface.implementations.HciMobileImpl

open class HciMobileSmart(private val context: Context) : HciMobileImpl() {
    private fun checkPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
        }

        return true
    }

    @Suppress("DEPRECATION")
    @SuppressLint("MissingPermission", "HardwareIds")
    public override fun platformImei(): String? {
        var imei: String? = null

        if (checkPermission()) {
            val telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            imei = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                telephonyManager.getImei(0)
            } else {
                telephonyManager.deviceId
            }
        }

        return imei
    }
}
