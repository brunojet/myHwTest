package com.example.myhwtest.coreinterface

import android.annotation.SuppressLint
import android.content.Context
import android.telephony.TelephonyManager
import com.example.myhwtest.coreinterface.apidependent.MobileInfo
import com.example.myhwtest.coreinterface.apidependent.PermissionUtils
import com.example.myhwtest.coreinterface.implementations.HciMobileImpl

open class HciMobileSmart(private val context: Context) : HciMobileImpl() {
    @Suppress("DEPRECATION")
    @SuppressLint("MissingPermission", "HardwareIds")
    public override fun platformImei(): String? {
        var imei: String? = null

        if (PermissionUtils.hasMobileStatePermission(context)) {
            val telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            imei = MobileInfo.imei(telephonyManager);
        }

        return imei
    }
}
