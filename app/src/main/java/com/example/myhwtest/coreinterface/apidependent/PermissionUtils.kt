package com.example.myhwtest.coreinterface.apidependent

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

object PermissionUtils {
    fun hasWifiStatePermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.checkSelfPermission(Manifest.permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    fun hasMobileStatePermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }
}