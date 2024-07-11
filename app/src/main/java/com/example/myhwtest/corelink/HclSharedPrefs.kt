package com.example.myhwtest.corelink

import com.example.myhwtest.corelink.interfaces.HclSharedPrefsInterface
import android.content.Context
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HclSharedPrefs(sharedName: String) : HclSharedPrefsInterface, KoinComponent {
    private val context: Context by inject()
    private val sharedPref by lazy {
        context.getSharedPreferences(context.packageName + sharedName, Context.MODE_PRIVATE)
    }

    override fun getString(key: String, defaultValue: String): String {
        return sharedPref.getString(key, defaultValue).toString()
    }

    override fun getString(key: String): String? {
        return sharedPref.getString(key, "unknown").toString()
    }

    override fun putString(key: String, value: String) {
        with(sharedPref.edit()) {
            putString(key, value)
            apply()
        }
    }
}
