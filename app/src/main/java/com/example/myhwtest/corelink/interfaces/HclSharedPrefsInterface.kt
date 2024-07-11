package com.example.myhwtest.corelink.interfaces

interface HclSharedPrefsInterface {
    fun getString(key: String, defaultValue: String): String
    fun getString(key: String): String?
    fun putString(key: String, value: String)
}
